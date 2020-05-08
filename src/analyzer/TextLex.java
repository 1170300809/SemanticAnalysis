package analyzer;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

public class TextLex {

  private String text;
  private DefaultTableModel tbmodel_lex_result;
  // private DefaultTableModel tbmodel_lex_error;
  private ArrayList<String> lex_result_stack;
  private ArrayList<HashMap<String, String>> lex_error_stack;
  private int text_length;
  private int row_number = 1;
  String[] Key = { "void", "int", "double", "long", "char", "float", "else",
      "if", "return", "for",
      "goto", "short", "static", "do", "while" };

  public TextLex(String text, DefaultTableModel tb_lex_result,
      DefaultTableModel tb_lex_error) {
    this.lex_result_stack = new ArrayList<String>();
    this.lex_error_stack = new ArrayList<HashMap<String, String>>();
    this.text = text;
    this.tbmodel_lex_result = tb_lex_result;
    // this.tbmodel_lex_error = tb_lex_error;
    this.text_length = text.length();
  }

  /**
   * 返回词法分析的结果
   */
  public ArrayList<String> get_Lex_Result() {
    return this.lex_result_stack;
  }

  /**
   * 返回词法分析的错误信息
   */
  public ArrayList<HashMap<String, String>> get_Lex_Error() {
    // TODO Auto-generated constructor stub
    return this.lex_error_stack;
  }

