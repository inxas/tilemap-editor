package net.inxas.tilemap.gui.laf;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * ツールコンテナ用UIです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapToolContainerUI extends ComponentUI {
    private static TilemapToolContainerUI toolContainerUI;
    public static ComponentUI createUI(JComponent c) {
        if(toolContainerUI == null) {
            toolContainerUI = new TilemapToolContainerUI();
        }
        return toolContainerUI;
    }
}
