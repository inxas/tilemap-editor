package net.inxas.tilemap.gui.laf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.plaf.metal.MetalLookAndFeel;

import net.inxas.tilemap.gui.laf.TilemapUtils.Direction;

import static javax.swing.UIDefaults.LazyValue;

/**
 * タイルマップエディタ用のルックアンドフィールです。
 * @author inxas
 *
 */
public class TilemapLookAndFeel extends BasicLookAndFeel {

	private static final long serialVersionUID = -562015729575801767L;
	
	protected static final String[] USING_FONTS = {
			"Meiryo UI",
			""
	};
	
	public UIDefaults getDefaults() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		return super.getDefaults();
	}
	
	/**
	 * 
	 */
	protected void initClassDefaults(UIDefaults table) {
		super.initClassDefaults(table);
		final String tilemapPackageName = "net.inxas.tilemap.gui.laf.";
		Object[] uiDefaults = {
			"ButtonUI",tilemapPackageName + "TilemapButtonUI",
			"TitlePaneUI",tilemapPackageName + "TilemapTitlePaneUI",
			"FrameBorderUI",tilemapPackageName + "TilemapFrameBorderUI"
		};
		table.putDefaults(uiDefaults);
	}
	
	/**
	 * 
	 */
	protected void initComponentDefaults(UIDefaults table) {
		super.initComponentDefaults(table);
		
		LazyValue buttonBorder = t -> TilemapBorders.getButtonBorder();
		
		Object[] defaults = {
			"Button.border",buttonBorder
		};
		table.putDefaults(defaults);
	}

	/**
	 * 
	 */
	@Override
	public String getName() {
		return "Tilemap";
	}

	/**
	 * 
	 */
	@Override
	public String getID() {
		return "Tilemap";
	}

	/**
	 * 
	 */
	@Override
	public String getDescription() {
		return "The Tilemap Look And Feel";
	}

	/**
	 * 
	 */
	@Override
	public boolean isNativeLookAndFeel() {
		return false;
	}

	/**
	 * 
	 */
	@Override
	public boolean isSupportedLookAndFeel() {
		return true;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean getSupportsWindowDecorations() {
		return true;
	}
}
