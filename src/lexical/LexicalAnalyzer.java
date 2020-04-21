package lexical;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import data.args.KeyWord;
import data.args.WordTypes;
import data.input.InputFilePaths;
import data.output.OutputFilePaths;

/**
 * This class can analyze a program file of specific format and get
 * all tokens of it.
 * 
 * @author AtoshDustosh
 */
public class LexicalAnalyzer {
  public static final String errorLogFilePath = OutputFilePaths.LEXICAL_ERROR_LOG
      .getFilePath();
  public static final String testFilePath = InputFilePaths.SYNTACTIC_TEST_1
      .getFilePath();

  public static final String ANALYSIS_ERROR = "analysis error";
  public static final String ANALYSIS_UNFINISHED = "analysis unfinished";
  public static final String ANALYSIS_FINISHED = "analysis finished";

  private final Map<String, DfaAnalyzer> analyzerMap = new HashMap<>();

  private int chPointer = 0;
  private List<Character> chList = new ArrayList<>();
  private Map<Integer, Location> chLocationMap = new HashMap<>();

  private DfaAnalyzer analyzerUsed = null;
  private String analyzedWordType = "";

  private Tokens tokens = new Tokens(Tokens.wordSerialNumberFilePath);

  private PrintWriter errorLogFileWriter = null;

  public LexicalAnalyzer() {
    this.analyzerMap.put(WordTypes.CHAR.getType(), new DfaAnalyzer(
        WordTypes.CHAR.getType(), InputFilePaths.CHAR_DFA.getFilePath()));
    this.analyzerMap.put(WordTypes.NUM.getType(), new DfaAnalyzer(
        WordTypes.NUM.getType(), InputFilePaths.NUM_DFA.getFilePath()));
    this.analyzerMap.put(WordTypes.STRING.getType(), new DfaAnalyzer(
        WordTypes.STRING.getType(), InputFilePaths.STRING_DFA.getFilePath()));
    this.analyzerMap.put(WordTypes.IDN.getType(), new DfaAnalyzer(
        WordTypes.IDN.getType(), InputFilePaths.IDN_DFA.getFilePath()));
    this.analyzerMap.put(WordTypes.OP_DL_COM.getType(), new DfaAnalyzer(
        WordTypes.OP_DL_COM.getType(),
        InputFilePaths.OP_DL_COM_DFA.getFilePath()));

    System.out.println(
        this.analyzerMap.get(WordTypes.OP_DL_COM.getType()).getState());
  }

  public static void main(String[] args) {
    LexicalAnalyzer analyzer = new LexicalAnalyzer();
    Tokens tokens = analyzer.analyzeFile(LexicalAnalyzer.testFilePath);
    System.out.println("\n");
    System.out.println("tokens:\n" + tokens.toString());
  }

