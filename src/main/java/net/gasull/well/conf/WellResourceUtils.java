package net.gasull.well.conf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Well Resource Utils.
 */
public class WellResourceUtils {

	private WellResourceUtils() {
	}

	/**
	 * Gets the conf from a plugin's resource.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param resource
	 *            the resource
	 * @return the conf
	 */
	public static WellConfig getConf(JavaPlugin plugin, String resource) {

		File dataFolderPath = new File(plugin.getDataFolder(), resource);
		File tmpResource = null;

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(plugin.getResource(resource), StandardCharsets.UTF_8))) {

			// Copy the resource to a temporary path for the check
			tmpResource = File.createTempFile(dataFolderPath.getName(), null, plugin.getDataFolder());
			try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(tmpResource), WellConfig.CHARSET)) {

				String line = reader.readLine();
				while (line != null) {
					writer.write(line);
					writer.write('\n');
					line = reader.readLine();
				}
			}

			return new WellConfig(plugin, tmpResource);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			// Always remove tmp file if exists
			if (tmpResource != null && tmpResource.isFile()) {
				tmpResource.delete();
			}
		}
	}
}
