package net.gasull.well;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The core plugin of the Well Bukkit plugin series.
 */
public class WellCore extends JavaPlugin {

	/** The instance. */
	private static WellCore instance;

	/** The well core's config. */
	private static WellConfig config;

	/** The permission manager. */
	private static WellPermissionManager permission;

	@Override
	public void onLoad() {
		instance = this;
		config = new WellConfig(this, "well-core.yml");
		permission = new WellPermissionManager(this, config);
	}

	/**
	 * Conf.
	 * 
	 * @return the well config
	 */
	public static WellConfig conf() {
		return config;
	}

	/**
	 * Permission.
	 * 
	 * @return the well permission manager
	 */
	public static WellPermissionManager permission() {
		return permission;
	}

	/**
	 * Logger.
	 * 
	 * @return the logger
	 */
	public static Logger logger() {
		return getInstance().getLogger();
	}

	/**
	 * Gets the single instance of WellCore.
	 * 
	 * @return single instance of WellCore
	 */
	public static WellCore getInstance() {
		return instance;
	}
}
