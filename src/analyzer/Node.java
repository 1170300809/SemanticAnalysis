package analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A node type for grammar tree.
 * 
 * @author AtoshDustosh
 */
public class Node {
  String symbol_name;
  Node father;
  public List<Node> sons = new ArrayList<>();
  public Map<String, String> attribute = new HashMap<>();

  public Node(String symbolName, Node father) {
    super();
    this.symbol_name = symbolName;
    this.father = father;
  }

  public void setSons(List<Node> sons) {
    this.sons = sons;
  }

  public String getSymbol_name() {
    return this.symbol_name;
  }

  public Node getFather() {
    return this.father;
  }
}
