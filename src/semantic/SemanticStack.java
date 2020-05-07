package semantic;

import java.util.ArrayList;

public class SemanticStack {

  ArrayList<StackSymbol> symbolList;

  public SemanticStack() {
    symbolList = new ArrayList<StackSymbol>();
  }

  /**
   * 将symbol压入栈中
   * 
   * @param symbol
   */
  public void push(StackSymbol symbol) {
    symbolList.add(0, symbol);
  }

  /**
   * 将栈顶符号弹出
   * 
   * @return 栈顶符号
   */
  public StackSymbol pop() {
    StackSymbol returnStackSymbol = symbolList.get(0);
    symbolList.remove(0);
    return returnStackSymbol;

  }

  /**
   * 栈顶下第i个元素
   * 
   * @param i
   * @return 栈顶下第i个元素
   */
  public StackSymbol getSymbol(int i) {
    StackSymbol returnStackSymbol = symbolList.get(i);
    return returnStackSymbol;
  }

  /**
   * 将栈内元素展示在终端上。
   */
  public void showStack() {
    for (int i = 0; i < symbolList.size(); i++) {
      System.out.println(symbolList.get(i).toString());
    }
  }

  /**
   * 
   * @return 栈的大小
   */
  public int size() {
    return symbolList.size();
  }



}
