package analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PredictiveParsingTable {

  private Map<GrammarSymbol, Integer> rowIndexMap = new HashMap<>(); // nonterminal to integer
  private Map<GrammarSymbol, Integer> columnIndexMap = new HashMap<>(); // terminal to integer
  private List<List<Production>> analysisTable = new ArrayList<>();
  private List<List<Boolean>> synchTable = new ArrayList<>();

  private Productions productions = new Productions(new ArrayList<>());

  public PredictiveParsingTable(Productions productions) {
    this.productions = productions;
    Set<GrammarSymbol> rowSet = productions.getNonterminalSet();
    Set<GrammarSymbol> columnSet = productions.getTerminalSet();
    int rowIndex = 0;
    int columnIndex = 0;
    // initialize the maps
    columnSet.remove(new GrammarSymbol("empty"));
    columnSet.add(new GrammarSymbol("$"));
    for (GrammarSymbol symbol : rowSet) {
      this.rowIndexMap.put(symbol, rowIndex++);
    }
    for (GrammarSymbol symbol : columnSet) {
      this.columnIndexMap.put(symbol, columnIndex++);
    }
    if (rowSet.size() == 0 || columnSet.size() == 0) {
      return;
    }
    // initialize the tables
    for (int i = 0; i < rowIndex; i++) {
      List<Production> analysisTableRow = new ArrayList<>();
      List<Boolean> synchTableRow = new ArrayList<>();
      for (int j = 0; j < columnIndex; j++) {
        analysisTableRow.add(new Production());
        synchTableRow.add(false);
      }
      this.analysisTable.add(analysisTableRow);
      this.synchTable.add(synchTableRow);
    }

  }

  public int getRowCount() {
    return this.rowIndexMap.keySet().size();
  }

  public int getColumnCount() {
    return this.columnIndexMap.keySet().size();
  }

  public Productions getProductions() {
    return this.productions.copy();
  }

  public boolean setProductionTableEntry(GrammarSymbol nonterminal,
      GrammarSymbol terminal,
      Production newEntry) {
    if (nonterminal.getType().equals(GrammarSymbolType.NONTERMINAL)
        && terminal.getType().equals(GrammarSymbolType.TERMINAL)) {
      int rowIndex = this.rowIndexMap.get(nonterminal);
      int columnIndex = this.columnIndexMap.get(terminal);
      Production oldEntry = this.analysisTable.get(rowIndex).get(columnIndex);
      if (oldEntry.equals(newEntry)) {
        return false;
      } else {
        this.analysisTable.get(rowIndex).set(columnIndex, newEntry);
        return true;
      }
    } else {
      return false;
    }
  }

  public Production getProductionTableEntry(GrammarSymbol nonterminal,
      GrammarSymbol terminal) {
    if (nonterminal.getType().equals(GrammarSymbolType.NONTERMINAL)
        && terminal.getType().equals(GrammarSymbolType.TERMINAL)) {
      int rowIndex = this.rowIndexMap.get(nonterminal);
      int columnIndex = this.columnIndexMap.get(terminal);
      return this.analysisTable.get(rowIndex).get(columnIndex);
    } else {
      return new Production();
    }
  }

  public boolean setSynchTableEntry(GrammarSymbol nonterminal,
      GrammarSymbol terminal,
      boolean newEntry) {
    if (nonterminal.getType().equals(GrammarSymbolType.NONTERMINAL)
        && terminal.getType().equals(GrammarSymbolType.TERMINAL)) {
      int rowIndex = this.rowIndexMap.get(nonterminal);
      int columnIndex = this.columnIndexMap.get(terminal);
      boolean oldEntry = this.synchTable.get(rowIndex).get(columnIndex);
      if (oldEntry == newEntry) {
        return false;
      } else {
        this.synchTable.get(rowIndex).set(columnIndex, newEntry);
        return true;
      }
    } else {
      return false;
    }
  }

  public boolean getSynchTableEntry(GrammarSymbol nonterminal,
      GrammarSymbol terminal) {
    if (nonterminal.getType().equals(GrammarSymbolType.NONTERMINAL)
        && terminal.getType().equals(GrammarSymbolType.TERMINAL)) {
      int rowIndex = this.rowIndexMap.get(nonterminal);
      int columnIndex = this.columnIndexMap.get(terminal);
      return this.synchTable.get(rowIndex).get(columnIndex);
    } else {
      return false;
    }
  }

  public Set<GrammarSymbol> getRowSet() {
    return new HashSet<>(this.rowIndexMap.keySet());
  }

  public Set<GrammarSymbol> getColumnSet() {
    return new HashSet<>(this.columnIndexMap.keySet());
  }

  public PredictiveParsingTable copy() {
    PredictiveParsingTable ppTable = new PredictiveParsingTable(
        this.productions);
    for (GrammarSymbol rowSymbol : this.rowIndexMap.keySet()) {
      for (GrammarSymbol columnSymbol : this.columnIndexMap.keySet()) {
        ppTable.setProductionTableEntry(rowSymbol, columnSymbol,
            this.getProductionTableEntry(rowSymbol, columnSymbol));
        ppTable.setSynchTableEntry(rowSymbol, columnSymbol,
            this.getSynchTableEntry(rowSymbol, columnSymbol));
      }
    }
    return ppTable;
  }

  @Override
  public String toString() {
    String str = "";
    Productions piecesProductions = this.productions.breakIntoPieces();
    Map<Production, Integer> productionSerialNumMap = new HashMap<>();
    str = str + "Serial numbers of productions\n";
    for (int i = 0; i < piecesProductions.size(); i++) {
      str = str + "(" + (i + 1) + ")\n " + piecesProductions.getProduction(i);
      productionSerialNumMap.put(piecesProductions.getProduction(i), (i + 1));
    }
    List<GrammarSymbol> rowList = new ArrayList<>(this.rowIndexMap.keySet());
    List<GrammarSymbol> columnList = new ArrayList<>(
        this.columnIndexMap.keySet());
    str = str + "\t";
    for (int i = 0; i < columnList.size(); i++) {
      str = str + columnList.get(i).getName() + "\t";
    }
    str = str + "\n";
    for (int i = 0; i < rowList.size(); i++) {
      str = str + rowList.get(i).getName() + "\t";
      for (int j = 0; j < columnList.size(); j++) {
        Production p = this.getProductionTableEntry(rowList.get(i),
            columnList.get(j));
        if (p.equals(new Production())) {
          str = str + null + "\t";
        } else {
          if (productionSerialNumMap.get(p) == null) {
            str = str + null + "\t";
          } else {
            str = str + productionSerialNumMap.get(p) + "\t";
          }
        }
      }
      str = str + "\n";
    }
    for (int i = 0; i < rowList.size(); i++) {
      str = str + rowList.get(i).getName() + "\t";
      for (int j = 0; j < columnList.size(); j++) {
        boolean entryValue = this.getSynchTableEntry(rowList.get(i),
            columnList.get(j));
        if (entryValue == false) {
          str = str + null + "\t";
        } else {
          str = str + "synch\t";
        }
      }
      str = str + "\n";
    }

    return str;
  }

  /**
   * Get the grammar symbol whose name is the longest.
   * 
   * @return longest grammar symbol
   */
  private String maxLengthGrammarSymbolName() {
    String maxLengthName = "";
    for (GrammarSymbol symbol : this.productions.getNonterminalSet()) {
      if (symbol.getName().length() > maxLengthName.length()) {
        maxLengthName = symbol.getName();
      }
    }
    return maxLengthName;
  }

}
