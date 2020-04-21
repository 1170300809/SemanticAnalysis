package lexical;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import data.args.WordTypes;
import data.input.InputFilePaths;

/**
 * This class loads a DFA table from an input file and change state
 * according to the table when input a character.
 * 
 * @author AtoshDustosh
 */
public class DfaAnalyzer {
  public static final int state_finished = -1;
  public static final int state_error = 0;

  private String dfaWordAnalyzerType = null;

  /*
   * index of present state in DFA - start from 1
   */
  private int presentState = 0;

  private String analyzedWordType = "";

  /**
   * fields to store the DFA table and can be used to make
   * transformation among states.
   */
  private Map<String, Integer> chToColumn = new HashMap<>();
  private List<List<String>> statesRow = new ArrayList<>();

  public static void main(String[] args) {
    /*
     * Test
     */
    DfaAnalyzer analyzer = new DfaAnalyzer(WordTypes.CHAR.getType(),
        InputFilePaths.CHAR_DFA.getFilePath());
    analyzer.printDfaTable();
    System.out.println(analyzer.enterNextState('\''));
    System.out.println(analyzer.enterNextState('a'));
    System.out.println(analyzer.enterNextState('\''));
    System.out.println(analyzer.enterNextState('\''));
    System.out.println(analyzer.getAnalyzedWordType());

    analyzer.rebuildAnalyzer(WordTypes.NUM.getType(),
        InputFilePaths.NUM_DFA.getFilePath());
    analyzer.printDfaTable();
    System.out.println(analyzer.enterNextState('1'));
    System.out.println(analyzer.enterNextState('.'));
    System.out.println(analyzer.enterNextState('8'));
    System.out.println(analyzer.enterNextState('0'));
    System.out.println(analyzer.enterNextState('g'));
    System.out.println(analyzer.enterNextState('g'));
    System.out.println(analyzer.getAnalyzedWordType());

    analyzer.rebuildAnalyzer(WordTypes.STRING.getType(),
        InputFilePaths.STRING_DFA.getFilePath());
    analyzer.printDfaTable();
    System.out.println(analyzer.enterNextState('\"'));
    System.out.println(analyzer.enterNextState('\\'));
    System.out.println(analyzer.enterNextState('\"'));
    System.out.println(analyzer.enterNextState('d'));
    System.out.println(analyzer.enterNextState('\"'));
    System.out.println(analyzer.enterNextState('\"'));
    System.out.println(analyzer.getAnalyzedWordType());

    analyzer.rebuildAnalyzer(WordTypes.IDN.getType(),
        InputFilePaths.IDN_DFA.getFilePath());
    analyzer.printDfaTable();
    System.out.println(analyzer.enterNextState('_'));
    System.out.println(analyzer.enterNextState('1'));
    System.out.println(analyzer.enterNextState('a'));
    System.out.println(analyzer.enterNextState(' '));
    System.out.println(analyzer.enterNextState('v'));
    System.out.println(analyzer.enterNextState('v'));
    System.out.println(analyzer.getAnalyzedWordType());

    analyzer.rebuildAnalyzer(WordTypes.OP_DL_COM.getType(),
        InputFilePaths.OP_DL_COM_DFA.getFilePath());
    analyzer.printDfaTable();
    System.out.println(analyzer.enterNextState('/'));
    System.out.println(analyzer.enterNextState('*'));
    System.out.println(analyzer.enterNextState('*'));
    System.out.println(analyzer.enterNextState('/'));
    System.out.println(analyzer.enterNextState('9'));
    System.out.println(analyzer.enterNextState('9'));
    System.out.println(analyzer.getAnalyzedWordType());

  }

  /**
   * Set the word type of this DFA analyzer and load corresponding DFA
   * table from file.
   * 
   * @param dfaWordAnalyzerType type of word this DFA analyzer can
   *                            recognize
   * @param dfaFilePath         file path of DFA table
   */
  public DfaAnalyzer(String dfaWordAnalyzerType, String dfaFilePath) {
    this.dfaWordAnalyzerType = dfaWordAnalyzerType;
    this.loadDfaTableFile(dfaFilePath);
    this.presentState = 1;
  }

