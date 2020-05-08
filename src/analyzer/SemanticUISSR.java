package analyzer;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SemanticUISSR extends JFrame {

  private JPanel contentPane;
  private JTable table_Tokens;
  private JTable table_SymbolList;
  private JTable table_Samdozjo;
  JTextArea textField_Code;
  DefaultTableModel model_Lexical = new DefaultTableModel(null,
      new String[] { "名称", "值" });
  DefaultTableModel model_LexicalError = new DefaultTableModel(null,
      new String[] { "推导", "运用的产生式" });
  DefaultTableModel model_SymbolLisTableModel = new DefaultTableModel(null,
      new String[] { "变量名称", "所属类型", "长度", "内存地址" });
  DefaultTableModel model_Triples = new DefaultTableModel(null,
      new String[] { "序号", "三地址码" });

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          SemanticUISSR frame = new SemanticUISSR();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public SemanticUISSR() {
    this.setTitle("语义分析器");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBounds(100, 100, 1150, 768);
    this.contentPane = new JPanel();
    this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    this.setContentPane(this.contentPane);
    this.contentPane.setLayout(null);

    JScrollPane scrollPane_input = new JScrollPane();
    scrollPane_input.setBounds(26, 55, 274, 441);
    this.contentPane.add(scrollPane_input);

    this.textField_Code = new JTextArea();
    scrollPane_input.setViewportView(this.textField_Code);

    JScrollPane scrollPane_SymbolList = new JScrollPane();
    scrollPane_SymbolList.setBounds(386, 407, 324, 281);
    this.contentPane.add(scrollPane_SymbolList);

    this.table_SymbolList = new JTable(this.model_SymbolLisTableModel);
    scrollPane_SymbolList.setViewportView(this.table_SymbolList);

    JScrollPane scrollPane_SanDiZhi = new JScrollPane();
    scrollPane_SanDiZhi.setBounds(802, 55, 324, 548);
    this.contentPane.add(scrollPane_SanDiZhi);

    this.table_Samdozjo = new JTable(this.model_Triples);
    scrollPane_SanDiZhi.setViewportView(this.table_Samdozjo);

    JScrollPane scrollPane_Token = new JScrollPane();
    scrollPane_Token.setBounds(386, 55, 324, 281);
    this.contentPane.add(scrollPane_Token);

    this.table_Tokens = new JTable(this.model_Lexical);
    scrollPane_Token.setViewportView(this.table_Tokens);

    JLabel label = new JLabel("代码");
    label.setFont(new Font("楷体", Font.BOLD, 20));
    label.setBounds(119, 10, 51, 36);
    this.contentPane.add(label);

    JLabel label_1 = new JLabel("词法分析结果");
    label_1.setFont(new Font("楷体", Font.BOLD, 20));
    label_1.setBounds(470, 9, 134, 36);
    this.contentPane.add(label_1);

    JLabel label_2 = new JLabel("符号表");
    label_2.setFont(new Font("楷体", Font.BOLD, 20));
    label_2.setBounds(499, 358, 73, 36);
    this.contentPane.add(label_2);

    JLabel label_3 = new JLabel("三地址指令");
    label_3.setFont(new Font("楷体", Font.BOLD, 20));
    label_3.setBounds(901, 10, 104, 36);
    this.contentPane.add(label_3);

    JButton button_Analysis = new JButton("分析");
    button_Analysis.setFont(new Font("楷体", Font.BOLD, 20));
    button_Analysis.setBounds(85, 554, 139, 49);
    this.contentPane.add(button_Analysis);
    button_Analysis.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        SemanticUISSR.this.executeThisCode();

      }
    });

    JButton button_Clear = new JButton("清空");
    button_Clear.setFont(new Font("楷体", Font.BOLD, 20));
    button_Clear.setBounds(85, 624, 139, 49);
    this.contentPane.add(button_Clear);
    button_Clear.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO 自动生成的方法存根
        SemanticUISSR.this.clearTable();
      }
    });
  }

  public void executeThisCode() {
    this.clearTable();
    if (this.textField_Code.getText().equals("")) {
      JOptionPane.showMessageDialog(this.contentPane, "请输入至少一个字符OK?", "提示",
          JOptionPane.ERROR_MESSAGE);
    } else {

      TextLex textLex = new TextLex(this.textField_Code.getText(),
          this.model_Lexical, this.model_LexicalError);
      textLex.scannerAll();
      ArrayList<String> lex_result = textLex.get_Lex_Result();
      ArrayList<HashMap<String, String>> lex_error = textLex.get_Lex_Error();
      if (lex_error.size() != 0) {
        JOptionPane.showMessageDialog(this.contentPane, "词法分析阶段出现错误！", "提示",
            JOptionPane.ERROR_MESSAGE);
      } else {
        SyntaxAnalyzer textParse = new SyntaxAnalyzer(lex_result,
            this.model_LexicalError);
        textParse.syntaxAnalysis();
        SemanticAnalyse sa = new SemanticAnalyse(this.textField_Code.getText(),
            this.model_SymbolLisTableModel, this.model_Triples);
        sa.Parsing();
      }
    }
  }

  public void clearTable() {

    int result_rows = this.model_Lexical.getRowCount();
    int triples_rows = this.model_Triples.getRowCount();
    int symbols_rows = this.model_SymbolLisTableModel.getRowCount();

    for (int i = 0; i < result_rows; i++) {
      this.model_Lexical.removeRow(0);
      this.table_Tokens.updateUI();
    }

    for (int i = 0; i < triples_rows; i++) {
      this.model_Triples.removeRow(0);
      this.table_Samdozjo.updateUI();
    }

    for (int i = 0; i < symbols_rows; i++) {
      this.model_SymbolLisTableModel.removeRow(0);
      this.table_SymbolList.updateUI();
    }
  }
}
