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

	@Override
	protected EbeanServer getDb() {
		return db;
	}
}