  /**
   * Load a character and enter next state according to its DFA table.
   * 
   * @param ch character loaded
   * @return state serial number (>0) if successfully changes state;
   *         state_error if error occurs such as when there is no next
   *         state according to DFA table; state_finished when the
   *         analyzing process finishes.
   */
  public int enterNextState(char ch) {
    if (this.isAnalysisFinished()) {
      return state_finished;
    }
    Set<String> columnKeySet = this.chToColumn.keySet();
    String str = ch + "";
    int column = 0;
    if (columnKeySet.contains(str)) {
      column = this.chToColumn.get(str);
    } else {
      column = this.chToColumn.get("else");
    }

    List<String> rowList = this.statesRow.get(this.presentState - 1);
    String entry = rowList.get(column);
//    System.out.println("entry: " + entry);
    if (this.isNonnegativeInteger(entry)) {
      // if table entry is a non-negative integer
      this.presentState = Integer.valueOf(entry);
      return this.presentState;
    } else {
      // analysis finished
      this.analyzedWordType = entry;
      this.presentState = state_finished;
      return state_finished;
    }
  }

  /**
   * Rebuild this DFA analyzer with a new DFA table and word type name.
   * 
   * @param dfaWordAnalyzerType type of word this DFA analyzer can
   *                            recognize
   * @param dfaFilePath         file path of DFA table
   */
  public void rebuildAnalyzer(String dfaWordAnalyzerType, String dfaFilePath) {
    this.chToColumn.clear();
    this.statesRow.clear();
    this.dfaWordAnalyzerType = dfaWordAnalyzerType;

    this.presentState = 1;
    this.analyzedWordType = "";

    this.loadDfaTableFile(dfaFilePath);
  }

  /**
   * Reset the state and result this DFA analyzer keeps.
   */
  public void reset() {
    this.presentState = 1;
    this.analyzedWordType = "";
  }

  public boolean isAnalysisFinished() {
    if (this.presentState == state_finished) {
      return true;
    } else if (this.presentState == state_error) {
      return true;
    } else {
      return false;
    }
  }

  public int getState() {
    return this.presentState;
  }

  /**
   * Get analyzed type name of the input word.
   * 
   * @return analyzed type name of the input word; analysisError if
   *         process is wrong; analysisNotFinished if process has not
   *         finished
   */
  public String getAnalyzedWordType() {
    return this.analyzedWordType;
  }

  public String getDfaAnalyzerWordType() {
    return this.dfaWordAnalyzerType;
  }

  public void printDfaTable() {
    System.out.println(this.chToColumn.toString());
    for (int i = 0; i < this.statesRow.size(); i++) {
      System.out.println(this.statesRow.get(i).toString());
    }
  }

  private void loadDfaTableFile(String dfaFilePath) {
    System.out
        .println("DFA(" + this.dfaWordAnalyzerType + ") loading file: "
            + dfaFilePath);
    try {
      Scanner scanner = new Scanner(new FileInputStream(dfaFilePath));
      boolean hasNext = scanner.hasNext();
      if (hasNext) {
        String[] columns = scanner.nextLine().split("\t");
        // store the columns of the DFA table
        for (int i = 0; i < columns.length; i++) {
          this.chToColumn.put(columns[i], i);
        }
        while ((hasNext = scanner.hasNext())) {
          columns = scanner.nextLine().split("\t");
          List<String> row = new ArrayList<>();
          for (int i = 0; i < columns.length; i++) {
            row.add(columns[i]);
          }
          this.statesRow.add(row);
        }
      } else {
        System.out.println("error: input file not valid.");
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if (!this.chToColumn.keySet().contains("else")) {
      System.out.println("error: DFA table not valid");
      System.exit(1);
    }
  }

  private boolean isNonnegativeInteger(String str) {
    Pattern pattern = Pattern.compile("^[\\+]?[\\d]+$");
    return pattern.matcher(str).matches();
  }

}
