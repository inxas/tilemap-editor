package net.inxas.tilemap.gui.laf;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

public abstract class TilemapTheme {
	protected static TilemapTheme tilemapTheme;
	protected HashMap<Object,Object> themes;
	protected TilemapTheme() {
		themes = new HashMap<>();
		installColorTheme();
		installFontTheme();
	}
	
	public static TilemapTheme getTheme() {
		if(tilemapTheme == null) {
			tilemapTheme = new DefaultTheme();
		}
		return tilemapTheme;
	}
	
	protected void putThemes(Object[] keyValueList) {
		for(int i = 0, l = keyValueList.length;i < l;i += 2) {
			Object key = keyValueList[i];
			Object value = keyValueList[i + 1];
			if(value == null) {
				themes.remove(key);
			}else {
				themes.put(key, value);
			}
		}
	}
	
	protected abstract void installColorTheme();
	protected abstract void installFontTheme();
	
	public Object get(Object key) {
		return themes.get(key);
	}
	
	public Color getColor(Object key) {
		Object value = get(key);
		return (value instanceof Color) ? (Color)value : null;
	}
	public Font getFont(Object key) {
		Object value = get(key);
		return (value instanceof Font) ? (Font)value : null;
	}
	
	public static class DefaultTheme extends TilemapTheme {
		protected void installColorTheme() {
			ColorUIResource titlebarBackground = new ColorUIResource(128,128,128);
			Object[] table = {
				"TitlePaneUI.background",titlebarBackground,
				"TitlePaneUI.foreground",ColorToolkit.text,
				"TitlePaneUI.disableForeground",ColorToolkit.disableText,
				"TitlePaneUI.buttonIconColor",new ColorUIResource(220,220,220),
				"TitlePaneUI.buttonBackground",titlebarBackground,
				"TitlePaneUI.buttonDisableBackground",new ColorUIResource(200,200,200),
				"TitlePaneUI.buttonHoveringBackground",new ColorUIResource(96,96,96),
				"TitlePaneUI.buttonPressingBackground",new ColorUIResource(80,80,80),
				"TitlePaneUI.buttonBorderColor",new ColorUIResource(112,112,112)
			};
			putThemes(table);
		}
		protected void installFontTheme() {
			Object[] table = {
				"TitlePaneUI.font",FontToolkit.getTitlebarFont()
			};
			putThemes(table);
		}
	}
}
