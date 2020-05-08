package analyzer;

import java.util.List;

public class Production {

  protected int productionNo;
  protected String leftHandSide;
  protected String[] rightHandSide;
  protected List<String> selectSet;

  public String getErrorMsg() {
    return null;
  }

  public String getSolutionMsg() {
    return null;
  }

  @Override
  public String toString() {
    String str = this.leftHandSide + " -->";

    if (this.rightHandSide.length == 0) {
      str += " ξ";
    }

    for (int i = 0; i < this.rightHandSide.length; i++) {
      str += " " + this.rightHandSide[i];
    }

    return str;
  }

  public List<String> getSelectSet() {
    return this.selectSet;
  }

  public void setSelectSet(List<String> select) {
    this.selectSet = select;
  }

  public int getProdcutionNo() {
    return this.productionNo;
  }

  public String getLHS() {
    return this.leftHandSide;
  }

  public String[] getRHS() {
    return this.rightHandSide;
  }

  public Production() {
  }

  public Production(int productionNo, String left) {
    this.productionNo = productionNo;
    this.leftHandSide = left;
    this.rightHandSide = new String[0];
  }

  public Production(int productionNo, String left, String symbol1) {
    this.productionNo = productionNo;
    this.leftHandSide = left;
    this.rightHandSide = new String[1];
    this.rightHandSide[0] = symbol1;
  }

  public Production(int productionNo, String left, String symbol1,
      String symbol2) {
    this.productionNo = productionNo;
    this.leftHandSide = left;
    this.rightHandSide = new String[2];
    this.rightHandSide[0] = symbol1;
    this.rightHandSide[1] = symbol2;
  }

  public Production(int productionNo, String left, String symbol1,
      String symbol2, String symbol3) {
    this.productionNo = productionNo;
    this.leftHandSide = left;
    this.rightHandSide = new String[3];
    this.rightHandSide[0] = symbol1;
    this.rightHandSide[1] = symbol2;
    this.rightHandSide[2] = symbol3;
  }

  public Production(int productionNo, String left, String symbol1,
      String symbol2, String symbol3, String symbol4) {
    this.productionNo = productionNo;
    this.leftHandSide = left;
    this.rightHandSide = new String[4];
    this.rightHandSide[0] = symbol1;
    this.rightHandSide[1] = symbol2;
    this.rightHandSide[2] = symbol3;
    this.rightHandSide[3] = symbol4;
  }

  public Production(int productionNo, String left, String symbol1,
      String symbol2, String symbol3, String symbol4, String symbol5) {
    this.productionNo = productionNo;
    this.leftHandSide = left;
    this.rightHandSide = new String[5];
    this.rightHandSide[0] = symbol1;
    this.rightHandSide[1] = symbol2;
    this.rightHandSide[2] = symbol3;
    this.rightHandSide[3] = symbol4;
    this.rightHandSide[4] = symbol5;
  }

  public Production(int productionNo, String left, String symbol1,
      String symbol2, String symbol3, String symbol4, String symbol5,
      String symbol6) {
    this.productionNo = productionNo;
    this.leftHandSide = left;
    this.rightHandSide = new String[6];
    this.rightHandSide[0] = symbol1;
    this.rightHandSide[1] = symbol2;
    this.rightHandSide[2] = symbol3;
    this.rightHandSide[3] = symbol4;
    this.rightHandSide[4] = symbol5;
    this.rightHandSide[5] = symbol6;
  }

  public Production(int productionNo, String left, String symbol1,
      String symbol2, String symbol3, String symbol4, String symbol5,
      String symbol6, String symbol7) {
    this.productionNo = productionNo;
    this.leftHandSide = left;
    this.rightHandSide = new String[7];
    this.rightHandSide[0] = symbol1;
    this.rightHandSide[1] = symbol2;
    this.rightHandSide[2] = symbol3;
    this.rightHandSide[3] = symbol4;
    this.rightHandSide[4] = symbol5;
    this.rightHandSide[5] = symbol6;
    this.rightHandSide[6] = symbol7;
  }

  public Production(int productionNo, String left, String symbol1,
      String symbol2, String symbol3, String symbol4, String symbol5,
      String symbol6, String symbol7, String symbol8) {
    this.productionNo = productionNo;
    this.leftHandSide = left;
    this.rightHandSide = new String[8];
    this.rightHandSide[0] = symbol1;
    this.rightHandSide[1] = symbol2;
    this.rightHandSide[2] = symbol3;
    this.rightHandSide[3] = symbol4;
    this.rightHandSide[4] = symbol5;
    this.rightHandSide[5] = symbol6;
    this.rightHandSide[6] = symbol7;
    this.rightHandSide[7] = symbol8;
  }

  public Production(int productionNo, String left, String symbol1,
      String symbol2, String symbol3, String symbol4, String symbol5,
      String symbol6, String symbol7, String symbol8, String symbol9) {
    this.productionNo = productionNo;
    this.leftHandSide = left;
    this.rightHandSide = new String[9];
    this.rightHandSide[0] = symbol1;
    this.rightHandSide[1] = symbol2;
    this.rightHandSide[2] = symbol3;
    this.rightHandSide[3] = symbol4;
    this.rightHandSide[4] = symbol5;
    this.rightHandSide[5] = symbol6;
    this.rightHandSide[6] = symbol7;
    this.rightHandSide[7] = symbol8;
    this.rightHandSide[8] = symbol9;
  }
}
