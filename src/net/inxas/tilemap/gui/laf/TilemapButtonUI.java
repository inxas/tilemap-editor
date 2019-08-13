package net.inxas.tilemap.gui.laf;

import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * ボタン用のUIです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapButtonUI extends BasicButtonUI {
    private static TilemapButtonUI buttonUI;
    public static ComponentUI createUI(JComponent c) {
        if(buttonUI == null) {
            buttonUI = new TilemapButtonUI();
        }
        return buttonUI;
    }

    public void paint(Graphics g,JComponent c) {
        paintBackground(g,(AbstractButton)c);
        TilemapUtils.drawString(c, g, ((AbstractButton)c).getText(), c.getForeground(),
                TilemapUtils.Direction.CENTER, TilemapUtils.Direction.CENTER);
    }

    protected void paintBackground(Graphics g,AbstractButton b) {
        g.setColor(TilemapTheme.getTheme().getColor("ButtonUI.background"));
        g.fillRect(0, 0, b.getWidth(), b.getHeight());
    }
}
