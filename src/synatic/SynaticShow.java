package analyzer;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SynaticShow extends JFrame {

  private JPanel contentPane;
  private JTable table_FirstAndFollow;
  private JTable table_Select;
  private JTable table_Predict;
  private JScrollPane scrollPane_GrammaTree;
  private JTextField textField_ProductionsFormat;
  private JTextField textField_TokenPath;
  private JTextField textField_ProductionShow;
  private JLabel label_1;
  private JPanel panel;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          SynaticShow frame = new SynaticShow();
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
  public SynaticShow() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBounds(100, 100, 1355, 694);
    this.contentPane = new JPanel();
    this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    this.setContentPane(this.contentPane);
    this.contentPane.setLayout(null);

    this.panel = new JPanel();
    this.panel.setBounds(40, 424, 1113, 78);
    this.contentPane.add(this.panel);
    this.panel.setLayout(null);

    JLabel label = new JLabel("\u4EA7\u751F\u5F0F\u6587\u4EF6");
    label.setBounds(63, 10, 132, 33);
    this.panel.add(label);
    label.setFont(new Font("华文隶书", Font.PLAIN, 18));

    this.textField_ProductionsFormat = new JTextField();
    this.textField_ProductionsFormat.setBounds(10, 36, 237, 21);
    this.panel.add(this.textField_ProductionsFormat);
    this.textField_ProductionsFormat.setText("src/data/input/productions.format");
    this.textField_ProductionsFormat.setColumns(10);

    this.textField_TokenPath = new JTextField();
    this.textField_TokenPath.setBounds(270, 36, 253, 21);
    this.panel.add(this.textField_TokenPath);
    this.textField_TokenPath.setText("src/test/synTest1.token");
    this.textField_TokenPath.setColumns(10);

    JLabel lblToken = new JLabel("token\u6587\u4EF6");
    lblToken.setBounds(293, 10, 132, 33);
    this.panel.add(lblToken);
    lblToken.setFont(new Font("华文隶书", Font.PLAIN, 18));

    this.label_1 = new JLabel("\u8BE6\u7EC6\u4EA7\u751F\u5F0F");
    this.label_1.setBounds(585, 10, 132, 33);
    this.panel.add(this.label_1);
    this.label_1.setFont(new Font("华文隶书", Font.PLAIN, 18));

    JButton button_Execute = new JButton("\u6267\u884C");
    button_Execute.setBounds(968, 26, 121, 39);
    this.panel.add(button_Execute);
    button_Execute.setFont(new Font("楷体", Font.PLAIN, 18));

    JButton button_ChangeSize = new JButton("\u6539\u53D8\u5E03\u5C40");

    button_ChangeSize.setFont(new Font("楷体", Font.PLAIN, 18));
    button_ChangeSize.setBounds(837, 16, 121, 39);
    this.panel.add(button_ChangeSize);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(553, 37, 244, 20);
    this.panel.add(scrollPane);

    this.textField_ProductionShow = new JTextField();
    scrollPane.setViewportView(this.textField_ProductionShow);
    this.textField_ProductionShow.setColumns(10);

    this.scrollPane_GrammaTree = new JScrollPane();
    this.scrollPane_GrammaTree.setBounds(908, 25, 286, 381);
    this.contentPane.add(this.scrollPane_GrammaTree);

    JTextArea textArea_Tree = new JTextArea();
    this.scrollPane_GrammaTree.setViewportView(textArea_Tree);

    JScrollPane scrollPane_Predict = new JScrollPane();
    scrollPane_Predict.setBounds(612, 25, 286, 381);
    this.contentPane.add(scrollPane_Predict);

    this.table_Predict = new JTable();
    scrollPane_Predict.setViewportView(this.table_Predict);
    this.table_Predict.setFillsViewportHeight(true);

    JScrollPane scrollPane_Select = new JScrollPane();
    scrollPane_Select.setBounds(316, 25, 286, 381);
    this.contentPane.add(scrollPane_Select);

    this.table_Select = new JTable();
    scrollPane_Select.setViewportView(this.table_Select);

    JScrollPane scrollPane_FirstAndFollow = new JScrollPane();
    scrollPane_FirstAndFollow.setBounds(40, 25, 266, 381);
    this.contentPane.add(scrollPane_FirstAndFollow);

    this.table_FirstAndFollow = new JTable();
    scrollPane_FirstAndFollow.setViewportView(this.table_FirstAndFollow);
    button_ChangeSize.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int width = SynaticShow.this.contentPane.getWidth();
        int height = SynaticShow.this.contentPane.getHeight();

        scrollPane_FirstAndFollow.setBounds(40, 25, (int) (286.0 / 1355.0 * width),
            (int) (381.0 / 694.0 * height));

        scrollPane_Select.setBounds(
            scrollPane_FirstAndFollow.getX() + scrollPane_FirstAndFollow.getWidth() + 10, 25,
            (int) (286.0 / 1355.0 * width), (int) (381.0 / 694.0 * height));

        scrollPane_Predict.setBounds(scrollPane_Select.getX() + scrollPane_Select.getWidth() + 10,
            25, (int) (286.0 / 1355.0 * width), (int) (381.0 / 694.0 * height));

        SynaticShow.this.scrollPane_GrammaTree.setBounds(
            scrollPane_Predict.getX() + scrollPane_Predict.getWidth() + 10, 25,
            (int) (286.0 / 1355.0 * width), (int) (381.0 / 694.0 * height));

        SynaticShow.this.panel.setBounds(40, 25 + scrollPane_FirstAndFollow.getHeight() + 10, 1113,
            78);

      }
    });

    button_Execute.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        Productions pros = new Productions(SynaticShow.this.textField_ProductionsFormat.getText());
        PredictiveParsingTableConstructor prtc = new PredictiveParsingTableConstructor(pros);
        System.out.println(prtc.getPPTable().toString());
        SynaticAnalysisExecutor sAE = new SynaticAnalysisExecutor();
        MutiWayTree mwt =
            sAE.getGrammaTree(prtc.getPPTable(), SynaticShow.this.textField_TokenPath.getText());

        // 变量准备-开始
        FirstSets first = prtc.getFirstSets();
        FollowSets follow = prtc.getFollowSets();

        SelectSets select = prtc.getSelectSets();

        Set<GrammarSymbol> firstGrammaSet = first.getGrammaSet();
        Set<GrammarSymbol> followGrammaSet = follow.getGrammaSet();

        Productions selectProductions = prtc.getPPTable().getProductions().breakIntoPieces();

        // 变量准备-结束

        // firstfollow-开始
        ArrayList<String[]> grammafirstfollowTable = new ArrayList<>();
        for (GrammarSymbol gs : firstGrammaSet) {
          String gramma = gs.getName();

          Set<GrammarSymbol> firstSet = first.getFirstSet(gs);
          String firstString = "";

          for (GrammarSymbol firstgs : firstSet) {
            firstString += firstgs.getName() + ",";
          }

          Set<GrammarSymbol> followSet = follow.getFollowSet(gs);
          String followString = "";

          for (GrammarSymbol followgs : followSet) {
            followString += followgs.getName() + ",";
          }
          String[] singleline = {gramma, firstString, followString};
          grammafirstfollowTable.add(singleline);

        }
        String[][] grammafirstfollowTableInput = new String[grammafirstfollowTable.size()][];
        grammafirstfollowTable.toArray(grammafirstfollowTableInput);
        String[] firstfollowcolumn = {"非终结符", "First集合", "Follow集合"};
        SynaticShow.this.initialFirstAndFollow(SynaticShow.this.table_FirstAndFollow,
            scrollPane_FirstAndFollow, firstfollowcolumn, grammafirstfollowTableInput,
            textField_ProductionShow);
        // firstfollow结束

        // select开始
        ArrayList<String[]> grammaSelectTable = new ArrayList<>();
        for (int i = 0; i < selectProductions.size(); i++) {
          Production production = selectProductions.getProduction(i);
          String selectlineString = "";
          String selectProductionString = production.getLHS().getName() + "-->";
          for (int j = 0; j < production.getRHSlist().get(0).size(); j++) {
            selectProductionString += production.getRHSlist().get(0).get(j).getName() + " ";
          }
          selectProductionString += "";

          Set<GrammarSymbol> selectSet = select.getSelectSet(production);
          for (GrammarSymbol selectgs : selectSet) {
            selectlineString += selectgs.getName() + ",";
          }
          String[] singleline = {selectProductionString, selectlineString};
          grammaSelectTable.add(singleline);
        }

        String[] selectcolumn = {"产生式", "Select集合"};
        String[][] grammaSelectTableInput = new String[grammaSelectTable.size()][];
        for (int i = 0; i < grammaSelectTable.size(); i++) {
          grammaSelectTableInput[i] = grammaSelectTable.get(i);
        }
        SynaticShow.this.initialSelect(SynaticShow.this.table_Select, scrollPane_Select,
            selectcolumn, grammaSelectTableInput, textField_ProductionShow);

        // select结束
        // table开始
        ArrayList<String[]> rowLineArrayList = new ArrayList<>();
        ArrayList<String> headList = new ArrayList<>();
        headList.add("  ");
        for (GrammarSymbol terminal : prtc.getPPTable().getColumnSet()) {
          headList.add(terminal.getName());
        }

        for (GrammarSymbol nonterminal : prtc.getPPTable().getRowSet()) {
          ArrayList<String> rowLine = new ArrayList<>();
          rowLine.add(nonterminal.getName());
          for (GrammarSymbol terminal : prtc.getPPTable().getColumnSet()) {
            String singleTableValue = "";

            // 填充单个表项
            if (!prtc.getPPTable().getProductionTableEntry(nonterminal, terminal)
                .equals(new Production())) {
              singleTableValue = prtc.getPPTable().getProductionTableEntry(nonterminal, terminal)
                  .getLHS().getName() + "->";
              for (GrammarSymbol gs : prtc.getPPTable()
                  .getProductionTableEntry(nonterminal, terminal).getRHSlist().get(0)) {
                singleTableValue += " " + gs.getName();
              }
            } else {
              if (prtc.getPPTable().getSynchTableEntry(nonterminal, terminal)) {
                singleTableValue = "synch";
              } else {
                singleTableValue = "null";
              }

            }
            // 填充单个表项
            rowLine.add(singleTableValue);
          }
          String[] rowLineArray = new String[rowLine.size()];
          rowLine.toArray(rowLineArray);
          rowLineArrayList.add(rowLineArray);
        }

        String[][] rowLineArrayArray = new String[rowLineArrayList.size()][];
        rowLineArrayList.toArray(rowLineArrayArray);
        String[] headArray = new String[headList.size()];
        headList.toArray(headArray);
        SynaticShow.this.initialFirstPredict(SynaticShow.this.table_Predict, scrollPane_Predict,
            headArray, rowLineArrayArray, SynaticShow.this.textField_ProductionShow);
        // table结束
        // tree开始
        String mwtString = mwt.toString();
        textArea_Tree.setText(mwtString);
        // tree结束

      }
    });
  }

  /**
   * 根据分析构建器的内容初始化FirstAndFollow表格
   * 
   * @param table
   * @param scrollPane
   */
  public void initialFirstAndFollow(JTable table, JScrollPane scrollPane, String[] columns,
      String[][] tableValues, JTextField show) {
    table = new JTable(tableValues, columns);
    scrollPane.setViewportView(table);
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o instanceof JTable) {
          JTable t = (JTable) o;
          String valueSelected = (String) t.getValueAt(t.getSelectedRow(), t.getSelectedColumn());
          show.setText(valueSelected);
        }
      }
    });
  }

  /**
   * 根据分析构建器的内容初始化Select表格
   * 
   * @param table
   * @param scrollPane
   */
  public void initialSelect(JTable table, JScrollPane scrollPane, String[] columns,
      String[][] tableValues, JTextField show) {

    table = new JTable(tableValues, columns);
    scrollPane.setViewportView(table);
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o instanceof JTable) {
          JTable t = (JTable) o;
          String valueSelected = (String) t.getValueAt(t.getSelectedRow(), t.getSelectedColumn());
          show.setText(valueSelected);
        }
      }
    });
  }

  /**
   * 根据分析构建器的内容初始化Predict表格
   * 
   * @param table
   * @param scrollPane
   */
  public void initialFirstPredict(JTable table, JScrollPane scrollPane, String[] columns,
      String[][] tableValues, JTextField show) {
    table = new JTable(tableValues, columns);
    scrollPane.setViewportView(table);
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o instanceof JTable) {
          JTable t = (JTable) o;
          String valueSelected = (String) t.getValueAt(t.getSelectedRow(), t.getSelectedColumn());
          show.setText(valueSelected);
        }
      }
    });
    table.getColumnModel().setColumnMargin(1);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

  }
}
