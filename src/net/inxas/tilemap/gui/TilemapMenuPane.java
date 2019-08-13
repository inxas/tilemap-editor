package net.inxas.tilemap.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.Border;

import net.inxas.tilemap.gui.laf.TilemapIcons;
import net.inxas.tilemap.gui.laf.TilemapMenuPaneUI;
import net.inxas.tilemap.gui.laf.TilemapTheme;
import net.inxas.tilemap.gui.laf.TilemapUtils;

/**
 * エディタペインに配置するメニューペインです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapMenuPane extends JComponent {
    private static final String uiClassID = "MenuPaneUI";

    private final int iconWidth;
    private final int containerWidth;

    private TilemapMenuIcon fileIcon;
    private TilemapMenuIcon editIcon;
    private TilemapMenuIcon dispIcon;
    private TilemapMenuIcon helpIcon;
    private TilemapMenuIcon confIcon;

    private TilemapMenuContainer currentContainer;
    /**
     * コンストラクタ
     */
    public TilemapMenuPane() {
        iconWidth = TilemapUtils.getDisplaySize().width / 30;
        containerWidth = iconWidth * 3;

        init();
    }
    /**
     * 初期化処理をします。
     */
    private void init() {
        setPreferredSize(new Dimension(iconWidth,TilemapUtils.getDisplaySize().height));

        setBorder(createBorder());
        setLayout(createLayout());

        initMenuIcons();

        updateUI();
    }

    /**
     * メニューアイコンを初期化します。
     */
    private void initMenuIcons() {
        fileIcon = new TilemapMenuIcon(TilemapIcons.MENU_FILE,TilemapIcons.MENU_FILE_HOVER,TilemapMenuContainer.createFileContainer());

        add(fileIcon,MenuPaneConstraints.TOP);
    }

    /**
     * 表示されるメニューコンテナを指定します。
     * 既に表示されているコンテナを指定した場合、コンテナを隠します。
     * @param container 切り替えたいコンテナ
     */
    public void setMenuContainer(TilemapMenuContainer container) {
        if(currentContainer != null)remove(currentContainer);

        if(currentContainer != container) {
            currentContainer = container;
            if(currentContainer != null)add(currentContainer,MenuPaneConstraints.CONTAINER);
        }else {
            currentContainer = null;
        }

        Insets i = getInsets();
        if(currentContainer != null) {
            setPreferredSize(new Dimension(iconWidth + containerWidth + i.left + i.right,TilemapUtils.getDisplaySize().height));
        }else {
            setPreferredSize(new Dimension(iconWidth + i.left + i.right,TilemapUtils.getDisplaySize().height));
        }
    }

    /**
     * 現在表示されているコンテナを返します。
     * @return
     */
    public TilemapMenuContainer getMenuContainer() {
        return currentContainer;
    }

    /**
     * レイアウトを作成します。
     * @return 作成したレイアウト
     */
    protected LayoutManager createLayout() {
        return new MenuPaneLayout();
    }

    /**
     * {@link MenuPaneLayout}で使用する制約です。
     * @author inxas
     * @version 0.0-alpha
     */
    protected enum MenuPaneConstraints{
        TOP,BOTTOM,CONTAINER;
    }
    /**
     * メニューペイン用のレイアウトマネージャです。
     * @author inxas
     * @version 0.0-alpha
     */
    protected class MenuPaneLayout implements LayoutManager2 {
        private ArrayList<TilemapMenuIcon> topIcons;
        private ArrayList<TilemapMenuIcon> bottomIcons;
        private TilemapMenuContainer container;
        protected MenuPaneLayout() {
            topIcons = new ArrayList<>();
            bottomIcons = new ArrayList<>();
        }
        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return getPreferredSize();
        }
        @Override
        public Dimension maximumLayoutSize(Container target) {
            return getPreferredSize();
        }
        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return getPreferredSize();
        }
        @Override
        public void layoutContainer(Container parent) {
            Insets i = parent.getInsets();
            int contentHeight = parent.getHeight() - i.top - i.bottom;

            int bottomY = contentHeight + i.top;
            for(TilemapMenuIcon icon : bottomIcons) {
                bottomY -= iconWidth;
                contentHeight -= iconWidth;
                if(contentHeight > iconWidth)icon.setBounds(i.left, bottomY, iconWidth, iconWidth);
            }

            int topY = i.top;
            for(TilemapMenuIcon icon : topIcons) {
                if(contentHeight > iconWidth) {
                    icon.setBounds(i.left, topY, iconWidth, iconWidth);
                    topY += iconWidth;
                    contentHeight -= iconWidth;
                }
            }

            if(container != null) {
                container.setBounds(iconWidth, i.top, containerWidth, parent.getHeight() - i.top - i.bottom);
            }
        }
        @Override
        public void addLayoutComponent(Component comp, Object constraints) {
            if(constraints == MenuPaneConstraints.TOP && comp instanceof TilemapMenuIcon) {
                topIcons.add((TilemapMenuIcon) comp);
            }else if(constraints == MenuPaneConstraints.BOTTOM && comp instanceof TilemapMenuIcon) {
                bottomIcons.add((TilemapMenuIcon) comp);
            }else if(constraints == MenuPaneConstraints.CONTAINER && comp instanceof TilemapMenuContainer) {
                container = (TilemapMenuContainer) comp;
            }
        }
        @Override
        public void removeLayoutComponent(Component comp) {
            if(comp == container)container = null;
        }
        @Override
        public void addLayoutComponent(String name, Component comp) {}
        @Override
        public float getLayoutAlignmentX(Container target) {return 0;}
        @Override
        public float getLayoutAlignmentY(Container target) {return 0;}
        @Override
        public void invalidateLayout(Container target) {}
    }

    /**
     * メニューペイン用のボーダーを作成します。
     * @return 作成したボーダー
     */
    protected Border createBorder() {
        return new MenuPaneBorder();
    }

    /**
     * メニューペイン用のボーダーです。
     * @author inxas
     * @version 0.0-alpha
     */
    protected class MenuPaneBorder implements Border {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(TilemapTheme.getTheme().getColor("MenuPaneUI.borderColor"));
            g.fillRect(width - 2, 0, width - 1, height);
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(0,0,0,2);
        }
        @Override
        public boolean isBorderOpaque() {
            return true;
        }
    }

    public void updateUI() {
        setUI((TilemapMenuPaneUI)UIManager.getUI(this));
    }
    public TilemapMenuPaneUI getUI() {
        return (TilemapMenuPaneUI)ui;
    }
    public void setUI(TilemapMenuPaneUI ui) {
        super.setUI(ui);
    }
    public String getUIClassID() {
        return uiClassID;
    }
}
