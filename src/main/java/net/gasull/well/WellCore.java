package net.gasull.well;

import java.util.logging.Logger;

import net.gasull.well.db.WellCommonDao;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The core plugin of the Well Bukkit plugin series.
 */
public class WellCore extends JavaPlugin {

	/** The instance. */
	private static WellCore instance;

	/** The well core's config. */
	private static WellConfig config;

	/** The db. */
	private WellCommonDao db;

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
	 * The common DAO fro well plugins. Dynamically inits, because this can't
	 * happen in {@link #onLoad()} but must happen before any other DAO inits.
	 * 
	 * @return the common DAO
	 */
	public static WellCommonDao db() {
		if (getInstance().db == null) {
			getInstance().db = new WellCommonDao(getInstance());
		}
		return getInstance().db;
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
