package net.gasull.well.conf;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Class WellPermissionManager.
 */
public class WellPermissionManager {

	/** The plugin. */
	private final JavaPlugin plugin;

	/** The config. */
	private final WellLanguageConfig lang;

	/**
	 * Instantiates a new well permission manager.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param lang
	 *            the language config
	 */
	public WellPermissionManager(JavaPlugin plugin, WellLanguageConfig lang) {
		this.plugin = plugin;
		this.lang = lang;
	}

	/**
	 * Determines if a player can do something.
	 * 
	 * @param player
	 *            the player
	 * @param thing
	 *            an expression describing the thing to do
	 * @param permission
	 *            the permission key
	 * @throws WellPermissionException
	 *             the well permission exception
	 */
	public void can(Player player, String thing, String permission) throws WellPermissionException {
		if (!player.hasPermission(permission)) {
			player.sendMessage(lang.error("permission.notAllowed").replace("%thing%", thing));
			throw new WellPermissionException(permission);
		}
	}

	/**
	 * Happens when a permission isn't matched.
	 */
	public class WellPermissionException extends Exception {

		/** The key. */
		private final String key;

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new well permission exception.
		 * 
		 * @param key
		 *            the key
		 */
		public WellPermissionException(String key) {
			this.key = key;
		}

		/**
		 * Gets the key.
		 * 
		 * @return the key
		 */
		public String getKey() {
			return key;
		}

	}
}
