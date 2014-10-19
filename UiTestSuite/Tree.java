package UiTest.UiTestSuite;

public class Tree {
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