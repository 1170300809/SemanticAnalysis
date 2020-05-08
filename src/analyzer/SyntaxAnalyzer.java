package analyzer;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

public class SyntaxAnalyzer {
  public static final String PREDICT_FILEPATH = "predictldy.txt";// 预测分析表文件

  private Map<String, String> predictmap = new HashMap<String, String>();// 预测分析字典
  private List<String> input_cache;// 输入的Token序列的缓存
  private List<String> deduce_str = new ArrayList<String>(); // stack
  private DefaultTableModel tbmodel_lex_result; // 在ui上展示的JTable的模式

  /**
   * 初始化语法分析器
   * 
   * @param input_cache
   * @param tbmodel_lex_result
   */
  public SyntaxAnalyzer(List<String> input_cache,
      DefaultTableModel tbmodel_lex_result) {
    this.input_cache = input_cache;
    this.tbmodel_lex_result = tbmodel_lex_result;
    this.getPredictMap();
  }

  /**
   * 语法分析核心函数
   */
  public void syntaxAnalysis() {
    this.deduce_str.add("S"); // 将开始符号压入栈
    String right;
    String leftandinput;
    String process = "";

    while (this.deduce_str.size() > 0 && this.input_cache.size() > 0) {
      // 如果栈顶非终结符与输入的Token序列相同，则将栈顶的非终结符弹栈
      // delete the first char if the char equals the first char of the production
      try {
        if (this.input_cache.get(0)
            .equals(this.deduce_str.get(this.deduce_str.size() - 1))) {
          this.input_cache.remove(0);
          this.deduce_str.remove(this.deduce_str.size() - 1);
          continue;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      // 预测分析表的行列拼接，即栈顶的非终结符和当前的终结符拼接成的Map的索引
      leftandinput = this.deduce_str.get(this.deduce_str.size() - 1) + "-"
          + this.input_cache.get(0);
      // if(input_cache.get(0)==null)
      // {
      // leftandinput = leftandinput+"$";
      // }
      // 如果能够找到匹配的产生式
      if ((right = this.predictmap.get(leftandinput)) != null) {

        // 输出产生式和推导过程
        process = "";
        for (int i = this.deduce_str.size() - 1; i > -1; i--) {
          process = process + this.deduce_str.get(i) + " ";
        }
        this.tbmodel_lex_result.addRow(new String[] { process,
            this.deduce_str.get(this.deduce_str.size() - 1) + " -> " + right });

        // 删掉产生的字符，压入堆栈
        this.deduce_str.remove(this.deduce_str.size() - 1);
        if (right.equals("$")) {
          // 只弹不压
        } else {
          String[] arg = right.split(" ");
          for (int i = arg.length - 1; i > -1; i--) {
            // 反向压入堆栈
            this.deduce_str.add(arg[i]);
          }
        }

      }
      // 否则的话报错
      else {
        // 重新书写process
        // 注：process是展示在ui上的推导过程的文字。
        process = "";
        for (int i = this.deduce_str.size() - 1; i > -1; i--) {
          process = process + this.deduce_str.get(i) + " ";
        }
        this.tbmodel_lex_result
            .addRow(new String[] { process, "ERROR!  un-recognizable character"
                + this.input_cache.get(0) + ", production: " + leftandinput });
        this.input_cache.remove(0);
      }
    }
  }

  // 获得预测分析表中的产生式以及对应的select集
  // 存储方式为键值对的形式
  public void getPredictMap() {
    String text_line;
    String left;
    String symbol;
    String right;
    try {
      // 采用随机读取方式
      File file = new File(PREDICT_FILEPATH);
      RandomAccessFile predictfile = new RandomAccessFile(file, "r");
      while ((text_line = predictfile.readLine()) != null) {
        // 格式：产生式左部#输入的Token->产生式右部
        left = text_line.split("#")[0];
        symbol = (text_line.split("#")[1]).split("->")[0].trim();
        right = (text_line.split("#")[1]).split("->")[1].trim();
        this.predictmap.put(left + "-" + symbol, right);

      }
      predictfile.close();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
