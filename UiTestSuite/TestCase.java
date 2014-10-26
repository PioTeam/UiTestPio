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
		UiObject app = openApp();		
		actual = new Node(null, "", getUiDevice().getCurrentActivityName(), app);
		tree = new Tree(actual);
		getActualViewElements();
		evaluateState();
		System.out.println("------------- Tree :D "+tree);
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
		System.out.println("------------ File of properties ready!");
		return text.toString();
	}
	
	public UiObject openApp() throws UiObjectNotFoundException{
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
		System.out.println("------------ App open "+ getUiDevice().getCurrentActivityName());
		
		return app;
	}

	public void getActualViewElements() throws UiObjectNotFoundException {

		// inicio obtener lista
		UiObject listview_elements = new UiObject(
				new UiSelector().className("android.widget.ListView"));

		int numeroItemsVisuales = listview_elements.getChildCount();
		System.out.println("-----------N° elementos lista " + numeroItemsVisuales);
		Node aux;
		for (int i = 0; i < numeroItemsVisuales; i++) {
			UiSelector selector1 = new UiSelector().index(i);
			UiObject obj = listview_elements.getChild(selector1);
			aux = addNode(actual, i+"", getUiDevice().getCurrentActivityName(), obj);
			System.out.println("-------------------Texto lista elemento " + i
					+ ":  " + obj.toString());
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
	}
	
	public String executeEvents() throws UiObjectNotFoundException{
		if(!actual.isCheckable() && actual.getObject().isCheckable()){
			actual.getObject().click();
			actual.setCheckable(true);
		}else if(!actual.isClickable() && actual.getObject().isClickable()){
			actual.getObject().click();
			actual.setClickable(true);
		}else if(!actual.isLongClickable() && actual.getObject().isLongClickable()){
			actual.getObject().longClick();
			actual.setLongClickable(true);
		}
		System.out.println("------------ execute event call! "+ getUiDevice().getCurrentActivityName());
		return getUiDevice().getCurrentActivityName();
	}
	
	public Node addNode(Node dad, String index, String activity, UiObject object){
		Node node = new Node(dad, index, activity, object);
		actual.add(node);
		return node;
	}
	
	public boolean isActualRoot(){
		return actual.getDad()==null;
	}
    
	public void evaluateState() throws UiObjectNotFoundException{
		if(actual.getState().equals(NodeState.PASS)){
			actual = actual.getDad();
		}else if(actual.getState().equals(NodeState.ERROR)){
			actual = actual.getDad();
		}else if(actual.getState().equals(NodeState.NONE)){
			if(!isActualRoot() && actual.isAnyEventAvaiable()){
				String activity = executeEvents();
				if(activity.equals(actual.getActivity())){
					actual.setState(NodeState.VISITED);
				}else{
					getActualViewElements();
					actual.setState(NodeState.VISITED);
					if(actual.getChilds() != null){
						actual = actual.getChilds().get(0);						
					}else{
						getUiDevice().pressBack();
					}
				}
			}else if(!isActualRoot()){
				actual.setState(NodeState.PASS);
				if(!actual.getDad().getActivity().equals(actual.getActivity())){
					getUiDevice().pressBack();
				}
			}
			
		}else if(actual.getState().equals(NodeState.VISITED)){
			if(actual.isAnyEventAvaiable() && !isActualRoot()){
				String activity = executeEvents();
				if(!activity.equals(actual.getActivity())){
					getActualViewElements();
					if(actual.getChilds() != null){
						actual = actual.getChilds().get(0);						
					}else{
						getUiDevice().pressBack();
					}
				}
			}else{
				for(Node n: actual.getChilds()){
					if(n.getState().equals(NodeState.NONE) || n.getState().equals(NodeState.VISITED)){
						actual=n;
						evaluateState();
						return;
					}
				}
				if(!isActualRoot() ){
					actual.setState(NodeState.PASS);
					if(!actual.getDad().getActivity().equals(actual.getActivity())){
						getUiDevice().pressBack();
					}
				}else{
					actual.setState(NodeState.PASS);
					return;
				}
			}
		}
		evaluateState();
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
	private boolean clickable;
	private boolean scrollable;
	private boolean checkable;
	private boolean longClickable;
	
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
		this.clickable = false;
		this.scrollable = false;
		this.checkable = false;
		this.longClickable = false;
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
	public boolean isClickable(){
		return clickable;
	}public boolean isLongClickable(){
		return longClickable;
	}
	public boolean isScrollable(){
		return scrollable;
	}
	public boolean isCheckable(){
		return checkable;
	}
	public void setClickable(boolean clickable){
		this.clickable = clickable;
	}
	public void setLongClickable(boolean longClickable){
		this.longClickable = longClickable;
	}
	public void setScrollable(boolean scrollable){
		this.scrollable = scrollable;
	}
	public void setCheckable(boolean checkable){
		this.checkable = checkable;
	}
	public boolean isAnyEventAvaiable(){
		return clickable || scrollable || checkable || longClickable;
	}
}

 enum NodeState {
	ERROR,PASS,VISITED,NONE
}

 