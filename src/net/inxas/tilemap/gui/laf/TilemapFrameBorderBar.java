package net.inxas.tilemap.gui.laf;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

public abstract class TilemapFrameBorderBar extends JComponent implements MouseListener,MouseMotionListener{
	protected int thickness = 2;
	protected Window parent;
	protected Cursor hoveringCursor;
	protected Cursor nearCursor;
	protected Cursor behindCursor;
	protected Cursor defaultCursor;
	protected Point pressedPoint;
	protected Point draggedPoint;
	protected int pressedX;
	protected int pressedY;
	protected boolean isSupportedCursor;
	protected int near;
	protected int behind;
	protected int nowCursorPoint;
	protected static final int NEAR = 0;
	protected static final int NORMAL = 1;
	protected static final int BEHIND = 2;
	protected int nowCursorMode;
	private static TilemapFrameBorderBar lock;
	private boolean topMin,bottomMin,leftMin,rightMin;
	public TilemapFrameBorderBar(Window parent) {
		this.parent = parent;
		hoveringCursor = createHoveringCursor();
		nearCursor = createNearCursor();
		behindCursor = createBehindCursor();
		defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		addMouseListener(this);
		addMouseMotionListener(this);
		near = 10;
		behind = 90;
		isSupportedCursor = false;
		nowCursorMode = NORMAL;
		topMin = bottomMin = leftMin = rightMin = false;
	}
	public abstract Cursor createHoveringCursor();
	public Cursor createNearCursor() {
		return null;
	}
	public Cursor createBehindCursor() {
		return null;
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(ColorToolkit.frameBorder);
		g.fillRect(0,0,getWidth(),getHeight());
	}
	public static TilemapFrameBorderBar createLeftBorder(Window parent) {
		return new LeftBorder(parent);
	}
	public static TilemapFrameBorderBar createRightBorder(Window parent) {
		return new RightBorder(parent);
	}
	public static TilemapFrameBorderBar createTopBorder(Window parent) {
		return new TopBorder(parent);
	}
	public static TilemapFrameBorderBar createBottomBorder(Window parent) {
		return new BottomBorder(parent);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		if(lock != null && lock != this) return;
		if(isSupportedCursor) {
			if(nowCursorPoint <= near) {
				setCursor(nearCursor);
				nowCursorMode = NEAR;
			}else if(nowCursorPoint >= behind) {
				setCursor(behindCursor);
				nowCursorMode = BEHIND;
			}else {
				setCursor(hoveringCursor);
				nowCursorMode = NORMAL;
			}
		}else {
			setCursor(hoveringCursor);
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(defaultCursor);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			pressedPoint = e.getLocationOnScreen();
			pressedX = e.getX();
			pressedY = e.getY();
			lock = this;
		}
		else pressedPoint = null;
	}
	@Override
	public void mouseDragged(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseEntered(e);
	}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {
		lock = null;
	}
	
	protected int xdiff() {
		if(pressedPoint == null || draggedPoint == null)return 0;
		int px = pressedPoint.x;
		int dx = draggedPoint.x;
		return px - dx;
	}
	protected int ydiff() {
		if(pressedPoint == null || draggedPoint == null)return 0;
		int py = pressedPoint.y;
		int dy = draggedPoint.y;
		return py - dy;
	}
	
