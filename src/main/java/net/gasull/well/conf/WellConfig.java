package net.gasull.well.conf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

	/** The file to which the conf refer to. */
	private File file;

	/** The Constant CHARSET. */
	public static final Charset CHARSET = Charset.defaultCharset();

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
		this.file = new File(plugin.getDataFolder(), filePath);

		if (checkDefault) {
			checkDefaultConf(filePath);
		} else {
			loadConf();
		}
	}

	/**
	 * Instantiates a new well config.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param conf
	 *            the conf
	 */
	public WellConfig(JavaPlugin plugin, File conf) {
		this.plugin = plugin;
		this.file = conf;
		loadConf();
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
			conf.save(file);
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
	 * Gets the file.
	 * 
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Sets the file.
	 * 
	 * @param file
	 *            the new file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Check if the conf is valid according to the plugin's reference resource.
	 * 
	 * @param filePath
	 *            the file path
	 */
	private void checkDefaultConf(String filePath) {
		// If user already has this file, check that required fields are set
		if (file.isFile()) {

			File tmpConf = null;

			try {
				FileConfiguration defConf = WellResourceUtils.getConf(plugin, filePath).getConfig();

				// Load user's conf
				this.conf = new YamlConfiguration();
				try {
					this.conf.load(file);
				} catch (IOException e) {
					plugin.getLogger().log(Level.SEVERE, "{} config is not a valid YAML file, please check before reloading plugin", new Object[] { filePath });
					throw e;
				}

				boolean confChanged = false;

				// Fill missing values from reference config
				for (Entry<String, Object> pair : defConf.getValues(true).entrySet()) {
					if (!this.conf.contains(pair.getKey())) {
						confChanged = true;
						this.conf.set(pair.getKey(), pair.getValue());
					}
				}

				if (confChanged) {
					save();
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
			try (InputStream stream = plugin.getResource(filePath)) {
				if (stream == null) {
					throw new RuntimeException(String.format("No config resource found at %s", filePath));
				}

				// Create directory if doesn't exist
				file.getAbsoluteFile().getParentFile().mkdirs();

				try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), CHARSET))) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

					String line = reader.readLine();
					while (line != null) {
						writer.write(line);
						writer.write('\n');
						line = reader.readLine();
					}
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			loadConf();
		}
	}

	/**
	 * Simply load conf, if exists.
	 */
	private void loadConf() {
		this.conf = new YamlConfiguration();
		try {
			conf.load(file);
		} catch (FileNotFoundException e) {
			// Do nothing, we'll create a new conf
		} catch (IOException | InvalidConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
}
