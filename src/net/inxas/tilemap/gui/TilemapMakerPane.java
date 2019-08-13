package net.inxas.tilemap.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;

import javax.swing.JComponent;
import javax.swing.UIManager;

import net.inxas.tilemap.Work;
import net.inxas.tilemap.gui.laf.TilemapMakerPaneUI;
import net.inxas.tilemap.gui.laf.TilemapUtils;

/**
 * エディタ部分のベースとなるペインです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapMakerPane extends JComponent {
    private static final String uiClassID = "MakerPaneUI";

    /**
     * このペインが管理するタブバーです。
     */
    private TilemapMakerTabBar tabBar;
    /**
     * このペインが管理するキャンバスです。
     */
    private TilemapCanvas canvas;
    /**
     * コンストラクタ
     */
    public TilemapMakerPane() {
        init();
    }
    /**
     * 初期化処理をします。通常はコンストラクタからのみ呼ばれるべきです。
     */
    private void init() {
        setLayout(createLayout());

        tabBar = new TilemapMakerTabBar(this);
        add(tabBar);

        updateUI();
    }

    public void changeTab() {

    }

    /**
     * ワークを新規作成し、タブに追加します。
     * @param title ワークのタイトル
     * @param width ワークの横幅
     * @param height ワークの縦幅
     * @param hSize マップチップの横幅
     * @param vSize マップチップの縦幅
     */
    public void create(String title,int width,int height,int hSize,int vSize) {
        if(tabBar != null) {
            tabBar.addWork(new Work(title,width,height,hSize,vSize));
        }
    }

    /**
     * {@link MakerPaneLayout}を作成します。
     * @return 作成したレイアウトマネージャ
     */
    protected LayoutManager createLayout() {
        return new MakerPaneLayout();
    }
    /**
     * メイカーペイン専用のレイアウトマネージャ
     * @author inxas
     * @version 0.0-alpha
     */
    protected class MakerPaneLayout implements LayoutManager2 {
        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return null;
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return null;
        }
        @Override
        public Dimension maximumLayoutSize(Container target) {
            return null;
        }

        @Override
        public void layoutContainer(Container parent) {
            if(tabBar != null) {
                tabBar.setBounds(0, 0, parent.getWidth(), TilemapUtils.getDisplaySize().height / 30);
            }
        }

        @Override
        public void addLayoutComponent(String name, Component comp) {}
        @Override
        public void removeLayoutComponent(Component comp) {}
        @Override
        public void addLayoutComponent(Component comp, Object constraints) {}
        @Override
        public float getLayoutAlignmentX(Container target) {return 0;}
        @Override
        public float getLayoutAlignmentY(Container target) {return 0;}
        @Override
        public void invalidateLayout(Container target) {}
    }

    public void updateUI() {
        setUI((TilemapMakerPaneUI)UIManager.getUI(this));
    }
    public TilemapMakerPaneUI getUI() {
        return (TilemapMakerPaneUI)ui;
    }
    public void setUI(TilemapMakerPaneUI ui) {
        super.setUI(ui);
    }
    public String getUIClassID() {
        return uiClassID;
    }
}
