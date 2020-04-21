package data.input;

public enum InputFilePaths {
  CHAR_DFA("src/data/input/charDFA.format"),
  IDN_DFA("src/data/input/idnDFA.format"),
  NUM_DFA("src/data/input/numDFA.format"),
  OP_DL_COM_DFA("src/data/input/operator_delimiter_com_DFA.format"),
  STRING_DFA("src/data/input/stringDFA.format"),
  LEXICAL_TEST_1("src/test/test1.code"),
  LEXICAL_TEST_ERROR("src/test/testError.code"),
  WORDSERIALNUMBER("src/data/input/WSnumbers.wsn"),
  PRODUCTIONS0("src/data/input/productions.format"),
  PRODUCTIONS1("src/data/input/productions1.format"),
  PRODUCTIONS2("src/data/input/productions2.format"),
  SYNTACTIC_TEST_1("src/test/synTest1.code"),
  ;

  private String filePath = null;

  private InputFilePaths(String filePath) {
    this.filePath = filePath;
  }

  /**
   * Get the path of the input file.
   * 
   * @return file path
   */
  public String getFilePath() {
    return this.filePath;
  }
}
