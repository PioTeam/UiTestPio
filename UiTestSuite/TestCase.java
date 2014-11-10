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

public class TestCase extends UiAutomatorTestCase {

	private static String TAG = "TestCase";

	private String propertiesPath = "/UiTest/properties.txt";
	private String TreePath = "/UiTest/Tree.txt";
	private String errorPath = "/UiTest/error.txt";
	private String[] args = new String[2];
	private String appName, packageName = "";
	private String contentDefaultFile = "appName = Settings\npackageName = com.android.settings";
	private Tree Tree;
	private Node actual;
	private String keyStringActivity = "";

	public void test() {
		try{ 
			readProperties();
			appName = args[0];
			packageName = args[1];
			UiObject app = openApp();
			actual = new Node(null, "", getActualActivity(), app);
			Tree = new Tree(actual);
			UiObject element = getGeneralElement();
			findObjectsFromContainer(element);
			evaluateState();
			writeTree();
			// System.out.println("------------- Tree :D "+Tree);
			// Validate that the package name is the expected one
			// UiObject settingsValidation = new UiObject(
			// new UiSelector().packageName(packageName));
	
			// assertTrue("Unable to detect Settings", settingsValidation.exists());
			// // CODE:END
		}catch (Exception e) {
			writeError("ERROR: test: "+e.getMessage());
		}
	}

	public String getActualActivity() throws UiObjectNotFoundException {
		UiObject o = new UiObject(new UiSelector().className("android.widget.TextView"));
		if(o.exists()){
			keyStringActivity = o.getText();
		}
		return getUiDevice().getCurrentPackageName()+keyStringActivity;
	}

	public boolean isAnyEventAvaiable(UiObject object)
			throws UiObjectNotFoundException {
		return object.isScrollable() || object.isCheckable()
				|| object.isLongClickable() || object.isClickable();
	}

	public String executeEvents() throws UiObjectNotFoundException {
		if (actual.isCheckable()) {
			actual.getObject().click();
			actual.setCheckable(false);
		} else if (actual.isClickable()) {
			actual.getObject().click();
			actual.setClickable(false);
		} else if (actual.isLongClickable()) {
			actual.getObject().longClick();
			actual.setLongClickable(false);
		}
		getUiDevice().waitForWindowUpdate(null, 2000);
		return getActualActivity();
	}

	public Node addNode(UiObject object) throws UiObjectNotFoundException {
		if (object!=null && isAnyEventAvaiable(object)) {
			Node Node = new Node(actual, object.getChildCount() + "",
					getActualActivity(), object);
			actual.add(Node);
			return Node;
		}
		return null;
	}

	public boolean isActualRoot() {
		return actual.getDad() == null;
	}

	public UiObject getGeneralElement() {
		UiObject element;
		UiSelector selector;
		try {
			selector = new UiSelector();
			element = new UiObject(selector);
		} catch (Exception e) {
			System.out.println("---ERROR: getGeneralElement");
			writeError("ERROR: getGeneralElement: "+e.getMessage());
			element = null;
		}
		return element;
	}
	
	public void findFrameLayout(UiObject container)
			throws UiObjectNotFoundException {
		UiObject frameLayout = container.getChild(new UiSelector()
				.className(android.widget.FrameLayout.class.getName()));
		if (frameLayout.exists()) {
			System.out.println("---frameLayout");
			findObjectsFromContainer(frameLayout);
			addNode(frameLayout);
		}
	}

	public void findLinearLayout(UiObject container)
			throws UiObjectNotFoundException {
		UiObject linearLayout = container.getChild(new UiSelector()
				.className(android.widget.LinearLayout.class.getName()));
		if (linearLayout.exists()) {
			System.out.println("---linearLayout");
			findObjectsFromContainer(linearLayout);
			addNode(linearLayout);
		}
	}

	public void findRelativeLayout(UiObject container)
			throws UiObjectNotFoundException {
		UiObject relativeLayout = container.getChild(new UiSelector()
				.className(android.widget.RelativeLayout.class.getName()));
		if (relativeLayout.exists()) {
			System.out.println("---relativeLayout");
			findObjectsFromContainer(relativeLayout);
			addNode(relativeLayout);
		}
	}

