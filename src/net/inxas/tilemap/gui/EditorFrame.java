package net.inxas.tilemap.gui;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import net.inxas.tilemap.gui.laf.TilemapIcons;

/**
 * エディタのウィンドウです。
 * @author inxas
 * @since 2019/07/16
 * @version 0.0-alpha
 */
public final class EditorFrame extends JFrame {
    /**
     * このウィンドウが保持するエディターペイン
     */
    private TilemapEditorPane editorPane;

    /**
     * このクラスのインスタンスはただ一つであるべきなので、コンストラクタはプライベートです。
     * インスタンスの取得は{@link #getInstance()}を用います。
     */
    private EditorFrame() {
        super("Tilemap Editor v0.0-alpha");

        setBounds(200,200,1000,700);
        setContentPane(new TilemapEditorPane());
        setIconImage(TilemapIcons.FRAME.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * {@link TilemapRootPane}を作成し、返します。
     */
    @Override
    protected JRootPane createRootPane() {
        TilemapRootPane rootPane = new TilemapRootPane(this);
        rootPane.setOpaque(true);
        return rootPane;
    }

    /**
     * {@link TilemapEditorPane}か、その子孫クラスのみコンテントペインに設定できます。
     */
    @Override
    public void setContentPane(Container comp) {
        if(comp instanceof TilemapEditorPane) {
            super.setContentPane(comp);
            editorPane = (TilemapEditorPane)comp;
        }
    }

    /**
     * このクラスが保持するエディターペインを返します。
     * これは実質{@link #getContentPane()}と同じ結果になります。
     * @return エディターペイン
     */
    public TilemapEditorPane getEditorPane() {
        return editorPane;
    }

    /**
     * {@link EditorFrame}の唯一のインスタンスを保持します。
     * @author inxas
     * @since 2019/07/16
     * @version 0.0-alpha
     */
    private static final class InstanceHolder{
        /**
         * Frameの唯一のインスタンス
         */
        public static final EditorFrame INSTANCE = new EditorFrame();
    }
    /**
     * このクラスの唯一のインスタンスを取得します。
     */
    public static EditorFrame getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
