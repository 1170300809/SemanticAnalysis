package semantic;

/**
 * 栈符号，包括符号和值
 * 
 * @author lenovo
 *
 */
public class StackSymbol {

  public StackSymbol(String sym, Double m) {
    symString = sym;
    value = new Value(m);
  }

  public StackSymbol(String sym, Integer m) {
    symString = sym;
    value = new Value(m);
  }

  public StackSymbol(String sym, String m) {
    symString = sym;
    value = new Value(m);
  }

  public StackSymbol(String sym, Character m) {
    symString = sym;
    value = new Value(m);
  }

  public StackSymbol(String sym, Object[] m) {
    symString = sym;
    value = new Value(m);
  }

  public StackSymbol(String sym, Enum<SemanticSymbolType> m) {
    symString = sym;
    value = new Value(m);
  }

  String symString;
  Value value;

  /**
   * 
   * @return 符号
   */
  public String getSymbol() {
    return symString;
  }

  /**
   * 
   * @return 值
   */
  public Object getValue() {
    return value.getValue();
  }

  /**
   * 
   * @return 类型为String 的值
   */
  public String getString() {

    return (String) value.getValue();
  }

  /**
   * 
   * @return 类型为Double 的值
   */
  public Double getDouble() {
    return (Double) value.getValue();
  }

  /**
   * 
   * @return 类型为Inetger 的值
   */
  public Integer getInteger() {
    return (Integer) value.getValue();
  }

  /**
   * 
   * @return 类型为Character 的值
   */
  public Character getChar() {
    return (Character) value.getValue();
  }


  /**
   * 
   * @return 类型为Object[]的值
   */
  public Object[] getArray() {
    return (Object[]) value.getValue();
  }



  @Override
  public String toString() {
    return symString + " : " + value.getValue().toString();
  }

  public void execute(SemanticStack ssk, SemanticDataManager sdm) {
    // TODO
  }

}
