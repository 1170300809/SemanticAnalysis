package analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A production is composed of left hand side and right hand side. The
 * left hand side must be a nonterminal, while the right hand side can
 * be any valid grammar symbols.
 * 
 * @author AtoshDustosh
 */
public class Production {
  public static final int PRODUCTION_INVALID = 0;

  public static final String DERIVATION_SYMBOL = "->";

  private GrammarSymbol leftHandSide = new GrammarSymbol();
  private List<List<GrammarSymbol>> rightHandSide = new ArrayList<>();

  public Production() {

  }

  public Production(List<GrammarSymbol> symbolList) {
    this.parseGrammarSymbolList(symbolList);
  }

  public Production(GrammarSymbol leftHandSide,
      List<List<GrammarSymbol>> rightHandSide) {
    this.leftHandSide = leftHandSide;
    this.rightHandSide = rightHandSide;
  }

  public Production(Production production) {
    this.leftHandSide = production.getLHS();
    this.rightHandSide = production.getRHSlist();
  }

  public static void main(String[] args) {
    List<GrammarSymbol> symbolList = new ArrayList<>();
    symbolList.add(new GrammarSymbol("A"));
    symbolList.add(new GrammarSymbol("->"));
    symbolList.add(new GrammarSymbol("A_1"));
    symbolList.add(new GrammarSymbol("|"));
    symbolList.add(new GrammarSymbol("digit"));
    symbolList.add(new GrammarSymbol(";"));

    Production production = new Production(symbolList);
    System.out.println("production: \n" + production.toString());

  }

  public GrammarSymbol getLHS() {
    return this.leftHandSide;
  }

  public List<List<GrammarSymbol>> getRHSlist() {
    List<List<GrammarSymbol>> rhs = new ArrayList<>();
    for (int i = 0; i < this.rightHandSide.size(); i++) {
      rhs.add(new ArrayList<>(this.rightHandSide.get(i)));
    }
    return rhs;
  }

  /**
   * Add a new RHS into the production.
   * 
   * @param newRHS new RHS
   * @return true if added successfully (no repetition) ; false
   *         otherwise
   */
  public boolean addNewRHS(List<GrammarSymbol> newRHS) {
    for (int i = 0; i < this.rightHandSide.size(); i++) {
      if (newRHS.equals(this.rightHandSide.get(i))) {
        return false;
      }
    }
    this.rightHandSide.add(newRHS);
    return true;
  }

  public void setLHS(GrammarSymbol LHS) {
    this.leftHandSide = new GrammarSymbol(LHS);
  }

  public List<Production> breakIntoPieces() {
    List<Production> pieces = new ArrayList<>();
    for (int i = 0; i < this.rightHandSide.size(); i++) {
      List<GrammarSymbol> piecedRHS = new ArrayList<>(
          this.rightHandSide.get(i));
      List<List<GrammarSymbol>> newRHS = new ArrayList<>(
          Arrays.asList(piecedRHS));
      Production piece = new Production(this.leftHandSide, newRHS);
      pieces.add(piece);
    }
    return pieces;
  }

  @Override
  public String toString() {
    String str = "";
    str = str + this.leftHandSide.getName() + "\t->";
    for (int i = 0; i < this.rightHandSide.size(); i++) {
      String rhsPiece = "";
      List<GrammarSymbol> productionPiece = this.rightHandSide.get(i);
      for (int j = 0; j < productionPiece.size(); j++) {
        rhsPiece = rhsPiece + productionPiece.get(j).getName().toString() + " ";
      }
      rhsPiece = "\t" + rhsPiece;
      if (i > 0) {
        rhsPiece = "\tor" + rhsPiece;
      }
      str = str + rhsPiece + "\n";
    }
    return str;
  }

  public boolean equals(Production production) {
    GrammarSymbol LHS = production.getLHS();
    List<List<GrammarSymbol>> rhsList = production.getRHSlist();
    boolean result = LHS.equals(this.leftHandSide);
    result = result && (rhsList.size() == this.rightHandSide.size());
    if (result == false) {
      return false;
    }
    for (int i = 0; i < rhsList.size(); i++) {
      List<GrammarSymbol> thatRHS = rhsList.get(i);
      for (int j = 0; j < this.rightHandSide.size(); j++) {
        List<GrammarSymbol> thisRHS = this.rightHandSide.get(j);
        result = result && (thatRHS.equals(thisRHS));
      }
    }
    return result;
  }

  @Override
  public int hashCode() {
    String str = this.leftHandSide.toString();
    for (int i = 0; i < this.rightHandSide.size(); i++) {
      for (int j = 0; j < this.rightHandSide.get(i).size(); j++) {
        str = str + this.rightHandSide.get(i).get(j).toString();
      }
    }
    return str.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return this.equals((Production) o);
  }

  private void parseGrammarSymbolList(List<GrammarSymbol> symbolList) {
    if (this.checkGrammarSymbolList(symbolList) == false) {
      System.exit(PRODUCTION_INVALID);
    }
    this.leftHandSide = symbolList.get(0);
    List<GrammarSymbol> rhsPiece = new ArrayList<>();
    for (int i = 2; i < symbolList.size(); i++) {
      GrammarSymbol symbol = symbolList.get(i);
      if (symbol.getType().equals(GrammarSymbolType.OR) == false) {
        rhsPiece.add(symbol);
      } else {
        this.rightHandSide.add(rhsPiece);
        rhsPiece = new ArrayList<>();
      }
    }
    if (rhsPiece.size() > 0) {
      this.rightHandSide.add(rhsPiece);
    }
  }

  private boolean checkGrammarSymbolList(List<GrammarSymbol> symbolList) {
    if (symbolList.size() < 3) {
      System.out.println(
          "error: production invalid - too short - " + symbolList.toString());
      return false;
    }
    if (symbolList.get(0).isTerminalSymbol()
        || symbolList.get(1).getName().equals(DERIVATION_SYMBOL) == false) {
      System.out
          .println("error: production invalid - first grammar symbol invalid");
      System.out.println(symbolList.get(0).toString());
      return false;
    }
    for (int i = 2; i < symbolList.size(); i++) {
      if (symbolList.get(i).getType().equals(GrammarSymbolType.UNDEFINED)) {
        System.out
            .println("error: production invalid - right hand symbol invalid");
        return false;
      }
    }
    return true;
  }

}
