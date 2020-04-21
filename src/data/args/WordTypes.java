package data.args;

/**
 * Types of words that can be recognized.
 * 
 * @author AtoshDustosh
 */
public enum WordTypes {
  IDN("IDN"),
  KEYWORD("KEYWORD"),
  NUM("NUM"),
  STRING("STRING"),
  CHAR("CHAR"),
  OP_DL_COM("Operator_Delimiter_COM"),
  ;

  private String type = null;

  private WordTypes(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }

}
