package net.inxas.tilemap.gui.laf;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Window;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * タイルマップエディタ用のタイトルバーです。
 * @author inxas
 *
 */
public class TilemapTitlePaneUI extends TitlePaneUI {
	private static TitlePaneUI titlePaneUI;
	
	protected Color activeBackground;
	protected Color disableBackground;
	
	protected Color activeForeground;
	protected Color disableForeground;
	
	protected TilemapTitlePaneUI() {
		super();
		TilemapTheme theme = TilemapTheme.getTheme();
		activeBackground = theme.getColor("TitlePaneUI.background");
		disableBackground = theme.getColor("TitlePaneUI.background");
		activeForeground = theme.getColor("TitlePaneUI.foreground");
		disableForeground = theme.getColor("TitlePaneUI.disableForeground");
	}
	
	public void setActiveBackground(Color bg) {
		if(bg != null) activeBackground = bg;
	}
	
	public void setDisableBackground(Color bg) {
		if(bg != null) disableBackground = bg;
	}

	public static ComponentUI createUI(JComponent c) {
		if(titlePaneUI == null) {
			titlePaneUI = new TilemapTitlePaneUI();
		}
		return titlePaneUI;
	}
	
	////////////////////////////////////////////////////////////////////
	// install and uninstall
	////////////////////////////////////////////////////////////////////
	
	public void installUI(JComponent c) {
		TilemapTitlePane t = (TilemapTitlePane)c;
		super.installUI(t);
		installDefaults(t);
	}
	public void uninstallUI(JComponent c) {
		TilemapTitlePane t = (TilemapTitlePane)c;
		uninstallDefaults(t);
		super.uninstallUI(t);
	}
	
	protected void installDefaults(TilemapTitlePane t) {
		TilemapUtils.installColors(t, "TitlePaneUI.foreground", "TitlePaneUI.background");
	}
	protected void uninstallDefaults(TilemapTitlePane t) {
		
	}
	
	public int getBaseLine(JComponent c,int width,int height) {
		super.getBaseline(c, width, height);
		return -1;
	}
	
	////////////////////////////////////////////////////////////////////
	// paint methods
	////////////////////////////////////////////////////////////////////
	
	protected void paintBackground(Graphics g,TilemapTitlePane t) {
		g.setColor(t.getBackground());
		g.fillRect(0, 0, t.getWidth(), t.getHeight());
	}
	
	protected void paintString(Graphics g,TilemapTitlePane t) {
		Graphics2D g2d = (Graphics2D)g;
		g.setColor(TilemapTheme.getTheme().getColor("TitlePaneUI.foreground"));
		g.setFont(TilemapTheme.getTheme().getFont("TitlePaneUI.font"));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		FontRenderContext frc = new FontRenderContext(null, true, true);
		GlyphVector gv = g.getFont().createGlyphVector(frc, t.getTitle());
		Rectangle2D b = gv.getVisualBounds();
		Point2D p = new Point2D.Double(b.getX() + b.getWidth() / 2d, b.getY() + b.getHeight() / 2d);
		AffineTransform atf = AffineTransform.getTranslateInstance(t.getDrawableLeftEdge(), t.getHeight() / 2d - p.getY());
		Shape shape = atf.createTransformedShape(gv.getOutline());
		FontMetrics fm = g.getFontMetrics();
		g2d.fill(shape);
		if(t.getDrawableWidth() < fm.stringWidth(t.getTitle())) {
			int edge = t.getDrawableLeftEdge() + t.getDrawableWidth();
			g2d.setColor(t.getBackground());
			g2d.fillRect(edge, 0, t.getWidth(), t.getHeight());
			Color bg = t.getBackground();
			Color[] fadeOut = ColorToolkit.gradation(bg,new Color(bg.getRed(),bg.getGreen(),bg.getBlue(),0), 20);
			for(int i = 0,l = fadeOut.length;i < l;i++) {
				g.setColor(fadeOut[i]);
				g.drawLine(edge - i, 0, edge - i, t.getHeight());
			}
		}
	}
	
	public void paint(Graphics g,JComponent c) {
		TilemapTitlePane titlePane = (TilemapTitlePane)c;
		Color oldColor = g.getColor();
		Font oldFont = g.getFont();
		
		Window window = TilemapUtils.getWindow(titlePane);
		boolean isUndecorated = false;
		if(window instanceof Frame) isUndecorated = ((Frame)window).isUndecorated();
		else if(window instanceof Dialog) isUndecorated = ((Dialog)window).isUndecorated();
		
		if(isUndecorated) {
			paintBackground(g,titlePane);
			String title = titlePane.getTitle();
			if(title != null && !title.equals(""))paintString(g,titlePane);
		}
		
		g.setColor(oldColor);
		g.setFont(oldFont);
	}
}
