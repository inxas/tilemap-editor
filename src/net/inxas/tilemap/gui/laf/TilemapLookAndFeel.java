package net.inxas.tilemap.gui.laf;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.text.DefaultEditorKit;

/**
 * タイルマップエディタ用のルックアンドフィールです。
 * @author inxas
 * @version 0.0-alpha
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
     * UIクラスを登録します。
     * 基本的に、
     * <code>"xxxUI","net.inxas.tilemap.gui.laf.TilemapxxxUI"</code>
     * の形をとります。
     */
    protected void initClassDefaults(UIDefaults table) {
        super.initClassDefaults(table);
        final String tilemapPackageName = "net.inxas.tilemap.gui.laf.";
        Object[] uiDefaults = {
                "ButtonUI",tilemapPackageName + "TilemapButtonUI",
                "TitlePaneUI",tilemapPackageName + "TilemapTitlePaneUI",
                "FrameBorderUI",tilemapPackageName + "TilemapFrameBorderUI",
                "EditorPaneUI",tilemapPackageName + "TilemapEditorPaneUI",
                "ToolUI",tilemapPackageName + "TilemapToolUI",
                "ToolPaneUI",tilemapPackageName + "TilemapToolPaneUI",
                "ToolContainerUI",tilemapPackageName + "TilemapToolContainerUI",
                "MakerPaneUI",tilemapPackageName + "TilemapMakerPaneUI",
                "MakerTabBarUI",tilemapPackageName + "TilemapMakerTabBarUI",
                "MenuPaneUI",tilemapPackageName + "TilemapMenuPaneUI",
                "MenuContainerUI",tilemapPackageName + "TilemapMenuContainerUI",
                "MenuIconUI",tilemapPackageName + "TilemapMenuIconUI"
        };
        table.putDefaults(uiDefaults);
    }

    /**
     * TilemapLookAndFeelのデフォルト機能を入力します。
     */
    protected void initComponentDefaults(UIDefaults table) {
        super.initComponentDefaults(table);

        Object fieldInputMap = new UIDefaults.LazyInputMap(new Object[] {
                "ctrl C", DefaultEditorKit.copyAction,
                "ctrl V", DefaultEditorKit.pasteAction,
                "ctrl X", DefaultEditorKit.cutAction,
                "COPY", DefaultEditorKit.copyAction,
                "PASTE", DefaultEditorKit.pasteAction,
                "CUT", DefaultEditorKit.cutAction,
                "control INSERT", DefaultEditorKit.copyAction,
                "shift INSERT", DefaultEditorKit.pasteAction,
                "shift DELETE", DefaultEditorKit.cutAction,
                "shift LEFT", DefaultEditorKit.selectionBackwardAction,
                "shift KP_LEFT", DefaultEditorKit.selectionBackwardAction,
                "shift RIGHT", DefaultEditorKit.selectionForwardAction,
                "shift KP_RIGHT", DefaultEditorKit.selectionForwardAction,
                "ctrl LEFT", DefaultEditorKit.previousWordAction,
                "ctrl KP_LEFT", DefaultEditorKit.previousWordAction,
                "ctrl RIGHT", DefaultEditorKit.nextWordAction,
                "ctrl KP_RIGHT", DefaultEditorKit.nextWordAction,
                "ctrl shift LEFT", DefaultEditorKit.selectionPreviousWordAction,
                "ctrl shift KP_LEFT", DefaultEditorKit.selectionPreviousWordAction,
                "ctrl shift RIGHT", DefaultEditorKit.selectionNextWordAction,
                "ctrl shift KP_RIGHT", DefaultEditorKit.selectionNextWordAction,
                "ctrl A", DefaultEditorKit.selectAllAction,
                "HOME", DefaultEditorKit.beginLineAction,
                "END", DefaultEditorKit.endLineAction,
                "shift HOME", DefaultEditorKit.selectionBeginLineAction,
                "shift END", DefaultEditorKit.selectionEndLineAction,
                "BACK_SPACE", DefaultEditorKit.deletePrevCharAction,
                "shift BACK_SPACE", DefaultEditorKit.deletePrevCharAction,
                "ctrl H", DefaultEditorKit.deletePrevCharAction,
                "DELETE", DefaultEditorKit.deleteNextCharAction,
                "ctrl DELETE", DefaultEditorKit.deleteNextWordAction,
                "ctrl BACK_SPACE", DefaultEditorKit.deletePrevWordAction,
                "RIGHT", DefaultEditorKit.forwardAction,
                "LEFT", DefaultEditorKit.backwardAction,
                "KP_RIGHT", DefaultEditorKit.forwardAction,
                "KP_LEFT", DefaultEditorKit.backwardAction,
                "ENTER", JTextField.notifyAction,
                "ctrl BACK_SLASH", "unselect",
                "control shift O", "toggle-componentOrientation"
        });

        Object[] defaults = {

                "TextField.focusInputMap", fieldInputMap
        };
        table.putDefaults(defaults);
    }

    /**
     * @return "Tilemap"
     */
    @Override
    public String getName() {
        return "Tilemap";
    }

    /**
     * @return "Tilemap"
     */
    @Override
    public String getID() {
        return "Tilemap";
    }

    /**
     * @return "The Tilemap Look And Feel"
     */
    @Override
    public String getDescription() {
        return "The Tilemap Look And Feel";
    }

    /**
     * @return false
     */
    @Override
    public boolean isNativeLookAndFeel() {
        return false;
    }

    /**
     * @return true
     */
    @Override
    public boolean isSupportedLookAndFeel() {
        return true;
    }

    /**
     * @return true
     */
    @Override
    public boolean getSupportsWindowDecorations() {
        return true;
    }
}
