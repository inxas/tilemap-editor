package net.inxas.tilemap.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import net.inxas.tilemap.Editor;
import net.inxas.tilemap.gui.laf.TilemapRootPane;

/**
 * エディタのウィンドウです。
 * @author inxas
 * @since 2019/07/16
 * @version 0.0-alpha
 */
public final class Frame extends JFrame {
	
	/**
	 * このクラスのインスタンスはただ一つであるべきなので、コンストラクタはプライベートです。
	 * インスタンスの取得は{@link #getInstance()}を用います。
	 */
	private Frame() {
		super("Tilemap Editor v0.0-alpha");
				
		setBounds(200,200,200,200);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	protected JRootPane createRootPane() {
		TilemapRootPane rootPane = new TilemapRootPane(this);
		rootPane.setOpaque(true);
		return rootPane;
	}
	
	/**
	 * {@link Frame}の唯一のインスタンスを保持します。
	 * @author inxas
	 * @since 2019/07/16
	 * @version 0.0-alpha
	 */
	private static final class InstanceHolder{
		/**
		 * Frameの唯一のインスタンス
		 */
		public static final Frame INSTANCE = new Frame();
	}
	/**
	 * このクラスの唯一のインスタンスを取得します。
	 */
	public static Frame getInstance() {
		return InstanceHolder.INSTANCE;
	}
	private final class Menu extends JMenu {
		Menu(String title,MenuItem... items){
			super(title);
			for(MenuItem item : items) {
				add(item);
			}
		}
	}
	private final class MenuItem extends JMenuItem {
		MenuItem(String title,ActionListener listener){
			super();
			addActionListener(listener);
		}
	}
}
