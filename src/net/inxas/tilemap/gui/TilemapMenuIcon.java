package net.inxas.tilemap.gui;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.UIManager;

import net.inxas.tilemap.gui.laf.TilemapMenuIconUI;

/**
 * メニューペインに配置する、メニューコンテナー開閉用ボタンです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapMenuIcon extends JComponent{
    private static final String uiClassID = "MenuIconUI";

    private final Icon icon;
    private final Icon hoverIcon;
    private Icon currentIcon;
    private final TilemapMenuContainer container;
    /**
     * 通常のアイコン、ホバーした時のアイコン、開閉するメニューコンテナを指定して作成します。
     * @param icon 通常のアイコン
     * @param hoverIcon ホバーした時のアイコン
     * @param container 開閉するメニューコンテナ
     */
    TilemapMenuIcon(Icon icon,Icon hoverIcon,TilemapMenuContainer container){
        super();

        this.icon = icon;
        this.hoverIcon = hoverIcon;
        this.container = container;
        init();
    }

    /**
     * 初期化処理をします。
     */
    private void init() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        currentIcon = icon;

        initListeners();

        updateUI();
    }

    /**
     * リスナーを初期化します。
     */
    protected void initListeners() {
        addMouseListener(new OpenAndCloseContainer());
    }

    /**
     * 現在表示されているアイコンを返します。
     * @return 現在表示されているアイコン
     */
    public Icon getCurrentIcon() {
        return currentIcon;
    }
    /**
     * このメニューアイコンが開閉するメニューコンテナを返します。
     * @return
     */
    public TilemapMenuContainer getMenuContainer() {
        return container;
    }

    /**
     * メニューアイコン用のマウスリスナーです。
     * @author inxas
     * @version 0.0-alpha
     */
    protected class OpenAndCloseContainer implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if(getParent() instanceof TilemapMenuPane) {
                TilemapMenuPane parent = (TilemapMenuPane) getParent();
                parent.setMenuContainer(container);
                if(parent.getMenuContainer() != null) {
                    currentIcon = hoverIcon;
                }
            }
            getParent().revalidate();
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {
            currentIcon = hoverIcon;
            repaint();
        }
        @Override
        public void mouseExited(MouseEvent e) {
            if(getParent() instanceof TilemapMenuPane) {
                TilemapMenuPane parent = (TilemapMenuPane) getParent();
                if(parent.getMenuContainer() == container) {
                    currentIcon = hoverIcon;
                }else {
                    currentIcon = icon;
                }
            }
            repaint();
        }
    }

    public void updateUI() {
        setUI((TilemapMenuIconUI)UIManager.getUI(this));
    }
    public TilemapMenuIconUI getUI() {
        return (TilemapMenuIconUI)ui;
    }
    public void setUI(TilemapMenuIconUI ui) {
        super.setUI(ui);
    }
    public String getUIClassID() {
        return uiClassID;
    }
}
