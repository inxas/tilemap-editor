package net.inxas.tilemap.gui.laf;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * ツール用のUIです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapToolUI extends ComponentUI {
    private static TilemapToolUI toolUI;
    public static ComponentUI createUI(JComponent c) {
        if(toolUI == null) {
            toolUI = new TilemapToolUI();
        }
        return toolUI;
    }
    public void paint(Graphics g,JComponent c) {
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
    }
}