	protected Rectangle checkBounds(Rectangle bounds,boolean isLeft,boolean isTop) {
		if(parent instanceof JFrame && ((JFrame)parent).getRootPane() instanceof TilemapRootPane) {
			TilemapRootPane pane = (TilemapRootPane)((JFrame)parent).getRootPane();
			TilemapTitlePane titlePane = pane.getTitlePane();
			if(titlePane != null) {
				Dimension dim = titlePane.getMinimumSize();
				int w = dim.width;
				int h = dim.height;
				
				if(w >= bounds.width) {
					if(isLeft && parent.getX() < MouseInfo.getPointerInfo().getLocation().x) {
						leftMin = true;
					}else if(!isLeft && parent.getX() + parent.getWidth() > MouseInfo.getPointerInfo().getLocation().x) {
						rightMin = true;
					}
				}
				if(leftMin) {
					bounds.x = parent.getX();
					bounds.width = w;
					if(parent.getX() > MouseInfo.getPointerInfo().getLocation().x) {
						leftMin = false;
					}
				}else if(rightMin) {
					bounds.x = parent.getX();
					bounds.width = w;
					if(parent.getX() + parent.getWidth() < MouseInfo.getPointerInfo().getLocation().x) {
						rightMin = false;
					}
				}
				
				if(h >= bounds.height) {
					if(isTop && parent.getY() < MouseInfo.getPointerInfo().getLocation().y) {
						topMin = true;
					}else if (!isTop && parent.getY() + parent.getHeight() > MouseInfo.getPointerInfo().getLocation().y) {
						bottomMin = true;
					}
				}
				if(topMin) {
					bounds.y = parent.getY();
					bounds.height = h;
					if(parent.getY() > MouseInfo.getPointerInfo().getLocation().y) {
						topMin = false;
					}
				}else if(bottomMin) {
					bounds.y = parent.getY();
					bounds.height = h;
					if(parent.getY() + parent.getHeight() < MouseInfo.getPointerInfo().getLocation().y) {
						bottomMin = false;
					}
				}
			}
		}else if(parent instanceof JDialog && ((JDialog)parent).getRootPane() instanceof TilemapRootPane) {
			TilemapRootPane pane = (TilemapRootPane)((JDialog)parent).getRootPane();
			TilemapTitlePane titlePane = pane.getTitlePane();
			if(titlePane != null) {
				Dimension dim = titlePane.getMinimumSize();
				int w = dim.width;
				int h = dim.height;
				if(w >= bounds.width) {
					bounds.x = parent.getX();
					bounds.width = w;
					if(parent.getX() < MouseInfo.getPointerInfo().getLocation().x) {
						topMin = true;
					}else if (parent.getX() > MouseInfo.getPointerInfo().getLocation().x) {
						bottomMin = true;
					}
				}else {
					topMin = bottomMin = false;
				}
				if(h >= bounds.height) {
					bounds.y = parent.getY();
					bounds.height = h;
				}
			}
		}
		return bounds;
	}
	
	protected void moveToTop(MouseEvent e) {
		if(lock != this) return;
		draggedPoint = e.getLocationOnScreen();
		int diff = ydiff();
		parent.setBounds(checkBounds(
				new Rectangle(parent.getX(), topMin ? parent.getY() : draggedPoint.y, parent.getWidth(), parent.getHeight() + diff),
				false,true));
		pressedPoint = e.getLocationOnScreen();
	}
	protected void moveToBottom(MouseEvent e) {
		if(lock != this) return;
		draggedPoint = e.getLocationOnScreen();
		int diff = ydiff();
		parent.setBounds(checkBounds(
				new Rectangle(parent.getX(), parent.getY(), parent.getWidth(), parent.getHeight() - diff),
				false,false));
		pressedPoint = e.getLocationOnScreen();
	}
	protected void moveToLeft(MouseEvent e) {
		if(lock != this) return;
		draggedPoint = e.getLocationOnScreen();
		int diff = xdiff();
		parent.setBounds(checkBounds(
				new Rectangle(draggedPoint.x, parent.getY(), parent.getWidth() + diff, parent.getHeight()),
				true,false));
		pressedPoint = e.getLocationOnScreen();
	}
	protected void moveToRight(MouseEvent e) {
		if(lock != this) return;
		draggedPoint = e.getLocationOnScreen();
		int diff = xdiff();
		parent.setBounds(checkBounds(
				new Rectangle(parent.getX(), parent.getY(), parent.getWidth() - diff, parent.getHeight()),
				false,false));
		pressedPoint = e.getLocationOnScreen();
	}
	protected void moveToTopLeft(MouseEvent e) {
		if(lock != this) return;
		draggedPoint = e.getLocationOnScreen();
		int xdiff = xdiff();
		int ydiff = ydiff();
		parent.setBounds(checkBounds(
				new Rectangle(draggedPoint.x - pressedX, draggedPoint.y - pressedY, parent.getWidth() + xdiff, parent.getHeight() + ydiff),
				true,true));
		pressedPoint = e.getLocationOnScreen();
	}
	protected void moveToTopRight(MouseEvent e) {
		if(lock != this) return;
		draggedPoint = e.getLocationOnScreen();
		int xdiff = xdiff();
		int ydiff = ydiff();
		parent.setBounds(checkBounds(
				new Rectangle(parent.getX(), draggedPoint.y - pressedY, parent.getWidth() - xdiff,parent.getHeight() + ydiff),
				false,true));
		pressedPoint = e.getLocationOnScreen();
	}
	protected void moveToBottomLeft(MouseEvent e) {
		if(lock != this) return;
		draggedPoint = e.getLocationOnScreen();
		int xdiff = xdiff();
		int ydiff = ydiff();
		parent.setBounds(checkBounds(
				new Rectangle(draggedPoint.x - pressedX, parent.getY(), parent.getWidth() + xdiff, parent.getHeight() - ydiff),
				true,false));
		pressedPoint = e.getLocationOnScreen();
	}
	protected void moveToBottomRight(MouseEvent e) {
		if(lock != this) return;
		draggedPoint = e.getLocationOnScreen();
		int xdiff = xdiff();
		int ydiff = ydiff();
		parent.setBounds(checkBounds(
				new Rectangle(parent.getX(), parent.getY(), parent.getWidth() - xdiff, parent.getHeight() - ydiff),
				false,false));
		pressedPoint = e.getLocationOnScreen();
	}
	
