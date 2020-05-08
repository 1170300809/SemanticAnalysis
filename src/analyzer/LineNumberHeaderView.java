package analyzer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public class LineNumberHeaderView extends javax.swing.JComponent {

  private static final long serialVersionUID = 1L;
  private final Font DEFAULT_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 11);
  public final Color DEFAULT_BACKGROUD = new Color(228, 228, 228);
  public final Color DEFAULT_FOREGROUD = Color.BLACK;
  public final int nHEIGHT = Integer.MAX_VALUE - 1000000;
  public final int MARGIN = 5;
  private int lineHeight;
  private int fontLineHeight;
  private int currentRowWidth;
  private FontMetrics fontMetrics;

  public LineNumberHeaderView() {
    this.setFont(this.DEFAULT_FONT);
    this.setForeground(this.DEFAULT_FOREGROUD);
    this.setBackground(this.DEFAULT_BACKGROUD);
    this.setPreferredSize(9999);
  }

  public void setPreferredSize(int row) {
    int width = this.fontMetrics.stringWidth(String.valueOf(row));
    if (this.currentRowWidth < width) {
      this.currentRowWidth = width;
      this.setPreferredSize(
          new Dimension(2 * this.MARGIN + width + 1, this.nHEIGHT));
    }
  }

  @Override
  public void setFont(Font font) {
    super.setFont(font);
    this.fontMetrics = this.getFontMetrics(this.getFont());
    this.fontLineHeight = this.fontMetrics.getHeight();
  }

  public int getLineHeight() {
    if (this.lineHeight == 0) {
      return this.fontLineHeight;
    }
    return this.lineHeight;
  }

  public void setLineHeight(int lineHeight) {
    if (lineHeight > 0) {
      this.lineHeight = lineHeight;
    }
  }

  public int getStartOffset() {
    return 4;
  }

  @Override
  protected void paintComponent(Graphics g) {
    int nlineHeight = this.getLineHeight();
    int startOffset = this.getStartOffset();
    Rectangle drawHere = g.getClipBounds();
    g.setColor(this.getBackground());
    g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
    g.setColor(this.getForeground());
    int startLineNum = (drawHere.y / nlineHeight) + 1;
    int endLineNum = startLineNum + (drawHere.height / nlineHeight);
    int start = (drawHere.y / nlineHeight) * nlineHeight + nlineHeight
        - startOffset;
    for (int i = startLineNum; i <= endLineNum; ++i) {
      String lineNum = String.valueOf(i);
      int width = this.fontMetrics.stringWidth(lineNum);
      g.drawString(lineNum + " ",
          this.MARGIN + this.currentRowWidth - width - 1, start);
      start += nlineHeight;
    }
    this.setPreferredSize(endLineNum);
  }
}