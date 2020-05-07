package semantic;

/**
 * 栈符号，包括符号和值
 * 
 * @author lenovo
 */
public class StackSymbol {

  public StackSymbol(String sym, Double m) {
    this.symString = sym;
    this.value = new Value(m);
  }

  public StackSymbol(String sym, Integer m) {
    this.symString = sym;
    this.value = new Value(m);
  }

  public StackSymbol(String sym, String m) {
    this.symString = sym;
    this.value = new Value(m);
  }

  public StackSymbol(String sym, Character m) {
    this.symString = sym;
    this.value = new Value(m);
  }

  public StackSymbol(String sym, Object[] m) {
    this.symString = sym;
    this.value = new Value(m);
  }

  public StackSymbol(String sym, Enum<SymbolDataType> m) {
    this.symString = sym;
    this.value = new Value(m);
  }

  String symString;
  Value value;

  /**
   * @return 符号
   */
  public String getSymbol() {
    return this.symString;
  }

  /**
   * @return 值
   */
  public Object getValue() {
    return this.value.getValue();
  }

  /**
   * @return 类型为String 的值
   */
  public String getString() {

    return (String) this.value.getValue();
  }

  /**
   * @return 类型为Double 的值
   */
  public Double getDouble() {
    return (Double) this.value.getValue();
  }

  /**
   * @return 类型为Inetger 的值
   */
  public Integer getInteger() {
    return (Integer) this.value.getValue();
  }

  /**
   * @return 类型为Character 的值
   */
  public Character getChar() {
    return (Character) this.value.getValue();
  }

  /**
   * @return 类型为Object[]的值
   */
  public Object[] getArray() {
    return (Object[]) this.value.getValue();
  }

  @Override
  public String toString() {
    return this.symString + " : " + this.value.getValue().toString();
  }

  public void execute(SemanticStack ssk, SemanticDataManager sdm) {
    // TODO
  }

}
