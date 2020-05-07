package semantic;

public enum SemanticSymbolType {
  INT("int"),
  DOUBLE("double"),
  ARRAY("array"),
  STRING("string"),
  CHAR("char"),
  ;

  private String name = "";

  SemanticSymbolType(String type) {
    this.name = type;
  }

  public String getName() {
    return this.name;
  }

  public boolean equals(SemanticSymbolType symbolType) {
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
