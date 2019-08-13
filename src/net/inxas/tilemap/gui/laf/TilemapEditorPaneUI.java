package net.inxas.tilemap.gui.laf;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import net.inxas.tilemap.gui.TilemapEditorPane;

/**
 * エディターペイン用のUIです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapEditorPaneUI extends ComponentUI {
    private static TilemapEditorPaneUI editorPaneUI;
    protected TilemapEditorPaneUI() {
        super();
    }
    public static ComponentUI createUI(JComponent c) {
        if(editorPaneUI == null) {
            editorPaneUI = new TilemapEditorPaneUI();
        }
        return editorPaneUI;
    }

    public void installUI(JComponent c) {
        TilemapEditorPane e = (TilemapEditorPane)c;
        super.installUI(e);
    }

    public void paint(Graphics g,JComponent c) {
        g.setColor(TilemapTheme.getTheme().getColor("EditorPaneUI.background"));
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
    }
}
