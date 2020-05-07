package analyzer;

import java.util.Stack;

/**
 * A multi-branch tree to show the syntax structure
 * 
 * @author lenovo
 */
public class MutiWayTree {

  private Node rootNode;// root node
  private Node node_Current;// The node to which the current pointer points

  public MutiWayTree(Node root) {
    this.rootNode = root;
    this.node_Current = root;
  }

  /**
   * @return root node
   */
  public Node getRoot() {
    return this.rootNode;
  }

  /**
   * get The node to which the current pointer points
   */
  public Node getCurrentNode() {
    return this.node_Current;
  }

  /**
   * Points the pointer to the root node
   */
  public void backToTheRoot() {
    this.node_Current = this.rootNode;
  }

  /**
   * set CurrentNode
   */
  public void setCurrentNode(Node n) {
    node_Current = n;
  }

  /**
   * 返回节点的层级数
   * 
   * @param n
   * @return
   */
  public int getLayer(Node n) {
    int layer = 0;
    while (n != rootNode) {
      n = n.getParent();
      layer++;
    }
    return layer;

  }

  @Override
  public String toString() {
    String treeString = "";

    Stack<Node> treeStack = new Stack<>();
    treeStack.push(rootNode);
    do {
      Node currentNode = treeStack.pop();
      String layerString = "";
      int layer = getLayer(currentNode);
      for (int i = 0; i < layer; i++) {
        layerString += "---->";
      }

      treeString += layerString + currentNode.getGrammaSymbol().getName() + "\n";
      for (int i = 0; i < currentNode.getChildrenList().size(); i++) {
        treeStack.push(currentNode.getChildrenList().get(i));
      }

    } while (!treeStack.isEmpty());

    return treeString;
  }

}
