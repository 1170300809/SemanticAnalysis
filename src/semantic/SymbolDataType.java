package semantic;

public enum SymbolDataType {
  INT("int"),
  DOUBLE("double"),
  ARRAY("array"),
  STRING("string"),
  CHAR("char"),
  ;

  private String name = "";

  SymbolDataType(String type) {
    this.name = type;
  }

  public String getName() {
    return this.name;
  }

  public boolean equals(SymbolDataType symbolType) {
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
