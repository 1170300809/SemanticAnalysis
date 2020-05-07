package analyzer;


import java.util.ArrayList;

/**
 * Node of the tree
 * 
 * @author lenovo
 */
public class Node {
  private Node parent;//
  private GrammarSymbol grammaSymbol;
  private ArrayList<Node> childrenList;// Child node set

  /**
   * The node is generated according to the list of input children, grammar symbols, and parent
   * nodes
   * 
   * @param p parent
   * @param gs grammar symbols
   * @param cl the list of input children
   */
  public Node(Node p, GrammarSymbol gs, ArrayList<Node> cl) {
    if (p != null)
      p.getChildrenList().add(this);
    this.setParent(p);
    this.setGrammaSymbol(gs);
    this.setChildrenList(cl);
  }

  /**
   * Construct a node without children
   * 
   * @param p parent
   * @param gs grammar symbols
   */

  public Node(Node p, GrammarSymbol gs) {
    if (p != null)
      p.getChildrenList().add(this);
    this.setParent(p);
    this.setGrammaSymbol(gs);
    this.setChildrenList(new ArrayList<Node>());
  }

  /**
   * @return parent
   */
  public Node getParent() {
    return this.parent;
  }

  /**
   * @param parent 要设置的 parent
   */
  public void setParent(Node parent) {
    this.parent = parent;
  }

  /**
   * @return grammaSymbol
   */
  public GrammarSymbol getGrammaSymbol() {
    return this.grammaSymbol;
  }

  /**
   * @param grammaSymbol 要设置的 grammaSymbol
   */
  public void setGrammaSymbol(GrammarSymbol grammaSymbol) {
    this.grammaSymbol = grammaSymbol;
  }

  /**
   * @return childrenList
   */
  public ArrayList<Node> getChildrenList() {
    return this.childrenList;
  }

  /**
   * @param childrenList 要设置的 childrenList
   */
  public void setChildrenList(ArrayList<Node> childrenList) {
    this.childrenList = childrenList;
  }

}
