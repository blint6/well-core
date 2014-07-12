package net.gasull.well.db;

import java.util.Collection;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Transaction;

/**
 * Common DAO for Well apps.
 */
public abstract class WellDao {

	/**
	 * Saves a model.
	 * 
	 * @param model
	 *            the model
	 */
	public void save(Object model) {
		getDb().save(model);
	}

	/**
	 * Saves a collection of models.
	 * 
	 * @param models
	 *            the models
	 */
	public void save(Collection<?> models) {
		getDb().save(models);
	}

	/**
	 * Delete.
	 * 
	 * @param model
	 *            the model
	 */
	public void delete(Object model) {
		refresh(model);
		getDb().delete(model);
	}

	/**
	 * Refresh.
	 * 
	 * @param model
	 *            the model
	 */
	public void refresh(Object model) {
		getDb().refresh(model);
	}

	/**
	 * Begin a new transaction.
	 * 
	 * @return the transaction
	 */
	public Transaction transaction() {
		return getDb().beginTransaction();
	}

	/**
	 * Let DAOs access to the DB.
	 * 
	 * @return the ebean server
	 */
	abstract protected EbeanServer getDb();
}