	public void findView(UiObject container) throws UiObjectNotFoundException {
		UiObject view = container.getChild(new UiSelector()
				.className(android.view.View.class.getName()));
		if (view.exists()) {
			System.out.println("---view");
			findObjectsFromContainer(view);
			addNode(view);
		}
	}

	public void findGridView(UiObject container)
			throws UiObjectNotFoundException {
		UiObject gridView = container.getChild(new UiSelector()
				.className(android.widget.GridView.class.getName()));
		if (gridView.exists()) {
			System.out.println("---gridView");
			findObjectsFromContainer(gridView);
			addNode(gridView);
		}
	}

	public void findListView(UiObject container)
			throws UiObjectNotFoundException {
		UiObject listView = container.getChild(new UiSelector()
				.className(android.widget.ListView.class.getName()));
		if (listView.exists()) {
			System.out.println("---listView");
			findObjectsFromContainer(listView);
			addNode(listView);
		}
	}

	public void findSwitch(UiObject container) throws UiObjectNotFoundException {
		UiObject switchObject = container.getChild(new UiSelector()
				.className(android.widget.Switch.class.getName()));
		if (switchObject.exists()) {
			System.out.println("---switchObject");
			findObjectsFromContainer(switchObject);
			addNode(switchObject);
		}
	}

	public void findButton(UiObject container) throws UiObjectNotFoundException {
		UiObject button = container.getChild(new UiSelector()
				.className(android.widget.Button.class.getName()));
		if (button.exists()) {
			System.out.println("---button");
			findObjectsFromContainer(button);
			addNode(button);
		}
	}

	public void findDatePicker(UiObject container)
			throws UiObjectNotFoundException {
		UiObject datePicker = container.getChild(new UiSelector()
				.className(android.widget.DatePicker.class.getName()));
		if (datePicker.exists()) {
			System.out.println("---datePicker");
			findObjectsFromContainer(datePicker);
			addNode(datePicker);
		}
	}

	public void findCheckBox(UiObject container)
			throws UiObjectNotFoundException {
		UiObject checkBox = container.getChild(new UiSelector()
				.className(android.widget.CheckBox.class.getName()));
		if (checkBox.exists()) {
			System.out.println("---CheckBox");
			findObjectsFromContainer(checkBox);
			addNode(checkBox);
		}
	}

	public void findEditText(UiObject container)
			throws UiObjectNotFoundException {
		UiObject editText = container.getChild(new UiSelector()
				.className(android.widget.EditText.class.getName()));
		if (editText.exists()) {
			System.out.println("---Frame");
			findObjectsFromContainer(editText);
			addNode(editText);
		}
	}

	public void findRadioButton(UiObject container)
			throws UiObjectNotFoundException {
		UiObject radioButton = container.getChild(new UiSelector()
				.className(android.widget.RadioButton.class.getName()));
		if (radioButton.exists()) {
			System.out.println("---radioButton");
			findObjectsFromContainer(radioButton);
			addNode(radioButton);
		}
	}

	public void findRadioGroup(UiObject container)
			throws UiObjectNotFoundException {
		UiObject radioGroup = container.getChild(new UiSelector()
				.className(android.widget.RadioGroup.class.getName()));
		if (radioGroup.exists()) {
			System.out.println("---radioGroup");
			findObjectsFromContainer(radioGroup);
			addNode(radioGroup);
		}
	}

	public void findToggleButton(UiObject container)
			throws UiObjectNotFoundException {
		UiObject toggleButton = container.getChild(new UiSelector()
				.className(android.widget.ToggleButton.class.getName()));
		if (toggleButton.exists()) {
			System.out.println("---toggleButton");
			findObjectsFromContainer(toggleButton);
			addNode(toggleButton);
		}
	}

	public void findSpinner(UiObject container)
			throws UiObjectNotFoundException {
		UiObject spinner = container.getChild(new UiSelector()
				.className(android.widget.Spinner.class.getName()));
		if (spinner.exists()) {
			System.out.println("---spinner");
			findObjectsFromContainer(spinner);
			addNode(spinner);
		}
	}

	public void findTimePicker(UiObject container)
			throws UiObjectNotFoundException {
		UiObject timePicker = container.getChild(new UiSelector()
				.className(android.widget.TimePicker.class.getName()));
		if (timePicker.exists()) {
			System.out.println("---timePicker");
			findObjectsFromContainer(timePicker);
			addNode(timePicker);
		}
	}

