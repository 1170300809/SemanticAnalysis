package semantic;

public class Value {

  Enum<SemanticSymbolType> typenum;/* ���ͱ�ţ�0λ�ַ�����1Ϊ���Σ�2Ϊ������ */

  String stringValue = null;
  Integer intValue = null;
  Double doubleValue = null;
  Character charValue = null;
  Object[] arrayValue = null;



  // ��ʼ������
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
   * @return �������ͷ���Value����ʵֵ
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
