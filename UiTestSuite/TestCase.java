package UiTest.UiTestSuite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;

import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.*;

/**
 * Sexy Result
 * 
 * NOTE:1.This class can contain only one test case. 2.Do not change the class
 * name.
 */
public class TestCase extends UiAutomatorTestCase {

	private static String TAG = "TestCase";
	
	private String propertiesPath = "/UiTest/properties.txt";
	private String[] args = new String[2];
	private String appName, tabApp = "";
	private String contentDefaultFile = "appName = SexyResults\ntabApp = 2";

	/**
	 * @generated All your test code will go here NOTE: Do not change the method
	 *            signature
	 */
	public void test() throws UiObjectNotFoundException {
		readProperties();
		appName = args[0];
		tabApp = args[1];
		System.out.println("arg 0: " + args[0] + " fin");
		System.out.println("arg 1: " + args[1] + " fin");
		// takeScreenShot();
		getUiDevice().pressHome();

		UiObject allAppsButton = new UiObject(
				new UiSelector().description("Apps"));

		allAppsButton.clickAndWaitForNewWindow();

		UiObject appsTab = new UiObject(new UiSelector().text("Apps"));

		appsTab.click();

		UiSelector selector = new UiSelector().scrollable(true);
		UiScrollable appViews = new UiScrollable(selector);
		UiObject settingsApp = appViews.getChildByText(new UiSelector().className(android.widget.TextView.class.getName()), appName);
		settingsApp.clickAndWaitForNewWindow();
		
		UiDevice device = getUiDevice();

		System.out.print("------------"+device.getCurrentActivityName());
		// Validate that the package name is the expected one
//		UiObject settingsValidation = new UiObject(
//				new UiSelector().packageName("com.android.settings"));
		
//		assertTrue("Unable to detect Settings", settingsValidation.exists());
//		// CODE:END
	}

	public String readProperties() {
		// Find the directory for the SD Card using the API
		// *Don't* hardcode "/sdcard"
		File sdcard = Environment.getExternalStorageDirectory();

		// Get the text file
		File file = new File(sdcard, propertiesPath);
		if (!file.exists()) {
			// Creating new directory in Java, if it doesn't exists
			File directory = new File(sdcard + "/UiTest");
			if (directory.exists()) {
				System.out.println("Directory already exists ...");
			} else {
				System.out.println("Directory not exists, creating now");
				if (directory.mkdir()) {
					System.out.printf(
							"Successfully created new directory : %s%n", sdcard
									+ "/UiTest");
				} else {
					System.out.printf("Failed to create new directory: %s%n",
							sdcard + "/UiTest");
				}
			}
			File f = new File(sdcard, propertiesPath);
			if (f.exists()) {
				System.out.println("File already exists");
			} else {
				System.out.println("No such file exists, creating now");
				try {
					if (f.createNewFile()) {
						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(contentDefaultFile);
						bw.close();
						System.out.printf(
								"Successfully created new file: %s%n", f);
					} else {
						System.out.printf("Failed to create new file: %s%n", f);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		file = new File(sdcard, propertiesPath);
		// Read text from file
		StringBuilder text = new StringBuilder();
		int index = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				text.append(line);
				text.append('\n');
				line = line.replace(" ", "");
				args[index] = line.substring(line.indexOf("=") + 1,
						line.length());
				index++;
			}
		} catch (IOException e) {

		}
		return text.toString();
	}
}
