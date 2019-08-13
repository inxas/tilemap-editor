package net.inxas.tilemap.gui.laf;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * メニューコンテナ用UIです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapMenuContainerUI extends ComponentUI {
    private static TilemapMenuContainerUI menuContainerUI;
    public static ComponentUI createUI(JComponent c) {
        if(menuContainerUI == null) {
            menuContainerUI = new TilemapMenuContainerUI();
        }
        return menuContainerUI;
    }
    public void paint(Graphics g,JComponent c) {
        g.setColor(TilemapTheme.getTheme().getColor("MenuContainerUI.background"));
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
    }
}
