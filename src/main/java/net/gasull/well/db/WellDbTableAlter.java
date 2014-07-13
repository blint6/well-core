package net.gasull.well.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Easy to use database alter request builder.
 */
public class WellDbTableAlter {

	/** The connection. */
	private final Connection connection;

	/** The table. */
	private final String table;

	/** The alters. */
	private final List<PreparedStatement> alters = new ArrayList<>();

	private static final Map<Class<?>, String> DATA_TYPES = new HashMap<>();

	static {
		DATA_TYPES.put(Integer.class, "INTEGER");
		DATA_TYPES.put(Byte.class, "TINYINT");
		DATA_TYPES.put(Short.class, "SMALLINT");
		DATA_TYPES.put(Long.class, "BIGINT");
		DATA_TYPES.put(Double.class, "DOUBLE");
		DATA_TYPES.put(Float.class, "FLOAT");
		DATA_TYPES.put(String.class, "VARCHAR");
		DATA_TYPES.put(Date.class, "DATETIME");
		DATA_TYPES.put(Calendar.class, "DATETIME");
		DATA_TYPES.put(Boolean.class, "BOOLEAN");
	}

	/**
	 * Instantiates a new well db table alter.
	 * 
	 * @param connection
	 *            the SQL connection
	 * @param table
	 *            the table
	 * @throws SQLException
	 *             the SQL exception
	 */
	public WellDbTableAlter(Connection connection, String table) throws SQLException {
		this.table = table;
		this.connection = connection;

		connection.setAutoCommit(false);
	}

	/**
	 * Adds the column.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param name
	 *            the name
	 * @param type
	 *            the type
	 * @param precision
	 *            the precision
	 * @param defaultValue
	 *            the default value
	 * @return this
	 * @throws SQLException
	 *             the SQL exception
	 */
	public <T> WellDbTableAlter addColumn(String name, Class<T> type, Integer precision, T defaultValue) throws SQLException {
		return addColumn(name, type, precision, defaultValue, true);
	}

	/**
	 * Adds the column.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param name
	 *            the name
	 * @param type
	 *            the type
	 * @param precision
	 *            the precision
	 * @param defaultValue
	 *            the default value
	 * @param nullable
	 *            the nullable
	 * @return this
	 * @throws SQLException
	 *             the SQL exception
	 */
	public <T> WellDbTableAlter addColumn(String name, Class<T> type, Integer precision, T defaultValue, boolean nullable) throws SQLException {
		String nullableString = nullable ? "" : "NOT NULL";
		String defaultString = defaultValue == null ? "" : "DEFAULT ?";
		addOperation(String.format("ADD COLUMN %s %s %s %s ;", name, getDbType(type, precision), defaultString, nullableString), defaultValue);
		return this;
	}

	/**
	 * Execute the alter.
	 * 
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void execute() throws SQLException {
		try {
			for (PreparedStatement s : alters) {
				s.execute();
			}
			connection.commit();
		} finally {
			connection.rollback();
		}
	}

	/**
	 * Builds an operation.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param operation
	 *            the operation
	 * @param defaultValue
	 *            the default value
	 * @throws SQLException
	 *             the SQL exception
	 */
	private <T> void addOperation(String operation, T defaultValue) throws SQLException {

		PreparedStatement statement = connection.prepareStatement(String.format("ALTER TABLE %s %s", table, operation));

		if (defaultValue != null) {
			statement.setObject(1, defaultValue);
		}

		alters.add(statement);
	}

	/**
	 * Gets the db type (column type) from a Java type.
	 * 
	 * @param type
	 *            the type
	 * @param precision
	 *            the precision
	 * @return the db type
	 */
	private String getDbType(Class<?> type, Integer precision) {
		String typeString;

		if (String.class.equals(type) && precision == null) {
			typeString = "TEXT";
		} else {
			typeString = DATA_TYPES.get(type);
		}

		if (typeString == null) {
			return getDbType(String.class, precision);
		}

		if (precision != null) {
			typeString = String.format("%s(%d)", typeString, precision);
		}

		return typeString;
	}
}
