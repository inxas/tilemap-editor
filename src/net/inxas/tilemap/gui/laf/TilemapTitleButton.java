package net.inxas.tilemap.gui.laf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class TilemapTitleButton extends JButton implements MouseListener{
	private Icon normalIcon;
	private Icon hoverIcon;
	private Icon pressIcon;
	private Icon disableIcon;
	private boolean pressing;
	private boolean hovering;
	private boolean disable;
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
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//g2d.setClip(createButtonClip());
		paintBackground(g2d);
		g2d.setColor(ColorToolkit.text);
		getIcon().paintIcon(this, g2d, 0, 0);
		g2d.setColor(TilemapTheme.getTheme().getColor("TitlePaneUI.buttonBorderColor"));
		g2d.drawLine(0,0,0,getHeight());
		g2d.drawLine(getWidth()-1, 0, getWidth()-1, getHeight()-1);
		g2d.drawLine(0, getHeight()-1, getWidth()-1, getHeight()-1);
	}
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
	}
	public void mousePressed(MouseEvent e) {
		pressing = true;
		changeIcon();
	}
	public void mouseReleased(MouseEvent e) {
		pressing = false;
		changeIcon();
	}
	public void mouseEntered(MouseEvent e) {
		hovering = true;
		changeIcon();
	}
	public void mouseExited(MouseEvent e) {
		hovering = false;
		changeIcon();
	}
	public void mouseClicked(MouseEvent e) {}
}
