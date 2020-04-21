package data.args;

import java.util.HashSet;
import java.util.Set;

/**
 * This class stores a set of key words and can judge whether a word
 * is a key word.
 * 
 * @author AtoshDustosh
 */
public class KeyWord {

  @SuppressWarnings("serial")
  private static final Set<String> keyWordSet = new HashSet<String>() {
    {
      this.add("int");
      this.add("double");
      this.add("boolean");
      this.add("record");
      this.add("if");
      this.add("then");
      this.add("else");
      this.add("do");
      this.add("while");
      this.add("call");
      this.add("return");
      this.add("proc");
      this.add("and");
      this.add("not");
      this.add("or");
      this.add("case");
      this.add("default");
      this.add("char");
      this.add("record");
    }
  };

  public KeyWord() {
  }

  public static String getKeyWord(String word) {
    for (String str : keyWordSet) {
      if (str.equals(word)) {
        return word;
      }
    }
    return "";
  }

  public static boolean isKeyWord(String word) {
    if (keyWordSet.contains(word)) {
      return true;
    } else {
      return false;
    }
  }

}
