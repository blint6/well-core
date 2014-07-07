package net.gasull.well.db;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;

import net.gasull.well.WellCore;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Transaction;

/**
 * Common DAO for Well apps.
 */
public abstract class WellDao {

	protected void checkVersion(JavaPlugin plugin) {
		YamlConfiguration pluginConfig = new YamlConfiguration();

		try (Reader reader = new InputStreamReader(plugin.getResource("plugin.yml"))) {
			pluginConfig.load(reader);
		} catch (IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}

		String pluginName = pluginConfig.getString("name");
		String pluginVersion = pluginConfig.getString("version");
		String lastVersion = WellCore.db().getLastVersion(pluginName);

		handleVersionChanges(lastVersion, pluginVersion);

		if (lastVersion == null || !lastVersion.equals(pluginVersion)) {
			WellCore.db().updateVersion(pluginName, pluginVersion);
		}
	}

	/**
	 * To be implemented to handle version changes. When subclass calls
	 * {@link #checkVersion(JavaPlugin)}, this method will receive associated
	 * arguments.
	 * 
	 * @param oldVersion
	 *            the old version of the plugin
	 * @param newVersion
	 *            the new version of the plugin
	 */
	protected void handleVersionChanges(String oldVersion, String newVersion) {
		// Do nothing by default. Otherwise, should be implemented.
	}

	/**
	 * Saves a model.
	 * 
	 * @param model
	 *            the model
	 */
	public void save(Object model) {
		getDb().save(model);
	}

	/**
	 * Saves a collection of models.
	 * 
	 * @param models
	 *            the models
	 */
	public void save(Collection<?> models) {
		getDb().save(models);
	}

	/**
	 * Delete.
	 * 
	 * @param model
	 *            the model
	 */
	public void delete(Object model) {
		refresh(model);
		getDb().delete(model);
	}

	/**
	 * Refresh.
	 * 
	 * @param model
	 *            the model
	 */
	public void refresh(Object model) {
		getDb().refresh(model);
	}

	/**
	 * Begin a new transaction.
	 * 
	 * @return the transaction
	 */
	public Transaction transaction() {
		return getDb().beginTransaction();
	}

	/**
	 * Let DAOs access to the DB.
	 * 
	 * @return the ebean server
	 */
	abstract protected EbeanServer getDb();
}