package net.inxas.tilemap.gui.laf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicBorders;

public class TilemapBorders {
	private static Border buttonBorder;
	public static Border getButtonBorder() {
		if(buttonBorder == null) {
			buttonBorder = new BorderUIResource.CompoundBorderUIResource(
					new TilemapBorders.ButtonBorder(),
					new BasicBorders.MarginBorder());
		}
        return buttonBorder;
	}
	
	public static class ButtonBorder extends AbstractBorder implements UIResource {
		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 3485235066876189462L;
		
		protected static Insets borderInsets = new Insets(3,3,3,3);
		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
			if (!(c instanceof AbstractButton)) return;
			
			AbstractButton button = (AbstractButton)c;
			ButtonModel model = button.getModel();
			
			if(model.isEnabled()) {
				boolean isPressed = model.isPressed() && model.isArmed();
				boolean isDefault = (button instanceof JButton && ((JButton)button).isDefaultButton());
				if (isPressed && isDefault) {
					
				}else if(isPressed) {
					TilemapUtils.drawPressedButtonBorder(g, x, y, w, h);
				}else if(isDefault) {
					
				}else {
					TilemapUtils.drawButtonBorder(g,x,y,w,h);
				}
			}else {
				
			}
		}
	}
}
