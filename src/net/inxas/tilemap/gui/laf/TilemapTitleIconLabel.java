package net.inxas.tilemap.gui.laf;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

public class TilemapTitleIconLabel extends JLabel {
	private TilemapTitlePane titlePane;
	private Icon titleIcon;
	public TilemapTitleIconLabel(TilemapTitlePane titlePane) {
		super();
		this.titlePane = titlePane;
		this.titleIcon = titlePane.getTitleIcon();
		setSize(getLabelSize());
		setPreferredSize(getLabelSize());
	}
	public Dimension getLabelSize() {
		int size = titlePane.getHeight();
		return new Dimension(size,size);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int size = getLabelSize().height;
		int x = (size - titleIcon.getIconWidth()) / 2;
		int y = (size - titleIcon.getIconHeight()) / 2;
		titleIcon.paintIcon(this, g, x, y);
	}
}
