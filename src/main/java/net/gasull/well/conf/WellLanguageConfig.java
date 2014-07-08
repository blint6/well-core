package net.gasull.well.conf;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Special config class for localization.
 */
public class WellLanguageConfig extends WellConfig {

	/** The fallback language. */
	private final WellLanguageConfig fallback;

	/** The Constant FALLBACK_LANG. */
	private static final String FALLBACK_LANG = "english";

	/**
	 * Instantiates a new well language config.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param language
	 *            the language
	 */
	public WellLanguageConfig(JavaPlugin plugin, String language) {
		super(plugin, String.format("lang/%s.yml", language), true);

		if (FALLBACK_LANG.equals(language)) {
			this.fallback = null;
		} else {
			this.fallback = new WellLanguageConfig(plugin, FALLBACK_LANG);
		}
	}

	/**
	 * Gets a localized string.
	 * 
	 * @param key
	 *            the key
	 * @return the string
	 */
	public String get(String key) {
		String string = getString(key);
		if (fallback != null && string == null) {
			string = fallback.getString(key);
		}
		return string;
	}

	/**
	 * Gets a localized string list.
	 * 
	 * @param key
	 *            the key
	 * @return the string list
	 */
	public List<String> getList(String key) {
		List<String> list = getStringList(key);
		if (fallback != null && list == null) {
			list = fallback.getStringList(key);
		}
		return list;
	}

	/**
	 * Success.
	 * 
	 * @param key
	 *            the key
	 * @return the string
	 */
	public String success(String key) {
		return ChatColor.GREEN + get(key);
	}

	/**
	 * Warning.
	 * 
	 * @param key
	 *            the key
	 * @return the string
	 */
	public String warn(String key) {
		return ChatColor.GOLD + get(key);
	}

	/**
	 * Error.
	 * 
	 * @param key
	 *            the key
	 * @return the string
	 */
	public String error(String key) {
		return ChatColor.DARK_RED + get(key);
	}
}
