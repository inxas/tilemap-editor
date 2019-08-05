package net.inxas.tilemap.gui.laf;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;

/**
 * タイルマップエディタ用のルートペインです。
 * @author inxas
 * @since 2019/07/28
 * @version 0.0-alpha
 */
public class TilemapRootPane extends JRootPane {
	protected TilemapTitlePane titlePane;
	protected Window parent;
	
	protected TilemapFrameBorderBar topBorder;
	protected TilemapFrameBorderBar bottomBorder;
	protected TilemapFrameBorderBar leftBorder;
	protected TilemapFrameBorderBar rightBorder;
		
	public TilemapRootPane(Window parent) {
		super();
		this.parent = parent;
		setTitlePane(createTitlePane());
		initBorders();
		setLayout(createRootLayout());
		setWindowDecorationStyle(FRAME);
	}
	
	public Window getParent() {
		return parent;
	}
	
	protected void setTitlePane(TilemapTitlePane titlePane) {
		if(titlePane != null) {
			if(this.titlePane != null && this.titlePane.getParent() == layeredPane) {
				layeredPane.remove(this.titlePane);
			}
			this.titlePane = titlePane;
			layeredPane.add(titlePane, JLayeredPane.FRAME_CONTENT_LAYER);
		}
	}
	protected TilemapTitlePane createTitlePane() {
		TilemapTitlePane titlePane = new TilemapTitlePane(this);
		titlePane.setName(this.getName() + ".titlePane");
		return titlePane;
	}
	public TilemapTitlePane getTitlePane() {
		return titlePane;
	}
	
	protected void initBorders() {
		add(topBorder = TilemapFrameBorderBar.createTopBorder(parent));
		add(bottomBorder = TilemapFrameBorderBar.createBottomBorder(parent));
		add(leftBorder = TilemapFrameBorderBar.createLeftBorder(parent));
		add(rightBorder = TilemapFrameBorderBar.createRightBorder(parent));
	}
	
	protected LayoutManager createRootLayout() {
		return new TilemapRootLayout();
	}
	
	protected class TilemapRootLayout implements LayoutManager2 {
		@Override
		public Dimension preferredLayoutSize(Container parent) {
			Dimension rd, mbd, tpd;
			Insets i = getInsets();
			if(contentPane != null) {
				rd = contentPane.getPreferredSize();
			} else {
				rd = parent.getSize();
			}
			if(menuBar != null && menuBar.isVisible()) {
				mbd = menuBar.getPreferredSize();
			} else {
				mbd = new Dimension(0, 0);
			}
			if(titlePane != null) {
				tpd = titlePane.getPreferredSize();
			}else {
				tpd = new Dimension(0,0);
			}
			return new Dimension(Math.max(Math.max(rd.width, mbd.width),tpd.width) + i.left + i.right,
						rd.height + mbd.height + tpd.height + i.top + i.bottom);
		}

		@Override
		public Dimension minimumLayoutSize(Container parent) {
			Dimension rd, mbd, tpd;
			Insets i = getInsets();
			if(contentPane != null) {
				rd = contentPane.getMinimumSize();
			} else {
				rd = parent.getSize();
			}
			if(menuBar != null && menuBar.isVisible()) {
				mbd = menuBar.getMinimumSize();
			} else {
				mbd = new Dimension(0, 0);
			}
			if(titlePane != null) {
				tpd = titlePane.getMinimumSize();
			}else {
				tpd = new Dimension(0,0);
			}
			return new Dimension(Math.max(Math.max(rd.width, mbd.width),tpd.width) + i.left + i.right,
						rd.height + mbd.height + tpd.height + i.top + i.bottom);
		}
		
		@Override
		public Dimension maximumLayoutSize(Container target) {
			Dimension rd, mbd,tpd;
			Insets i = getInsets();
			if(titlePane != null) {
				tpd = titlePane.getMaximumSize();
			}else {
				tpd = new Dimension(0, 0);
			}
			if(menuBar != null && menuBar.isVisible()) {
				mbd = menuBar.getMaximumSize();
			} else {
				mbd = new Dimension(0, 0);
			}
			if(contentPane != null) {
				rd = contentPane.getMaximumSize();
			} else {
				rd = new Dimension(Integer.MAX_VALUE,
						Integer.MAX_VALUE - i.top - i.bottom - mbd.height - tpd.height - 1);
			}
			return new Dimension(Math.max(Math.max(rd.width, mbd.width),tpd.width) + i.left + i.right,
								rd.height + mbd.height + tpd.height + i.top + i.bottom);
		}

		@Override
		public void layoutContainer(Container parent) {
			Insets i = new Insets(3,3,3,3);
			int w = getWidth() - i.left - i.right;
			int h = getHeight() - i.top - i.bottom;
			
			if(topBorder != null) {
				topBorder.setBounds(0, 0, getWidth(), i.top);
			}
			if(bottomBorder != null) {
				bottomBorder.setBounds(0,i.top + h,getWidth(),i.bottom);
			}
			if(leftBorder != null) {
				leftBorder.setBounds(0, 0, i.left, getHeight());
			}
			if(rightBorder != null) {
				rightBorder.setBounds(w + i.left, 0, i.right, getHeight());
			}
			
			int titleY = 0;
			int contentY = 0;
			
			if(layeredPane != null) {
				layeredPane.setBounds(i.left, i.top, w, h);
			}
			if(getWindowDecorationStyle() != NONE) {
				if(titlePane != null) {
					Dimension titleDim = titlePane.getPreferredSize();
					titlePane.setBounds(0, 0, w, titleDim.height);
					titleY = titleDim.height;
					contentY += titleY;
				}
			}
			if(menuBar != null && menuBar.isVisible()) {
				Dimension menuDim = menuBar.getPreferredSize();
				menuBar.setBounds(0, contentY, w, menuDim.height);
				contentY += menuDim.height;
			}
			if(contentPane != null) {
				contentPane.setBounds(0, contentY, w, h - contentY);
			}
			if(glassPane != null) {
				glassPane.setBounds(i.left,i.top + titleY,w,h - titleY);
			}
		}
		public void addLayoutComponent(String name, Component comp) {}
		public void removeLayoutComponent(Component comp) {}
		public void addLayoutComponent(Component comp, Object constraints) {}
		public float getLayoutAlignmentX(Container target) {return 0.0f;}
		public float getLayoutAlignmentY(Container target) {return 0.0f;}
		public void invalidateLayout(Container target) {}
	}
}
