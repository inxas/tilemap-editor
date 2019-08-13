package net.inxas.tilemap.gui.laf;

import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;

/**
 * このルックアンドフィールのボーダー一覧です。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapBorders {
    private static Border buttonBorder;
    public static Border getButtonBorder() {
        if(buttonBorder == null) {
            buttonBorder = new BasicBorders.MarginBorder();
        }
        return buttonBorder;
    }
}
