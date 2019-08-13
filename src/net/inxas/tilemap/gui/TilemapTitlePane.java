package net.inxas.tilemap.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import net.inxas.tilemap.gui.laf.TilemapIcons;
import net.inxas.tilemap.gui.laf.TilemapTitlePaneUI;
import net.inxas.tilemap.gui.laf.TilemapUtils;

/**
 * タイトルバーを実装します。
 * @author inxas
 *
 */
public class TilemapTitlePane extends JComponent implements MouseListener,MouseMotionListener{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2111274014969207692L;
    /**
     * @see #getUIClassID
     */
    private static final String uiClassID = "TitlePaneUI";

    /**
     * タイトルペインの高さ
     */
    public static final int DEFAULT_TITLE_PANE_HEIGHT = 25;

    private TilemapTitleIconLabel titleIconLabel;

    private Icon titleIcon;

    private TilemapTitleButton iconifyButton;
    private TilemapTitleButton resizeButton;
    private TilemapTitleButton closeButton;

    private Action iconifyAction;
    private Action resizeAction;
    private Action closeAction;

    private Icon iconifyIcon;
    private Icon maxIcon;
    private Icon restoreIcon;
    private Icon closeIcon;

    private Icon iconifyHoverIcon;
    private Icon maxHoverIcon;
    private Icon closeHoverIcon;

    private Icon iconifyPressIcon;
    private Icon maxPressIcon;
    private Icon closePressIcon;

    private Icon iconifyDisableIcon;
    private Icon maxDisableIcon;
    private Icon closeDisableIcon;

    private Window window;

    /**
     * 属するルートペイン
     */
    protected TilemapRootPane rootPane;
    /**
     * 属するルートペインを指定して作成します。
     * @param rootPane 属するルートペイン
     */
    public TilemapTitlePane(TilemapRootPane rootPane) {
        super();
        this.rootPane = rootPane;
        window = rootPane.getParent();
        init();
    }

    /**
     * 初期化処理をします。
     */
    protected void init() {
        createIcons();
        createActions();
        createButtons();

        setLayout(createLayout());

        add(iconifyButton,TitlePaneLayout.ICONIFY);
        add(resizeButton,TitlePaneLayout.RESIZE);
        add(closeButton,TitlePaneLayout.CLOSE);

        add(titleIconLabel,TitlePaneLayout.ICON);

        addMouseListener(this);
        addMouseMotionListener(this);

        updateUI();
    }

    /**
     * アイコンを初期化します。
     */
    protected void createIcons() {
        titleIcon = TilemapIcons.FRAME;
        titleIconLabel = new TilemapTitleIconLabel(this);

        iconifyIcon = TilemapIcons.ICONIFY_NORMAL;
        maxIcon = TilemapIcons.MAX_NORMAL;
        restoreIcon = TilemapIcons.RESTORE_NORMAL;
        closeIcon = TilemapIcons.CLOSE_NORMAL;

        iconifyHoverIcon = TilemapIcons.ICONIFY_HOVER;
        maxHoverIcon = TilemapIcons.MAX_HOVER;
        closeHoverIcon = TilemapIcons.CLOSE_HOVER;

        iconifyPressIcon = TilemapIcons.ICONIFY_PRESS;
        maxPressIcon = TilemapIcons.MAX_PRESS;
        closePressIcon = TilemapIcons.CLOSE_PRESS;

        iconifyDisableIcon = TilemapIcons.ICONIFY_DISABLE;
        maxDisableIcon = TilemapIcons.MAX_DISABLE;
        closeDisableIcon = TilemapIcons.CLOSE_DISABLE;
    }

