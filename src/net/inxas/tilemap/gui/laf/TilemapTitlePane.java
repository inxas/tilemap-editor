package net.inxas.tilemap.gui.laf;

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
import javax.swing.UIManager;

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
	
	public static final int DEFAULT_TITLE_PANE_HEIGHT = 25;
	
	private TilemapTitleIconLabel titleIconLabel;
	
	private Icon titleIcon;
	
	private TilemapTitleButton iconifyButton;
	private TilemapTitleButton maxButton;
	private TilemapTitleButton closeButton;
	
	private Action iconifyAction;
	private Action maxAction;
	private Action closeAction;
	
	private Icon iconifyIcon;
	private Icon maxIcon;
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
	
	protected TilemapRootPane rootPane;
	public TilemapTitlePane(TilemapRootPane rootPane) {
		super();
		this.rootPane = rootPane;
		window = rootPane.getParent();
		init();
	}
	
	protected void init() {
		createIcons();
		createActions();
		createButtons();
		
		setLayout(createLayout());
		
		add(iconifyButton,TitlePaneLayout.ICONIFY);
		add(maxButton,TitlePaneLayout.MAX);
		add(closeButton,TitlePaneLayout.CLOSE);
		
		add(titleIconLabel,TitlePaneLayout.ICON);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		updateUI();
	}
	
	protected void createIcons() {
		titleIcon = TilemapIcons.FRAME;
		titleIconLabel = new TilemapTitleIconLabel(this);
		
		iconifyIcon = TilemapIcons.ICONIFY_NORMAL;
		maxIcon = TilemapIcons.MAX_NORMAL;
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
	
	protected void createActions() {
		iconifyAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(window instanceof Frame && window != null) {
					((Frame)window).setExtendedState(Frame.ICONIFIED);
				}
			}
		};
		maxAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(window instanceof Frame && window != null) {
					Frame frame = (Frame)window;
					GraphicsConfiguration gc = frame.getGraphicsConfiguration();
					Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
					Rectangle max = gc.getBounds();
					max.x = 0 < insets.left ? insets.left : 0;
					max.y = 0 < insets.top ? insets.top : 0;
					max.width -= insets.left + insets.right;
					max.height -= insets.top + insets.bottom;
					frame.setMaximizedBounds(max);
					frame.setExtendedState(Frame.MAXIMIZED_BOTH);
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

	protected void createButtons() {
		iconifyButton = new TilemapTitleButton(
				iconifyIcon,
				iconifyHoverIcon,
				iconifyPressIcon,
				iconifyDisableIcon,
				iconifyAction);
		maxButton = new TilemapTitleButton(
				maxIcon,
				maxHoverIcon,
				maxPressIcon,
				maxDisableIcon,
				maxAction);
		closeButton = new TilemapTitleButton(
				closeIcon,
				closeHoverIcon,
				closePressIcon,
				closeDisableIcon,
				closeAction);
	}
	
	protected LayoutManager createLayout() {
		return new TitlePaneLayout(rootPane,10,10);
	}
	
	public void updateUI() {
		setUI((TitlePaneUI)UIManager.getUI(this));
	}
	public TitlePaneUI getUI() {
		return (TitlePaneUI)ui;
	}
	public void setUI(TitlePaneUI ui) {
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
	
	public JRootPane getRootPane() {
		return rootPane;
	}
	
	public Icon getTitleIcon() {
		return titleIcon;
	}
	
	public String getTitle() {
		Window window = TilemapUtils.getWindow(rootPane);
		if(window instanceof Frame) return ((Frame)window).getTitle();
		if(window instanceof Dialog) return ((Dialog)window).getTitle();
		else return null;
	}
	
	public int getDrawableWidth() {
		return getWidth() - getMinimumSize().width;
	}
	public int getDrawableLeftEdge() {
		if(titleIconLabel != null) {
			return titleIconLabel.getWidth();
		}
		return 0;
	}
	
	protected class TitlePaneLayout implements LayoutManager2 {
		public static final String ICON = "iconLabel";
		private TilemapTitleIconLabel iconLabel;
		
		public static final String ICONIFY = "iconify";
		public static final String MAX = "max";
		public static final String CLOSE = "close";
		private TilemapTitleButton iconify;
		private TilemapTitleButton max;
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
			if(maxButton != null)width += maxButton.getWidth();
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
			if(max != null) {
				Dimension pre = max.getPreferredSize();
				int x = rightPoint - pre.width;
				max.setBounds(x, 0, pre.width, pre.height);
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
			}else if(constraints == MAX && comp instanceof TilemapTitleButton) {
				max = (TilemapTitleButton)comp;
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

	
	protected Point pressedPoint;
	@Override
	public void mouseDragged(MouseEvent e) {
		if(pressedPoint == null) return;
		Point screenPoint = e.getLocationOnScreen();
		int x = screenPoint.x - pressedPoint.x;
		int y = screenPoint.y - pressedPoint.y;
		window.setLocation(x, y);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		pressedPoint = e.getPoint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		pressedPoint = null;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
