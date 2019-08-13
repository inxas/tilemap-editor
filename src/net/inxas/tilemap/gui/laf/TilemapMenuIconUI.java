package net.inxas.tilemap.gui.laf;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import net.inxas.tilemap.gui.TilemapMenuIcon;

/**
 * メニューアイコン用UIです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapMenuIconUI extends ComponentUI {
    private static TilemapMenuIconUI menuIconUI;
    public static ComponentUI createUI(JComponent c) {
        if(menuIconUI == null) {
            menuIconUI = new TilemapMenuIconUI();
        }
        return menuIconUI;
    }
    public void paint(Graphics g,JComponent c) {
        TilemapMenuIcon t = (TilemapMenuIcon)c;
        t.getCurrentIcon().paintIcon(c, g, 0, 0);
    }
}
