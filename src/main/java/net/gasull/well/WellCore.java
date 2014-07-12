package net.gasull.well;

import java.util.logging.Logger;

import net.gasull.well.conf.WellConfig;
import net.gasull.well.conf.WellLanguageConfig;
import net.gasull.well.conf.WellPermissionManager;
import net.gasull.well.version.WellVersionable;
import net.gasull.well.version.WellVersioning;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The core plugin of the Well Bukkit plugin series.
 */
public class WellCore extends JavaPlugin {

	/** The instance. */
	private static WellCore instance;

	/** The well core's config. */
	private static WellConfig config;

	/** The core localization. */
	private static WellLanguageConfig lang;

	/** The permission manager. */
	private static WellPermissionManager permission;

	/** The versions manager. */
	private static WellVersioning versioning;

	@Override
	public void onLoad() {
		instance = this;
		config = new WellConfig(this, "well-core.yml", true);
		lang = new WellLanguageConfig(this, config.getString("language"));
		permission = new WellPermissionManager(instance, lang);
		versioning = new WellVersioning(this);
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
	 * Lang.
	 * 
	 * @return the well language config
	 */
	public static WellLanguageConfig lang() {
		return lang;
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
		return instance().getLogger();
	}

	/**
	 * Gets the single instance of WellCore.
	 * 
	 * @return single instance of WellCore
	 */
	public static WellCore instance() {
		return instance;
	}

	/**
	 * Checks the version of a versionable.
	 * 
	 * @param versionable
	 *            the versionable
	 */
	public static void checkVersion(WellVersionable versionable) {
		versioning.checkVersion(versionable);
	}
}
