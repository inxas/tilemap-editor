package net.inxas.tilemap;

public enum PropertyType {
    /** そのプロパティが透明度情報を持たない色を表していることを示します。 */
    COLOR,
    /** そのプロパティが透明度情報を持つ色を表していることを示します。 */
    COLOR_WITH_ALPHA,
    /** そのプロパティがパスを表すことを示します。 */
    PATH;
}
