package net.inxas.tilemap.gui.laf;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.metal.MetalButtonUI;

public class TilemapButtonUI extends BasicButtonUI {
	private static final String propertyPrefix = "Button.";
	
	public static ComponentUI createUI(JComponent c) {
		return new TilemapButtonUI();
	}
	
	public void installDefaults(AbstractButton b) {
		super.installDefaults(b);
		b.setOpaque(false);
		b.setRolloverEnabled(true);
	}
	
	protected BasicButtonListener createButtonListener(AbstractButton b) {
		return new TilemapButtonListener(b);
	}
	
//	protected void paintBackground(Graphics g,AbstractButton b) {
//		
//	}
	
	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
	}
}
