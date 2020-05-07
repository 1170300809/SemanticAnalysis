package analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import data.input.InputFilePaths;

public class PredictiveParsingTableConstructor {

  private Productions productions = null;

  private boolean firstSetBuilt = false;
  private FirstSets firstSets = new FirstSets();
  private boolean followSetBuilt = false;
  private FollowSets followSets = new FollowSets();
  private boolean selectSetBuilt = false;
  private SelectSets selectSets = new SelectSets();

  private PredictiveParsingTable ppTable = null;
  private boolean ppTableBuilt = false;

  public PredictiveParsingTableConstructor(Productions productions) {
    this.loadProductions(productions);
  }

  public static void main(String[] args) {
    Productions productions = new Productions(
        InputFilePaths.PRODUCTIONS0.getFilePath());
    PredictiveParsingTableConstructor pptConstructor = new PredictiveParsingTableConstructor(
        productions);

    System.out.println("Productions: \n" + productions.toString());
    System.out.println("... data loaded");

    System.out
        .println("First Sets: \n" + pptConstructor.getFirstSets().toString());
    System.out
        .println("Follow Sets: \n" + pptConstructor.getFollowSets().toString());
    System.out
        .println("Select Sets: \n" + pptConstructor.getSelectSets().toString());
    System.out.println(pptConstructor.getPPTable().toString());

  }

  private void loadProductions(Productions productions) {
    this.productions = productions;
    this.buildFirstSets();
    this.buildFollowSets();
    this.buildSelectSets();
    if (this.isLL1Grammar()) {
      this.buildPredictiveParsingTable();
    } else {
      System.out.println("error: not LL(1) grammar\n");
    }
  }

  public FirstSets getFirstSets() {
    return this.firstSets.copy();
  }

  public FollowSets getFollowSets() {
    return this.followSets.copy();
  }

  public SelectSets getSelectSets() {
    return this.selectSets.copy();
  }

  public PredictiveParsingTable getPPTable() {
    return this.ppTable.copy();
  }

  private void buildFirstSets() {
    // finished - whether the FIRST set of a nonterminal is calculated
    Map<String, Boolean> finished = new HashMap<>();
    // initialize the finished map
    for (int i = 0; i < this.productions.size(); i++) {
      GrammarSymbol LHS = this.productions.getProduction(i).getLHS();
      finished.put(LHS.toString(), false);
    }
    // select a production to process with recursive procedure
    for (int i = 0; i < this.productions.size(); i++) {
      Production production = this.productions.getProduction(i);
      if (finished.get(production.getLHS().toString()) == true) {
        continue;
      } else {
        this.recursiveBuildFirstSet(production, finished);
      }
    }
    this.firstSetBuilt = true;
  }

  /**
   * Recursively build the first set of productions.
   * 
   * @param production production whose first set is to be built
   * @param finished   a map from the string of left hand side symbol of
   *                   the production to the boolean whether the first
   *                   set of this LHS symbol is built.
   */
  private void recursiveBuildFirstSet(Production production,
      Map<String, Boolean> finished) {
//    System.out.println("select production - " + production.toString());
    GrammarSymbol LHS = production.getLHS();
    List<List<GrammarSymbol>> rhsList = production.getRHSlist();
    // process all RHS of a production one by one
    for (int i = 0; i < rhsList.size(); i++) {
      List<GrammarSymbol> RHS = rhsList.get(i);
      int emptyCount = 0;
      // process all GrammarSymbol of an RHS one by one
      for (int j = 0; j < RHS.size(); j++) {
        GrammarSymbol symbol = RHS.get(j);
//        System.out.println("process symbol: " + symbol);
        if (symbol.equals(LHS)) {
          break;
        } else if (symbol.getType().equals(GrammarSymbolType.TERMINAL)) {
          Set<GrammarSymbol> tempSet = new HashSet<>();
          tempSet.add(symbol);
          this.firstSets.addFirstSet(LHS, new HashSet<>(tempSet));
          if (symbol.equals(new GrammarSymbol("empty"))) {
            emptyCount++;
          }
          break;
        } else if (symbol.getType().equals(GrammarSymbolType.NONTERMINAL)) {
          boolean ifFirstSetFinished = finished.get(symbol.toString());
          if (ifFirstSetFinished) {
            Set<GrammarSymbol> newFirstSet = this.firstSets
                .getFirstSet(symbol);
            if (emptyCount == j) {
              this.firstSets.addFirstSet(LHS, new HashSet<>(newFirstSet));
            }
            if (newFirstSet.contains(new GrammarSymbol("empty"))) {
              emptyCount++;
            } else {
              break;
            }
          } else {
            Production newPro = this.productions.getProduction(symbol);
            this.recursiveBuildFirstSet(newPro, finished);
            j = j - 1;
          }
        } else {
          System.out.println("error - invalid production");
          System.exit(-1);
        }
      } // end of loop (j)
      if (emptyCount == RHS.size()) {
        Set<GrammarSymbol> tempSet = new HashSet<>();
        tempSet.add(new GrammarSymbol("empty"));
        this.firstSets.addFirstSet(LHS, new HashSet<>(tempSet));
      } else {
        this.firstSets.removeFirstSetItem(LHS, new GrammarSymbol("empty"));
      }
    } // end of loop (i)
    finished.put(LHS.toString(), true);
//    System.out.println("set Map finished of " + LHS.toString() + " to true");
  }