  /**
   * 字符是否为字母或下划线
   */
  public int isAlpha(char c) {
    if (((c <= 'z') && (c >= 'a')) || ((c <= 'Z') && (c >= 'A'))
        || (c == '_')) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * 字符是否为数字
   */
  public int isNumber(char c) {
    if ((c >= '0') && (c <= '9')) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * 字符是否为关键字或标识符
   */
  public int isKey(String t) {
    for (int i = 0; i < this.Key.length; i++) {
      if (t.equals(this.Key[i])) {
        return 1;
      }
    }
    // 只是普通的标识符
    return 0;
  }

  // 处理整个字符串
  public void scannerAll() {
    int i = 0;
    char c;
    // 将字符串延长一位，防止溢出
    this.text = this.text + '\0';
    while (i < this.text_length) {
      c = this.text.charAt(i);
      if (c == ' ' || c == '\t') {
        i++;
      } else if (c == '\r' || c == '\n') {
        this.row_number++;
        i++;
      } else {
        i = this.scannerPart(i);
      }
    }
  }

  /**
   * 处理内容的首字母
   */
  public int scannerPart(int arg0) {
    int i = arg0;
    char ch = this.text.charAt(i);
    String s = "";
    // 第一个输入的字符是字母
    if (this.isAlpha(ch) == 1) {
      s = "" + ch;
      return this.handleFirstAlpha(i, s);
    }
    // 第一个是数字的话
    else if (this.isNumber(ch) == 1) {
      s = "" + ch;
      return this.handleFirstNum(i, s);

    }
    // 既不是既不是数字也不是字母，判断它是哪个界符或双界符
    else {
      s = "" + ch;
      switch (ch) {
        case ' ':
        case '\n':
        case '\r':
        case '\t':
          return ++i;
        case '[':
        case ']':
        case '(':
        case ')':
        case '{':
        case '}':
          this.printResult(s, "双界符");
          return ++i;
        case ':':
          if (this.text.charAt(i + 1) == '=') {
            s = s + "=";
            this.printResult(s, "界符");
            return i + 2;
          } else {
            this.printError(this.row_number, s, "不能识别");
            return i + 1;
          }
        case ',':
        case '.':
        case ';':
          this.printResult(s, "单界符");
          return ++i;
        case '\\':
          if (this.text.charAt(i + 1) == 'n' || this.text.charAt(i + 1) == 't'
              || this.text.charAt(i + 1) == 'r') {
            this.printResult(s + this.text.charAt(i + 1), "转义");
            return i + 2;
          }
        case '\'':
          // 判断是否为单字符，否则报错
          return this.handleChar(i, s);
        case '\"':
          // 判定字符串
          return this.handleString(i, s);
        case '+':
          return this.handlePlus(i, s);
        case '-':
          return this.handleMinus(i, s);
        case '*':
        case '/':
          if (this.text.charAt(i + 1) == '*') {
            return this.handleNote(i, s);
          } else if (this.text.charAt(i + 1) == '/') {
            return this.handleSingleLineNote(i, s);
          }
        case '!':
        case '=':
          ch = this.text.charAt(++i);
          if (ch == '=') {
            // 输出运算符
            s = s + ch;
            this.printResult(s, "运算符");
            return ++i;
          } else {
            // 输出运算符
            this.printResult(s, "运算符");
            return i;
          }
        case '>':
          return this.handleMore(i, s);
        case '<':
          return this.handleLess(i, s);
        case '%':
          ch = this.text.charAt(++i);
          if (ch == '=') {
            // 输出运算符
            s = s + ch;
            this.printResult(s, "运算符");
            return ++i;
          } else if (ch == 's' || ch == 'c' || ch == 'd' || ch == 'f'
              || ch == 'l') {
            // 输出类型标识符
            s = s + ch;
            this.printResult(s, "输出类型标识符");
            return ++i;
          } else {
            // 输出求余标识符
            this.printResult(s, "求余标识符");
            return i;
          }
        default:
          // 输出暂时无法识别的字符,制表符也被当成了有问题的字符
          this.printError(this.row_number, s, "暂时无法识别的标识符");
          return ++i;
      }
    }
  }

  public int handleFirstAlpha(int arg, String arg0) {
    int i = arg;
    String s = arg0;
    char ch = this.text.charAt(++i);
    while (this.isAlpha(ch) == 1 || this.isNumber(ch) == 1) {
      s = s + ch;
      ch = this.text.charAt(++i);
    }
    // if(s.length()==1){
    // printResult(s, "字符常数");
    // return i;
    // }
    // 到了结尾
    if (this.isKey(s) == 1) {
      // 输出key
      this.printResult(s, "关键字");
      return i;

    } else {
      // 输出普通的标识符
      this.printResult(s, "标识符");
      return i;
    }
  }

  public int handleFirstNum(int arg, String arg0) {
    int i = arg;
    char ch = this.text.charAt(++i);
    String s = arg0;
    while (this.isNumber(ch) == 1) {
      s = s + ch;
      ch = this.text.charAt(++i);
    }
    if ((this.text.charAt(i) == ' ') || (this.text.charAt(i) == '\t')
        || (this.text.charAt(i) == '\n')
        || (this.text.charAt(i) == '\r') || (this.text.charAt(i) == '\0')
        || ch == ';' || ch == ','
        || ch == ')' || ch == ']' || ch == '[' || ch == '(') {
      // 到了结尾，输出数字
      this.printResult(s, "整数");
      return i;
    } else if (ch == 'E') {
      if (this.text.charAt(i + 1) == '+') {
        s = s + ch;
        ch = this.text.charAt(++i);
        s = s + ch;
        ch = this.text.charAt(++i);
        while (this.isNumber(ch) == 1) {
          s = s + ch;
          ch = this.text.charAt(++i);
        }
        if (ch == '\r' || ch == '\n' || ch == ';' || ch == '\t') {
          this.printResult(s, "科学计数");
          return ++i;
        } else {
          this.printError(i, s, "浮点数错误");
          return i;
        }
      } else if (this.isNumber(this.text.charAt(i + 1)) == 1) {
        s = s + ch;
        ch = this.text.charAt(++i);
        while (this.isNumber(ch) == 1) {
          s = s + ch;
          ch = this.text.charAt(++i);
        }
        if (ch == '\r' || ch == '\n' || ch == ';' || ch == '\t') {
          this.printResult(s, "科学计数");
          return ++i;
        } else {
          this.printError(this.row_number, s, "浮点数错误");
          return i;
        }
      } else {
        this.printError(this.row_number, s, "科学计数法错误");
        return ++i;
      }
    }

    // 浮点数判断
    else if (this.text.charAt(i) == '.'
        && (this.isNumber(this.text.charAt(i + 1)) == 1)) {
      s = s + '.';
      ch = this.text.charAt(++i);
      while (this.isNumber(ch) == 1) {
        s = s + ch;
        ch = this.text.charAt(++i);
      }
      if (ch == 'E') {
        if (this.text.charAt(i + 1) == '+') {
          s = s + ch;
          ch = this.text.charAt(++i);
          s = s + ch;
          ch = this.text.charAt(++i);
          while (this.isNumber(ch) == 1) {
            s = s + ch;
            ch = this.text.charAt(++i);
          }
          if (ch == '\r' || ch == '\n' || ch == ';' || ch == '\t') {
            this.printResult(s, "科学计数");
            return ++i;
          } else {
            this.printError(i, s, "浮点数错误");
            return i;
          }
        } else if (this.isNumber(this.text.charAt(i + 1)) == 1) {
          s = s + ch;
          ch = this.text.charAt(++i);
          while (this.isNumber(ch) == 1) {
            s = s + ch;
            ch = this.text.charAt(++i);
          }
          if (ch == '\r' || ch == '\n' || ch == ';' || ch == '\t') {
            this.printResult(s, "科学计数");
            return ++i;
          } else {
            this.printError(this.row_number, s, "浮点数错误");
            return i;
          }
        } else {
          this.printError(this.row_number, s, "科学计数法错误");
          return ++i;
        }
      } else if (ch == '\n' || ch == '\r' || ch == '\t' || ch == ' '
          || ch == '\0' || ch != ','
          || ch != ';') {
        this.printResult(s, "浮点数");
        return i;
      } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/'
          || ch == '\0') {
        this.printResult(s, "浮点数");
        return i;
      } else {
        while (ch != '\n' && ch != '\t' && ch != ' ' && ch != '\r' && ch != '\0'
            && ch != ';'
            && ch != '.' && ch != ',') {
          s = s + ch;
          ch = this.text.charAt(++i);
        }
        this.printError(this.row_number, s, "不合法的字符");
        return i;
      }
    } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '\0') {
      this.printResult(s, "整数");
      return i;
    } else {
      do {
        ch = this.text.charAt(i++);
        s = s + ch;
      } while ((this.text.charAt(i) != ' ') && (this.text.charAt(i) != '\t')
          && (this.text.charAt(i) != '\n')
          && (this.text.charAt(i) != '\r') && (this.text.charAt(i) != '\0'));
      this.printError(this.row_number, s, "错误的标识符");
      return i;
    }
  }

