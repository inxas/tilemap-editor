package net.inxas.tilemap.gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;

import net.inxas.tilemap.gui.laf.ColorToolkit;

/**
 * このエディタのデザインに適したラベルです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapLabel extends JLabel {
    /**
     * 表示する文字列を設定してラベルを作成します。
     * @param string 表示する文字列
     */
    public TilemapLabel(String string) {
        super(string);
    }
    /**
     * 文字列を描画します。
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g.setColor(getForeground());
        g.setFont(getFont());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        FontRenderContext frc = new FontRenderContext(null, true, true);
        GlyphVector gv = g.getFont().createGlyphVector(frc, getText());
        Rectangle2D b = gv.getVisualBounds();
        Point2D p = new Point2D.Double(b.getX() + b.getWidth() / 2d, b.getY() + b.getHeight() / 2d);
        FontMetrics fm = g.getFontMetrics();
        int horizontal = 0;
        if(getHorizontalAlignment() == JLabel.LEFT) horizontal = 0;
        else if(getHorizontalAlignment() == JLabel.CENTER) horizontal = (int) (getWidth() / 2d - p.getX());
        else if(getHorizontalAlignment() == JLabel.RIGHT) horizontal = getWidth() - fm.stringWidth(getText());
        int vertical = 0;
        if(getVerticalAlignment() == JLabel.TOP) vertical = 0;
        else if(getVerticalAlignment() == JLabel.CENTER) vertical = (int) (getHeight() / 2d - p.getY());
        else if(getVerticalAlignment() == JLabel.BOTTOM) vertical = getHeight() - fm.getHeight();
        AffineTransform atf = AffineTransform.getTranslateInstance(horizontal, vertical);
        Shape shape = atf.createTransformedShape(gv.getOutline());
        g2d.fill(shape);
        if(getWidth() < fm.stringWidth(getText())) {
            int edge = horizontal + getWidth();
            Color bg = getBackground();
            Color[] fadeOut = ColorToolkit.gradation(bg,new Color(bg.getRed(),bg.getGreen(),bg.getBlue(),0), 20);
            for(int i = 0,l = fadeOut.length;i < l;i++) {
                g.setColor(fadeOut[i]);
                g.drawLine(edge - i, 0, edge - i, getHeight());
            }
        }
    }
}
