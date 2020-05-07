package semantic;

public class Value {

  int typenum;/* ���ͱ�ţ�0λ�ַ�����1Ϊ���Σ�2Ϊ������ */

  String stringValue = null;
  Integer intValue = null;
  Double doubleValue = null;


  // ��ʼ������
  public Value(String v) {
    typenum = 0;
    stringValue = v;
  }

  public Value(Integer v) {
    typenum = 1;
    intValue = v;
  }

  public Value(Double v) {
    typenum = 2;
    doubleValue = v;
  }

  /**
   * 
   * @return �������ͷ���Value����ʵֵ
   */
  public Object getValue() {
    switch (typenum) {
      case 0:
        return stringValue;
      case 1:
        return intValue;
      case 2:
        return doubleValue;
      default:
        return null;
    }
  }



}
