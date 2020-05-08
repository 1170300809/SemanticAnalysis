package analyzer;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyseList {

  public static final String GRAMMAR_FILEPATH = "grammar.txt";

  List<LProduction> productions = new ArrayList<>();
  List<String> terminals = new ArrayList<>();
  List<String> nonterminals = new ArrayList<>();
  Map<String, List<String>> firstSet = new HashMap<>();
  Map<String, List<String>> followSet = new HashMap<>();

  public AnalyseList() {
    this.loadFileAndGetProductions();
    this.loadFileAndGetNonTerminals();
    this.loadFileAndGetTerminals();

    this.calculateFirstSet();
    this.calculateFollowSet();
    this.calculateSelectSet();

    this.Predict();

  }

  /**
   * Load productions from file and get productions.
   */
  public void loadFileAndGetProductions() {
    try {
      File file = new File(GRAMMAR_FILEPATH);
      RandomAccessFile randomfile = new RandomAccessFile(file, "r");
      String line;
      String leftHandSide;
      String rightHandSide;
      LProduction production;
      while ((line = randomfile.readLine()) != null) {
        leftHandSide = line.split("->")[0].trim();
        rightHandSide = line.split("->")[1].trim();
        production = new LProduction(leftHandSide, rightHandSide.split(" "));
        this.productions.add(production);
      }
      randomfile.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Load productions from file and get non-terminals.
   */
  public void loadFileAndGetNonTerminals() {
    try {
      File file = new File(GRAMMAR_FILEPATH);
      RandomAccessFile randomfile = new RandomAccessFile(file, "r");
      String line;
      String leftHandSide;
      while ((line = randomfile.readLine()) != null) {
        leftHandSide = line.split("->")[0].trim();
        if (this.nonterminals.contains(leftHandSide)) {
          continue;
        } else {
          this.nonterminals.add(leftHandSide);
        }
      }
      randomfile.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Load productions from file and get terminals.
   */
  public void loadFileAndGetTerminals() {
    // traverse all productions
    String[] rightHandSides;
    for (int i = 0; i < this.productions.size(); i++) {
      rightHandSides = this.productions.get(i).getRHSs();
      // search terminals in the RHS part of the production
      for (int j = 0; j < rightHandSides.length; j++) {
        if (this.nonterminals.contains(rightHandSides[j])
            || rightHandSides[j].equals("$")) {
          continue;
        } else {
          this.terminals.add(rightHandSides[j]);
        }
      }
    }
  }

  public void calculateFirstSet() {
    // get First set of terminals directly
    ArrayList<String> first;
    for (int i = 0; i < this.terminals.size(); i++) {
      first = new ArrayList<String>();
      first.add(this.terminals.get(i));
      this.firstSet.put(this.terminals.get(i), first);
    }
    // load nonterminals into first set and get them ready for calculation
    for (int i = 0; i < this.nonterminals.size(); i++) {
      first = new ArrayList<String>();
      this.firstSet.put(this.nonterminals.get(i), first);
    }

    // calculate the first set
    boolean flag = false;
    while (true) {
      flag = true;
      String leftHandSide;
      String rightHandSide;
      String[] rightHandSides;
      for (int i = 0; i < this.productions.size(); i++) {
        leftHandSide = this.productions.get(i).getLHS();
        rightHandSides = this.productions.get(i).getRHSs();
        for (int j = 0; j < rightHandSides.length; j++) {
          rightHandSide = rightHandSides[j];
          // right hand side can be null - empty, which is represented in grammar as "$"
          if (!rightHandSide.equals("$")) {
            for (int k = 0; k < this.firstSet.get(rightHandSide).size(); k++) {
              if (this.firstSet.get(leftHandSide)
                  .contains(this.firstSet.get(rightHandSide).get(k))) {
                continue;
              } else {
                this.firstSet.get(leftHandSide)
                    .add(this.firstSet.get(rightHandSide).get(k));
                flag = false;
              }
            }
          }
          if (this.ifCanProduceNull(rightHandSide)) {
            continue;
          } else {
            break;
          }
        }
      }
      if (flag == true) {
        break;
      }
    }
  }

  public void calculateFollowSet() {
    // initialize all follow sets of non-terminals
    ArrayList<String> follow;
    for (int i = 0; i < this.nonterminals.size(); i++) {
      follow = new ArrayList<String>();
      this.followSet.put(this.nonterminals.get(i), follow);
    }
    // add "#" into the follow set of starting symbol "S"
    this.followSet.get("S").add("#");
    boolean flag;
    boolean ifcanProduceNull;
    while (true) {
      flag = true;
      for (int i = 0; i < this.productions.size(); i++) {
        String leftHandSide;
        String rightHandSide;
        String[] rightHandSides;
        rightHandSides = this.productions.get(i).getRHSs();
        for (int j = 0; j < rightHandSides.length; j++) {
          rightHandSide = rightHandSides[j];
          // handle non-terminals
          if (this.nonterminals.contains(rightHandSide)) {
            ifcanProduceNull = true;
            for (int k = j + 1; k < rightHandSides.length; k++) {
              // find the first set of the non-terminal
              for (int v = 0; v < this.firstSet.get(rightHandSides[k])
                  .size(); v++) {
                // add the first set of the latter grammar symbol into this follow set
                if (this.followSet.get(rightHandSide)
                    .contains(this.firstSet.get(rightHandSides[k]).get(v))) {
                  continue;
                } else {
                  this.followSet.get(rightHandSide)
                      .add(this.firstSet.get(rightHandSides[k]).get(v));
                  flag = false;
                }
              }
              if (this.ifCanProduceNull(rightHandSides[k])) {
                continue;
              } else {
                ifcanProduceNull = false;
                break;
              }
            }
            if (ifcanProduceNull) {
              leftHandSide = this.productions.get(i).getLHS();
              for (int p = 0; p < this.followSet.get(leftHandSide)
                  .size(); p++) {
                if (this.followSet.get(rightHandSide)
                    .contains(this.followSet.get(leftHandSide).get(p))) {
                  continue;
                } else {
                  this.followSet.get(rightHandSide)
                      .add(this.followSet.get(leftHandSide).get(p));
                  flag = false;
                }
              }
            }
          } // end of if
        } // end of loop (j)
      } // end of loop (i)
      if (flag == true) {
        break;
      }
    } // end of while loop

    // remove the character "#" from follow sets
    String left;
    for (int j = 0; j < this.nonterminals.size(); j++) {
      left = this.nonterminals.get(j);
      for (int v = 0; v < this.followSet.get(left).size(); v++) {
        if (this.followSet.get(left).get(v).equals("#")) {
          this.followSet.get(left).remove(v);
        }
      }
    }
  }

  public void calculateSelectSet() {
    String leftHandSide;
    String rightHandSide;
    String[] righthandSides;
    List<String> follow = new ArrayList<String>();
    List<String> first = new ArrayList<String>();

    for (int i = 0; i < this.productions.size(); i++) {
      leftHandSide = this.productions.get(i).getLHS();
      righthandSides = this.productions.get(i).getRHSs();
      if (righthandSides[0].equals("$")) {
        // select(i) = follow(A)
        follow = this.followSet.get(leftHandSide);
        for (int j = 0; j < follow.size(); j++) {
          if (this.productions.get(i).select.contains(follow.get(j))) {
            continue;
          } else {
            this.productions.get(i).select.add(follow.get(j));
          }
        }
      } else {
        boolean flag = true;
        for (int j = 0; j < righthandSides.length; j++) {
          rightHandSide = righthandSides[j];
          first = this.firstSet.get(rightHandSide);
          for (int v = 0; v < first.size(); v++) {
            if (this.productions.get(i).select.contains(first.get(v))) {
              continue;
            } else {
              this.productions.get(i).select.add(first.get(v));
            }
          }
          if (this.ifCanProduceNull(rightHandSide)) {
            continue;
          } else {
            flag = false;
            break;
          }
        }
        if (flag) {
          follow = this.followSet.get(leftHandSide);
          for (int j = 0; j < follow.size(); j++) {
            if (this.productions.get(i).select.contains(follow.get(j))) {
              continue;
            } else {
              this.productions.get(i).select.add(follow.get(j));
            }
          }
        }
      }
    }
  }

  // 生成产生式
  // TODO
  public void Predict() {
    LProduction production;
    String line;
    String[] rights;
    try {
      File file = new File("predictldy.txt");
      RandomAccessFile randomfile = new RandomAccessFile(file, "rw");
      for (int i = 0; i < this.productions.size(); i++) {
        production = this.productions.get(i);
        for (int j = 0; j < production.select.size(); j++) {
          line = production.getLHS() + "#" + production.select.get(j)
              + " ->";
          rights = production.getRHSs();
          for (int v = 0; v < rights.length; v++) {
            line = line + " " + rights[v];
          }
          line = line + "\n";
          // 写入文件
          randomfile.writeBytes(line);
        }
      }
      randomfile.close();

    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }

  }

  public boolean isTerminal(String symbol) {
    if (this.terminals.contains(symbol)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Judge whether a grammar symbol can produce an empty string.
   * 
   * @param symbol grammar symbol
   * @return true if it can; false otherwise
   */
  public boolean ifCanProduceNull(String symbol) {
    String[] rightHandSides;
    for (int i = 0; i < this.productions.size(); i++) {
      // find the symbol's productions
      if (this.productions.get(i).getLHS().equals(symbol)) {
        rightHandSides = this.productions.get(i).getRHSs();
        if (rightHandSides[0].equals("$")) {
          return true;
        }
      }
    }
    return false;
  }
}

class LProduction {
  String leftHandSide;
  String[] rightHandSides;
  List<String> select = new ArrayList<String>();

  public LProduction(String leftHandSide, String[] rightHandSide) {
    this.leftHandSide = leftHandSide;
    this.rightHandSides = rightHandSide;
  }

  public String[] getRHSs() {
    return this.rightHandSides;
  }

  public String getLHS() {
    return this.leftHandSide;
  }
}