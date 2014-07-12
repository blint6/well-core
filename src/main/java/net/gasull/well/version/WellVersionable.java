package net.gasull.well.version;

import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * A Well versionable.
 */
public interface WellVersionable {

	/**
	 * To be implemented to handle version changes. Caution: the list must be
	 * ordered in the incramental versions order.
	 * 
	 * @return the list of upgrades
	 */
	List<WellUpgrade> getVersionChanges();

	/**
	 * Gets the versioned plugin.
	 * 
	 * @return the plugin
	 */
	JavaPlugin getPlugin();
}
