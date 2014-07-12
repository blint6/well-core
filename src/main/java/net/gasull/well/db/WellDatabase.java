package net.gasull.well.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.lennardf1989.bukkitex.MyDatabase;

/**
 * Well's database management.
 */
public abstract class WellDatabase extends MyDatabase {

	/** The plugin. */
	private JavaPlugin plugin;

	/** The is init. */
	private boolean isInit = false;

	/**
	 * Instantiates a new well database.
	 */
	public WellDatabase(JavaPlugin plugin) {
		super(plugin);
	}

	/**
	 * Initialize database.
	 * 
	 * @param rebuild
	 *            the rebuild
	 */
	public void initializeDatabase(boolean rebuild) {
		try {
			YamlConfiguration conf = new YamlConfiguration();
			conf.load(new File("bukkit.yml"));

			super.initializeDatabase(conf.getString("database.driver"), conf.getString("database.url"), conf.getString("database.username"),
					conf.getString("database.password"), conf.getString("database.isolation"), conf.getBoolean("database.logging", false), rebuild);
			isInit = true;
		} catch (FileNotFoundException e) {
			plugin.getLogger().log(Level.SEVERE, "Bukkit's configuration not found");
			throw new RuntimeException(e);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Error while fetching Bukkit's configuration");
			throw new RuntimeException(e);
		} catch (InvalidConfigurationException e) {
			plugin.getLogger().log(Level.SEVERE, "Bukkit's configuration is invalid");
			throw new RuntimeException(e);
		}
	}

	/**
	 * Initialize if not already.
	 * 
	 * @param rebuild
	 *            the rebuild
	 */
	public void initializeIfNotInit(boolean rebuild) {
		if (!isInit) {
			initializeDatabase(rebuild);
		}
	}

	/**
	 * Gets the plugin.
	 * 
	 * @return the plugin
	 */
	public JavaPlugin getPlugin() {
		return plugin;
	}
}
