package analyzer;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class GrammarComplier {
  private List<Production> productions; // list of default productions
  private List<Symbol> symbols; // list of default symbols
  private List<Id> ids; // list of IDNs from the source code
  private List<String> codes; // 3-address codes

  public List<Id> getIds() {
    return this.ids;
  }

  public List<String> getCodes() {
    return this.codes;
  }

  public GrammarComplier() {

    this.ids = new ArrayList<Id>();
    this.codes = new ArrayList<String>();
    this.symbols = new ArrayList<Symbol>();
    this.productions = new ArrayList<Production>();
    this.CreateNewProduction();
    this.calculateFirstSets();
    this.calculateFollowSets();
    this.calculateSelectSets();
  }

  // create new productions
  public void CreateNewProduction() {
    //TODO:改造文法
    this.productions.add(new Production(0, "S", "func", "S"));
    this.productions.add(new Production(1, "S"));
    this.productions.add(new Production(2, "func", "type", "id", "(", "params",
        ")", "func_body"));
    this.productions.add(new Production(3, "func", "VOID", "id", "(", "params",
        ")", "func_body"));
    this.productions.add(new Production(4, "params"));
    this.productions.add(new Production(5, "params", "type", "id", "params\'"));
    this.productions.add(new Production(6, "params\'"));
    this.productions
        .add(new Production(7, "params\'", ",", "type", "id", "params\'"));
    this.productions.add(new Production(8, "func_body", ";"));
    this.productions.add(new Production(9, "func_body", "block"));
    this.productions
        .add(new Production(10, "block", "{", "def_stmts", "stmts", "}"));
    this.productions
        .add(new Production(11, "def_stmts", "def_stmt", "def_stmts"));
    this.productions.add(new Production(12, "def_stmts"));
    this.productions.add(new Production(13, "def_stmt", "type", "id", "M13_2",
        "array", "M13_4", "vars", ";"));
    this.productions.add(new Production(13, "M13_2"));
    this.symbols.add(new Symbol(-1, "M13_2", 'N'));
    this.productions.add(new Production(13, "M13_4"));
    this.symbols.add(new Symbol(-1, "M13_4", 'N'));
    this.productions.add(new Production(14, "array", "M14_1"));
    this.productions.add(new Production(14, "M14_1"));
    this.symbols.add(new Symbol(-1, "M14_1", 'N'));
    this.productions.add(new Production(15, "array", "[", "int", "]", "M15_4",
        "array", "M15_6"));
    this.productions.add(new Production(15, "M15_4"));
    this.symbols.add(new Symbol(-1, "M15_4", 'N'));
    this.productions.add(new Production(15, "M15_6"));
    this.symbols.add(new Symbol(-1, "M15_6", 'N'));
    this.productions.add(new Production(16, "vars"));
    this.productions.add(new Production(17, "vars", ",", "id", "M17_3", "array",
        "M17_5", "vars"));
    this.productions.add(new Production(17, "M17_3"));
    this.symbols.add(new Symbol(-1, "M17_3", 'N'));
    this.productions.add(new Production(17, "M17_5"));
    this.symbols.add(new Symbol(-1, "M17_5", 'N'));
    this.productions.add(new Production(18, "stmts"));
    this.productions.add(new Production(19, "stmts", "stmt", "stmts"));
    this.productions.add(new Production(20, "stmt", "E", ";"));
    this.productions.add(new Production(21, "stmt", ";"));
    this.productions.add(new Production(22, "stmt", "block"));
    this.productions.add(new Production(23, "stmt", "RETURN", "e", ";"));
    this.productions.add(new Production(24, "stmt", "CONTINUE", ";"));
    this.productions.add(new Production(25, "stmt", "BREAK", ";"));
    this.productions.add(new Production(26, "stmt", "IF", "(", "E", ")",
        "M26_5", "stmt", "M26_7", "else", "M26_9"));
    this.productions.add(new Production(26, "M26_5"));
    this.symbols.add(new Symbol(-1, "M26_5", 'N'));
    this.productions.add(new Production(26, "M26_7"));
    this.symbols.add(new Symbol(-1, "M26_7", 'N'));
    this.productions.add(new Production(26, "M26_9"));
    this.symbols.add(new Symbol(-1, "M26_9", 'N'));
    this.productions.add(
        new Production(27, "stmt", "SWITCH", "(", "E", ")", "{", "cases", "}"));
    this.productions.add(
        new Production(28, "stmt", "DO", "stmt", "WHILE", "(", "E", ")", ";"));
    this.productions.add(new Production(30, "else", "ELSE", "stmt"));
    this.productions.add(new Production(29, "else"));
    this.productions.add(new Production(31, "cases", "case", "cases"));
    this.productions.add(new Production(32, "cases"));
    this.productions
        .add(new Production(33, "case", "CASE", "const", ":", "stmts"));
    this.productions.add(new Production(34, "case", "DEFAULT", ":", "stmts"));
    this.productions.add(new Production(35, "e"));
    this.productions.add(new Production(36, "e", "E"));
    this.productions.add(new Production(37, "stmt", "FOR", "(", "e", ";", "e",
        ";", "e", ")", "stmt"));
    this.productions.add(new Production(38, "stmt", "WHILE", "(", "M38_3", "E",
        ")", "M26_5", "stmt", "M38_7"));
    this.productions.add(new Production(38, "M38_3"));
    this.symbols.add(new Symbol(-1, "M38_3", 'N'));
    this.productions.add(new Production(38, "M38_7"));
    this.symbols.add(new Symbol(-1, "M38_7", 'N'));
    this.productions.add(new Production(39, "factor", "+", "factor"));
    this.productions
        .add(new Production(40, "E", "value", "M52_2", "comp", "M52_4"));
    this.productions.add(new Production(41, "comp"));
    this.productions.add(new Production(42, "comp", "<", "value", "M42_4"));
    this.productions.add(new Production(42, "M42_4"));
    this.symbols.add(new Symbol(-1, "M42_4", 'N'));
    this.productions.add(new Production(43, "comp", "<=", "value", "M43_4"));
    this.productions.add(new Production(43, "M43_4"));
    this.symbols.add(new Symbol(-1, "M43_4", 'N'));
    this.productions.add(new Production(44, "comp", ">", "value", "M44_4"));
    this.productions.add(new Production(44, "M44_4"));
    this.symbols.add(new Symbol(-1, "M44_4", 'N'));
    this.productions.add(new Production(45, "comp", ">=", "value", "M45_4"));
    this.productions.add(new Production(45, "M45_4"));
    this.symbols.add(new Symbol(-1, "M45_4", 'N'));
    this.productions.add(new Production(46, "comp", "==", "value", "M46_4"));
    this.productions.add(new Production(46, "M46_4"));
    this.symbols.add(new Symbol(-1, "M46_4", 'N'));
    this.productions.add(new Production(47, "comp", "!=", "value", "M47_4"));
    this.productions.add(new Production(47, "M47_4"));
    this.symbols.add(new Symbol(-1, "M47_4", 'N'));
    this.productions
        .add(new Production(48, "value", "item", "M52_2", "items", "M52_4"));
    this.productions.add(new Production(49, "items"));
    this.productions.add(
        new Production(50, "items", "+", "item", "M50_3", "items", "M52_4"));
    this.productions.add(new Production(50, "M50_3"));
    this.symbols.add(new Symbol(-1, "M50_3", 'N'));
    this.productions.add(
        new Production(51, "items", "-", "item", "M51_3", "items", "M52_4"));
    this.productions.add(new Production(51, "M51_3"));
    this.symbols.add(new Symbol(-1, "M51_3", 'N'));
    this.productions
        .add(new Production(52, "item", "factor", "M52_2", "factors", "M52_4"));
    this.productions.add(new Production(52, "M52_2"));
    this.symbols.add(new Symbol(-1, "M52_2", 'N'));
    this.productions.add(new Production(52, "M52_4"));
    this.symbols.add(new Symbol(-1, "M52_4", 'N'));
    this.productions.add(new Production(53, "factors"));
    this.productions.add(new Production(54, "factors", "*", "factor", "M54_3",
        "factors", "M52_4"));
    this.productions.add(new Production(54, "M54_3"));
    this.symbols.add(new Symbol(-1, "M54_3", 'N'));
    this.productions.add(new Production(55, "factors", "/", "factor", "M55_3",
        "factors", "M52_4"));
    this.productions.add(new Production(55, "M55_3"));
    this.symbols.add(new Symbol(-1, "M55_3", 'N'));
    this.productions.add(new Production(56, "factors", "%", "factor", "M56_3",
        "factors", "M52_4"));
    this.productions.add(new Production(56, "M56_3"));
    this.symbols.add(new Symbol(-1, "M56_3", 'N'));
    this.productions.add(new Production(57, "factor", "!", "factor", "M57_3"));
    this.productions.add(new Production(58, "M57_3"));
    this.symbols.add(new Symbol(-1, "M57_3", 'N'));
    this.productions.add(new Production(58, "factor", "++", "factor", "M58_3"));
    this.productions.add(new Production(58, "M58_3"));
    this.symbols.add(new Symbol(-1, "M58_3", 'N'));
    this.productions.add(new Production(59, "factor", "--", "factor", "M59_3"));
    this.productions.add(new Production(59, "M59_3"));
    this.symbols.add(new Symbol(-1, "M59_3", 'N'));
    this.productions.add(new Production(60, "factor", "(", "E", ")", "M60_4"));
    this.productions.add(new Production(60, "M60_4"));
    this.symbols.add(new Symbol(-1, "M60_4", 'N'));
    this.productions
        .add(new Production(61, "factor", "id", "M61_2", "call", "M61_4"));
    this.productions.add(new Production(61, "M61_2"));
    this.symbols.add(new Symbol(-1, "M61_2", 'N'));
    this.productions.add(new Production(61, "M61_4"));
    this.symbols.add(new Symbol(-1, "M61_4", 'N'));
    this.productions.add(new Production(62, "factor", "const", "M62_2"));
    this.productions.add(new Production(62, "M62_2"));
    this.symbols.add(new Symbol(-1, "M62_2", 'N'));
    this.productions.add(new Production(63, "call", "M63_1", "array", "M63_3"));
    this.productions.add(new Production(63, "M63_1"));
    this.symbols.add(new Symbol(-1, "M63_1", 'N'));
    this.productions.add(new Production(63, "M63_3"));
    this.symbols.add(new Symbol(-1, "M63_3", 'N'));
    this.productions.add(new Production(64, "call", "(", "Es", ")"));
    this.productions.add(new Production(65, "Es", "E", "Es\'"));
    this.productions.add(new Production(66, "Es\'", ",", "E", "Es\'"));
    this.productions.add(new Production(67, "Es\'"));
    this.productions.add(new Production(68, "type", "CHAR", "M68_2"));
    this.productions.add(new Production(68, "M68_2"));
    this.symbols.add(new Symbol(-1, "M68_2", 'N'));
    this.productions.add(new Production(69, "type", "INT", "M69_2"));
    this.productions.add(new Production(69, "M69_2"));
    this.symbols.add(new Symbol(-1, "M69_2", 'N'));
    this.productions.add(new Production(70, "type", "LONG", "M70_2"));
    this.productions.add(new Production(70, "M70_2"));
    this.symbols.add(new Symbol(-1, "M70_2", 'N'));
    this.productions.add(new Production(71, "type", "SHORT", "M71_2"));
    this.productions.add(new Production(71, "M71_2"));
    this.symbols.add(new Symbol(-1, "M71_2", 'N'));
    this.productions.add(new Production(72, "type", "FLOAT", "M72_2"));
    this.productions.add(new Production(72, "M72_2"));
    this.symbols.add(new Symbol(-1, "M72_2", 'N'));
    this.productions.add(new Production(73, "type", "DOUBLE", "M73_2"));
    this.productions.add(new Production(73, "M73_2"));
    this.symbols.add(new Symbol(-1, "M73_2", 'N'));
    this.productions.add(new Production(74, "const", "int", "M74_2"));
    this.productions.add(new Production(75, "const", "float", "M74_2"));
    this.productions.add(new Production(76, "const", "double", "M74_2"));
    this.productions.add(new Production(77, "const", "char", "M74_2"));
    this.productions.add(new Production(74, "M74_2"));
    this.symbols.add(new Symbol(-1, "M74_2", 'N'));
    this.productions.add(new Production(78, "comp", "=", "value", "M78_3"));
    this.productions.add(new Production(78, "M78_3"));
    this.symbols.add(new Symbol(-1, "M78_3", 'N'));
    this.productions.add(new Production(79, "comp", "+=", "value", "M79_3"));
    this.productions.add(new Production(79, "M79_3"));
    this.symbols.add(new Symbol(-1, "M79_3", 'N'));
    this.productions.add(new Production(80, "comp", "-=", "value", "M80_3"));
    this.productions.add(new Production(80, "M80_3"));
    this.symbols.add(new Symbol(-1, "M80_3", 'N'));
    this.productions.add(new Production(81, "comp", "*=", "value", "M81_3"));
    this.productions.add(new Production(81, "M81_3"));
    this.symbols.add(new Symbol(-1, "M81_3", 'N'));
    this.productions.add(new Production(82, "comp", "/=", "value", "M82_3"));
    this.productions.add(new Production(82, "M82_3"));
    this.symbols.add(new Symbol(-1, "M82_3", 'N'));
    this.productions.add(new Production(83, "comp", "%=", "value", "M83_3"));
    this.productions.add(new Production(83, "M83_3"));
    this.symbols.add(new Symbol(-1, "M83_3", 'N'));
    this.productions.add(new Production(84, "factor", "-", "factor", "M84_3"));
    this.productions.add(new Production(84, "M84_3"));
    this.symbols.add(new Symbol(-1, "M84_3", 'N'));
    this.productions.add(new Production(85, "const", "string"));
    this.productions.add(new Production(86, "params", "VOID"));

    this.symbols.add(new Symbol(0, "S", 'N'));
    this.symbols.add(new Symbol(1, "func", 'N'));
    this.symbols.add(new Symbol(2, "params", 'N'));
    this.symbols.add(new Symbol(3, "params\'", 'N'));
    this.symbols.add(new Symbol(4, "func_body", 'N'));
    this.symbols.add(new Symbol(5, "block", 'N'));
    this.symbols.add(new Symbol(6, "def_stmts", 'N'));
    this.symbols.add(new Symbol(7, "def_stmt", 'N'));
    this.symbols.add(new Symbol(8, "array", 'N'));
    this.symbols.add(new Symbol(9, "vars", 'N'));
    this.symbols.add(new Symbol(10, "stmts", 'N'));
    this.symbols.add(new Symbol(11, "stmt", 'N'));
    this.symbols.add(new Symbol(12, "else", 'N'));
    this.symbols.add(new Symbol(13, "cases", 'N'));
    this.symbols.add(new Symbol(14, "case", 'N'));
    this.symbols.add(new Symbol(15, "e", 'N'));
    this.symbols.add(new Symbol(16, "E", 'N'));
    this.symbols.add(new Symbol(17, "comp", 'N'));
    this.symbols.add(new Symbol(18, "value", 'N'));
    this.symbols.add(new Symbol(19, "items", 'N'));
    this.symbols.add(new Symbol(20, "item", 'N'));
    this.symbols.add(new Symbol(21, "factors", 'N'));
    this.symbols.add(new Symbol(22, "factor", 'N'));
    this.symbols.add(new Symbol(23, "call", 'N'));
    this.symbols.add(new Symbol(24, "Es", 'N'));
    this.symbols.add(new Symbol(25, "Es\'", 'N'));
    this.symbols.add(new Symbol(26, "type", 'N'));
    this.symbols.add(new Symbol(27, "const", 'N'));
    this.symbols.add(new Symbol(28, "string", 'T'));
    this.symbols.add(new Symbol(29, "id", 'T'));
    this.symbols.add(new Symbol(30, "(", 'T'));
    this.symbols.add(new Symbol(31, ")", 'T'));
    this.symbols.add(new Symbol(32, "VOID", 'T'));
    this.symbols.add(new Symbol(33, "{", 'T'));
    this.symbols.add(new Symbol(34, "}", 'T'));
    this.symbols.add(new Symbol(35, ",", 'T'));
    this.symbols.add(new Symbol(36, ";", 'T'));
    this.symbols.add(new Symbol(37, "=", 'T'));
    this.symbols.add(new Symbol(38, "IF", 'T'));
    this.symbols.add(new Symbol(39, "SWITCH", 'T'));
    this.symbols.add(new Symbol(40, "DO", 'T'));
    this.symbols.add(new Symbol(41, "WHILE", 'T'));
    this.symbols.add(new Symbol(42, "ELSE", 'T'));
    this.symbols.add(new Symbol(43, "CASE", 'T'));
    this.symbols.add(new Symbol(44, ":", 'T'));
    this.symbols.add(new Symbol(45, "DEFAULT", 'T'));
    this.symbols.add(new Symbol(46, "FOR", 'T'));
    this.symbols.add(new Symbol(47, "<", 'T'));
    this.symbols.add(new Symbol(48, "<=", 'T'));
    this.symbols.add(new Symbol(49, ">", 'T'));
    this.symbols.add(new Symbol(50, ">=", 'T'));
    this.symbols.add(new Symbol(51, "==", 'T'));
    this.symbols.add(new Symbol(52, "!=", 'T'));
    this.symbols.add(new Symbol(53, "+", 'T'));
    this.symbols.add(new Symbol(54, "-", 'T'));
    this.symbols.add(new Symbol(55, "*", 'T'));
    this.symbols.add(new Symbol(56, "/", 'T'));
    this.symbols.add(new Symbol(57, "%", 'T'));
    this.symbols.add(new Symbol(58, "!", 'T'));
    this.symbols.add(new Symbol(59, "++", 'T'));
    this.symbols.add(new Symbol(60, "--", 'T'));
    this.symbols.add(new Symbol(61, "CHAR", 'T'));
    this.symbols.add(new Symbol(62, "INT", 'T'));
    this.symbols.add(new Symbol(63, "LONG", 'T'));
    this.symbols.add(new Symbol(64, "SHORT", 'T'));
    this.symbols.add(new Symbol(65, "FLOAT", 'T'));
    this.symbols.add(new Symbol(66, "DOUBLE", 'T'));
    this.symbols.add(new Symbol(67, "int", 'T'));
    this.symbols.add(new Symbol(68, "float", 'T'));
    this.symbols.add(new Symbol(69, "double", 'T'));
    this.symbols.add(new Symbol(70, "char", 'T'));
    this.symbols.add(new Symbol(71, "+=", 'T'));
    this.symbols.add(new Symbol(72, "-=", 'T'));
    this.symbols.add(new Symbol(73, "*=", 'T'));
    this.symbols.add(new Symbol(74, "/=", 'T'));
    this.symbols.add(new Symbol(75, "%=", 'T'));
    this.symbols.add(new Symbol(76, "RETURN", 'T'));
    this.symbols.add(new Symbol(77, "CONTINUE", 'T'));
    this.symbols.add(new Symbol(78, "BREAK", 'T'));
    this.symbols.add(new Symbol(79, "[", 'T'));
    this.symbols.add(new Symbol(80, "]", 'T'));
    this.symbols.add(new Symbol(81, "#", 'T'));//结束符
  }

  public void calculateFirstSets() {
    boolean flag = true;
    while (flag) {
      // iterate all productions
      for (int i = 0; i < this.productions.size(); i++) {
        Production temp = this.productions.get(i);
        String left = temp.getLHS();
        String[] right = temp.getRHS();
        Symbol left_symbol = this.getSymbol(left);

        for (int j = 0; j < right.length; j++) {
          Symbol right_symbol = this.getSymbol(right[j]);
          for (int k = 0; k < right_symbol.first.size(); k++) {
            String element = right_symbol.first.get(k);
            if (!left_symbol.has("first", element)) {
              left_symbol.first.add(element);
              flag = false;// first set is changed 
            }
          }
          // if the symbol can result in null, break
          if (!this.ifCanProduceNull(right[j])) {
            break;
          }
        }
      }
      flag = !flag; // break only when first set is not changed
    }
  }

  public void calculateFollowSets() {
    boolean flag = true;
    this.getSymbol("S").follow.add("#");

    while (flag) {
      // iterate all productions
      for (int i = 0; i < this.productions.size(); i++) {
        Production temp = this.productions.get(i);
        String left = temp.getLHS();
        String[] right = temp.getRHS();

        if (right.length == 0) {
          continue;
        }

        Symbol left_symbol = this.getSymbol(left);
        for (int j = 0; j < right.length - 1; j++) {
          Symbol right_symbol = this.getSymbol(right[j]);
          if (right_symbol.isTerminal()) {
            continue;
          }
          Symbol follow_symbol = this.getSymbol(right[j + 1]);
          for (int k = 0; k < follow_symbol.first.size(); k++) {
            String element = follow_symbol.first.get(k);

            if (!right_symbol.has("follow", element)) {
              right_symbol.follow.add(element);
              flag = false; // follow set is changed
            }
          }

          // add left_symbol.follow to right_symbol.follow if the right_symbol 
          // can result in a null string
          boolean blank = true;
          for (int k = j + 1; k < right.length; k++) {
            if (this.ifCanProduceNull(right[k])) {
              if (k + 1 < right.length) {
                Symbol rr_symbol = this.getSymbol(right[k + 1]);
//                System.out
//                    .println(rr_symbol.getName() + right_symbol.getName());
                for (int m = 0; m < rr_symbol.first.size(); m++) {
                  String element = rr_symbol.first.get(m);

                  if (!right_symbol.has("follow", element)) {
                    right_symbol.follow.add(element);
                    flag = false; // follow set is changed
                  }
                }
              }
            } else {
              blank = false;
              break;
            }
          }
          if (blank) {
            for (int k = 0; k < left_symbol.follow.size(); k++) {
              String element = left_symbol.follow.get(k);

              if (!right_symbol.has("follow", element)) {
                right_symbol.follow.add(element);
                flag = false; // follow set is changed
              }
            }
          }
        }

        // for each production, follow set of the left_symbol must be merged 
        // into the follow set of the last symbol in RHS
        Symbol last_symbol = this.getSymbol(right[right.length - 1]);
        if (last_symbol.isTerminal()) {
          continue;
        }

        for (int k = 0; k < left_symbol.follow.size(); k++) {
          String element = left_symbol.follow.get(k);

          if (!last_symbol.has("follow", element)) {
            last_symbol.follow.add(element);
            flag = false; // follow set is changed
          }
        }
      }

      flag = !flag; // break only when follow set is not changed after an iteration
    }

  }

  public void calculateSelectSets() {
    for (int i = 0; i < this.productions.size(); i++) {
      Production production = this.productions.get(i);

      if (production.getRHS().length == 0) {
        List<String> select = new ArrayList<String>();
        List<String> follow = this.getSymbol(production.getLHS()).follow;
        for (int j = 0; j < follow.size(); j++) {
          select.add(follow.get(j));
        }

        production.setSelectSet(select);
      } else {
        List<String> select = new ArrayList<String>();
        String[] right = production.getRHS();

        boolean canBeBlank = true; // whether the production can produce a null string
        for (int j = 0; j < right.length; j++) {
          List<String> first = this.getSymbol(right[j]).first;
          for (int k = 0; k < first.size(); k++) {
            select.add(first.get(k));
          }
          if (!this.ifCanProduceNull(right[j])) {
            canBeBlank = false;
            break;
          }
        }
        if (canBeBlank) {
          List<String> follow = this.getSymbol(production.getLHS()).follow;

          for (int k = 0; k < follow.size(); k++) {
            if (!select.contains(follow.get(k))) {
              select.add(follow.get(k));
            }
          }
        }

        production.setSelectSet(select);
      }
    }
  }

  public List<Production> getProductions() {
    return new ArrayList<>(this.productions);
  }

  public List<Production> getProductionsByLHS(String left) {
    List<Production> productionList = new ArrayList<Production>();
    for (int i = 0; i < this.productions.size(); i++) {
      Production temp = this.productions.get(i);
      if (temp.getLHS().equals(left)) {
        productionList.add(temp);
      }
    }
    return productionList;
  }

  /**
   * Judge whether a grammar symbol can produce an empty string.
   * 
   * @param symbol grammar symbol
   * @return true if it can; false otherwise
   */
  private boolean ifCanProduceNull(String name) {
    // iterate all productions and check 
    for (int i = 0; i < this.productions.size(); i++) {
      Production temp = this.productions.get(i);
      // select the productions of this grammar symbol
      if (temp.getLHS().equals(name)) {
        String[] right = temp.getRHS();

        if (right.length == 0) {
          return true;
        }

        boolean flag = true;
        for (int j = 0; j < right.length; j++) {
          if (!this.ifCanProduceNull(right[j])) {
            flag = false;
            break;
          }
        }
        if (flag) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Get the first production that can produce null from grammar symbol.
   * 
   * @param name grammar symbol
   * @return the first production that can produce null from the grammar
   *         symbol; null if there is no such production
   */
  private Production getProductionToBlank(String name) {
    for (int i = 0; i < this.productions.size(); i++) {
      Production temp = this.productions.get(i);
      if (temp.getLHS().equals(name)) {
        String[] right = temp.getRHS();

        if (right.length == 0) {
          return temp;
        }

        boolean flag = true;
        for (int j = 0; j < right.length; j++) {
          if (!this.ifCanProduceNull(right[j])) {
            flag = false;
            break;
          }
        }

        if (flag) {
          return temp;
        }
      }
    }

    return null;
  }

  public List<Symbol> getSymbols() {
    return new ArrayList<>(this.symbols);
  }

  Symbol getSymbol(String name) {
    for (int i = 0; i < this.symbols.size(); i++) {
      Symbol temp = this.symbols.get(i);
      if (temp.getName().equals(name)) {
        return temp;
      }
    }

    return null; // symbol is not in the symbol list
  }

  private Id getId(String name) {
    for (int i = 0; i < this.ids.size(); i++) {
      if (this.ids.get(i).getName().equals(name)) {
        return this.ids.get(i);
      }
    }
    return null;
  }

  public List<Production> analysis(List<Token> token_list) {

    int offset = 0; // offset of IDNs in memory
    int tempNo = 0; // number of temporary variable
    int boolNo = 0; // number of bool variable

    token_list.add(new Token("#", null));

    Stack<Symbol> stack = new Stack<Symbol>(); // stack for symbols
    Stack<Node> node_stack = new Stack<Node>();
    List<Production> pro_list = new ArrayList<Production>(); // productions list by order

    stack.push(this.getSymbol("#")); // bottom of the stack
    stack.push(this.getSymbol("S"));

    node_stack.push(new Node("S", null));

    int tokenPointer = 0; // number of matched token / token pointer
    int linePointer = 1; // line number

    while (tokenPointer < token_list.size()) {
      Token token = token_list.get(tokenPointer);

      Symbol input_symbol = this.getSymbol(token.getName());

      // error handling - only when tokens are wrong
      if (input_symbol == null) { // cannot recognize the input_symbol
        // the symbol is not in the default symbols' list
        if (token.getName().equals("ENTER")) {
          // the token is a line shifter
          linePointer++;
        } else if (token.getName().equals("ERROR")) {
          // the token is an wrong token
          ErrorProduction err_pro = new ErrorProduction(-1,
              stack.get(stack.size() - 1).getName(),
              stack.get(stack.size() - 1).getName());
          err_pro.setErrorMsg("无法识别的输入符号 \'" + token.getSource()
              + "\' at line " + linePointer);
          err_pro.setSolutionMsg("跳过错误单词\'" + token.getSource() + "\'");
          pro_list.add(err_pro);
        } else {
          // whatever the type of this token is, it's a wrong token
          ErrorProduction err_pro = new ErrorProduction(-1,
              stack.get(stack.size() - 1).getName(),
              stack.get(stack.size() - 1).getName());
          err_pro.setErrorMsg(
              "无法识别的输入符号\'" + token.getName() + "\' at line " + linePointer);
          err_pro.setSolutionMsg("跳过输入符号\'" + token.getName() + "\'");
          pro_list.add(err_pro);
        }

        tokenPointer++;//跳过该输入符号
        continue;
      }

      Symbol stackTopSymbol = null;
      Node nodeStackTopSymbol = null;
      try {
        stackTopSymbol = stack.pop();

        if (!stackTopSymbol.isTerminal()) {
          nodeStackTopSymbol = node_stack.pop();
        }

      } catch (EmptyStackException e) {//符号栈已空，输入未结束
        ErrorProduction err_pro = new ErrorProduction(-1, "#", "#");
        err_pro.setErrorMsg("符号栈已空，输入栈仍然有字符存在");
        err_pro.setSolutionMsg("句法分析终止");

        pro_list.add(err_pro);

        break;
      }

      // 当stackTopSymbol是一个代表语义分析程序段的文法符号时，
      // left_node.getFather()就代表它所在产生式的左部结点
      /*
       * 直接将声明变量的类型以及该变量的内存占用放到属性列表中
       */
      if (stackTopSymbol.getName().equals("M68_2")) {
        nodeStackTopSymbol.getFather().attribute.put("type", "char");
        nodeStackTopSymbol.getFather().attribute.put("length", "1");
      } else if (stackTopSymbol.getName().equals("M69_2")) {
        nodeStackTopSymbol.getFather().attribute.put("type", "int");
        nodeStackTopSymbol.getFather().attribute.put("length", "4");
      } else if (stackTopSymbol.getName().equals("M70_2")) {
        nodeStackTopSymbol.getFather().attribute.put("type", "long");
        nodeStackTopSymbol.getFather().attribute.put("length", "4");
      } else if (stackTopSymbol.getName().equals("M71_2")) {
        nodeStackTopSymbol.getFather().attribute.put("type", "short");
        nodeStackTopSymbol.getFather().attribute.put("length", "2");
      } else if (stackTopSymbol.getName().equals("M72_2")) {
        nodeStackTopSymbol.getFather().attribute.put("type", "float");
        nodeStackTopSymbol.getFather().attribute.put("length", "4");
      } else if (stackTopSymbol.getName().equals("M73_2")) {
        nodeStackTopSymbol.getFather().attribute.put("type", "double");
        nodeStackTopSymbol.getFather().attribute.put("length", "8");
      } else if (stackTopSymbol.getName().equals("M13_2")) {
        /*
         * 0维数组的声明语句，标记了数组变量的（名称，类型，内存占用，维度）
         */
        Node father = nodeStackTopSymbol.getFather();

        father.sons.get(1).attribute.put("name",
            token_list.get(tokenPointer - 1).getSource());
        father.sons.get(1).attribute.put("type",
            father.sons.get(0).attribute.get("type"));
        father.sons.get(1).attribute.put("length",
            father.sons.get(0).attribute.get("length"));
        father.sons.get(1).attribute.put("dimension", "0");
      } else if (stackTopSymbol.getName().equals("M15_4")) {
        /*
         * 多维数组的声明语句，通过获取父结点来修改父结点的对应属性值
         */
        Node father = nodeStackTopSymbol.getFather();
        int num = 0;
        try {
          num = Integer
              .parseInt(token_list.get(tokenPointer - 2).getValue());
        } catch (NumberFormatException e) {
          System.out.println(
              "error: not a valid index for array at line " + linePointer);
          continue;
        }
        int father_dimension = Integer
            .parseInt(father.attribute.get("dimension"));

        father.sons.get(0).attribute.put("name", father.attribute.get("name"));
        father.sons.get(0).attribute.put("type", father.attribute.get("type"));

        if (father.attribute.get("length") != null) {
          father.sons.get(0).attribute.put("length",
              Integer.parseInt(father.attribute.get("length")) * num + "");
        }

        father.sons.get(0).attribute.put("dimension",
            (father_dimension + 1) + "");
        father.sons.get(0).attribute.put("arr" + father_dimension, "" + num);

        for (int i = 0; i < father_dimension; i++) {
          father.sons.get(0).attribute.put("arr" + i,
              "" + father.attribute.get("arr" + i));
        }

      } else if (stackTopSymbol.getName().equals("M14_1")) {
        /*
         * 数组声明结束，将数组加入记录管理器中 
         */
        Node father = nodeStackTopSymbol.getFather();

        if (father.attribute.get("length") != null) { //定义语句中的数组
          // 在定义语句中会对数组变量声明的结点的属性length做修改
          int length = Integer.parseInt(father.attribute.get("length"));
          Id id = new Id(father.attribute.get("name"),
              father.attribute.get("type"), offset, length);

          offset += length;

          int dimension = Integer.parseInt(father.attribute.get("dimension"));

          for (int i = 0; i < dimension; i++) {
            id.arr_list.add(Integer.parseInt(father.attribute.get("arr" + i)));
          }

          Id checkDuplicated = this.getId(father.attribute.get("name"));
          if (checkDuplicated == null) {
            this.ids.add(id);
          } else {
            System.out.println(
                "error - duplicated declaration at line: " + linePointer);
          }
        } else {//执行语句中的数组
          // 执行语句中的数组引用，不会对结点的length做修改
          // 以下是对数组的引用内存的位置的求值（根据偏移量）
          String name = father.attribute.get("name");
          Id id = this.getId(name);
          if (id == null) {
            // there is no such IDN. used without declaration. 
            System.out.println(
                "error: IDN used without declaration - at line " + linePointer);
            continue;
          }
          String type = id.getType();
          int dimension = id.arr_list.size();

          int offstinArray = 0;
          int width = 1;

          for (int i = dimension - 1; i >= 0; i--) {
            int arr = Integer.parseInt(father.attribute.get("arr" + i));
            offstinArray += arr * width;
            width *= id.arr_list.get(i);
          }

          if (type.equals("int") || type.equals("long")
              || type.equals("float")) {
            offstinArray *= 4;
          } else if (type.equals("double")) {
            offstinArray *= 8;
          } else if (type.equals("short")) {
            offstinArray *= 2;
          }

          if (id.arr_list.size() > 0) {
            String t = "t" + (tempNo++);
            this.codes.add(t + " := " + name + "[" + offstinArray + "]");
            father.attribute.put("value", t);
            father.attribute.put("val", name + "[" + offstinArray + "]");
          } else {
            father.attribute.put("value", name);
          }

        }

      } else if (stackTopSymbol.getName().equals("M13_4")) {
        /*
         * 传递属性给兄弟结点（根据M13_4所在的产生式，产生式内的IDN为父结点的第3个子结点）
         */
        Node father = nodeStackTopSymbol.getFather();

        father.sons.get(2).attribute.put("type",
            father.sons.get(0).attribute.get("type"));
        father.sons.get(2).attribute.put("length",
            father.sons.get(0).attribute.get("length"));
      } else if (stackTopSymbol.getName().equals("M17_3")) {
        /*
         * 连续声明的组合拓展。（可能是数组，也可能是单纯的数值变量）
         */
        Node father = nodeStackTopSymbol.getFather();

        father.sons.get(0).attribute.put("type", father.attribute.get("type"));
        father.sons.get(0).attribute.put("length",
            father.attribute.get("length"));
        father.sons.get(0).attribute.put("name",
            token_list.get(tokenPointer - 1).getSource());
        father.sons.get(0).attribute.put("dimension", "0");
      } else if (stackTopSymbol.getName().equals("M17_5")) {
        /*
         * 传递属性给兄弟结点（根据M17_5所在的产生式，产生式内的IDN为父结点的第2个子结点）
         */
        Node father = nodeStackTopSymbol.getFather();

        father.sons.get(1).attribute.put("type",
            father.sons.get(0).attribute.get("type"));
        father.sons.get(1).attribute.put("length",
            father.sons.get(0).attribute.get("length"));
      } else if (stackTopSymbol.getName().equals("M74_2")) {
        /*
         * 根据输入语法的特点，当前产生式中，栈顶token的前一个token的属性值应当赋值给父结点的属性值
         */
        Node father = nodeStackTopSymbol.getFather();

        father.attribute.put("value",
            token_list.get(tokenPointer - 1).getValue());
      } else if (stackTopSymbol.getName().equals("M57_3")) {
        /*
         * “！”运算符的实现，同时修改父结点的值（赋值临时变量）
         */
        Node father = nodeStackTopSymbol.getFather();

        String f1 = "b" + (boolNo++);
        String f2 = father.sons.get(0).attribute.get("value");

        this.codes.add(f1 + " := ~" + f2); // 添加三地址代码

        father.attribute.put("value", f1);
      } else if (stackTopSymbol.getName().equals("M58_3")) {
        /*
         * “++”运算符的实现，同时修改父结点的值（赋值临时变量）
         */
        Node father = nodeStackTopSymbol.getFather();

        String f1 = "t" + (tempNo++);
        String f2 = father.sons.get(0).attribute.get("value");

        this.codes.add(f1 + " := " + f2 + " + 1"); // 添加三地址代码

        father.attribute.put("value", f1);
      } else if (stackTopSymbol.getName().equals("M59_3")) {
        /*
         * “--”运算符的实现，同时修改父结点的值（赋值临时变量）
         */
        Node father = nodeStackTopSymbol.getFather();

        String f1 = "t" + (tempNo++);
        String f2 = father.sons.get(0).attribute.get("value");

        this.codes.add(f1 + " := " + f2 + " - 1");

        father.attribute.put("value", f1);
      } else if (stackTopSymbol.getName().equals("M84_3")) {
        /*
         * 取反操作“-”的实现，同时修改父结点的值（复制临时变量）
         */
        Node father = nodeStackTopSymbol.getFather();

        String f1 = "t" + (tempNo++);
        String f2 = father.sons.get(0).attribute.get("value");

        this.codes.add(f1 + " := 0 - " + f2);

        father.attribute.put("value", f1);
      } else if (stackTopSymbol.getName().equals("M60_4")) {
        /*
         * 运算中“（）”的实现，提取孩子结点的属性值赋值给父结点
         */
        Node father = nodeStackTopSymbol.getFather();

        father.attribute.put("value",
            father.sons.get(0).attribute.get("value"));
      } else if (stackTopSymbol.getName().equals("M61_2")) {
        /*
         * 提取token_list，给父结点的孩子结点（表示IDN的symbol）的属性赋值（name属性）
         */
        Node father = nodeStackTopSymbol.getFather();

        father.sons.get(0).attribute.put("name",
            token_list.get(tokenPointer - 1).getSource());
      } else if (stackTopSymbol.getName().equals("M63_1")) {
        /*
         * 数组的引用，父结点的初始化
         */
        Node father = nodeStackTopSymbol.getFather();

        father.sons.get(0).attribute.put("name", father.attribute.get("name"));
        father.sons.get(0).attribute.put("dimension", "0");
      } else if (stackTopSymbol.getName().equals("M15_6")
          || stackTopSymbol.getName().equals("M63_3")
          || stackTopSymbol.getName().equals("M61_4")
          || stackTopSymbol.getName().equals("M62_2")) {
        /*
         * 取值操作（数组赋值，函数运行）的结束
         */
        Node father = nodeStackTopSymbol.getFather();

        father.attribute.put("value",
            father.sons.get(0).attribute.get("value"));
        father.attribute.put("val", father.sons.get(0).attribute.get("val"));
      } else if (stackTopSymbol.getName().equals("M52_2")) {
        /*
         * 将属性值传递给兄弟结点
         */
        Node father = nodeStackTopSymbol.getFather();

        father.sons.get(1).attribute.put("value",
            father.sons.get(0).attribute.get("value"));
        father.sons.get(1).attribute.put("val",
            father.sons.get(0).attribute.get("val"));
      } else if (stackTopSymbol.getName().equals("M54_3")) {
        /*
         * “*”乘法运算的实现
         */
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");
        String t = "t" + (tempNo++);

        this.codes.add(t + " := " + inh + " * " + value);

        father.sons.get(1).attribute.put("value", t);
      } else if (stackTopSymbol.getName().equals("M55_3")) {
        /*
         * “/”除法运算的实现
         */
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");
        String t = "t" + (tempNo++);

        this.codes.add(t + " := " + inh + " / " + value);

        father.sons.get(1).attribute.put("value", t);
      } else if (stackTopSymbol.getName().equals("M56_3")) {
        /*
         * “%”取余运算的实现
         */
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");
        String t = "t" + (tempNo++);

        this.codes.add(t + " := " + inh + " % " + value);

        father.sons.get(1).attribute.put("value", t);
      } else if (stackTopSymbol.getName().equals("M50_3")) {
        /*
         * “+”加法运算的实现
         */
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");
        String t = "t" + (tempNo++);

        this.codes.add(t + " := " + inh + " + " + value);

        father.sons.get(1).attribute.put("value", t);
      } else if (stackTopSymbol.getName().equals("M51_3")) {
        /*
         * “-”减法运算的实现
         */
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");
        String t = "t" + (tempNo++);

        this.codes.add(t + " := " + inh + " - " + value);

        father.sons.get(1).attribute.put("value", t);
      } else if (stackTopSymbol.getName().equals("M52_4")) {
        /*
         * 代数运算结束，将子结点的值传递给父结点
         */
        Node father = nodeStackTopSymbol.getFather();

        father.attribute.put("value",
            father.sons.get(1).attribute.get("value"));
        father.attribute.put("val", father.sons.get(1).attribute.get("val"));
      } else if (stackTopSymbol.getName().equals("M42_4")) {
        /*
         * “<”运算的实现
         */
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");
        String b = "b" + (boolNo++);

        this.codes.add(b + " := " + inh + " < " + value);

        father.attribute.put("value", b);
      } else if (stackTopSymbol.getName().equals("M43_4")) {
        /*
         * “<=”运算的实现
         */
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");
        String b = "b" + (boolNo++);

        this.codes.add(b + " := " + inh + " <= " + value);

        father.attribute.put("value", b);
      } else if (stackTopSymbol.getName().equals("M44_4")) {
        /*
         * “>”运算的实现
         */
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");
        String b = "b" + (boolNo++);

        this.codes.add(b + " := " + inh + " > " + value);

        father.attribute.put("value", b);
      } else if (stackTopSymbol.getName().equals("M45_4")) {
        /*
         * “>=”运算的实现
         */
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");
        String b = "b" + (boolNo++);

        this.codes.add(b + " := " + inh + " >= " + value);

        father.attribute.put("value", b);
      } else if (stackTopSymbol.getName().equals("M46_4")) {
        /*
         * “==”运算的实现
         */
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");
        String b = "b" + (boolNo++);

        this.codes.add(b + " := " + inh + " == " + value);

        father.attribute.put("value", b);
      } else if (stackTopSymbol.getName().equals("M47_4")) {
        /*
         * “!=”运算的实现
         */
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");
        String b = "b" + (boolNo++);

        this.codes.add(b + " := " + inh + " != " + value);

        father.attribute.put("value", b);
      } else if (stackTopSymbol.getName().equals("M78_3")) {
        // 输出赋值语句的三地址指令
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("val");
        String value = father.sons.get(0).attribute.get("value");
        if (inh == null || inh.equals("null")) {
          inh = father.attribute.get("value");
        } else {
          String temp = father.attribute.get("value");

          for (int i = this.codes.size() - 1; i >= 0; i--) {
            if (this.codes.get(i) != null
                && this.codes.get(i).startsWith(temp)) {
              this.codes.remove(i);
            }
          }
        }

        this.codes.add(inh + " := " + value);

        father.attribute.put("value", inh);
      } else if (stackTopSymbol.getName().equals("M79_3")) {
        // 输出出加法+赋值语句的三地址指令
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");

        this.codes.add(inh + " := " + inh + " + " + value);

        father.attribute.put("value", inh);
      } else if (stackTopSymbol.getName().equals("M80_3")) {
        // 输出出减法+赋值语句的三地址指令
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");

        this.codes.add(inh + " := " + inh + " - " + value);

        father.attribute.put("value", inh);
      } else if (stackTopSymbol.getName().equals("M81_3")) {
        // 输出出乘法+赋值语句的三地址指令
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");

        this.codes.add(inh + " := " + inh + " * " + value);

        father.attribute.put("value", inh);
      } else if (stackTopSymbol.getName().equals("M82_3")) {
        // 输出出除法+赋值语句的三地址指令
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");

        this.codes.add(inh + " := " + inh + " / " + value);

        father.attribute.put("value", inh);
      } else if (stackTopSymbol.getName().equals("M83_3")) {
        // 输出取余+赋值语句的三地址指令
        Node father = nodeStackTopSymbol.getFather();

        String inh = father.attribute.get("value");
        String value = father.sons.get(0).attribute.get("value");

        this.codes.add(inh + " := " + inh + " % " + value);

        father.attribute.put("value", inh);
      } else if (stackTopSymbol.getName().equals("M26_5")) {
        // 将父亲节点的条件提取出来，输出 if 条件 goto 三地址码规模+2 的语句。
        Node father = nodeStackTopSymbol.getFather();
        String b = father.sons.get(0).attribute.get("value");

        this.codes.add("if " + b + " goto " + (this.codes.size() + 2));
        // 把“backpatch”设置为一个属性，之后根据它提取出来codes中对应的三地址码，进行修改
        father.attribute.put("backpatch", "" + this.codes.size());
        this.codes.add(null);
      } else if (stackTopSymbol.getName().equals("M26_7")) {
        // 将父亲节点的“backpatch”属性输出，并将其设置为三地址码的规模-1.
        Node father = nodeStackTopSymbol.getFather();
        int backpatch = Integer.parseInt(father.attribute.get("backpatch"));

        this.codes.add(null);
        this.codes.set(backpatch, "goto " + this.codes.size());// 回填

        father.attribute.put("backpatch", "" + (this.codes.size() - 1));
      } else if (stackTopSymbol.getName().equals("M26_9")) {
        // 输出父亲节点的“backpatch”属性
        Node father = nodeStackTopSymbol.getFather();
        int backpatch = Integer.parseInt(father.attribute.get("backpatch"));

        this.codes.set(backpatch, "goto " + this.codes.size());// 回填
      } else if (stackTopSymbol.getName().equals("M38_7")) {
        // 将父亲的“backpatch”与“backto”属性提取出来并将其以goto形式输出
        Node father = nodeStackTopSymbol.getFather();
        int backpatch = Integer.parseInt(father.attribute.get("backpatch"));
        int backto = Integer.parseInt(father.attribute.get("backto"));
        this.codes.add("goto " + backto);
        this.codes.set(backpatch, "goto " + this.codes.size());// 回填
      } else if (stackTopSymbol.getName().equals("M38_3")) {
        // 将该节点的父亲的属性“backto”设置为三地址指令的最后的标号
        Node father = nodeStackTopSymbol.getFather();

        father.attribute.put("backto", "" + this.codes.size());
      }
//			System.out.println(leftest.getName());

      if (stackTopSymbol.isTerminal()) {
        // if the symbol is a terminal
        if (stackTopSymbol.getName().equals(input_symbol.getName())) {
          tokenPointer++;//匹配
        } else if (tokenPointer < token_list.size()
            && token_list.get(tokenPointer + 1).getName()
                .equals(stackTopSymbol.getName())) {//error:栈顶的终结符与输入的终结符不匹配

          ErrorProduction err_pro = new ErrorProduction(-1,
              stackTopSymbol.getName(),
              stackTopSymbol.getName());//action:弹出此终结符
          err_pro
              .setErrorMsg("栈顶的终结符\'" + stackTopSymbol.getName() + "\'与输入的终结符\'"
                  + input_symbol.getName() + "\'不匹配 at line " + linePointer);
          err_pro.setSolutionMsg("跳过输入的终结符\'" + input_symbol.getName() + "\'");

          pro_list.add(err_pro);

          tokenPointer++;//跳过一个输入
          stack.push(stackTopSymbol);//把栈顶终结符压回
        } else {//error:栈顶的终结符与输入的终结符不匹配

          ErrorProduction err_pro = new ErrorProduction(-1,
              stackTopSymbol.getName());//action:弹出此终结符
          err_pro
              .setErrorMsg("栈顶的终结符\'" + stackTopSymbol.getName() + "\'与输入的终结符\'"
                  + input_symbol.getName() + "\'不匹配 at line " + linePointer);
          err_pro.setSolutionMsg("弹出栈顶终结符\'" + stackTopSymbol.getName() + "\'");

          pro_list.add(err_pro);
        }
      } else {
        // 如果符号是非终结符，将其Select集合中与输入的token匹配的产生会提取出来，
        // 并将产生式中的元素反向压入栈中，在树中将这些节点加上并处理。
        List<Production> pros = this
            .getProductionsByLHS(stackTopSymbol.getName());

        boolean error = true;//出现错误
        for (int i = 0; i < pros.size(); i++) {
          if (pros.get(i).getSelectSet().contains(input_symbol.getName())) {
            pro_list.add(pros.get(i));
            String[] right = pros.get(i).getRHS();

            for (int j = right.length - 1; j >= 0; j--) {

              Symbol temp = this.getSymbol(right[j]);

              stack.push(temp);

              if (!temp.isTerminal()) {
                Node node = new Node(temp.getName(), nodeStackTopSymbol);

                if (node.getSymbol_name().charAt(0) != 'M') {
                  nodeStackTopSymbol.sons.add(0, node);
                }

                node_stack.push(node);
              }
            }

            error = false;
            break;
          }
        }

        // 如果发生错误，那么如果栈顶非终结符推出的产生式非空，那么打印错误信息并推迟处理；如果栈顶非终结
        // 符推出的产生式为空而且该当前非终结符的follow集合中含有输入符号，那么跳过栈顶的非终结符；
        if (error) {
          Production pro = this.getProductionToBlank(stackTopSymbol.getName());

          if (pro != null) {//栈顶非终结符能推出空
            ErrorProduction err_pro = new ErrorProduction(pro);
            err_pro
                .setErrorMsg(
                    "栈顶非终结符\'" + stackTopSymbol.getName() + "\'不能接收输入的终结符\'"
                        + input_symbol.getName() + "\' at line " + linePointer);
            err_pro.setSolutionMsg(
                "使用能将栈顶非终结符\'" + stackTopSymbol.getName()
                    + "\'推导为空的产生式，推迟错误处理");

            pro_list.add(err_pro);
          } else if (stackTopSymbol.has("follow", input_symbol.getName())) {
            ErrorProduction err_pro = new ErrorProduction(-1,
                stackTopSymbol.getName());
            err_pro
                .setErrorMsg(
                    "栈顶非终结符\'" + stackTopSymbol.getName() + "\'不能接收输入的终结符\'"
                        + input_symbol.getName() + "\' at line " + linePointer);
            err_pro
                .setSolutionMsg("跳过栈顶非终结符\'" + stackTopSymbol.getName() + "\'");

            pro_list.add(err_pro);
          } else {// 其他情况下跳过输入的终结符
            ErrorProduction err_pro = new ErrorProduction(-1,
                stack.get(stack.size() - 1).getName(),
                stack.get(stack.size() - 1).getName());
            err_pro
                .setErrorMsg(
                    "栈顶非终结符\'" + stackTopSymbol.getName() + "\'不能接收输入的终结符\'"
                        + input_symbol.getName() + "\' at line " + linePointer);
            err_pro
                .setSolutionMsg("跳过输入的终结符\'" + input_symbol.getName() + "\'");
            pro_list.add(err_pro);

            tokenPointer++;
            stack.push(input_symbol);
          }
        }
      }
    }

    /////////////测试
    for (int i = 0; i < this.codes.size(); i++) {
      System.out.println(i + "\t" + this.codes.get(i));
    }

    return pro_list;
  }
}
