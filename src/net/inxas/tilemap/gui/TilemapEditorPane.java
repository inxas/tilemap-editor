package net.inxas.tilemap.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;

import javax.swing.JComponent;
import javax.swing.UIManager;

import net.inxas.tilemap.gui.laf.TilemapEditorPaneUI;

/**
 * {@link EditorFrame}のコンテントペインです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapEditorPane extends JComponent {
    private static final String uiClassID = "EditorPaneUI";
    private TilemapMenuPane menuPane;
    private TilemapMakerPane makerPane;
    private TilemapToolPane toolPane;
    
    /**
     * コンストラクタ
     */
    public TilemapEditorPane() {
        super();
        init();
    }

    /**
     * 初期化処理をします。通常はコンストラクタにのみ呼ばれるべきです。
     */
    protected void init() {
        setLayout(createLayout());
        initPanes();

        updateUI();
    }
    /**
     * 各種ペインの初期化をします。通常は{@link #init()}からのみ呼ばれるべきです。
     */
    protected void initPanes() {
        menuPane = new TilemapMenuPane();
        makerPane = new TilemapMakerPane();
        toolPane = new TilemapToolPane(new TilemapToolContainer[] {
                new TilemapToolContainer(),
                new TilemapToolContainer()
        });

        add(menuPane);
        add(makerPane);
        add(toolPane);
    }

    /**
     * メニューペインを返します。
     * @return メニューペイン
     */
    public TilemapMenuPane getMenuPane() {
        return menuPane;
    }
    /**
     * メイカーペインを返します。
     * @return メイカーペイン
     */
    public TilemapMakerPane getMakerPane() {
        return makerPane;
    }
    /**
     * ツールペインを返します。
     * @return ツールペイン
     */
    public TilemapToolPane getToolPane() {
        return toolPane;
    }

    /**
     * UIのアップデートを行います。
     */
    public void updateUI() {
        setUI((TilemapEditorPaneUI)UIManager.getUI(this));
    }
    /**
     * このエディタペインのUIを返します。
     */
    public TilemapEditorPaneUI getUI() {
        return (TilemapEditorPaneUI)ui;
    }
    /**
     * このエディタペインのUIを変更します。
     * @param ui 新しいUI
     */
    public void setUI(TilemapEditorPaneUI ui) {
        super.setUI(ui);
    }
    /**
     * エディタペインのUIClassIDを返します。返す値は{@value #uiClassID}です。
     */
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * {@link EditorPaneLayout}を作成し、返します。
     * @return 作成したエディターペインレイアウト
     */
    protected EditorPaneLayout createLayout() {
        return new EditorPaneLayout();
    }
    /**
     * エディターペイン専用のレイアウトマネージャです。
     * @author inxas
     * @version 0.0-alpha
     */
    protected class EditorPaneLayout implements LayoutManager2 {
        /**
         * エディタペインのサイズを返します。
         */
        @Override
        public Dimension maximumLayoutSize(Container target) {
            return target.getSize();
        }
        /**
         * エディタペインのサイズを返します。
         */
        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return parent.getSize();
        }
        /**
         * エディタペインのサイズを返します。
         */
        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return parent.getSize();
        }
        /**
         * 各種ペインを配置します。
         */
        @Override
        public void layoutContainer(Container parent) {
            int width = parent.getWidth();
            int height = parent.getHeight();

            int contentWidth = width;
            int makerPaneX = 0;
            if(menuPane != null) {
                Dimension pre = menuPane.getPreferredSize();
                int menuWidth = Math.min(pre.width, contentWidth);
                int menuHeight = height;
                menuPane.setBounds(0, 0, menuWidth, menuHeight);

                contentWidth -= menuWidth;
                makerPaneX = menuWidth;
            }
            if(toolPane != null) {
                Dimension pre = toolPane.getPreferredSize();
                int toolWidth = Math.min(pre.width, contentWidth);
                if(toolWidth == contentWidth) {
                    toolPane.setNormalWidth(toolWidth);
                }
                int toolHeight = height;
                toolPane.setBounds(width - toolWidth, 0, toolWidth, toolHeight);

                contentWidth -= toolWidth;
            }
            if(makerPane != null) {
                makerPane.setBounds(makerPaneX, 0, contentWidth, height);
            }
        }
        @Override
        public void addLayoutComponent(Component comp, Object constraints) {}
        @Override
        public void addLayoutComponent(String name, Component comp) {}
        @Override
        public void removeLayoutComponent(Component comp) {}
        @Override
        public float getLayoutAlignmentX(Container target) {return 0;}
        @Override
        public float getLayoutAlignmentY(Container target) {return 0;}
        @Override
        public void invalidateLayout(Container target) {}
    }
}
