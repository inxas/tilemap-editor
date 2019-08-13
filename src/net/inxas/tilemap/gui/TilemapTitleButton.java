package net.inxas.tilemap.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import net.inxas.tilemap.gui.laf.ColorToolkit;
import net.inxas.tilemap.gui.laf.TilemapTheme;

/**
 * タイトルペインに配置するボタンです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapTitleButton extends JButton implements MouseListener{
    private Icon normalIcon;
    private Icon hoverIcon;
    private Icon pressIcon;
    private Icon disableIcon;
    private boolean pressing;
    private boolean hovering;
    private boolean disable;
    /**
     * 各種アイコンとアクションを設定して作成します。
     * @param normal 通常状態のアイコン
     * @param hover ホバー状態のアイコン
     * @param press プレス状態のアイコン
     * @param disable 停止状態のアイコン
     * @param action アクション
     */
    public TilemapTitleButton(Icon normal,Icon hover,Icon press,Icon disable,Action action) {
        super();

        this.normalIcon = normal;
        this.hoverIcon = hover;
        this.pressIcon = press;
        this.disableIcon = disable;

        pressing = false;
        hovering = false;

        setFocusPainted(false);
        setFocusable(false);
        setBorderPainted(false);
        setText(null);
        setContentAreaFilled(false);
        setAction(action);

        addMouseListener(this);
        changeIcon();

        setPreferredSize(new Dimension(normal.getIconWidth(),normal.getIconHeight()));
    }
    /**
     * 通常状態のアイコンを変更します。
     * @param normal 新しいアイコン
     */
    public void setNormalIcon(Icon normal) {
        this.normalIcon = normal;
    }
    /**
     * 背景を描画します。
     * @param g グラフィックス
     */
    private void paintBackground(Graphics g) {
        Color old = g.getColor();
        if(disable) {
            g.setColor(TilemapTheme.getTheme().getColor("TitlePaneUI.buttonDisableBackground"));
        }else if(pressing && hovering) {
            g.setColor(TilemapTheme.getTheme().getColor("TitlePaneUI.buttonPressingBackground"));
        }else if(hovering) {
            g.setColor(TilemapTheme.getTheme().getColor("TitlePaneUI.buttonHoveringBackground"));
        }else {
            g.setColor(TilemapTheme.getTheme().getColor("TitlePaneUI.buttonBackground"));
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(old);
    }
    /**
     * 描画をします。
     * @param g グラフィックス
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        paintBackground(g2d);
        g2d.setColor(ColorToolkit.text);
        getIcon().paintIcon(this, g2d, 0, 0);
        g2d.setColor(TilemapTheme.getTheme().getColor("TitlePaneUI.buttonBorderColor"));
        g2d.drawLine(0,0,0,getHeight());
        g2d.drawLine(getWidth()-1, 0, getWidth()-1, getHeight()-1);
        g2d.drawLine(0, getHeight()-1, getWidth()-1, getHeight()-1);
    }
    /**
     * 状況に合わせてアイコンを変更します。
     */
    private void changeIcon() {
        if(disable && disableIcon != null) {
            setIcon(disableIcon);
        }else if(pressing && pressIcon != null) {
            setIcon(pressIcon);
        }else if(hovering && hoverIcon != null) {
            setIcon(hoverIcon);
        }else {
            setIcon(normalIcon);
        }
        repaint();
    }
    /**
     * プレス処理をして{@link #changeIcon()}を呼び出します。
     */
    public void mousePressed(MouseEvent e) {
        pressing = true;
        changeIcon();
    }
    /**
     * リリース処理をして{@link #changeIcon()}を呼び出します。
     */
    public void mouseReleased(MouseEvent e) {
        pressing = false;
        changeIcon();
    }
    /**
     * エンター処理をして{@link #changeIcon()}を呼び出します。
     */
    public void mouseEntered(MouseEvent e) {
        hovering = true;
        changeIcon();
    }
    /**
     * イグジット処理をして{@link #changeIcon()}を呼び出します。
     */
    public void mouseExited(MouseEvent e) {
        hovering = false;
        changeIcon();
    }
    /**
     * 何もしません。
     */
    public void mouseClicked(MouseEvent e) {}
}