  private void buildFollowSets() {
    if (this.firstSetBuilt == false) {
      this.buildFirstSets();
    }
    boolean modified = false;
    do {
      modified = this.traverseBuildFollowSets();
    } while (modified);
    this.followSetBuilt = true;
  }

  /**
   * Traverse all productions and build Follow Sets for grammar symbols
   * in them.
   * 
   * @return true if modified FollowSets after this traverse; false
   *         otherwise
   */
  private boolean traverseBuildFollowSets() {
    boolean modified = false;
    for (int i = 0; i < this.productions.size(); i++) {
      Production production = this.productions.getProduction(i);
      GrammarSymbol LHS = production.getLHS();
      List<List<GrammarSymbol>> rhsList = production.getRHSlist();
      if (i == 0) {
        Set<GrammarSymbol> tempFollowSet = new HashSet<>();
        tempFollowSet.add(new GrammarSymbol("$"));
        modified = modified || this.followSets.addFollowSet(LHS, tempFollowSet);
      }
//      System.out.println("process production: \n" + production);
      for (int j = 0; j < rhsList.size(); j++) {
        List<GrammarSymbol> RHS = rhsList.get(j);
        for (int k = 0; k < RHS.size(); k++) {
          GrammarSymbol symbol = RHS.get(k);
          if (symbol.getType().equals(GrammarSymbolType.TERMINAL)) {
            continue;
          } else if (symbol.getType().equals(GrammarSymbolType.NONTERMINAL)) {
            Set<GrammarSymbol> lhsFollowSet = this.followSets
                .getFollowSet(LHS);
            if (k == RHS.size() - 1) {
              modified = modified
                  || this.followSets.addFollowSet(symbol, lhsFollowSet);
            } else {
              Set<GrammarSymbol> subRHSfirstSet = new HashSet<>();
              List<GrammarSymbol> subRHS = new ArrayList<>();
              if (k + 1 == RHS.size() - 1) {
                subRHS = new ArrayList<>(
                    Arrays.asList(RHS.get(RHS.size() - 1)));
              } else {
                subRHS = RHS.subList(k + 1, RHS.size());
              }
              subRHSfirstSet = this.firstSetofSymbolList(subRHS);
              modified = modified
                  || this.followSets.addFollowSet(symbol, subRHSfirstSet);
              if (subRHSfirstSet.contains(new GrammarSymbol("empty"))) {
                modified = modified
                    || this.followSets.addFollowSet(symbol, lhsFollowSet);
              }
            } // end of judging whether k is the last of RHS
          } // end of judging symbol being terminal or nonterminal
        } // end of loop (k)
      } // end of loop (j)
    } //end of loop (i)
    return modified;
  }

  private void buildSelectSets() {
    if (this.followSetBuilt == false) {
      this.buildFollowSets();
    }
    Productions brokenProductions = this.productions.breakIntoPieces();
    for (int i = 0; i < brokenProductions.size(); i++) {
      Production piece = brokenProductions.getProduction(i);
      GrammarSymbol LHS = piece.getLHS();
      List<GrammarSymbol> RHS = piece.getRHSlist().get(0);
      Set<GrammarSymbol> rhsFirstSet = this.firstSetofSymbolList(RHS);
      if (rhsFirstSet.contains(new GrammarSymbol("empty"))) {
        Set<GrammarSymbol> lhsFollowSet = this.followSets.getFollowSet(LHS);
        rhsFirstSet.remove(new GrammarSymbol("empty"));
        this.selectSets.addSelectSet(piece, rhsFirstSet);
        this.selectSets.addSelectSet(piece, lhsFollowSet);
      } else {
        this.selectSets.addSelectSet(piece, rhsFirstSet);
      }
    }
    this.selectSetBuilt = true;
  }

