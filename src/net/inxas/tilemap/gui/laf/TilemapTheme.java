package net.inxas.tilemap.gui.laf;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.plaf.ColorUIResource;

/**
 * このルックアンドフィールで使用するカラーリングや、フォントなどのテーマをまとめます。
 * @author inxas
 * @version 0.0-alpha
 */
public abstract class TilemapTheme {
    /** 現在のテーマ */
    protected static TilemapTheme tilemapTheme;
    
    /** テーマのマップ */
    protected HashMap<Object,Object> themes;
    /**
     * コンストラクタ
     */
    protected TilemapTheme() {
        themes = new HashMap<>();
        installColorTheme();
        installFontTheme();
    }

    /**
     * {@link #tilemapTheme}を返します。もしnullならばデフォルトテーマを返します。
     * @return tilemapTheme
     */
    public static TilemapTheme getTheme() {
        if(tilemapTheme == null) {
            tilemapTheme = new DefaultTheme();
        }
        return tilemapTheme;
    }

    /**
     * テーマをマップにプットします。
     * @param keyValueList テーマのリスト
     */
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

    /**
     * 色に関するテーマをインストールします。
     */
    protected abstract void installColorTheme();
    /**
     * フォントに関するテーマをインストールします。
     */
    protected abstract void installFontTheme();

    /**
     * キーに対する値を返します。
     * @param key キー
     * @return キーに対する値
     */
    public Object get(Object key) {
        return themes.get(key);
    }

    /**
     * キーに対する色を返します。
     * @param key キー
     * @return キーに対する色 もしなければnull
     */
    public Color getColor(Object key) {
        Object value = get(key);
        return (value instanceof Color) ? (Color)value : null;
    }
    /**
     * キーに対するフォントを返します。
     * @param key キー
     * @return キーに対するフォント もしなければnull
     */
    public Font getFont(Object key) {
        Object value = get(key);
        return (value instanceof Font) ? (Font)value : null;
    }

    /**
     * デフォルトのテーマです。ほかのテーマはこのクラスを拡張すべきです。
     * @author inxas
     * @version 0.0-alpha
     */
    public static class DefaultTheme extends TilemapTheme {
        protected void installColorTheme() {
            ColorUIResource editorBackground = new ColorUIResource(192,192,192);
            ColorUIResource titlebarBackground = new ColorUIResource(128,128,128);
            Object[] table = {
                    // buttonUI theme
                    "ButtonUI.background",new ColorUIResource(150,150,150),
                    
                    // textField theme
                    "TextFieldUI.background",new ColorUIResource(235,235,235),
                    "TextFieldUI.hintColor",new ColorUIResource(150,150,150),
                    
                    // frame theme
                    "FrameUI.borderColor",ColorToolkit.frameBorder,
                    
                    // editorPane theme
                    "EditorPaneUI.background",editorBackground,
                    
                    // makerPane theme
                    "MakerPaneUI.background",new ColorUIResource(64,64,64),
                    "MakerTabBarUI.background",new ColorUIResource(32,32,32),
                    "MakerTabBarUI.selectedBackground",new ColorUIResource(128,128,128),
                    
                    // menuPane theme
                    "MenuPaneUI.background",new ColorUIResource(112,112,112),
                    "MenuPaneUI.borderColor",new ColorUIResource(96,96,96),
                    "MenuIconUI.iconColor",new ColorUIResource(220,220,220),
                    "MenuIconUI.hoverIconColor",new ColorUIResource(255,255,255),
                    "MenuContainerUI.borderColor",new ColorUIResource(96,96,96),
                    "MenuContainerUI.background",new ColorUIResource(144,144,144),
                    "MenuContainerUI.pullDownBackground",new ColorUIResource(64,64,64),
                    "MenuContainerUI.pullDownForeground",new ColorUIResource(255,255,255),
                    
                    // titlePane theme
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
                    // titlePane theme
                    "TitlePaneUI.font",FontToolkit.getTitlebarFont(),
                    
                    // menuPene theme
                    "MenuContainerUI.pullDownFont",FontToolkit.getDefaultFontUIResource(18),
                    "MenuContainerUI.contentsFont",FontToolkit.getDefaultFontUIResource(14),
            };
            putThemes(table);
        }
    }
    /**
     * 明るいテーマです。試作段階なのでまともに使用できません。
     * @author inxas
     * @version 0.0-alpha
     */
    public static class LightTheme extends DefaultTheme {
        protected void installColorTheme() {
            super.installColorTheme();
            ColorUIResource editorBackground = new ColorUIResource(240,240,240);
            ColorUIResource titlebarBackground = new ColorUIResource(255,255,255);
            Object[] table = {
                    "FrameUI.borderColor",new ColorUIResource(220,220,220),
                    "EditorPaneUI.background",editorBackground,
                    "TitlePaneUI.background",titlebarBackground,
                    "TitlePaneUI.foreground",ColorToolkit.text,
                    "TitlePaneUI.disableForeground",ColorToolkit.disableText,
                    "TitlePaneUI.buttonIconColor",new ColorUIResource(156,156,156),
                    "TitlePaneUI.buttonBackground",titlebarBackground,
                    "TitlePaneUI.buttonDisableBackground",new ColorUIResource(200,200,200),
                    "TitlePaneUI.buttonHoveringBackground",new ColorUIResource(220,220,220),
                    "TitlePaneUI.buttonPressingBackground",new ColorUIResource(192,192,192),
                    "TitlePaneUI.buttonBorderColor",new ColorUIResource(156,156,156)
            };
            putThemes(table);
        }
    }
}
