package net.inxas.tilemap.gui.laf;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * メイカーペイン用のUIです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapMakerPaneUI extends ComponentUI {
    private static TilemapMakerPaneUI makerPaneUI;
    public static ComponentUI createUI(JComponent c) {
        if(makerPaneUI == null) {
            makerPaneUI = new TilemapMakerPaneUI();
        }
        return makerPaneUI;
    }

    public void paint(Graphics g,JComponent comp) {
        g.setColor(TilemapTheme.getTheme().getColor("MakerPaneUI.background"));
        g.fillRect(0, 0, comp.getWidth(), comp.getHeight());
    }
}
