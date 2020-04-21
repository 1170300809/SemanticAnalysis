package lexical;

public class Token {
  private int wordSerialNumber = 0;
  private String wordValue = "";

  private Location location = null;

  public Token(int wordSerialNumber, String wordValue, Location location) {
    this.wordSerialNumber = wordSerialNumber;
    this.wordValue = wordValue;
    this.location = location;
  }

  public int getWordSerialNumber() {
    return this.wordSerialNumber;
  }

  public String getWordValue() {
    return this.wordValue;
  }

  public Location getLocation() {
    return new Location(this.location);
  }

  @Override
  public String toString() {
    return "<" + this.wordSerialNumber + "," + this.wordValue + ">("
        + this.location.getRow() + "," + this.location.getColumn() + ")";
  }

}
