package net.inxas.tilemap.gui;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JTextField;

import net.inxas.tilemap.gui.laf.ColorToolkit;
import net.inxas.tilemap.gui.laf.TilemapTheme;

/**
 * テキストが空かnullで、フォーカスが当たっていないときにヒントを中心に表示し、
 * フォーカスが当たってないときはテキストを中心に表示するテキストフィールドです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapTextField extends JTextField implements FocusListener{
    private String hint;
    /**
     * デフォルトテキスト、ヒントメッセージ無しで作成します。
     */
    public TilemapTextField() {
        this("");
    }
    /**
     * デフォルトテキスト付き、ヒントメッセージ無しで作成します。
     * @param defaultText デフォルトで表示されるテキスト
     */
    public TilemapTextField(String defaultText) {
        this(defaultText,null);
    }
    /**
     * デフォルトテキスト、ヒントメッセージ付きで作成します。
     * @param defaultText デフォルトで表示されるテキスト
     * @param defaultHint デフォルトで表示されるヒント
     */
    public TilemapTextField(String defaultText,String defaultHint) {
        super(8);
        setText(defaultText);
        setHint(defaultHint);
        addFocusListener(this);
    }

    /**
     * ヒントメッセージを設定します。
     * @param hint 新しいヒント
     */
    public void setHint(String hint) {
        this.hint = hint;
        repaint();
    }
    /**
     * ヒントメッセージを返します。
     * @return ヒントメッセージ
     */
    public String getHint() {
        return hint;
    }

    /**
     * テキストやフォーカスの状況を基に適した描画をします。
     * @param g グラフィックス
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(TilemapTheme.getTheme().getColor("TextFieldUI.background"));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(getFont());

        if(!hasFocus() && (getText() == null || getText().equals(""))) {
            paintHint(g);
        }else if(!hasFocus() && getText() != null && !getText().equals("")){
            paintDefineText(g);
        }else {
            paintText(g);
        }
    }

    /**
     * ヒントを表示します。
     * @param g グラフィックス
     */
    private void paintHint(Graphics g) {
        if(hint != null && !hint.equals("")) {
            g.setColor(TilemapTheme.getTheme().getColor("TextFieldUI.hintColor"));
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            FontRenderContext frc = new FontRenderContext(null, true, true);
            GlyphVector gv = g.getFont().createGlyphVector(frc, hint);
            Rectangle2D b = gv.getVisualBounds();
            Point2D p = new Point2D.Double(b.getX() + b.getWidth() / 2d, b.getY() + b.getHeight() / 2d);
            AffineTransform atf = AffineTransform.getTranslateInstance(getWidth() / 2d - p.getX(), getHeight() / 2d - p.getY());
            Shape shape = atf.createTransformedShape(gv.getOutline());
            g2d.fill(shape);
        }
    }
    /**
     * テキストを中心に描画します。
     * @param g グラフィックス
     */
    private void paintDefineText(Graphics g) {
        if(getText() != null) {
            g.setColor(ColorToolkit.text);
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            FontRenderContext frc = new FontRenderContext(null, true, true);
            GlyphVector gv = g.getFont().createGlyphVector(frc, getText());
            Rectangle2D b = gv.getVisualBounds();
            Point2D p = new Point2D.Double(b.getX() + b.getWidth() / 2d, b.getY() + b.getHeight() / 2d);
            AffineTransform atf = AffineTransform.getTranslateInstance(getWidth() / 2d - p.getX(), getHeight() / 2d - p.getY());
            Shape shape = atf.createTransformedShape(gv.getOutline());
            g2d.fill(shape);
        }
    }
    /**
     * テキストを左寄せに描画し、キャレットも描画します。
     * @param g グラフィックス
     */
    private void paintText(Graphics g) {
        if(getText() != null) {
            g.setColor(ColorToolkit.text);
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            FontRenderContext frc = new FontRenderContext(null, true, true);
            GlyphVector gv = g.getFont().createGlyphVector(frc, getText());
            Rectangle2D b = gv.getVisualBounds();
            Point2D p = new Point2D.Double(b.getX() + b.getWidth() / 2d, b.getY() + b.getHeight() / 2d);
            AffineTransform atf = AffineTransform.getTranslateInstance(getInsets().left, getHeight() / 2d - p.getY());
            Shape shape = atf.createTransformedShape(gv.getOutline());
            g2d.fill(shape);

            FontMetrics fm = g2d.getFontMetrics();
            int caretPoint = getCaret().getDot();
            getCaret().setMagicCaretPosition(new Point(
                    getInsets().left + fm.stringWidth(new StringBuilder(getText()).substring(0, caretPoint)),0));
        }
        getCaret().paint(g);
    }
    
    /**
     * {@link #repaint()}を呼び出します。
     */
    @Override
    public void focusGained(FocusEvent e) {
        repaint();
    }
    /**
     * {@link #repaint()}を呼び出します。
     */
    @Override
    public void focusLost(FocusEvent e) {
        repaint();
    }
}
