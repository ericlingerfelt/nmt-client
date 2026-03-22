package org.nuclearmasses.gui.util;

import java.lang.reflect.Method;

/**
 * This class attempts to open a web browser to display a URL. 
 * Use the Java Desktop API if Java 1.6 is available.
 */
public class BrowserLaunch {

	/**
	 * Opens a web browser with the url.
	 * 
	 * @param	url		the url
	 */
	public static void openURL(String url) {

		String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Mac OS")) {
				Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
				Method openURL = fileMgr.getDeclaredMethod("openURL",
						new Class[] {String.class});
				openURL.invoke(null, new Object[] {url});
			}
			else if (osName.startsWith("Windows")){
				if(url.startsWith("http")){
					Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
				}else{
					Runtime.getRuntime().exec("cmd /c " + url);
				}
			}else { //assume Unix or Linux
				String[] browsers = {
						"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++)
					if (Runtime.getRuntime().exec(
							new String[] {"which", browsers[count]}).waitFor() == 0)
						browser = browsers[count];
				if (browser == null){
					throw new Exception("Could not find web browser");
				}
				Runtime.getRuntime().exec(new String[] {browser, url});
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}