	public void findImageView(UiObject container)
			throws UiObjectNotFoundException {
		UiObject imageView = container.getChild(new UiSelector()
				.className(android.widget.ImageView.class.getName()));
		if (imageView.exists()) {
			System.out.println("---imageView");
			findObjectsFromContainer(imageView);
			addNode(imageView);
		}
	}

	public void findTextView(UiObject container)
			throws UiObjectNotFoundException {
		UiObject textView = container.getChild(new UiSelector()
				.className(android.widget.TextView.class.getName()));
		if (textView.exists()) {
			System.out.println("---textView");
			findObjectsFromContainer(textView);
			addNode(textView);
		}
	}

	public void findObjectsFromContainer(UiObject container)
			throws UiObjectNotFoundException {
		System.out.println("-Buscando todos");
		try {
			findFrameLayout(container);
		} catch (Exception e) {
			System.out.println("-Error findFrameLayout");
		}
		try {
			findLinearLayout(container);
		} catch (Exception e) {
			System.out.println("-Error findLinearLayout");
		}
		try {
			findRelativeLayout(container);
		} catch (Exception e) {
			System.out.println("-Error findRelativeLayout");
		}
		try {
			findView(container);
		} catch (Exception e) {
			System.out.println("-Error findView");
		}
		try {
			findListView(container);
		} catch (Exception e) {
			System.out.println("-Error findListView");
		}
		try {
			findGridView(container);
		} catch (Exception e) {
			System.out.println("-Error findGridView");
		}
		try {
			findButton(container);
		} catch (Exception e) {
			System.out.println("-Error findButton");
		}
		try {
			findSwitch(container);
		} catch (Exception e) {
			System.out.println("-Error findSwitch");
		}
		try {
			findCheckBox(container);
		} catch (Exception e) {
			System.out.println("-Error findCheckBox");
		}
		try {
			findEditText(container);
		} catch (Exception e) {
			System.out.println("-Error findEditText");
		}
		try {
			findTextView(container);
		} catch (Exception e) {
			System.out.println("-Error findTextView");
		}
		try {
			findSpinner(container);
		} catch (Exception e) {
			System.out.println("-Error findSpinner");
		}
		try {
			findRadioButton(container);
		} catch (Exception e) {
			System.out.println("-Error findRadioButton");
		}
		try {
			findRadioGroup(container);
		} catch (Exception e) {
			System.out.println("-Error findRadioGroup");
		}
		try {
			findToggleButton(container);
		} catch (Exception e) {
			System.out.println("-Error findToggleButton");
		}
		try {
			findTimePicker(container);
		} catch (Exception e) {
			System.out.println("-Error findTimePicker");
		}
		try {
			findDatePicker(container);
		} catch (Exception e) {
			System.out.println("-Error findDatePicker");
		}
	}

	public void evaluateState() throws UiObjectNotFoundException {
		System.out.println("------------ evaluate Node " + actual.getId()
				+ " state " + actual.getState());
		if (actual.getState().equals(NodeState.PASS)) {
			actual = actual.getDad();
			if(actual==null){
				getUiDevice().pressBack();
				return;
			}
		} else if (actual.getState().equals(NodeState.ERROR)) {
			actual = actual.getDad();
		} else if (actual.getState().equals(NodeState.NONE)) {
			if (!isActualRoot() && actual.isAnyEventAvaiable()) {
				String activity = executeEvents();
				if (activity.equals(actual.getActivity())) {
					actual.setState(NodeState.VISITED);
				} else {
					findObjectsFromContainer(getGeneralElement());
					actual.setState(NodeState.VISITED);
					if (actual.getChilds() != null) {
						actual = actual.getChilds().get(0);
					} else {
						getUiDevice().pressBack();
					}
				}
			} else if (!isActualRoot()) {
				actual.setState(NodeState.PASS);
				if (!actual.getDad().getActivity().equals(actual.getActivity())) {
					getUiDevice().pressBack();
				}
			} else {
				actual.setState(NodeState.VISITED);
				if (actual.getChilds() != null) {
					actual = actual.getChilds().get(0);
				} else {
					getUiDevice().pressBack();
				}
			}

		} else if (actual.getState().equals(NodeState.VISITED)) {
			if (actual.isAnyEventAvaiable() && !isActualRoot()) {
				String activity = executeEvents();
				if (!activity.equals(actual.getActivity())) {
					findObjectsFromContainer(getGeneralElement());
					if (actual.getChilds() != null) {
						actual = actual.getChilds().get(0);
					} else {
						getUiDevice().pressBack();
					}
				}
			} else {
				if(actual.getChilds()!=null){
					for (Node n : actual.getChilds()) {
						if (n.getState().equals(NodeState.NONE)
								|| n.getState().equals(NodeState.VISITED)) {
							actual = n;
							evaluateState();
							return;
						}
					}
				}
				if (!isActualRoot()) {
					actual.setState(NodeState.PASS);
					if (!actual.getDad().getActivity()
							.equals(actual.getActivity())) {
						getUiDevice().pressBack();
					}
				} else {
					System.out.println("1");
					if(actual!=null){
						System.out.println("2");
						actual.setState(NodeState.PASS);
					}else{
						System.out.println("3");
						getUiDevice().pressBack();
						return;
					}
					System.out.println("4");
					
				}
			}
		}
		evaluateState();
	}

