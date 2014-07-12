package net.gasull.well.version;

/**
 * A version upgrade of a well plugin.
 */
public abstract class WellUpgrade {

	/** The version. */
	private final String version;

	/**
	 * Instantiates a new well upgrade.
	 * 
	 * @param version
	 *            the version
	 */
	public WellUpgrade(String version) {
		this.version = version;
	}

	/**
	 * Checks if is newer.
	 * 
	 * @param cmpVersion
	 *            the version
	 * @return true, if is newer
	 */
	public boolean isNewer(String cmpVersion) {

		// null is the "zero" version
		if (version == null) {
			// The upgrade is necessary only if there is no previous version
			return cmpVersion == null;
		}

		String[] versionSplit = cmpVersion.split("\\.");
		String[] thisSplit = version.split("\\.");

		try {
			for (int i = 0; i < versionSplit.length && i < thisSplit.length; i++) {
				if (Integer.valueOf(thisSplit[i]) > Integer.valueOf(versionSplit[i])) {
					return true;
				}
			}
		} catch (NumberFormatException e) {
			return version.compareTo(cmpVersion) > 0 ? true : false;
		}

		return false;
	}

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Handles the actual upgrade.
	 */
	public abstract void handleUpgrade();
}
