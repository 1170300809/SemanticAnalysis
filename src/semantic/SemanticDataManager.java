package semantic;

import java.util.List;

public class SemanticDataManager {

  /**
   * Create a new entry (symbol) and save it in this manager.
   * 
   * @param name   name of the new symbol
   * @param type   type of the new symbol
   * @param offset the new symbol's offset in memory when executing the
   *               program
   * @return true if created successfully (no duplication); false
   *         otherwise
   */
  public boolean createNewEntry(String name, SemanticSymbolType type,
      int offset) {
    // TODO
    return false;
  }

  /**
   * Create a 3-address code.
   * 
   * @param codeTokens
   */
  public void createCode(List<String> codeTokens) {
    // TODO
  }

  /**
   * Look up this data manager to see if symbol with name is in it.
   * 
   * @param name name of the entry to be looked up for
   * @return true if exists; false otherwise
   */
  public boolean lookupEntry(String name) {
    // TODO
    return false;
  }

  /**
   * Create a list for backpatching and initialize it with only one
   * element.
   * 
   * @param codeNo the serial number of the code to be backpatched
   * @return a new list for backpatching
   */
  public List<Integer> makeList(int codeNo) {
    // TODO
    return null;
  }

  /**
   * Merge 2 lists of integers into 1 piece. No duplicated element is
   * allowed.
   * 
   * @param list1 list 1
   * @param list2 list 2
   * @return merged list
   */
  public List<Integer> mergeList(List<Integer> list1, List<Integer> list2) {
    // TODO
    return null;
  }

  /**
   * Backpatch all codes whose numbers are in the list with codeNo.
   * 
   * @param list   a list of codes to be backpatched
   * @param codeNo codeNo used for backpatching
   */
  public void backpatch(List<Integer> list, int codeNo) {
    // TODO
  }
}
