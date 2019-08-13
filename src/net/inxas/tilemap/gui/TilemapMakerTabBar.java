package net.inxas.tilemap.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.UIManager;

import net.inxas.tilemap.Work;
import net.inxas.tilemap.gui.laf.TilemapMakerTabBarUI;
import net.inxas.tilemap.gui.laf.TilemapTheme;
import net.inxas.tilemap.gui.laf.TilemapUtils;

/**
 * ワークを一つずつ管理するためのタブバーです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapMakerTabBar extends JComponent {
    private static final String uiClassID = "MakerTabBarUI";
    private TilemapMakerPane pane;
    private OverviewButton overview;
    private LinkedHashMap<Work,Tab> works;
    /**
     * 親のメイカーペインを登録して作成します。
     * @param pane 親のペイン
     */
    public TilemapMakerTabBar(TilemapMakerPane pane) {
        this.pane = pane;
        overview = new OverviewButton();
        works = new LinkedHashMap<>();
        init();
    }
    /**
     * 初期化処理をします。通常はコンストラクタからのみ呼ばれるべきです。
     */
    protected void init() {
        setLayout(createLayout());
        add(overview);
        addWork(new Work("a", 255, 255, 32, 32));
        addWork(new Work("b", 255, 255, 32, 32));
        addWork(new Work("c", 255, 255, 32, 32));
        addWork(new Work("d", 255, 255, 32, 32));
        addWork(new Work("e", 255, 255, 32, 32));
        addWork(new Work("f", 255, 255, 32, 32));
        addWork(new Work("g", 255, 255, 32, 32));
        addWork(new Work("h", 255, 255, 32, 32));
        updateUI();
    }

    /**
     * ワークを追加します。
     * @param work 追加するワーク
     */
    public void addWork(Work work) {
        Tab tab = new Tab();
        works.put(work, tab);
        add(tab);
    }
    /**
     * ワークを削除します。
     * @param work 削除するワーク
     */
    public void removeWork(Work work) {
        remove(works.get(work));
        works.remove(work);
    }

    /**
     * 現在追加されているワークの一覧を返します。
     * @return ワークの一覧
     */
    public Work[] getWorks() {
        return (Work[])works.keySet().toArray();
    }

    /**
     * ワークを優先度順にソートして返します。
     * @return ソートしたタブ
     */
    private ArrayList<Tab> prioritySort() {
        ArrayList<Tab> list = new ArrayList<>(works.size());
        for(Work key : works.keySet()) {
            list.add(works.get(key));
        }
        list.sort(Comparator.comparing(Tab::getPriority));
        return list;
    }

    /**
     * レイアウトを返します。
     * @return 作成したレイアウト
     */
    protected LayoutManager createLayout() {
        return new MakerTabBarLayout();
    }
    /**
     * タブバー専用のレイアウトマネージャ
     * @author inxas
     * @version 0.0-alpha
     */
    protected class MakerTabBarLayout implements LayoutManager2{
        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return new Dimension(parent.getParent().getWidth(),TilemapUtils.getDisplaySize().height / 30);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return preferredLayoutSize(parent);
        }
        @Override
        public Dimension maximumLayoutSize(Container target) {
            return preferredLayoutSize(target);
        }

        @Override
        public void layoutContainer(Container parent) {
            int length = works.size();
            Insets i = parent.getInsets();
            int width = parent.getWidth() - i.left - i.right;
            int widthPerPiece = width / length;
            if(widthPerPiece > Tab.maxWidth) {
                int index = 0;
                for(Tab tab : works.values()) {
                    if(tab != null) {
                        tab.setBounds(i.left + Tab.maxWidth * index, 0, Tab.maxWidth, parent.getHeight());
                        index++;
                    }
                }
            }else if(widthPerPiece < Tab.minWidth) {
                ArrayList<Tab> tabs = prioritySort();
                int len = width / Tab.minWidth;
                for(int index = 0;index < len;index++) {
                    tabs.get(index).setBounds(i.left + Tab.minWidth * index, 0, Tab.minWidth, parent.getHeight());
                }
            }else {
                int index = 0;
                for(Tab tab : works.values()) {
                    if(tab != null) {
                        tab.setBounds(i.left + widthPerPiece * index, 0, widthPerPiece, parent.getHeight());
                        index++;
                    }
                }
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

    /**
     * タブ一つを表します。
     * @author inxas
     * @version 0.0-alpha
     */
    private static class Tab extends TilemapPanel{
        static final int maxWidth = TilemapUtils.getDisplaySize().width / 10;
        static final int minWidth = TilemapUtils.getDisplaySize().width / 20;

        @Override
        public void paintComponent(Graphics g) {
            g.setColor(TilemapTheme.getTheme().getColor("MakerTabBarUI.background"));
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        private int priority;
        public Tab() {
            reset();
        }
        public void rise() {
            priority++;
        }
        public void fall() {
            priority--;
        }
        public void reset() {
            priority = 0;
        }
        public int getPriority() {
            return priority;
        }
    }
    /**
     * タブがあふれたときに隠れたタブ一覧を表示するためのボタンです。
     * @author inxas
     * @version 0.0-alpha
     */
    private static class OverviewButton extends JButton{

    }

    public void updateUI() {
        setUI((TilemapMakerTabBarUI)UIManager.getUI(this));
    }
    public TilemapMakerTabBarUI getUI() {
        return (TilemapMakerTabBarUI)ui;
    }
    public void setUI(TilemapMakerTabBarUI ui) {
        super.setUI(ui);
    }
    public String getUIClassID() {
        return uiClassID;
    }
}
