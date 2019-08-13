package net.inxas.tilemap.gui;

import javax.swing.JComponent;
import javax.swing.UIManager;

import net.inxas.tilemap.gui.laf.TilemapEditorPaneUI;

public abstract class TilemapTool extends JComponent {
    private static final String uiClassID = "ToolUI";

    private String title;
    public TilemapTool(String title) {
        this.title = title;

        init();
    }

    private void init() {
        updateUI();
    }

    public void updateUI() {
        setUI((TilemapEditorPaneUI)UIManager.getUI(this));
    }
    public TilemapEditorPaneUI getUI() {
        return (TilemapEditorPaneUI)ui;
    }
    public void setUI(TilemapEditorPaneUI ui) {
        super.setUI(ui);
    }

    public String getUIClassID() {
        return uiClassID;
    }
}
