package analyzer;

public class ErrorProduction extends Production {

  String errorMsg; // description of the error
  String solutionMsg; // description of the solution to the error

  @Override
  public String getErrorMsg() {
    return this.errorMsg;
  }

  public void setErrorMsg(String error) {
    this.errorMsg = error;
  }

  @Override
  public String getSolutionMsg() {
    return this.solutionMsg;
  }

  public void setSolutionMsg(String solution) {
    this.solutionMsg = solution;
  }

  public ErrorProduction(Production father) {
    this.productionNo = father.productionNo;
    this.leftHandSide = father.leftHandSide;
    this.rightHandSide = father.rightHandSide;
  }

  public ErrorProduction(int productionNo, String left, String symbol1,
      String symbol2,
      String symbol3, String symbol4, String symbol5, String symbol6,
      String symbol7, String symbol8, String symbol9) {
    super(productionNo, left, symbol1, symbol2, symbol3, symbol4, symbol5,
        symbol6,
        symbol7,
        symbol8, symbol9);
  }

  public ErrorProduction(int productionNo, String left, String symbol1,
      String symbol2,
      String symbol3, String symbol4, String symbol5, String symbol6,
      String symbol7, String symbol8) {
    super(productionNo, left, symbol1, symbol2, symbol3, symbol4, symbol5,
        symbol6,
        symbol7,
        symbol8);
  }

  public ErrorProduction(int productionNo, String left, String symbol1,
      String symbol2,
      String symbol3, String symbol4, String symbol5, String symbol6,
      String symbol7) {
    super(productionNo, left, symbol1, symbol2, symbol3, symbol4, symbol5,
        symbol6,
        symbol7);
  }

  public ErrorProduction(int productionNo, String left, String symbol1,
      String symbol2,
      String symbol3, String symbol4, String symbol5, String symbol6) {
    super(productionNo, left, symbol1, symbol2, symbol3, symbol4, symbol5,
        symbol6);
  }

  public ErrorProduction(int productionNo, String left, String symbol1,
      String symbol2,
      String symbol3, String symbol4, String symbol5) {
    super(productionNo, left, symbol1, symbol2, symbol3, symbol4, symbol5);
  }

  public ErrorProduction(int productionNo, String left, String symbol1,
      String symbol2,
      String symbol3, String symbol4) {
    super(productionNo, left, symbol1, symbol2, symbol3, symbol4);
  }

  public ErrorProduction(int productionNo, String left, String symbol1,
      String symbol2,
      String symbol3) {
    super(productionNo, left, symbol1, symbol2, symbol3);
  }

  public ErrorProduction(int productionNo, String left, String symbol1,
      String symbol2) {
    super(productionNo, left, symbol1, symbol2);
  }

  public ErrorProduction(int productionNo, String left, String symbol1) {
    super(productionNo, left, symbol1);
  }

  public ErrorProduction(int productionNo, String left) {
    super(productionNo, left);
  }
}
