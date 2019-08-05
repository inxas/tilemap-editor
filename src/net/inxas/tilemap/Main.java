package net.inxas.tilemap;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.inxas.tilemap.gui.Frame;

/**
 * エディタの実行クラスです。
 * @author inxas
 * @since 2019/07/15
 * @version 0.0-alpha
 */
public final class Main {
	public static void main(String[] args) {
		new Main();
	}
	private Main() {
		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel("net.inxas.tilemap.gui.laf.TilemapLookAndFeel");
			}catch(Exception e) {
				e.printStackTrace();
			}
			Editor.getInstance();
			Frame.getInstance().setVisible(true);
		});
	}
}