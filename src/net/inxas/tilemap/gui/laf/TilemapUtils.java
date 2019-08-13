package net.inxas.tilemap.gui.laf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Window;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.UIResource;

/**
 * このL&amp;Fに対しての様々な機能をそろえています。
 * @author inxas
 * @since 2019/07/27
 * @version 0.0-alpha
 */
public class TilemapUtils {
    /**
     * draw,fillメソッドで細かさがある場合に使用します。
     * @author inxas
     */
    public enum Fineness {
        /** ウィンドウ全体を塗るときなどに使用 */
        VERY_FINE(1000),
        /** 画面の半分ほど塗るときなどに使用 */
        FINELY(300),
        /** 通常使用する分にはこれを使用 */
        NORMAL(100),
        /** ボタンなどの小さい範囲に使用 */
        COARSE(50);
        private final int fineness;
        Fineness(int fineness){
            this.fineness = fineness;
        }
        /**
         * 具体的な細かさの度合いを返します。
         * この値をどう使うかは各メソッドに委ねます。
         * @return 細かさの度合い
         */
        public int getFineness() {
            return fineness;
        }
    }
    /**
     * draw,fillメソッドで、方向規則がある場合に使用します。
     * @author inxas
     */
    public enum Direction {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        CENTER,
        TO_RIGHT,
        TO_BOTTOM,
        TO_LEFT,
        TO_TOP,
    }
    /**
     * 指定の領域にグラデーションをかけて塗りつぶします。<br>
     * @param g Graphics
     * @param x x
     * @param y y
     * @param w width
     * @param h height
     * @param c1 Color
     * @param c2 Color
     * @param fineness グラデーションの細かさ もしnullならNORMAL
     * @param direction グラデーションの方向 もしnullならTO_RIGHT
     */
    public static void fillGradation(Graphics g,int x,int y,int w,int h,Color c1,Color c2,Fineness fineness,Direction direction) {
        if(fineness == null) fineness = Fineness.NORMAL;
        if(direction == null) direction = Direction.TO_RIGHT;
        Color[] gradation = null;
        switch(fineness) {
        case VERY_FINE:
            gradation = ColorToolkit.gradation(c1, c2, Fineness.VERY_FINE.getFineness());
            break;
        case FINELY:
            gradation = ColorToolkit.gradation(c1, c2, Fineness.FINELY.getFineness());
            break;
        case NORMAL:
            gradation = ColorToolkit.gradation(c1, c2, Fineness.NORMAL.getFineness());
            break;
        case COARSE:
            gradation = ColorToolkit.gradation(c1, c2, Fineness.COARSE.getFineness());
            break;
        }
        int startX = 0,startY = 0;
        int endX = w,endY = h;
        float xIncrement = 0, yIncrement = 0;
        switch(direction) {
        case TO_RIGHT:
            xIncrement = (float)w / gradation.length;
            break;
        case TO_BOTTOM:
            yIncrement = (float)h / gradation.length;
            break;
        case TO_LEFT:
            xIncrement = -((float)w / gradation.length);
            startX = w;
            endX = 0;
            break;
        case TO_TOP:
            yIncrement = -((float)h / gradation.length);
            startY = h;
            endY = 0;
            break;
        default:
            xIncrement = (float)w / gradation.length;
        }
        g.translate(x,y);
        Color oldColor = g.getColor();
        g.setColor(gradation[0]);
        g.fillRect(x, y, w, h);
        for(int i = 1,l = gradation.length;i < l;i++) {
            g.setColor(gradation[i]);
            int x1 = Math.round(startX + xIncrement * i),
                    x2 = Math.round(endX),
                    y1 = Math.round(startY + yIncrement * i),
                    y2 = Math.round(endY);
            int[] xPoints = {x1,x1,x2,x2};
            int[] yPoints = {y1,y2,y2,y1};
            g.fillPolygon(xPoints, yPoints, 4);
        }
        g.setColor(oldColor);
        g.translate(-x,-y);
    }

