package analyzer;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

public class Symbol {
  private int productionNo; // note that this field is never used

  private String name;
  // N - nonterminal; T - terminal
  private char type;
  public List<String> first = new ArrayList<>();
  public List<String> follow;

  public Symbol(int productionNo, String name, char type) {
    super();
    this.productionNo = productionNo;
    this.name = name;
    this.type = type;

    if (type == 'N') {
      this.follow = new ArrayList<String>();
    } else if (type == 'T') {
      this.first.add(name); // the first set of a terminal only contains itself
      this.follow = null;
    } else {
      throw new IllegalArgumentException("invalid symbol type");
    }
  }

  public String getName() {
    return this.name;
  }

  public boolean isTerminal() {
    if (this.type == 'N') {
      return false;
    } else {
      return true;
    }
  }

  public boolean has(String arr_name, String sym_name) {
    List<String> arr = null;

    if (arr_name.toUpperCase().equals("FIRST")) {
      arr = this.first;
    } else if (arr_name.toUpperCase().equals("FOLLOW")) {
      arr = this.follow;
    } else {
      throw new IllegalArgumentException(
          "invalid set type - allows only {FIRST, FOLLOW}");
    }

    for (int i = 0; i < arr.size(); i++) {
      if (arr.get(i).equals(sym_name)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public String toString() {
    return this.productionNo + " " + this.name + " " + this.type;
  }
}