  public Tokens analyzeFile(String filePath) {
    try {
      this.errorLogFileWriter = new PrintWriter(
          new FileOutputStream(LexicalAnalyzer.errorLogFilePath));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    this.convertFileIntoCharList(filePath);
    System.out.println(this.chList.toString());
    this.chPointer = 0;

    System.out.println("file reading completed");
    String analysisResult = "";
    int charSerialNumber = 0;
    while (this.chList.isEmpty() == false) {
      if (this.chList.size() <= this.chPointer) {
        break;
      }
      char ch = this.chList.get(this.chPointer);
//      System.out.println("(" + ch + ")");

      if (this.chPointer == 0 && (ch == ' ' || ch == '\t')) {
        this.chList.remove(0);
        charSerialNumber++;
        continue;
      }

      analysisResult = this.analyzeChar(ch);
      if (analysisResult.equals(ANALYSIS_UNFINISHED)) {
        this.chPointer++;
        charSerialNumber++;
      } else if (analysisResult.equals(ANALYSIS_ERROR)) {
        char errorChar = this.chList.get(0);
        if (errorChar != '\n') { // this is a bad patch - made according to  the program
          this.writeErrorLog(charSerialNumber - this.chPointer);
        }
        charSerialNumber = charSerialNumber - this.chPointer + 1;
        this.chList.remove(0);
        this.chPointer = 0;
      } else {
        Location location = this.chLocationMap
            .get(charSerialNumber - this.chPointer);
        Token token = this.buildToken(this.analyzedWordType, this.chPointer,
            location);
        for (int i = 0; i < this.chPointer; i++) {
          this.chList.remove(0);
        }
        this.tokens.add(token);
        System.out.println("token: " + token.toString());
        this.chPointer = 0;
        this.analyzerUsed.reset();
      }
    }

    this.tokens.add(this.tokens.wordTypeToSerialNumber("$"), "$",
        new Location(-1, -1));
    this.errorLogFileWriter.close();
    return this.tokens.getTokensCopy();
  }

  public Tokens getTokens() {
    return this.tokens.getTokensCopy();
  }

  private String analyzeChar(char ch) {
    int analyzerState = 0;
    if (this.chPointer == 0) {
      this.selectAnalyzer(ch);
    }
    analyzerState = this.analyzerUsed.enterNextState(ch);
    return this.convertStateToResult(analyzerState);
  }

  private void selectAnalyzer(char ch) {
    if (ch == '\'') {
      this.analyzerUsed = this.analyzerMap.get(WordTypes.CHAR.getType());
    } else if (ch == '\"') {
      this.analyzerUsed = this.analyzerMap.get(WordTypes.STRING.getType());
    } else if (ch >= '0' && ch <= '9') {
      this.analyzerUsed = this.analyzerMap.get(WordTypes.NUM.getType());
    } else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
        || ch == '_') {
      this.analyzerUsed = this.analyzerMap.get(WordTypes.IDN.getType());
    } else {
      this.analyzerUsed = this.analyzerMap.get(WordTypes.OP_DL_COM.getType());
    }
    this.analyzerUsed.reset();
//    System.out.println("analyzer " + this.analyzerUsed.getDfaAnalyzerWordType()
//        + "status: " + this.analyzerUsed.getState());
  }

  private String convertStateToResult(int analyzerState) {
    this.analyzedWordType = this.analyzerUsed.getAnalyzedWordType();
    switch (analyzerState) {
      case DfaAnalyzer.state_error:
        return LexicalAnalyzer.ANALYSIS_ERROR;
      case DfaAnalyzer.state_finished:
        return LexicalAnalyzer.ANALYSIS_FINISHED;
      default:
        return LexicalAnalyzer.ANALYSIS_UNFINISHED;
    }
  }

  private Token buildToken(String wordType, int borderIndex,
      Location location) {
    int wordSerialNumber = 0;
    String wordValue = "";
    for (int i = 0; i < borderIndex; i++) {
      wordValue = wordValue + this.chList.get(i);
    }
    if (wordType.equals(WordTypes.IDN.getType())
        && KeyWord.isKeyWord(wordValue)) {
      // recognize whether this word is a key word
      wordSerialNumber = this.tokens.wordTypeToSerialNumber(wordValue);
    } else {
      wordSerialNumber = this.tokens.wordTypeToSerialNumber(wordType);
    }
    return new Token(wordSerialNumber, wordValue, location);
  }

  private void convertFileIntoCharList(String filePath) {
    try {
      Scanner scanner = new Scanner(new FileInputStream(filePath));
      // read input file and split into characters
      int line = 0;
      int charSerialNum = 0;
      while (scanner.hasNext()) {
        char[] lineChar = (scanner.nextLine() + "\n").toCharArray();
        for (int i = 0; i < lineChar.length; i++) {
          this.chList.add(lineChar[i]);
          this.chLocationMap.put(charSerialNum++, new Location(line, i));
        }
        line++;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
//    System.out.println(this.chLocationMap.toString());
  }

  private void writeErrorLog(int errorCharSerialNumber) {
    System.out.println("error char serial number: " + errorCharSerialNumber);
    this.errorLogFileWriter.write(
        this.chLocationMap.get(errorCharSerialNumber).toString() + "\n");
  }
}
