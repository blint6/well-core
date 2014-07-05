package net.gasull.well.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Well's version management Model.
 */
@Entity
@Table(name = "well_version")
public class WellVersionModel {

	/** The plugin name. */
	@Id
	@Column(length = 25)
	private String plugin;

	/** The last version. */
	@Column(length = 20)
	private String version;

	/**
	 * Gets the plugin.
	 * 
	 * @return the plugin
	 */
	public String getPlugin() {
		return plugin;
	}

	/**
	 * Sets the plugin.
	 * 
	 * @param plugin
	 *            the new plugin
	 */
	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 * 
	 * @param version
	 *            the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
}
