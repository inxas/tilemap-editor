package net.inxas.tilemap.gui.laf;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * メニューペイン用のUIです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapMenuPaneUI extends ComponentUI {
    private static TilemapMenuPaneUI menuPaneUI;
    public static ComponentUI createUI(JComponent c) {
        if(menuPaneUI == null) {
            menuPaneUI = new TilemapMenuPaneUI();
        }
        return menuPaneUI;
    }
    public void installUI(JComponent c) {
        super.installUI(c);
    }
    public void paint(Graphics g,JComponent comp) {
        g.setColor(TilemapTheme.getTheme().getColor("MenuPaneUI.background"));
        g.fillRect(0, 0, comp.getWidth(), comp.getHeight());
    }
}
