package net.gasull.well;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Color;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Well Suite {@link Configuration} wrapper which sets default values if not in
 * config yet.
 */
public class WellConfig {

	/** The plugin. */
	private final JavaPlugin plugin;

	/** The conf. */
	private FileConfiguration conf;

	/** The file's relative path. */
	private final String filePath;

	/** The Constant charset. */
	private static final Charset CHARSET = Charset.forName("UTF-8");

	/**
	 * Instantiates a new well config.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param filePath
	 *            the file's relative path
	 * @param checkDefault
	 *            if we need to fill in missing values from default conf
	 */
	public WellConfig(JavaPlugin plugin, String filePath, boolean checkDefault) {
		this.plugin = plugin;
		this.filePath = filePath;

		if (checkDefault) {
			checkDefaultConf();
		} else {
			newEmptyConf();
		}
	}

	/**
	 * Instantiates a new well config.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param filePath
	 *            the file's relative path
	 */
	public WellConfig(JavaPlugin plugin, String filePath) {
		this(plugin, filePath, false);
	}

	/**
	 * Gets the config.
	 * 
	 * @return the config
	 */
	public FileConfiguration getConfig() {
		return conf;
	}

	/**
	 * Saves the config.
	 */
	public void save() {
		try {
			conf.save(new File(plugin.getDataFolder(), filePath));
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Couldn't save config file well-auction.yml", e);
		}
	}

	/**
	 * Gets the boolean.
	 * 
	 * @param key
	 *            the key
	 * @return the boolean
	 */
	public boolean getBoolean(final String key) {
		return conf.getBoolean(key);
	}

	/**
	 * Gets the color.
	 * 
	 * @param key
	 *            the key
	 * @return the color
	 */
	public Color getColor(final String key) {
		return conf.getColor(key);
	}

	/**
	 * Gets the double.
	 * 
	 * @param key
	 *            the key
	 * @return the double
	 */
	public double getDouble(final String key) {
		return conf.getDouble(key);
	}

	/**
	 * Gets the int.
	 * 
	 * @param key
	 *            the key
	 * @return the int
	 */
	public int getInt(final String key) {
		return conf.getInt(key);
	}

	/**
	 * Gets the item stack.
	 * 
	 * @param key
	 *            the key
	 * @return the item stack
	 */
	public ItemStack getItemStack(final String key) {
		return conf.getItemStack(key);
	}

	/**
	 * Gets the long.
	 * 
	 * @param key
	 *            the key
	 * @return the long
	 */
	public long getLong(final String key) {
		return conf.getLong(key);
	}

	/**
	 * Gets the string.
	 * 
	 * @param key
	 *            the key
	 * @return the string
	 */
	public String getString(final String key) {
		return conf.getString(key);
	}

	/**
	 * Gets the boolean list.
	 * 
	 * @param key
	 *            the key
	 * @return the boolean list
	 */
	public List<Boolean> getBooleanList(final String key) {
		return conf.getBooleanList(key);
	}

	/**
	 * Gets the character list.
	 * 
	 * @param key
	 *            the key
	 * @return the character list
	 */
	public List<Character> getCharacterList(final String key) {
		return conf.getCharacterList(key);
	}

	/**
	 * Gets the double list.
	 * 
	 * @param key
	 *            the key
	 * @return the double list
	 */
	public List<Double> getDoubleList(final String key) {
		return conf.getDoubleList(key);
	}

	/**
	 * Gets the integer list.
	 * 
	 * @param key
	 *            the key
	 * @return the integer list
	 */
	public List<Integer> getIntegerList(final String key) {
		return conf.getIntegerList(key);
	}

	/**
	 * Gets the long list.
	 * 
	 * @param key
	 *            the key
	 * @return the long list
	 */
	public List<Long> getLongList(final String key) {
		return conf.getLongList(key);
	}

	/**
	 * Gets the short list.
	 * 
	 * @param key
	 *            the key
	 * @return the short list
	 */
	public List<Short> getShortList(final String key) {
		return conf.getShortList(key);
	}

	/**
	 * Gets the map list.
	 * 
	 * @param key
	 *            the key
	 * @return the map list
	 */
	public List<Map<?, ?>> getMapList(final String key) {
		return conf.getMapList(key);
	}

	/**
	 * Gets the string list.
	 * 
	 * @param key
	 *            the key
	 * @return the string list
	 */
	public List<String> getStringList(final String key) {
		return conf.getStringList(key);
	}

	/**
	 * Check if the conf is valid according to the plugin's reference resource.
	 */
	private void checkDefaultConf() {
		File usersFile = new File(plugin.getDataFolder(), filePath);

		// If user already has this file, check that required fields are set
		if (usersFile.isFile()) {

			File tmpConf = null;

			try (Reader reader = new InputStreamReader(plugin.getResource(filePath), CHARSET)) {

				// Load user's conf
				this.conf = new YamlConfiguration();
				try {
					this.conf.load(usersFile);
				} catch (IOException e) {
					plugin.getLogger().log(Level.SEVERE, "{} config is not a valid YAML file, please check before reloading plugin", new Object[] { filePath });
					throw e;
				}

				// Copy the resource to a temporary path for the check
				tmpConf = File.createTempFile(usersFile.getName(), null, plugin.getDataFolder());
				try (Writer writer = new OutputStreamWriter(new FileOutputStream(tmpConf), CHARSET)) {
					char[] buf = new char[12];

					int nRead = reader.read(buf);
					while (nRead > 0) {
						writer.write(buf, 0, nRead);
						nRead = reader.read(buf);
					}
				}

				YamlConfiguration defConf = new YamlConfiguration();
				defConf.load(tmpConf);
				boolean confChanged = false;

				// Fill missing values from reference config
				for (Entry<String, Object> pair : defConf.getValues(true).entrySet()) {
					if (!this.conf.contains(pair.getKey())) {
						confChanged = true;
						this.conf.set(pair.getKey(), pair.getValue());
					}
				}

				if (confChanged) {
					this.conf.save(usersFile);
				}
			} catch (IOException | InvalidConfigurationException e) {
				throw new RuntimeException(e);
			} finally {
				// Always remove tmp file if exists
				if (tmpConf != null && tmpConf.isFile()) {
					tmpConf.delete();
				}
			}
		} else {
			// Check if resource exists
			try (InputStream reader = plugin.getResource(filePath)) {
				if (reader == null) {
					throw new RuntimeException(String.format("No config resource found at %s", filePath));
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			plugin.saveResource(filePath, false);
			newEmptyConf();
		}
	}

	/**
	 * New empty conf from current data.
	 */
	private void newEmptyConf() {
		this.conf = new YamlConfiguration();
		try {
			conf.load(new File(plugin.getDataFolder(), filePath));
		} catch (FileNotFoundException e) {
			// Do nothing, we'll create a new conf
		} catch (IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
}
