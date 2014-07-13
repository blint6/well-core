package net.gasull.well.db;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import com.lennardf1989.bukkitex.MyDatabase;

/**
 * Well's database management.
 */
public class WellDatabase extends MyDatabase {

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
		ConfigurationSection conf = WellDatabaseSimple.getDbConf(null);

		super.initializeDatabase(conf.getString("driver"), conf.getString("url"), conf.getString("username"), conf.getString("password"),
				conf.getString("isolation"), conf.getBoolean("logging", false), rebuild);
		isInit = true;
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
