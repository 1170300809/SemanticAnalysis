package analyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FollowSets {

  private Map<GrammarSymbol, Set<GrammarSymbol>> followSetMap = new HashMap<>();

  public FollowSets() {

  }

  public static void main(String[] args) {
    FollowSets test = new FollowSets();
    Set<GrammarSymbol> set = new HashSet<>();
    GrammarSymbol s1 = new GrammarSymbol("A");
    GrammarSymbol s2 = new GrammarSymbol("A");
    GrammarSymbol s3 = new GrammarSymbol("A");

    set.clear();
    set.add(new GrammarSymbol("a"));
    set.add(new GrammarSymbol("b"));
    set.add(new GrammarSymbol("b"));
    System.out.println("modified: " + test.addFollowSet(s1, set));

    System.out.println(test.toString());

    set.clear();
    set.add(new GrammarSymbol("b"));
    set.add(new GrammarSymbol("c"));
    System.out.println("modified: " + test.addFollowSet(s2, set));

    System.out.println(test.toString());

    set.clear();
    set.add(new GrammarSymbol("b"));
    set.add(new GrammarSymbol("b"));
    set.add(new GrammarSymbol("c"));
    set.add(new GrammarSymbol("c"));
    System.out.println("modified: " + test.addFollowSet(s3, set));

    System.out.println(test.toString());

    System.out.println(
        "modified: " + test.removeFollowSetItem(s1, new GrammarSymbol("c")));
    System.out.println(test.toString());
    System.out.println(
        "modified: " + test.removeFollowSetItem(s1, new GrammarSymbol("b")));
    System.out.println(test.toString());
    System.out.println(
        "modified: " + test.removeFollowSetItem(s1, new GrammarSymbol("a")));
    System.out.println(test.toString());
    System.out.println(
        "modified: " + test.removeFollowSetItem(s1, new GrammarSymbol("a")));
    System.out.println(test.toString());
  }

  /**
   * Add a new Follow set (nonterminal, list of terminals) into the
   * database.
   * 
   * @param nonterminal  nonterminal grammar symbol
   * @param terminalList terminals in the Follow set of the nonterminal
   * @return true if modified; false otherwise
   */
  public boolean addFollowSet(GrammarSymbol nonterminal,
      Set<GrammarSymbol> terminalSet) {
    boolean modified = false;
    Set<GrammarSymbol> tempSet = new HashSet<>(terminalSet);
    tempSet.remove(new GrammarSymbol("empty"));
    if (this.followSetMap.containsKey(nonterminal)) {
      Set<GrammarSymbol> oldSet = this.followSetMap.get(nonterminal);
      if (oldSet.containsAll(tempSet) == false) {
        oldSet.addAll(tempSet);
        modified = true;
      }
    } else {
      this.followSetMap.put(nonterminal, tempSet);
      modified = true;
    }
    return modified;
  }

  /**
   * Remove a terminal from the Follow set to the nonterminal in this
   * database.
   * 
   * @param nonterminal nonterminal grammar symbol
   * @param terminal    terminal to be removed from Follow set to the
   *                    nonterminal
   * @return true if modified; false otherwise
   */
  public boolean removeFollowSetItem(GrammarSymbol nonterminal,
      GrammarSymbol terminal) {
    boolean modified = false;
    Set<GrammarSymbol> oldSet = this.followSetMap.get(nonterminal);
    modified = oldSet.remove(terminal);
    return modified;
  }

  public Set<GrammarSymbol> getFollowSet(GrammarSymbol symbol) {
    Set<GrammarSymbol> oldSet = this.followSetMap.get(symbol);
    if (oldSet == null) {
      return new HashSet<>();
    } else {
      return new HashSet<>(oldSet);
    }
  }

  public boolean ifFollowSetHasTerminal(GrammarSymbol nonterminal,
      GrammarSymbol terminal) {
    Set<GrammarSymbol> oldSet = this.followSetMap.get(nonterminal);
    if (oldSet == null) {
      return false;
    }
    if (oldSet.contains(terminal)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean ifFollowSetHasNonterminal(GrammarSymbol nonterminal) {
    return this.followSetMap.containsKey(nonterminal);
  }

  public FollowSets copy() {
    FollowSets copy = new FollowSets();
    for (GrammarSymbol gS : this.followSetMap.keySet()) {
      Set<GrammarSymbol> symbolSet = this.followSetMap.get(gS);
      copy.addFollowSet(gS, symbolSet);
    }
    return copy;
  }

  @Override
  public String toString() {
    String str = "";
    for (GrammarSymbol gS : this.followSetMap.keySet()) {
      Set<GrammarSymbol> symbolSet = this.followSetMap.get(gS);
      str = str + gS.toString() + " -> " + symbolSet.toString() + "\n";
    }
    return str;
  }

  /**
   * @return follow字典中的所有键
   */
  public Set<GrammarSymbol> getGrammaSet() {
    return this.followSetMap.keySet();
  }
}
