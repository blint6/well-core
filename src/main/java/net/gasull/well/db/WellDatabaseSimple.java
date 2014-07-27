package net.gasull.well.db;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Instantiates a simple DB connection (for specific requests)
 */
public class WellDatabaseSimple implements Closeable {

	/** The Constant CONF_PATH. */
	private static final String CONF_PATH = "database";

	/** The db conf. */
	private final ConfigurationSection dbConf;

	/** The connection. */
	private final Connection connection;

	/**
	 * Instantiates a new well database sql.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param conf
	 *            the conf to allow your user to specify a specific database at.
	 * @throws SQLException
	 *             the SQL exception
	 */
	public WellDatabaseSimple(JavaPlugin plugin, FileConfiguration conf) throws SQLException {
		this.dbConf = getDbConf(conf);
		String url = replaceDatabaseURL(plugin, dbConf.getString("url"));
		String fullUrl;

		if (url.startsWith("jdbc:sqlite")) {
			DriverManager.registerDriver(new org.sqlite.JDBC());
			fullUrl = url;
		} else {
			fullUrl = String.format("%s?user=%s&password=%s", url, dbConf.getString("username"), dbConf.getString("password"));
		}

		this.connection = DriverManager.getConnection(fullUrl);
	}

	/**
	 * Gets the connection.
	 * 
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	@Override
	public void close() throws IOException {
		try {
			this.connection.close();
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	/**
	 * Replace database url.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param input
	 *            the input
	 * @return the string
	 */
	public static String replaceDatabaseURL(JavaPlugin plugin, String input) {
		input = input.replaceAll("\\{DIR\\}", plugin.getDataFolder().getPath().replaceAll("\\\\", "/") + "/");
		input = input.replaceAll("\\{NAME\\}", plugin.getDescription().getName().replaceAll("[^\\w_-]", ""));

		return input;
	}

	/**
	 * Gets the db conf from "database" node of parameter conf file. This method
	 * fallbacks in bukkit.yml if no database is specified.
	 * 
	 * @param conf
	 *            the conf
	 * @return the db conf
	 */
	public static ConfigurationSection getDbConf(FileConfiguration conf) {

		if (conf != null && conf.contains(CONF_PATH)) {
			return conf.getConfigurationSection(CONF_PATH);
		}

		FileConfiguration defaultConf = new YamlConfiguration();
		try {
			defaultConf.load(new File("bukkit.yml"));

			if (!defaultConf.contains(CONF_PATH)) {
				throw new RuntimeException("No database definition found in given conf or in bukkit.yml");
			}

			return defaultConf.getConfigurationSection(CONF_PATH);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Bukkit's configuration not found", e);
		} catch (IOException e) {
			throw new RuntimeException("Error while fetching Bukkit's configuration", e);
		} catch (InvalidConfigurationException e) {
			throw new RuntimeException("Bukkit's configuration is invalid", e);
		}
	}
}
