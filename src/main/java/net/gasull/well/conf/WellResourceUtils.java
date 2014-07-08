package net.gasull.well.conf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

		try (BufferedInputStream reader = new BufferedInputStream(plugin.getResource(resource))) {

			// Copy the resource to a temporary path for the check
			tmpResource = File.createTempFile(dataFolderPath.getName(), null, plugin.getDataFolder());
			try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(tmpResource))) {
				byte[] buf = new byte[2048];

				int nRead = reader.read(buf);
				while (nRead > 0) {
					writer.write(buf, 0, nRead);
					nRead = reader.read(buf);
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
