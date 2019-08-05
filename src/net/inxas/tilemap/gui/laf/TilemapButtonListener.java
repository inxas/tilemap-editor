package net.inxas.tilemap.gui.laf;

import java.awt.event.FocusEvent;

import javax.swing.AbstractButton;
import javax.swing.plaf.basic.BasicButtonListener;

public class TilemapButtonListener extends BasicButtonListener {
	public TilemapButtonListener(AbstractButton b) {
		super(b);
	}
	
	public void focusGained(FocusEvent e) {
		((AbstractButton)e.getSource()).repaint();
	}
	public void focusLost(FocusEvent e) {
		((AbstractButton)e.getSource()).repaint();
	}
}
