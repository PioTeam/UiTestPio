package UiTest.UiTestSuite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import java.util.ArrayList;

import android.os.Build;
import android.os.Environment;
import android.util.Log;
import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.*;

/**
  asd

NOTE:1.This class can contain only one test case.
	 2.Do not change the class name.
*/
public class TestCase extends UiAutomatorTestCase {   

	private static String TAG = "asd";
	
	private String propertiesPath = "/UiTest/properties.txt";
	private String[] args = new String[2];
	private String appName, tabApp = "";
	private String contentDefaultFile = "appName = Settings\ntabApp = 2";
	private Tree tree;
	private Node actual;

	public void test() throws UiObjectNotFoundException {
		readProperties();
		appName = args[0];
		tabApp = args[1]; 
		UiObject app = abrirApp();		
		actual = new Node(null, "", getUiDevice().getCurrentActivityName(), app);
		tree = new Tree(actual);
		capturarElementosVistaActual();
		// Validate that the package name is the expected one
		// UiObject settingsValidation = new UiObject(
		// new UiSelector().packageName("com.android.settings"));

		// assertTrue("Unable to detect Settings", settingsValidation.exists());
		// // CODE:END
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
	
	public UiObject abrirApp() throws UiObjectNotFoundException{
		// takeScreenShot();
		getUiDevice().pressHome();

		UiObject allAppsButton = new UiObject(
				new UiSelector().description("Apps"));

		allAppsButton.clickAndWaitForNewWindow();

		UiObject appsTab = new UiObject(new UiSelector().text("Apps"));

		appsTab.click();

		UiSelector selector = new UiSelector().scrollable(true);
		UiScrollable appViews = new UiScrollable(selector);
		UiObject app = appViews.getChildByText(new UiSelector()
				.className(android.widget.TextView.class.getName()), appName);
		app.clickAndWaitForNewWindow();
		System.out.println("------------ Hola :D "+ getUiDevice().getCurrentActivityName());
		
		return app;
	}

	public void capturarElementosVistaActual() throws UiObjectNotFoundException {

		// inicio obtener lista
		UiObject listview_elements = new UiObject(
				new UiSelector().className("android.widget.ListView"));

		int numeroItemsVisuales = listview_elements.getChildCount();
		System.out.println("-----------N� elementos lista " + numeroItemsVisuales);
		Node aux;
		for (int i = 0; i < numeroItemsVisuales; i++) {
			UiSelector selector1 = new UiSelector().index(i);
			UiObject obj = listview_elements.getChild(selector1);
			aux = agregarNodo(actual, i+"", getUiDevice().getCurrentActivityName(), obj);
			System.out.println("-------------------Texto lista elemento " + i
					+ ":  " + obj.toString());
//			System.out.println("----------------------------objeto " + i
//					+ ":  " + obj.getClassName());
			System.out.println("----------------------------childCount " + i
					+ ":  " + obj.getChildCount());
			System.out.println("----------------------------descrip " + i
					+ ":  " + obj.getContentDescription());
			System.out.println("----------------------------texto " + i + ":  "
					+ obj.getText());
			System.out.println("----------------------------checkable " + i
					+ ":  " + obj.isCheckable());
			System.out.println("----------------------------clickable " + i
					+ ":  " + obj.isClickable());
			System.out.println("----------------------------focusable " + i
					+ ":  " + obj.isFocusable());
			System.out.println("----------------------------scrollable " + i
					+ ":  " + obj.isScrollable());
			System.out.println("----------------------------bounds " + i
					+ ":  " + obj.getBounds());
		}

		// fin obtener lista
	}
	
	public Node agregarNodo(Node dad, String index, String activity, UiObject object){
		Node node = new Node(dad, index, activity, object);
		actual.add(node);
		return node;
	}
    
}
 
class Tree {
	private Node root;
	
	public Tree(Node node){
		this.root = node;
		node.setDad(null);
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
	
}

class Node {
	private String activity;
	private String id;
	private Node dad;
	private ArrayList<Node> childs;
	private NodeState state;
	private UiObject object;
	
	public Node(Node dad, String index, String activity, UiObject object){
		this.dad = dad;
		if(dad==null){
			this.id = "0";
		}else{
			this.id = dad.getId()+"."+index;
		}
		this.activity = activity;
		this.state = NodeState.NONE;
		this.object = object;
	}
	
	public void add(Node node){
		if(childs==null)childs = new ArrayList<Node>();
		childs.add(node);
	}
	
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Node getDad() {
		return dad;
	}
	public void setDad(Node dad) {
		this.dad = dad;
	}
	public ArrayList<Node> getChilds() {
		return childs;
	}
	public void setChilds(ArrayList<Node> childs) {
		this.childs = childs;
	}

	public NodeState getState() {
		return state;
	}

	public void setState(NodeState state) {
		this.state = state;
	}
	
	public UiObject getObject(){
		return object;
	}
	
	public void setObject(UiObject object){
		this.object = object;
	}
}

 enum NodeState {
	ERROR,PASS,VISITED,NONE
}

 