	protected static class LeftBorder extends TilemapFrameBorderBar{
		public LeftBorder(Window parent) {
			super(parent);
			isSupportedCursor = true;
		}
		public Cursor createHoveringCursor() {
			return new Cursor(Cursor.W_RESIZE_CURSOR);
		}
		public Cursor createNearCursor() {
			return new Cursor(Cursor.NW_RESIZE_CURSOR);
		}
		public Cursor createBehindCursor() {
			return new Cursor(Cursor.SW_RESIZE_CURSOR);
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			nowCursorPoint = (int) (e.getPoint().getY() / getHeight() * 100);
			super.mouseMoved(e);
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if(nowCursorMode == NEAR)moveToTopLeft(e);
			else if(nowCursorMode == BEHIND)moveToBottomLeft(e);
			else moveToLeft(e);
		}
	}
	
	protected static class RightBorder extends TilemapFrameBorderBar {
		public RightBorder(Window parent) {
			super(parent);
			isSupportedCursor = true;
		}
		public Cursor createHoveringCursor() {
			return new Cursor(Cursor.E_RESIZE_CURSOR);
		}
		public Cursor createNearCursor() {
			return new Cursor(Cursor.NE_RESIZE_CURSOR);
		}
		public Cursor createBehindCursor() {
			return new Cursor(Cursor.SE_RESIZE_CURSOR);
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			nowCursorPoint = (int) (e.getPoint().getY() / getHeight() * 100);
			super.mouseMoved(e);
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if(nowCursorMode == NEAR)moveToTopRight(e);
			else if(nowCursorMode == BEHIND)moveToBottomRight(e);
			else moveToRight(e);
		}
	}
	
	protected static class TopBorder extends TilemapFrameBorderBar {
		public TopBorder(Window parent) {
			super(parent);
			isSupportedCursor = true;
		}
		public Cursor createHoveringCursor() {
			return new Cursor(Cursor.N_RESIZE_CURSOR);
		}
		public Cursor createNearCursor() {
			return new Cursor(Cursor.NW_RESIZE_CURSOR);
		}
		public Cursor createBehindCursor() {
			return new Cursor(Cursor.NE_RESIZE_CURSOR);
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			nowCursorPoint = (int) (e.getPoint().getX() / getWidth() * 100);
			super.mouseMoved(e);
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if(nowCursorMode == NEAR)moveToTopLeft(e);
			else if(nowCursorMode == BEHIND)moveToTopRight(e);
			else moveToTop(e);
		}
	}
	
	protected static class BottomBorder extends TilemapFrameBorderBar {
		public BottomBorder(Window parent) {
			super(parent);
			isSupportedCursor = true;
		}
		public Cursor createHoveringCursor() {
			return new Cursor(Cursor.S_RESIZE_CURSOR);
		}
		public Cursor createNearCursor() {
			return new Cursor(Cursor.SW_RESIZE_CURSOR);
		}
		public Cursor createBehindCursor() {
			return new Cursor(Cursor.SE_RESIZE_CURSOR);
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			nowCursorPoint = (int) (e.getPoint().getX() / getWidth() * 100);
			super.mouseMoved(e);
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if(nowCursorMode == NEAR)moveToBottomLeft(e);
			else if(nowCursorMode == BEHIND)moveToBottomRight(e);
			else moveToBottom(e);
		}
	}
}
