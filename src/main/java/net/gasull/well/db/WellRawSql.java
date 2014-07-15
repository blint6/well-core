package net.gasull.well.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;

/**
 * Simple {@link RawSql} helper.
 */
public class WellRawSql {

	/** The columns. */
	private Map<String, String> columns = new HashMap<>();

	/**
	 * Maps a column.
	 * 
	 * @param tableAlias
	 *            the table alias
	 * @param column
	 *            the column
	 * @param mapping
	 *            the mapping
	 * @return this
	 */
	public WellRawSql mapColumn(String tableAlias, String column, String mapping) {

		columns.put(String.format("%s.%s", tableAlias, column), mapping);
		return this;
	}

	/**
	 * Maps a join.
	 * 
	 * @param attr
	 *            the attribute name of the parent Entity to join on.
	 * @param joinRaw
	 *            the join raw
	 * @return the well raw sql
	 */
	public WellRawSql mapJoin(String attr, WellRawSql joinRaw) {

		for (Entry<String, String> en : joinRaw.columns.entrySet()) {
			columns.put(en.getKey(), String.format("%s.%s", attr, en.getValue()));
		}

		return this;
	}

	/**
	 * Creates the actual {@link RawSql}.
	 * 
	 * @param sql
	 *            the rest of sql
	 * @return the raw sql
	 */
	public RawSql create(String sql) {
		RawSqlBuilder raw = RawSqlBuilder.parse("select " + StringUtils.join(columns.keySet(), ", ") + " " + sql);

		for (Entry<String, String> en : columns.entrySet()) {
			raw.columnMapping(en.getKey(), en.getValue());
		}

		return raw.create();
	}
}
