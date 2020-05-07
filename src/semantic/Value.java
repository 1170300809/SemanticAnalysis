package semantic;

public class Value {

  int typenum;/* 类型标号，0位字符串，1为整形，2为浮点型 */

  String stringValue = null;
  Integer intValue = null;
  Double doubleValue = null;


  // 初始化函数
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
   * @return 根据类型返回Value的真实值
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
