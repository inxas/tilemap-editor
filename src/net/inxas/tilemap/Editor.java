package net.inxas.tilemap;

/**
 * エディタの中枢機能を有します。
 * @author inxas
 * @since 2019/07/15
 * @version 0.0-alpha
 */
public final class Editor {
	public static final String VERSION = "tilemap-editor v0.0-alpha last-update:2019/07/19 author:inxas";
	/**
	 * このクラスのインスタンスはただ一つであるべきなので、コンストラクタはプライベートです。
	 * インスタンスの取得は{@link #getInstance()}を用います。
	 */
	private Editor() {}
	/**
	 * {@link Editor}の唯一のインスタンスを保持します。
	 * @author inxas
	 * @since 2019/07/15
	 * @version 0.0-alpha
	 */
	private static final class InstanceHolder {
		/**
		 * Editorクラスの唯一のインスタンスです。
		 */
		public static final Editor INSTANCE = new Editor();
	}
	/**
	 * このクラスの唯一のインスタンスを取得します。
	 */
	public static Editor getInstance() {
		return InstanceHolder.INSTANCE;
	}
	
}
