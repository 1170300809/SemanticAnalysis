package semantic;

public class Value {

  Enum<SymbolDataType> typenum;/* 类型标号，0位字符串，1为整形，2为浮点型 */

  String stringValue = null;
  Integer intValue = null;
  Double doubleValue = null;
  Character charValue = null;
  Object[] arrayValue = null;

  // 初始化函数
  public Value(String v) {
    this.typenum = SymbolDataType.STRING;
    this.stringValue = v;
  }

  public Value(Integer v) {
    this.typenum = SymbolDataType.INT;
    this.intValue = v;
  }

  public Value(Double v) {
    this.typenum = SymbolDataType.DOUBLE;
    this.doubleValue = v;
  }

  public Value(Character v) {
    this.typenum = SymbolDataType.CHAR;
    this.charValue = v;
  }

  public Value(Object[] v) {
    this.typenum = SymbolDataType.ARRAY;
    this.arrayValue = v;
  }

  public Value(Enum<SymbolDataType> i) {
    this.typenum = i;
  }

  /**
   * @return 根据类型返回Value的真实值
   */
  public Object getValue() {
    if (this.typenum.equals(SymbolDataType.STRING)) {
      return this.stringValue;
    }
    if (this.typenum.equals(SymbolDataType.DOUBLE)) {
      return this.doubleValue;
    }
    if (this.typenum.equals(SymbolDataType.INT)) {
      return this.intValue;
    }
    if (this.typenum.equals(SymbolDataType.CHAR)) {
      return this.charValue;
    }
    if (this.typenum.equals(SymbolDataType.ARRAY)) {
      return this.arrayValue;
    }

    return null;
  }

}
