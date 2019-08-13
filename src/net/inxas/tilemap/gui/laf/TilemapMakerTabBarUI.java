package net.inxas.tilemap.gui.laf;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * メイカータブバー用のUIです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapMakerTabBarUI extends ComponentUI {
    private static TilemapMakerTabBarUI tabBarUI;
    public static ComponentUI createUI(JComponent c) {
        if(tabBarUI == null) {
            tabBarUI = new TilemapMakerTabBarUI();
        }
        return tabBarUI;
    }
    public void paint(Graphics g,JComponent comp) {
        g.setColor(TilemapTheme.getTheme().getColor("MakerTabBarUI.background"));
        g.fillRect(0, 0, comp.getWidth(), comp.getHeight());
    }
}
