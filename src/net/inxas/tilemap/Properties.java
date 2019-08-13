package net.inxas.tilemap;

/**
 * 設定情報の列挙です。
 * @author inxas
 * @since 2019/07/15
 * @version 0.0-alpha
 */
public enum Properties {
    /** エディタの背景色です。 */
    EDITOR_BACKGROUND("#ffffff",PropertyType.COLOR);
    /** このプロパティの値です。 */
    private String value;
    /** このプロパティのタイプを示します。 */
    private final PropertyType TYPE;
    /**
     * コンストラクタです。
     * @param value プロパティの値
     */
    private Properties(String value,PropertyType type) {
        set(value);
        TYPE = type;
    }
    /**
     * プロパティの値を設定します。
     * @param value プロパティの値
     */
    public void set(String value) {
        this.value = value;
    }
    /**
     * プロパティの値を取得します。
     * @return プロパティの値
     */
    public String get() {
        return value;
    }
    /**
     * このプロパティのタイプを取得します。
     * @return このプロパティのタイプ
     */
    public PropertyType getType() {
        return TYPE;
    }
    /**
     * 適切な型に変換した値を取得します。
     * @return プロパティの値
     */
    public Object getConvertedTypeValue() {
        Object result = null;
        return result;
    }
}
