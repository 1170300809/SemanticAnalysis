package semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SemanticDataManager {

  // store the information of each symbol
  private Map<String, SymbolInfo> symbolMap = new HashMap<>();

  // Map < codeNo, backpatchListforCodeNo > - used to 
  private Map<Integer, List<Integer>> backpatchMap = new HashMap<>();

  private int offset = 0;

  /**
   * Create a new entry (symbol) and save it in this manager.
   * 
   * @param name  name of the new symbol
   * @param type  type of the new symbol
   * @param width the new symbol's width in memory when executing the
   *              program
   * @return true if created successfully (no duplication); false
   *         otherwise
   */
  public boolean createNewEntry(String name, SymbolDataType type,
      int width) {
    if (this.symbolMap.containsKey(name)) {
      return false;
    } else {
      SymbolInfo info = new SymbolInfo(type, this.offset, width);
      this.symbolMap.put(name, info);
      this.offset = this.offset + width;
      return true;
    }
  }

  /**
   * Create a 3-address code.
   * 
   * @param codeTokens tokens split from code to be used for creation of
   *                   3-address code
   */
  public void createCode(List<String> codeTokens) {
    // ambiguous example (not split): createCode(E.addr ¡®=¡¯ E1.addr ¡®*¡¯ E2.addr); 
    // TODO
  }

  /**
   * Look up this data manager to see if symbol with name is in it.
   * 
   * @param name name of the entry to be looked up for
   * @return true if exists; false otherwise
   */
  public boolean existsEntry(String name) {
    /*
     * the original method in our courseware is called "lookup(string)", which will 
     * return a pointer to the symbol(IDN) looked up for. But in fact, this method 
     * only provides to to check the existence of the symbol(IDN)
     */
    return this.symbolMap.containsKey(name);
  }

  /**
   * Create a list for backpatching and initialize it with only one
   * element.
   * 
   * @param codeNo the serial number of the code to be backpatched
   * @return a new list for backpatching
   */
  public List<Integer> makeList(int codeNo) {
    List<Integer> backpatchList = new ArrayList<>();
    backpatchList.add(codeNo);
    this.backpatchMap.put(codeNo, backpatchList);
    return backpatchList;
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
    List<Integer> newList = new ArrayList<>();
    newList.addAll(list1);
    newList.addAll(list2);
    return newList;
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

  /**
   * A class used for storing the information of IDNs.
   * 
   * @author AtoshDustosh
   */
  class SymbolInfo {
    public SymbolDataType type;
    public int offset;
    public int width;

    public SymbolInfo(SymbolDataType type, int offset, int width) {
      this.type = type;
      this.offset = offset;
      this.width = width;
    }

    public boolean equals(SymbolInfo e) {
      if (e.type.equals(this.type) && e.offset == this.offset) {
        return true;
      } else {
        return false;
      }
    }

    @Override
    public String toString() {
      return "(" + this.type.toString() + ", " + this.offset + ")";
    }

    @Override
    public int hashCode() {
      return (this.offset + this.type.toString()).hashCode();
    }

    @Override
    public boolean equals(Object o) {
      return this.equals((SymbolInfo) o);
    }
  }
}
