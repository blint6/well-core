package net.gasull.well.version;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import net.gasull.well.WellCore;
import net.gasull.well.conf.WellConfig;
import net.gasull.well.db.WellDatabase;
import net.gasull.well.db.model.WellVersionModel;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Well Versioning manager.
 */
public class WellVersioning {

	/** The plugin versions. */
	private final WellConfig pluginVersions;

	/**
	 * Instantiates a new well versioning.
	 * 
	 * @param plugin
	 *            the plugin
	 */
	public WellVersioning(JavaPlugin plugin) {
		this.pluginVersions = new WellConfig(plugin, "plugin-versions.yml");

		WellDatabase wellDatabase = new WellDatabase(plugin) {
			protected java.util.List<Class<?>> getDatabaseClasses() {
				List<Class<?>> models = new ArrayList<>();
				models.add(WellVersionModel.class);
				return models;
			};
		};

		// Upgrade from 0.4: no more conf in DB
		if (!pluginVersions.getFile().isFile()) {
			try {
				wellDatabase.initializeDatabase(false);

				// Save in conf last version
				List<WellVersionModel> models = wellDatabase.getDatabase().find(WellVersionModel.class).findList();
				for (WellVersionModel model : models) {
					pluginVersions.getConfig().set(model.getPlugin(), model.getVersion());
				}

				pluginVersions.save();
				plugin.getLogger().log(Level.WARNING,
						"Since 0.4, well-core doesn't use well-version anymore but YAML file instead. Please drop well-version which is obsolete now.");
			} catch (RuntimeException e) {
				// DB couldn't be initialized: we assume it didn't exist anyway!
			}
		}
	}

	/**
	 * Check version.
	 * 
	 * @param versionable
	 *            the versionable
	 */
	public void checkVersion(WellVersionable versionable) {
		YamlConfiguration pluginConfig = new YamlConfiguration();

		try (Reader reader = new InputStreamReader(versionable.getPlugin().getResource("plugin.yml"))) {
			pluginConfig.load(reader);
		} catch (IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}

		String pluginName = pluginConfig.getString("name");
		String pluginVersion = pluginConfig.getString("version");
		String lastVersion = pluginVersions.getString(pluginName);

		if (lastVersion != null && lastVersion.equals(pluginVersion)) {
			return;
		}

		Iterator<WellUpgrade> upgrades = versionable.getVersionChanges().iterator();

		while (upgrades.hasNext()) {
			WellUpgrade upgrade = upgrades.next();

			if (upgrade.isNewer(lastVersion)) {
				upgrade.handleUpgrade();
				lastVersion = upgrade.getVersion();

				if (lastVersion == null) {
					pluginVersions.getConfig().set(pluginName, pluginVersion);
					pluginVersions.save();
					return;
				} else {
					pluginVersions.getConfig().set(pluginName, lastVersion);
					pluginVersions.save();
					WellCore.logger().log(Level.INFO, String.format("Successfully upgraded %s to version %s", pluginName, lastVersion));
				}
			}
		}
	}

}
