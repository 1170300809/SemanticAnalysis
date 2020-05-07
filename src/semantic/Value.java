package semantic;

public class Value {

  Enum<SemanticSymbolType> typenum;/* 类型标号，0位字符串，1为整形，2为浮点型 */

  String stringValue = null;
  Integer intValue = null;
  Double doubleValue = null;
  Character charValue = null;
  Object[] arrayValue = null;



  // 初始化函数
  public Value(String v) {
    typenum = SemanticSymbolType.STRING;
    stringValue = v;
  }

  public Value(Integer v) {
    typenum = SemanticSymbolType.INT;
    intValue = v;
  }

  public Value(Double v) {
    typenum = SemanticSymbolType.DOUBLE;
    doubleValue = v;
  }

  public Value(Character v) {
    typenum = SemanticSymbolType.CHAR;
    charValue = v;
  }

  public Value(Object[] v) {
    typenum = SemanticSymbolType.ARRAY;
    arrayValue = v;
  }

  public Value(Enum<SemanticSymbolType> i) {
    typenum = i;
  }

  /**
   * 
   * @return 根据类型返回Value的真实值
   */
  public Object getValue() {
    if (typenum.equals(SemanticSymbolType.STRING))
      return stringValue;
    if (typenum.equals(SemanticSymbolType.DOUBLE))
      return doubleValue;
    if (typenum.equals(SemanticSymbolType.INT))
      return intValue;
    if (typenum.equals(SemanticSymbolType.CHAR))
      return charValue;
    if (typenum.equals(SemanticSymbolType.ARRAY))
      return arrayValue;

    return null;
  }



}