    /**
     * 各ボタンのアクションを作成します。
     */
    protected void createActions() {
        iconifyAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(window instanceof Frame && window != null) {
                    ((Frame)window).setExtendedState(Frame.ICONIFIED);
                }
            }
        };
        resizeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(window instanceof Frame && window != null) {
                    Frame frame = (Frame)window;
                    if(frame.getExtendedState() != Frame.MAXIMIZED_BOTH) {
                        GraphicsConfiguration gc = frame.getGraphicsConfiguration();
                        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
                        Rectangle max = gc.getBounds();
                        max.x = 0 < insets.left ? insets.left : 0;
                        max.y = 0 < insets.top ? insets.top : 0;
                        max.width -= insets.left + insets.right;
                        max.height -= insets.top + insets.bottom;
                        frame.setMaximizedBounds(max);
                        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
                        resizeButton.setNormalIcon(restoreIcon);
                    }else {
                        frame.setExtendedState(Frame.NORMAL);
                        resizeButton.setNormalIcon(maxIcon);
                    }
                    repaint();
                }
            }
        };
        closeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(window != null) {
                    window.dispatchEvent(new WindowEvent(window,WindowEvent.WINDOW_CLOSING));
                }
            }
        };
    }

    /**
     * ボタンの初期化をします。
     */
    protected void createButtons() {
        iconifyButton = new TilemapTitleButton(
                iconifyIcon,
                iconifyHoverIcon,
                iconifyPressIcon,
                iconifyDisableIcon,
                iconifyAction);
        resizeButton = new TilemapTitleButton(
                maxIcon,
                maxHoverIcon,
                maxPressIcon,
                maxDisableIcon,
                resizeAction);
        closeButton = new TilemapTitleButton(
                closeIcon,
                closeHoverIcon,
                closePressIcon,
                closeDisableIcon,
                closeAction);
    }

    /**
     * レイアウトを作成します。
     * @return 作成したレイアウト
     */
    protected LayoutManager createLayout() {
        return new TitlePaneLayout(rootPane,10,10);
    }

    public void updateUI() {
        setUI((TilemapTitlePaneUI)UIManager.getUI(this));
    }
    public TilemapTitlePaneUI getUI() {
        return (TilemapTitlePaneUI)ui;
    }
    public void setUI(TilemapTitlePaneUI ui) {
        super.setUI(ui);
    }

    /**
     * @return "TitlePaneUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * 属するルートペインを返します。
     */
    public JRootPane getRootPane() {
        return rootPane;
    }

    /**
     * タイトルアイコンを返します。
     * @return タイトルアイコン
     */
    public Icon getTitleIcon() {
        return titleIcon;
    }

    /**
     * タイトルを返します。
     * @return タイトル
     */
    public String getTitle() {
        Window window = TilemapUtils.getWindow(rootPane);
        if(window instanceof Frame) return ((Frame)window).getTitle();
        if(window instanceof Dialog) return ((Dialog)window).getTitle();
        else return null;
    }

    /**
     * アイコンやボタンの横幅などを考慮して、タイトルを描画可能な横幅を計算します。
     * @return 計算結果
     */
    public int getDrawableWidth() {
        return getWidth() - getMinimumSize().width;
    }
    /**
     * タイトルを描画するときの左端の座標を計算します。
     * @return 計算結果
     */
    public int getDrawableLeftEdge() {
        if(titleIconLabel != null) {
            return titleIconLabel.getWidth();
        }
        return 0;
    }

    /**
     * タイトルペインに適したレイアウトマネージャです。
     * @author inxas
     * @version 0.0-alpha
     */
    protected class TitlePaneLayout implements LayoutManager2 {
        public static final String ICON = "iconLabel";
        private TilemapTitleIconLabel iconLabel;

        public static final String ICONIFY = "iconify";
        public static final String RESIZE = "resize";
        public static final String CLOSE = "close";
        private TilemapTitleButton iconify;
        private TilemapTitleButton resize;
        private TilemapTitleButton close;

        private int rightButtonMargin;
        private int leftButtonMargin;
        private TilemapRootPane rootPane;

        public TitlePaneLayout(TilemapRootPane rootPane,int rightButtonMargin,int leftButtonMargin) {
            this.rootPane = rootPane;
            this.rightButtonMargin = rightButtonMargin;
            this.leftButtonMargin = leftButtonMargin;
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            int width = 0;
            int height = DEFAULT_TITLE_PANE_HEIGHT;
            if(rootPane != null) width = rootPane.getWidth();
            return new Dimension(width,height);
        }
        @Override
        public Dimension minimumLayoutSize(Container parent) {
            int width = rightButtonMargin;
            if(iconifyButton != null)width += iconifyButton.getWidth();
            if(resizeButton != null)width += resizeButton.getWidth();
            if(closeButton != null)width += closeButton.getWidth();
            width += leftButtonMargin;
            if(iconLabel != null)width += iconLabel.getLabelSize().width;
            int height = DEFAULT_TITLE_PANE_HEIGHT;
            return new Dimension(width,height);
        }
        @Override
        public Dimension maximumLayoutSize(Container target) {
            return preferredLayoutSize(target);
        }
        @Override
        public void layoutContainer(Container parent) {
            int titleWidth = parent.getWidth();

            Dimension dim = preferredLayoutSize(parent);
            parent.setBounds(0,0,dim.width,dim.height);

            int rightPoint = titleWidth - rightButtonMargin;

            if(close != null) {
                Dimension pre = close.getPreferredSize();
                int x = rightPoint - pre.width;
                close.setBounds(x, 0, pre.width, pre.height);
                rightPoint = x;
            }
            if(resize != null) {
                Dimension pre = resize.getPreferredSize();
                int x = rightPoint - pre.width;
                resize.setBounds(x, 0, pre.width, pre.height);
                rightPoint = x;
            }
            if(iconify != null) {
                Dimension pre = iconify.getPreferredSize();
                int x = rightPoint - pre.width;
                iconify.setBounds(x, 0, pre.width, pre.height);
                rightPoint = x;
            }
            if(iconLabel != null) {
                Dimension pre = iconLabel.getLabelSize();
                iconLabel.setBounds(0, 0, pre.width, pre.height);
            }
        }
        @Override
        public void addLayoutComponent(Component comp, Object constraints) {
            if(comp == null)return;
            if(constraints == ICON && comp instanceof TilemapTitleIconLabel) {
                iconLabel = (TilemapTitleIconLabel)comp;
            }else if(constraints == ICONIFY && comp instanceof TilemapTitleButton) {
                iconify = (TilemapTitleButton)comp;
            }else if(constraints == RESIZE && comp instanceof TilemapTitleButton) {
                resize = (TilemapTitleButton)comp;
            }else if(constraints == CLOSE && comp instanceof TilemapTitleButton) {
                close = (TilemapTitleButton)comp;
            }
        }
        @Override
        public void addLayoutComponent(String name, Component comp) {}
        @Override
        public void removeLayoutComponent(Component comp) {}
        @Override
        public float getLayoutAlignmentX(Container target) {return 0.0f;}
        @Override
        public float getLayoutAlignmentY(Container target) {return 0.0f;}
        @Override
        public void invalidateLayout(Container target) {}
    }

    /**
     * マウスを押したポイント
     */
    protected Point pressedPoint;
    /**
     * フレームを移動します。
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if(pressedPoint == null) return;
        if(window instanceof Frame && ((Frame)window).getExtendedState() == Frame.MAXIMIZED_BOTH) return;
        Point screenPoint = e.getLocationOnScreen();
        int x = screenPoint.x - pressedPoint.x;
        int y = screenPoint.y - pressedPoint.y;
        window.setLocation(x, y);
    }
    /**
     * {@link #pressedPoint}を設定します。
     */
    @Override
    public void mousePressed(MouseEvent e) {
        pressedPoint = e.getPoint();
    }
    /**
     * {@link #pressedPoint}を解除します。
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        pressedPoint = null;
    }

    /**
     * 何もしません。
     */
    @Override
    public void mouseMoved(MouseEvent e) {}
    /**
     * 何もしません。
     */
    @Override
    public void mouseClicked(MouseEvent e) {}
    /**
     * 何もしません。
     */
    @Override
    public void mouseEntered(MouseEvent e) {}
    /**
     * 何もしません。
     */
    @Override
    public void mouseExited(MouseEvent e) {}
}
