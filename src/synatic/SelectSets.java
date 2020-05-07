package analyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SelectSets {

  private Map<Production, Set<GrammarSymbol>> selectSetMap = new HashMap<>();

  public SelectSets() {

  }

  public void addSelectSet(Production production,
      Set<GrammarSymbol> terminalSet) {
    if (this.selectSetMap.containsKey(production)) {
      this.selectSetMap.get(production).addAll(terminalSet);
    } else {
      this.selectSetMap.put(production, terminalSet);
    }
  }

  public Set<GrammarSymbol> getSelectSet(Production production) {
    if (this.selectSetMap.containsKey(production)) {
      return new HashSet<>(this.selectSetMap.get(production));
    } else {
      return new HashSet<>();
    }
  }

  public SelectSets copy() {
    SelectSets copy = new SelectSets();
    for (Production p : this.selectSetMap.keySet()) {
      copy.addSelectSet(p, this.selectSetMap.get(p));
    }
    return copy;
  }

  @Override
  public String toString() {
    String str = "";
    for (Production p : this.selectSetMap.keySet()) {
      Set<GrammarSymbol> symbolSet = this.selectSetMap.get(p);
      str = str + "production:\n\t" + p.toString() + "select set: "
          + symbolSet.toString() + "\n";
    }
    return str;
  }

  /**
   * @return Select字典中的所有键
   */
  public Set<Production> getProductionSet() {
    return this.selectSetMap.keySet();
  }

}
