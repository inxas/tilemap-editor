package net.inxas.tilemap.gui;

import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.UIManager;

import net.inxas.tilemap.gui.laf.TilemapToolContainerUI;
public class TilemapToolContainer extends JComponent {
    private static final String uiClassID = "ToolContainerUI";
    public static final int ICON_SIZE = 40;

    private LinkedList<TilemapTool> tools;
    public TilemapToolContainer(TilemapTool... defaultTools) {
        init(defaultTools);
    }
    protected void init(TilemapTool... defaultTools) {
        tools = new LinkedList<>();

        updateUI();
    }

    public void addTool(TilemapTool tool) {
        tools.add(tool);
        add(tool);
    }

    public void updateUI() {
        setUI((TilemapToolContainerUI)UIManager.getUI(this));
    }
    public TilemapToolContainerUI getUI() {
        return (TilemapToolContainerUI)ui;
    }
    public void setUI(TilemapToolContainerUI ui) {
        super.setUI(ui);
    }

    public String getUIClassID() {
        return uiClassID;
    }
}
