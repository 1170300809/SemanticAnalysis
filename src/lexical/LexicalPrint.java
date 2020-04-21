package lexical;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class LexicalPrint extends JFrame {

  private JPanel contentPane;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          LexicalPrint frame = new LexicalPrint();
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
  public LexicalPrint() {
    this.setTitle("\u8BCD\u6CD5\u5206\u6790\u5668");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBounds(100, 100, 901, 612);
    this.contentPane = new JPanel();
    this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    this.setContentPane(this.contentPane);
    this.contentPane.setLayout(null);

    JScrollPane scrollPane_1 = new JScrollPane();
    scrollPane_1.setBounds(37, 55, 388, 445);
    this.contentPane.add(scrollPane_1);

    JTextArea textArea_SourceCode = new JTextArea();
    scrollPane_1.setViewportView(textArea_SourceCode);

    JScrollPane scrollPane_2 = new JScrollPane();
    scrollPane_2.setBounds(468, 55, 388, 445);
    this.contentPane.add(scrollPane_2);

    JTextArea textArea_Message = new JTextArea();
    scrollPane_2.setViewportView(textArea_Message);

    JLabel lblMessage = new JLabel("\u6253\u5370\u6D88\u606F");
    lblMessage.setFont(new Font("宋体", Font.PLAIN, 20));
    lblMessage.setBounds(622, 16, 108, 29);
    this.contentPane.add(lblMessage);

    JLabel label_SourceCode = new JLabel("\u6E90\u4EE3\u7801");
    label_SourceCode.setFont(new Font("宋体", Font.PLAIN, 20));
    label_SourceCode.setBounds(201, 16, 108, 29);
    this.contentPane.add(label_SourceCode);

    JButton btnopen = new JButton(
        "\u6253\u5F00\u6587\u4EF6\u5E76\u751F\u6210Token\u8868");
    btnopen.setFont(new Font("楷体", Font.PLAIN, 17));
    btnopen.setBounds(119, 510, 213, 38);
    btnopen.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        LexicalPrint.this.OpenFileAndAnalysis(textArea_SourceCode,
            textArea_Message);
      }
    });
    this.contentPane.add(btnopen);

    JButton btnsave = new JButton("\u4FDD\u5B58token\u8868");
    btnsave.setFont(new Font("楷体", Font.PLAIN, 17));
    btnsave.setBounds(562, 510, 168, 38);
    btnsave.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        LexicalPrint.this.SaveTokens(textArea_Message);
      }
    });
    this.contentPane.add(btnsave);
  }

  /**
   * Open the code file and analyze it.尚需填充
   */
  private void OpenFileAndAnalysis(JTextArea textArea_SourceCode,
      JTextArea textArea_Tokens) {
    try {
      JFrame f = new JFrame("my window");// 创建窗体对象
      f.setBounds(300, 100, 650, 600);// 设置窗体位置和大小
      FileDialog openDia = new FileDialog(f, "打开", FileDialog.LOAD);
      openDia.setVisible(true);// 显示打开文件对话框
      String dirpath = openDia.getDirectory();// 获取打开文件路径并保存到字符串中。
      String fileName = openDia.getFile();// 获取打开文件名称并保存到字符串中
      if (dirpath == null || fileName == null)// 判断路径和文件是否为空,为空报错退出
      {
        textArea_Tokens.setText("你想要打开的文件不存在！");
        return;
      } else {
        textArea_SourceCode.setText(null);// 文件不为空，清空原来的文本内容。
        textArea_Tokens.setText(null);
      }

      /*
       * File file = new File(dirpath, fileName); BufferedReader br = new BufferedReader(new
       * FileReader(file));// 文件指针 String line;// 每行代码
       * 
       * String allCode = "";// Code string, used for analysis
       * 
       * 
       * while ((line = br.readLine()) != null) { textArea_SourceCode.append(line + "\n"); allCode
       * += line + "\n"; }
       */
      String line;
      File file = new File(dirpath, fileName);
      BufferedReader br = new BufferedReader(new FileReader(file));// 文件指针
      while ((line = br.readLine()) != null) {
        textArea_SourceCode.append(line + "\n");
      }

      LexicalAnalyzer la = new LexicalAnalyzer();
      Tokens tokenPrint = la.analyzeFile(dirpath + fileName);// The new Token object modifies the //
                                                             // initialization statement // if the
                                                             // program requires it, but does not
                                                             // change the // quantity name

      for (Token tk : tokenPrint.getTokenListCopy()) {
        String tkline = tk.toString() + "\n";
        textArea_Tokens.append(tkline);
      }
      br.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Saves the list of tokens displayed in the text box
   * 
   * @param textArea_Tokens
   */
  private void SaveTokens(JTextArea textArea_Tokens) {
    String text_save = textArea_Tokens.getText();// 要保存的Token文本
    JFrame f = new JFrame("my window");// 创建窗体对象
    f.setBounds(300, 100, 650, 600);// 设置窗体位置和大小
    FileDialog saveDia = new FileDialog(f, "保存", FileDialog.SAVE);
    saveDia.setVisible(true);
    String dirpath = saveDia.getDirectory();// 获取保存文件路径并保存到字符串中。
    String fileName = saveDia.getFile();//// 获取打保存文件名称并保存到字符串中
    File file = null;
    if (dirpath == null || fileName == null) {
      return;// 空操作
    } else {
      file = new File(dirpath, fileName);// 文件不为空，新建一个路径和名称
    }

    try {
      BufferedWriter bufw = new BufferedWriter(new FileWriter(file));

      bufw.write(text_save);// 将获取文本内容写入到字符输出流

      bufw.close();// 关闭文件
    } catch (IOException e1) {
      // 抛出IO异常
      e1.printStackTrace();
    }

  }
}
