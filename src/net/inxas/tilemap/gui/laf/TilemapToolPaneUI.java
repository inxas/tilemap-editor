package net.inxas.tilemap.gui.laf;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * ツールペイン用のUIです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapToolPaneUI extends ComponentUI {
    private static TilemapToolPaneUI toolPaneUI;
    public static ComponentUI createUI(JComponent c) {
        if(toolPaneUI == null) {
            toolPaneUI = new TilemapToolPaneUI();
        }
        return toolPaneUI;
    }
    public void paint(Graphics g,JComponent c) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
    }
}
