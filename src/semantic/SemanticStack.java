package semantic;

import java.util.ArrayList;

public class SemanticStack {

  ArrayList<StackSymbol> symbolList;

  public SemanticStack() {
    symbolList = new ArrayList<StackSymbol>();
  }

  /**
   * ��symbolѹ��ջ��
   * 
   * @param symbol
   */
  public void push(StackSymbol symbol) {
    symbolList.add(0, symbol);
  }

  /**
   * ��ջ�����ŵ���
   * 
   * @return ջ������
   */
  public StackSymbol pop() {
    StackSymbol returnStackSymbol = symbolList.get(0);
    symbolList.remove(0);
    return returnStackSymbol;

  }

  /**
   * ջ���µ�i��Ԫ��
   * 
   * @param i
   * @return ջ���µ�i��Ԫ��
   */
  public StackSymbol getSymbol(int i) {
    StackSymbol returnStackSymbol = symbolList.get(i);
    return returnStackSymbol;
  }

  /**
   * ��ջ��Ԫ��չʾ���ն��ϡ�
   */
  public void showStack() {
    for (int i = 0; i < symbolList.size(); i++) {
      System.out.println(symbolList.get(i).toString());
    }
  }

  /**
   * 
   * @return ջ�Ĵ�С
   */
  public int size() {
    return symbolList.size();
  }



}