    /**
     * 文字列の描画をします。
     * @param c 描画するコンポーネント
     * @param g 使用するグラフィックス
     * @param text 描画する文字列
     * @param color 文字の色
     * @param vertical 垂直方向の制約
     * @param horizon 水平方向の制約
     */
    public static void drawString(JComponent c,Graphics g,String text,Color color,Direction vertical,Direction horizon) {
        g.setColor(color);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        FontRenderContext frc = new FontRenderContext(null, true, true);
        GlyphVector gv = g.getFont().createGlyphVector(frc, text);
        Rectangle2D b = gv.getVisualBounds();
        Point2D p = new Point2D.Double(b.getX() + b.getWidth() / 2d, b.getY() + b.getHeight() / 2d);
        double x = 0,y = 0;
        if(vertical == Direction.CENTER) y = c.getHeight() / 2d - p.getY();
        else if(vertical == Direction.BOTTOM) y = c.getHeight() - b.getHeight();
        if(horizon == Direction.CENTER) x = c.getWidth() / 2d - p.getX();
        else if(horizon == Direction.RIGHT) x = c.getWidth() - b.getWidth();
        AffineTransform atf = AffineTransform.getTranslateInstance(x, y);
        Shape shape = atf.createTransformedShape(gv.getOutline());
        g2d.fill(shape);
    }

    /**
     * コンポーネントの前背景色がUIResourceまたはnullだった場合に置き換えます。
     * @param c JComponent
     * @param foreground foreground
     * @param background background
     */
    static void installColors(JComponent c,String foreground,String background) {
        Color fore = c.getForeground();
        Color themeFore = TilemapTheme.getTheme().getColor(foreground);
        if((fore == null || fore instanceof UIResource) && themeFore != null) {
            c.setForeground(themeFore);
        }
        Color back = c.getBackground();
        Color themeBack = TilemapTheme.getTheme().getColor(background);
        if((back == null || back instanceof UIResource) && themeBack != null) {
            c.setBackground(themeBack);
        }
    }

    /**
     * 通常のボタン描画用メソッド
     * @param g graphics
     * @param x x
     * @param y y
     * @param w width
     * @param h height
     */
    static void drawButtonBorder(Graphics g,int x,int y,int w,int h) {
        g.translate(x,y);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(0,0,w - 2,h - 2);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(1,1,w - 2,h - 2);
        g.setColor(Color.RED);
        g.drawLine(0,h - 1,1,h - 2);
        g.drawLine(w - 1,0,w - 2,1);
        g.translate(-x,-y);
    }
    static void drawPressedButtonBorder(Graphics g,int x,int y,int w,int h) {
        g.translate(x,y);

        g.translate(-x,-y);
    }

    /**
     * 指定されたコンポーネントのトップレベルコンテナーがアクティブかどうかを返します。
     * @param c component
     * @return トップレベルコンテナーがアクティブならtrue
     */
    public static boolean isWindowActive(Component c) {
        if(c == null) return false;
        Window window = SwingUtilities.getWindowAncestor(c);
        return window != null ? window.isActive() : false;
    }
    /**
     * 指定されたコンポーネントのトップレベルウィンドウを返します。
     * @param c コンポーネント
     * @return コンポーネントのトップレベルウィンドウ
     */
    public static Window getWindow(Component c) {
        if(c == null) return null;
        return SwingUtilities.getWindowAncestor(c);
    }
    /**
     * 指定されたコンポーネントのトップレベルウィンドウのアイコンを返します。
     * @param c コンポーネント
     * @return トップレベルウィンドウのアイコン
     */
    public static Image getWindowIconImage(Component c) {
        Window window = getWindow(c);
        if(window instanceof Frame)return ((Frame)window).getIconImage();
        if(window instanceof Dialog)return ((Dialog)window).getIconImages().get(0);
        return null;
    }
    /**
     * 現在使用されているディスプレイのサイズを返します。
     * @return ディスプレイのサイズ
     */
    public static Rectangle getDisplaySize() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return ge.getMaximumWindowBounds();
    }
}