  public int handleChar(int arg, String arg0) {
    String s = arg0;
    int i = arg;
    char ch = this.text.charAt(++i);
    while (ch != '\'') {
      if (ch == '\r' || ch == '\n') {
        this.row_number++;
      } else if (ch == '\0') {
        this.printError(this.row_number, s, "单字符错误");
        return i;
      }
      s = s + ch;
      ch = this.text.charAt(++i);
    }
    s = s + ch;
    System.out.println(s);
    if (s.length() == 3 || s.equals("\'" + "\\" + "t" + "\'")
        || s.equals("\'" + "\\" + "n" + "\'")
        || s.equals("\'" + "\\" + "r" + "\'")) {
      this.printResult(s, "单字符");
    } else {
      this.printError(this.row_number, s, "字符溢出");
    }
    return ++i;
  }

  // 单行注释处理
  public int handleSingleLineNote(int arg, String arg0) {
    String s = arg0;
    int i = arg;
    char ch = this.text.charAt(++i);
    while (ch != '\r' && ch != '\n' && ch != '\0') {
      s = s + ch;
      ch = this.text.charAt(++i);
    }
    this.printResult(s, "单行注释");
    return i;
  }

  // 字符串处理
  public int handleString(int arg, String arg0) {
    String s = arg0;
    int i = arg;
    char ch = this.text.charAt(++i);
    while (ch != '"') {
      if (ch == '\r' || ch == '\n') {
        this.row_number++;
      } else if (ch == '\0') {
        this.printError(this.row_number, s, "字符串没有闭合");
        return i;
      }
      s = s + ch;
      ch = this.text.charAt(++i);
    }
    s = s + ch;
    this.printResult(s, "字符串");
    return ++i;
  }

  public int handlePlus(int arg, String arg0) {
    int i = arg;
    char ch = this.text.charAt(++i);
    String s = arg0;
    if (ch == '+') {
      // 输出运算符
      s = s + ch;
      this.printResult(s, "运算符");
      return ++i;
    }

    else if (ch == '=') {
      s = s + ch;
      // 输出运算符
      this.printResult(s, "运算符");
      return ++i;
    } else {
      // 输出运算符
      this.printResult(s, "运算符");
      return i;
    }
  }