  private void buildPredictiveParsingTable() {
    if (this.selectSetBuilt == false) {
      this.buildSelectSets();
    }
    this.ppTable = new PredictiveParsingTable(this.productions);
    Productions pieces = this.productions.breakIntoPieces();
    // add production entries
    for (int i = 0; i < pieces.size(); i++) {
      Production piece = pieces.getProduction(i);
      Set<GrammarSymbol> pieceSelectSet = this.selectSets.getSelectSet(piece);
      GrammarSymbol LHS = piece.getLHS();
      for (GrammarSymbol symbol : pieceSelectSet) {
        this.ppTable.setProductionTableEntry(LHS, symbol, piece);
      }
    }
    // add synch entries
    Set<GrammarSymbol> nonterminalSet = this.productions.getNonterminalSet();
    for (GrammarSymbol nonterminal : nonterminalSet) {
      Set<GrammarSymbol> followSet = this.followSets.getFollowSet(nonterminal);
      for (GrammarSymbol terminal : followSet) {
        this.ppTable.setSynchTableEntry(nonterminal, terminal, true);
      }
    }
    this.ppTableBuilt = true;
  }

  /**
   * Calculate the First set of a list of grammar symbols.
   * 
   * @param list grammar symbols organized into a list
   * @return the First set of the grammar list
   */
  private Set<GrammarSymbol> firstSetofSymbolList(List<GrammarSymbol> list) {
    Set<GrammarSymbol> firstSet = new HashSet<>();
    int emptyCount = 0;
    // process all grammar symbols in the input list
    for (int i = 0; i < list.size(); i++) {
      GrammarSymbol symbol = list.get(i);
      if (symbol.getType().equals(GrammarSymbolType.TERMINAL)) {
        firstSet.add(new GrammarSymbol(symbol));
        if (symbol.equals(new GrammarSymbol("empty"))) {
          emptyCount++;
        }
        break;
      } else if (symbol.getType().equals(GrammarSymbolType.NONTERMINAL)) {
        Set<GrammarSymbol> symbolFirstSet = this.firstSets
            .getFirstSet(symbol);
        firstSet.addAll(symbolFirstSet);
        if (symbolFirstSet.contains(new GrammarSymbol("empty")) == false) {
          break;
        } else {
          emptyCount++;
        }
      }
    }
    if (emptyCount < list.size()) {
      firstSet.remove(new GrammarSymbol("empty"));
    } else if (emptyCount == list.size()) {
      firstSet.add(new GrammarSymbol("empty"));
    }
    return firstSet;
  }

  private boolean isLL1Grammar() {
    // check every production
    System.out.println("checking LL(1) grammar...");
    for (int i = 0; i < this.productions.size(); i++) {
      Production p = this.productions.getProduction(i);
      GrammarSymbol LHS = p.getLHS();
      Set<GrammarSymbol> lhsFollowSet = this.followSets.getFollowSet(LHS);
      List<List<GrammarSymbol>> rhsList = p.getRHSlist();
      boolean canInferEmpty = false;
//      System.out.println("production: \n" + p.toString());
      // check the RHS list of a LHS
      for (int j = 0; j < rhsList.size(); j++) {
        List<GrammarSymbol> RHS = rhsList.get(j);
        Set<GrammarSymbol> rhsFirstSet = this.firstSetofSymbolList(RHS);
        // check every RHS in the list whether they can infer grammar string "empty"
        if (rhsFirstSet.contains(new GrammarSymbol("empty"))) {
          Set<GrammarSymbol> otherRHSfirstSets = new HashSet<>();
          for (int k = 0; k < rhsList.size(); k++) {
            if (k != j) {
              List<GrammarSymbol> otherRHS = rhsList.get(k);
              Set<GrammarSymbol> otherRHSfirstSet = this
                  .firstSetofSymbolList(otherRHS);
              otherRHSfirstSets.addAll(otherRHSfirstSet);
            }
          }
          otherRHSfirstSets.retainAll(lhsFollowSet);
          if (otherRHSfirstSets.size() > 0) {
            System.out.println("production: \n" + p.toString());
            System.out.println("RHS first set: \n" + rhsFirstSet.toString());
            System.out.println(
                "other RHS first set: \n" + otherRHSfirstSets.toString());
            System.out.println("LHS follow set: \n" + lhsFollowSet.toString());

            return false;
          }
          canInferEmpty = true;
        } // end of checking "empty"
      } // end of checking RHS list
      if (canInferEmpty == false) {
        Set<GrammarSymbol> intersection = new HashSet<>();
        if (rhsList.size() > 1) {
          intersection.addAll(rhsList.get(0));
        }
        for (int j = 0; j < rhsList.size(); j++) {
          List<GrammarSymbol> RHS = rhsList.get(j);
          Set<GrammarSymbol> rhsFirstSet = this.firstSetofSymbolList(RHS);
//          System.out.println("RHS: " + RHS + " - first set: " + rhsFirstSet);
          intersection.retainAll(rhsFirstSet);
        }
        if (intersection.isEmpty() == false) {
          System.out.println("production: \n" + p.toString());
          System.out.println("LHS follow set: \n" + lhsFollowSet.toString());
          System.out.println("remained gS: \n" + intersection.toString());
          return false;
        }
      }
    } // end of checking this production
    System.out.println("... checked - is LL(1) grammar");
    return true;
  }
}