	public String readProperties() {
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

	public void writeTree() {
		// Find the directory for the SD Card using the API
		// *Don't* hardcode "/sdcard"
		File sdcard = Environment.getExternalStorageDirectory();

		// Get the text file
		File file = new File(sdcard, TreePath);
		if (!file.exists()) {
			System.out.println("No such file exists, creating now");
			try {
				if (file.createNewFile()) {
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(Tree.toString());
					bw.close();
					System.out.printf("Successfully created new file: %s%n",
							file);
				} else {
					System.out.printf("Failed to create new file: %s%n", file);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			FileWriter fw;
			try {
				fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(Tree.toString());
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void writeError(String error) {
		// Find the directory for the SD Card using the API
		// *Don't* hardcode "/sdcard"
		File sdcard = Environment.getExternalStorageDirectory();

		// Get the text file
		File file = new File(sdcard, errorPath);
		if (!file.exists()) {
			System.out.println("No such file exists, creating now");
			try {
				if (file.createNewFile()) {
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(error);
					bw.close();
					System.out.printf("Successfully created new file: %s%n",
							file);
				} else {
					System.out.printf("Failed to create new file: %s%n", file);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			FileWriter fw;
			try {
				fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(error);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public UiObject openApp() throws UiObjectNotFoundException {
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
		System.out.println("------------ App open "
				+ getUiDevice().getCurrentActivityName());

		return app;
	}

}

class Tree {
	private Node root;

	public Tree(Node Node) {
		this.root = Node;
		Node.setDad(null);
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public String toString() {
		return root.toString();
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

	public Node(Node dad, String index, String activity, UiObject object) {
		this.dad = dad;
		if (dad == null) {
			this.id = "0";
		} else {
			this.id = dad.getId() + "." + index;
		}
		this.activity = activity;
		this.state = NodeState.NONE;
		this.object = object;
		try {
			this.clickable = object.isClickable();
			this.scrollable = object.isScrollable();
			this.checkable = object.isCheckable();
			this.longClickable = object.isLongClickable();
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void add(Node Node) {
		if (childs == null)
			childs = new ArrayList<Node>();
		childs.add(Node);
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

	public UiObject getObject() {
		return object;
	}

	public void setObject(UiObject object) {
		this.object = object;
	}

	public boolean isClickable() {
		return clickable;
	}

	public boolean isLongClickable() {
		return longClickable;
	}

	public boolean isScrollable() {
		return scrollable;
	}

	public boolean isCheckable() {
		return checkable;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

	public void setLongClickable(boolean longClickable) {
		this.longClickable = longClickable;
	}

	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}

	public void setCheckable(boolean checkable) {
		this.checkable = checkable;
	}

	public boolean isAnyEventAvaiable() {
		return clickable || scrollable || checkable || longClickable;
	}

	public String toString() {
		String text = "";
		text += "id: " + id + " \n";
		for (Node n : childs) {
			text += "\t" + n.toString();
		}
		return text;
	}
}

enum NodeState {
	ERROR, PASS, VISITED, NONE
}
