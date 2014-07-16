package modmuss50.network.app;

import java.util.List;

public class App {

	private String		name, version;
	private String[]	authors;

	public App() {
		this("Unnamed Game", "Unknown Version", new String[] { "Unknown Author" });
	}

	public App(String name) {
		this(name, "Unknown Version", new String[] { "Unknown Author" });
	}

	public App(String name, String[] authors) {
		this(name, "Unknown Version", authors);
	}

	public App(String name, String version, String[] authors) {
		this.name = name;
		this.version = version;
		this.authors = authors;
	}

	/**
	 * @return The specified name of the app
	 */
	public String getAppName() {
		return name;
	}

	/**
	 * @return The specified build number / version of this app
	 */
	public String getAppVersion() {
		return version;
	}

	/**
	 * @return A list containing the specified authors for this app
	 */
	public String[] getAppAuthors() {
		return authors;
	}

	/**
	 * Called when the app is opened
	 */
	public void start() {

	}

	/**
	 * Called every time the game is ticked
	 */
	public void update() {

	}

	/**
	 * @param defaultInfo
	 *            The default information that is stored such as app name, app
	 *            version and a list of app authors
	 * @return The list, modified or not, so that the app information panel can
	 *         render the information correctly
	 */
	public List<String> getAppInfo(List<String> defaultInfo) {
		return defaultInfo;
	}
}
