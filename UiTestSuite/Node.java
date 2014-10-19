package UiTest.UiTestSuite;

import java.util.ArrayList;


public class Node {
	private String activity;
	private String id;
	private Node dad;
	private ArrayList<Node> childs;
	private NodeState state;
	
	public Node(Node dad, String index, String activity){
		this.dad = dad;
		this.id = dad.getId()+"."+index;
		this.activity = activity;
		this.state = NodeState.NONE;
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
}
