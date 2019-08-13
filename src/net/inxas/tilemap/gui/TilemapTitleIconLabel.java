package net.inxas.tilemap.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * フレームのタイトルアイコン描画用のラベルです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapTitleIconLabel extends JLabel {
    private TilemapTitlePane titlePane;
    private Icon titleIcon;
    /**
     * 属するタイトルペインを指定して作成します。
     * @param titlePane 属するタイトルペイン
     */
    public TilemapTitleIconLabel(TilemapTitlePane titlePane) {
        super();
        this.titlePane = titlePane;
        this.titleIcon = titlePane.getTitleIcon();
        setSize(getLabelSize());
        setPreferredSize(getLabelSize());
    }
    /**
     * ラベルに適したサイズを返します。
     * @return ラベルに適したサイズ
     */
    public Dimension getLabelSize() {
        int size = titlePane.getHeight();
        return new Dimension(size,size);
    }
    /**
     * 描画します。
     * @param g グラフィックス
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size = getLabelSize().height;
        int x = (size - titleIcon.getIconWidth()) / 2;
        int y = (size - titleIcon.getIconHeight()) / 2;
        titleIcon.paintIcon(this, g, x, y);
    }
}