  // 处理注释,没有考虑不闭合的情况
  public int handleNote(int arg, String arg0) {
    int i = arg;
    char ch = this.text.charAt(++i);
    String s = arg0 + ch;
    ch = this.text.charAt(++i);
    while (ch != '*'
        || ((i + 1) < this.text_length) && this.text.charAt(i + 1) != '/') {
      s = s + ch;
      if (ch == '\r' || ch == '\n') {
        this.row_number++;
      } else if (ch == '\0') {
        this.printError(this.row_number, s, "注释没有闭合");
        return i;
      }
      ch = this.text.charAt(++i);
    }
    s = s + "*/";
    this.printResult(s, "注释");
    return i + 2;
  }

  // 处理减号
  public int handleMinus(int arg, String arg0) {
    int i = arg;
    char ch = this.text.charAt(++i);
    String s = arg0;
    if (ch == '-') {
      s = s + ch;
      // 输出运算符
      this.printResult(s, "运算符");
      return ++i;
    }

    else if (ch == '=') {
      s = s + ch;
      // 输出运算符
      this.printResult(s, "运算符");
      return ++i;
    } else {
      // 输出运算符
      this.printResult(s, "运算符");
      return i;
    }
  }

  public int handleMore(int arg, String arg0) {
    int i = arg;
    char ch = this.text.charAt(++i);
    String s = arg0;
    if (ch == '=') {
      s = s + ch;
      // 输出运算符
      this.printResult(s, "运算符");
      return ++i;
    }

    else if (ch == '>') {
      s = s + ch;
      // 输出运算符
      this.printResult(s, "运算符");
      return ++i;
    } else {
      // 输出运算符
      this.printResult(s, "运算符");
      return i;
    }
  }

  public int handleLess(int arg, String arg0) {
    int i = arg;
    String s = arg0;
    char ch = this.text.charAt(++i);
    if (ch == '=') {
      s = s + ch;
      // 输出运算符
      this.printResult(s, "运算符");
      return ++i;
    }

    else if (ch == '<') {
      s = s + ch;
      // 输出运算符
      this.printResult(s, "运算符");
      return ++i;
    } else {
      // 输出运算符
      this.printResult(s, "运算符");
      return i;
    }
  }

  // 打印结果
  public void printResult(String rs_value, String rs_name) {
    // tbmodel_lex_result.addRow(new String[]{rs_value, rs_name});
    if (rs_name.equals("标识符")) {
      this.lex_result_stack.add("IDN");
      this.tbmodel_lex_result.addRow(new String[] { "IDN", rs_value });
    } else if (rs_name.equals("整数")) {
      this.lex_result_stack.add("INT10");
      this.tbmodel_lex_result.addRow(new String[] { "INT10", rs_value });
    } else if (rs_name.equals("科学计数") || rs_name.equals("浮点数")) {
      this.lex_result_stack.add("FLOAT");
      this.tbmodel_lex_result.addRow(new String[] { "FLOAT", rs_value });
    } else if (rs_name.equals("单字符")) {
      this.lex_result_stack.add("CHAR");
      this.tbmodel_lex_result.addRow(new String[] { "CHAR", rs_value });
    } else if (rs_name.equals("字符串")) {
      this.lex_result_stack.add("STR");
      this.tbmodel_lex_result.addRow(new String[] { "STR", rs_value });
    } else {
      this.lex_result_stack.add(rs_value);
      this.tbmodel_lex_result.addRow(new String[] { rs_name, rs_value });
    }

  }

  // 打印错误信息
  public void printError(int row_num, String rs_value, String rs_name) {
    // tbmodel_lex_error.addRow(new String[]{row_num+"", rs_value, rs_name});
    HashMap<String, String> hashMap = new HashMap<String, String>();
    hashMap.put("row_num", row_num + "");
    hashMap.put("rs_value", rs_value + "");
    hashMap.put("rs_name", rs_name + "");
    this.lex_error_stack.add(hashMap);
    this.tbmodel_lex_result
        .addRow(new String[] { "ERROR，" + rs_name, rs_value });
  }

}
