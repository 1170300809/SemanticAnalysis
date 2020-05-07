package semantic;

/**
 * ջ���ţ��������ź�ֵ
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

  String symString;
  Value value;

  /**
   * 
   * @return ����
   */
  public String getSymbol() {
    return symString;
  }

  /**
   * 
   * @return ֵ
   */
  public Object getValue() {
    return value.getValue();
  }

  /**
   * 
   * @return ����ΪString ��ֵ
   */
  public String getString() {

    return (String) value.getValue();
  }

  /**
   * 
   * @return ����ΪDouble ��ֵ
   */
  public Double getDouble() {
    return (Double) value.getValue();
  }

  /**
   * 
   * @return ����ΪInetger ��ֵ
   */
  public Integer getInteger() {
    return (Integer) value.getValue();
  }

  @Override
  public String toString() {
    return symString + " : " + value.getValue().toString();
  }

}
