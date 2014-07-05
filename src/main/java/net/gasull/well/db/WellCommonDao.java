package net.gasull.well.db;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.avaje.ebean.EbeanServer;

import net.gasull.well.WellCore;
import net.gasull.well.db.model.WellVersionModel;

/**
 * Common DAO for well plugins.
 */
public class WellCommonDao extends WellDao {

	/** The actual database object. */
	private EbeanServer db;

	/**
	 * Instantiates a new well common dao.
	 * 
	 * @param plugin
	 *            the plugin
	 */
	public WellCommonDao(WellCore plugin) {
		WellDatabase wellDatabase = new WellDatabase(plugin) {
			protected java.util.List<Class<?>> getDatabaseClasses() {
				List<Class<?>> models = new ArrayList<>();
				models.add(WellVersionModel.class);
				return models;
			};
		};

		try {
			wellDatabase.initializeDatabase(false);
		} catch (RuntimeException e) {
			plugin.getLogger().log(Level.INFO, "Couldn't initialize core database, assuming it's the first run, trying to rebuild");
			wellDatabase.initializeDatabase(true);
		}

		db = wellDatabase.getDatabase();
	}

	/**
	 * Gets the lastest version of a well plugin.
	 * 
	 * @param pluginName
	 *            the plugin name
	 * @return the last version
	 */
	public String getLastVersion(String pluginName) {
		WellVersionModel model = db.find(WellVersionModel.class).where().eq("plugin", pluginName).findUnique();
		return model == null ? null : model.getVersion();
	}

	/**
	 * Update version for a give plugin (by name).
	 * 
	 * @param pluginName
	 *            the plugin name
	 * @param version
	 *            the version
	 */
	public void updateVersion(String pluginName, String version) {
		WellVersionModel model = db.find(WellVersionModel.class).where().eq("plugin", pluginName).findUnique();

		if (model == null) {
			model = new WellVersionModel();
			model.setPlugin(pluginName);
		}

		model.setVersion(version);
		save(model);
	}

	@Override
	protected EbeanServer getDb() {
		return db;
	}
}
