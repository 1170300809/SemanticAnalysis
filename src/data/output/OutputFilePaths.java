package data.output;

public enum OutputFilePaths {
  LEXICAL_ERROR_LOG("src/log/lexicalError.log"),
  ;

  private String filePath = null;

  private OutputFilePaths(String filePath) {
    this.filePath = filePath;
  }

  /**
   * Get the path of the output file.
   * 
   * @return file path
   */
  public String getFilePath() {
    return this.filePath;
  }
}
