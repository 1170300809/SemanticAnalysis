package analyzer;

/**
 * Types of grammar symbols in a production.
 * 
 * @author AtoshDustosh
 */
public enum GrammarSymbolType {
  TERMINAL("terminal"),
  NONTERMINAL("nonterminal"),
  UNDEFINED("undefined"),
  OR("or"),
  ;

  private String name = "";

  GrammarSymbolType(String type) {
    this.name = type;
  }

  public String getName() {
    return this.name;
  }

  public boolean equals(GrammarSymbolType symbolType) {
    if (this.name.equals(symbolType.getName())) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return this.name;
  }
}
