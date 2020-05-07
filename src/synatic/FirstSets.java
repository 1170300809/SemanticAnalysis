package analyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Store the First sets of a series of nonterminals. The First sets are calculated according to some
 * productions.
 * 
 * @author AtoshDustosh
 */
public class FirstSets {

  private Map<GrammarSymbol, Set<GrammarSymbol>> firstSetMap = new HashMap<>();

  public FirstSets() {

  }

  public static void main(String[] args) {
    FirstSets test = new FirstSets();
    Set<GrammarSymbol> set = new HashSet<>();
    GrammarSymbol s1 = new GrammarSymbol("A");
    GrammarSymbol s2 = new GrammarSymbol("A");
    GrammarSymbol s3 = new GrammarSymbol("A");

    set.clear();
    set.add(new GrammarSymbol("a"));
    set.add(new GrammarSymbol("b"));
    set.add(new GrammarSymbol("b"));
    System.out.println("modified: " + test.addFirstSet(s1, set));

    System.out.println(test.toString());

    set.clear();
    set.add(new GrammarSymbol("b"));
    set.add(new GrammarSymbol("c"));
    System.out.println("modified: " + test.addFirstSet(s2, set));

    System.out.println(test.toString());

    set.clear();
    set.add(new GrammarSymbol("b"));
    set.add(new GrammarSymbol("b"));
    set.add(new GrammarSymbol("c"));
    set.add(new GrammarSymbol("c"));
    System.out.println("modified: " + test.addFirstSet(s3, set));

    System.out.println(test.toString());

    System.out.println("modified: " + test.removeFirstSetItem(s1, new GrammarSymbol("c")));
    System.out.println(test.toString());
    System.out.println("modified: " + test.removeFirstSetItem(s1, new GrammarSymbol("b")));
    System.out.println(test.toString());
    System.out.println("modified: " + test.removeFirstSetItem(s1, new GrammarSymbol("a")));
    System.out.println(test.toString());
    System.out.println("modified: " + test.removeFirstSetItem(s1, new GrammarSymbol("a")));
    System.out.println(test.toString());
  }

  /**
   * Add a new First set (nonterminal, list of terminals) into the database.
   * 
   * @param nonterminal nonterminal grammar symbol
   * @param terminalList terminals in the First set of the nonterminal
   * @return true if modified; false otherwise
   */
  public boolean addFirstSet(GrammarSymbol nonterminal, Set<GrammarSymbol> terminalSet) {
    boolean modified = false;
    if (this.firstSetMap.containsKey(nonterminal)) {
      Set<GrammarSymbol> oldSet = this.firstSetMap.get(nonterminal);
      if (oldSet.containsAll(terminalSet) == false) {
        oldSet.addAll(terminalSet);
        modified = true;
      }
    } else {
      this.firstSetMap.put(nonterminal, terminalSet);
      modified = true;
    }
    return modified;
  }

  /**
   * Remove a terminal from the First set to the nonterminal in this database.
   * 
   * @param nonterminal nonterminal grammar symbol
   * @param terminal terminal to be removed from First set to the nonterminal
   * @return true if modified; false otherwise
   */
  public boolean removeFirstSetItem(GrammarSymbol nonterminal, GrammarSymbol terminal) {
    boolean modified = false;
    Set<GrammarSymbol> oldSet = this.firstSetMap.get(nonterminal);
    modified = oldSet.remove(terminal);
    return modified;
  }

  public Set<GrammarSymbol> getFirstSet(GrammarSymbol symbol) {
    Set<GrammarSymbol> oldSet = this.firstSetMap.get(symbol);
    if (oldSet == null) {
      return new HashSet<>();
    } else {
      return new HashSet<>(oldSet);
    }
  }

  public boolean ifFirstSetHasTerminal(GrammarSymbol nonterminal, GrammarSymbol terminal) {
    Set<GrammarSymbol> oldSet = this.firstSetMap.get(nonterminal);
    if (oldSet == null) {
      return false;
    }
    if (oldSet.contains(terminal)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean ifFirstSetHasNonterminal(GrammarSymbol nonterminal) {
    return this.firstSetMap.containsKey(nonterminal);
  }

  public FirstSets copy() {
    FirstSets copy = new FirstSets();
    for (GrammarSymbol gS : this.firstSetMap.keySet()) {
      Set<GrammarSymbol> symbolSet = this.firstSetMap.get(gS);
      copy.addFirstSet(gS, symbolSet);
    }
    return copy;
  }

  @Override
  public String toString() {
    String str = "";
    for (GrammarSymbol gS : this.firstSetMap.keySet()) {
      Set<GrammarSymbol> symbolSet = this.firstSetMap.get(gS);
      str = str + gS.toString() + " -> " + symbolSet.toString() + "\n";
    }
    return str;
  }

  /**
   * 
   * @return first字典中的所有键
   */
  public Set<GrammarSymbol> getGrammaSet() {
    return firstSetMap.keySet();
  }
}
