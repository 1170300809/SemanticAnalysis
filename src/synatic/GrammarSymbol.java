package analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Grammar symbol in a production - this is an immutable tye.
 * 
 * @author AtoshDustosh
 */
public class GrammarSymbol {

  private String symbolName = "";

  private GrammarSymbolType symbolType = GrammarSymbolType.UNDEFINED;

  public GrammarSymbol() {

  }

  public GrammarSymbol(String symbolStr) {
    this.symbolName = symbolStr;
    if (this.isNonterminalSymbol(symbolStr)) {
      this.symbolType = GrammarSymbolType.NONTERMINAL;
    } else if (this.isTerminalSymbol(symbolStr)) {
      this.symbolType = GrammarSymbolType.TERMINAL;
    } else if (this.isOrSymbol(symbolStr)) {
      this.symbolType = GrammarSymbolType.OR;
    } else {
      this.symbolType = GrammarSymbolType.UNDEFINED;
    }
  }

  public GrammarSymbol(GrammarSymbol symbol) {
    this.symbolName = symbol.getName();
    this.symbolType = symbol.getType();
  }

  public static void main(String[] args) {
    Set<GrammarSymbol> set1 = new HashSet<>();
    Set<GrammarSymbol> set2 = new HashSet<>();
    set1.add(new GrammarSymbol("a"));
    set1.add(new GrammarSymbol("b"));
    set1.add(new GrammarSymbol("c"));
    set2.add(new GrammarSymbol("a"));
    set2.add(new GrammarSymbol("b"));
    set2.add(new GrammarSymbol("d"));
    set1.addAll(set2);
    set2.remove(new GrammarSymbol("b"));
    System.out.println("set1: " + set1.toString());
    System.out.println("set2: " + set2.toString());

    System.out.println(set1.containsAll(set2));
    System.out.println(set2.containsAll(set1));

    Map<String, List<GrammarSymbol>> map1 = new HashMap<>();
    map1.put("a", new ArrayList<>(
        Arrays.asList(new GrammarSymbol("A"), new GrammarSymbol("AA"))));
    map1.put("b", new ArrayList<>(Arrays.asList(new GrammarSymbol("B"))));
    map1.put("c", new ArrayList<>(Arrays.asList(new GrammarSymbol("C"))));

    Map<String, List<GrammarSymbol>> map2 = new HashMap<>();
    map2.put("a", new ArrayList<>(
        Arrays.asList(new GrammarSymbol("A"), new GrammarSymbol("AA"))));
    map2.put("b", new ArrayList<>(Arrays.asList(new GrammarSymbol("B"))));
    map2.put("c", new ArrayList<>(Arrays.asList(new GrammarSymbol("C"))));

    System.out.println(map1.toString());
    System.out.println(map2.toString());
    System.out.println(map1.equals(map2));

  }

  public String getName() {
    return this.symbolName;
  }

  public GrammarSymbolType getType() {
    return this.symbolType;
  }

  public boolean isNonterminalSymbol() {
    return this.symbolType.equals(GrammarSymbolType.NONTERMINAL);
  }

  public boolean isTerminalSymbol() {
    return this.symbolType.equals(GrammarSymbolType.TERMINAL);
  }

  public boolean isOrSymbol() {
    return this.symbolType.equals(GrammarSymbolType.OR);
  }

  @Override
  public String toString() {
    String str = "";
    str = "[" + this.symbolName + "," + this.symbolType.toString() + "]";
    return str;
  }

  @Override
  public int hashCode() {
    return (this.symbolName + this.symbolType.toString()).hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return this.equals((GrammarSymbol) o);
  }

  public boolean equals(GrammarSymbol symbol) {
    if (this.symbolName.equals(symbol.getName())
        && this.symbolType.equals(symbol.getType())) {
      return true;
    } else {
      return false;
    }
  }

  private boolean isNonterminalSymbol(String str) {
    Pattern p = Pattern.compile("([A-Z]|_|[0-9])+");
    Matcher m = p.matcher(str);
    return m.matches();
  }

  private boolean isTerminalSymbol(String str) {
    Pattern p = Pattern.compile("([A-Z]|_|[0-9])+|\\|");
    Matcher m = p.matcher(str);
    return !m.matches();
  }

  private boolean isOrSymbol(String str) {
    if (str.equals("|")) {
      return true;
    } else {
      return false;
    }
  }

}
