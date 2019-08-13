package net.inxas.tilemap.gui.laf;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.plaf.FontUIResource;

/**
 * フォントに関する様々な機能があります。
 * @author inxas
 * @since 2019/07/27
 * @version 0.0-alpha
 */
public class FontToolkit {
    /**
     * このタイルマップエディタが使用するデフォルトのフォントの列挙。優先順位が高い順になっています。
     */
    public static final String[] DEFAULT_FONTS = {
            "游ゴシック体", "YuGothic",
            "Yu Gothic M",
            "游ゴシック Medium", "Yu Gothic Medium",
            "ヒラギノ角ゴ ProN W3", "Hiragino Kaku Gothic ProN W3", "HiraKakuProN-W3",
            "ヒラギノ角ゴ ProN", "Hiragino Kaku Gothic ProN",
            "ヒラギノ角ゴ Pro", "Hiragino Kaku Gothic Pro",
            "メイリオ", "Meiryo",
            "Osaka",
            "ＭＳ Ｐゴシック", "MS PGothic",
            "Helvetica Neue", "HelveticaNeue",
            "Helvetica",
            "Arial",
            "Segoe UI",
    };
    /**
     * ウィンドウのタイトルバーに使用するデフォルトのフォントの列挙。
     */
    public static final String[] DEFAULT_TITLEBAR_FONTS = {
            "MS UI Gothic",
            "游ゴシック Medium",
            "メイリオ",
            "ヒラギノ角ゴ",
            "San Francisco",
            "Kochi Gothic",
            "Nachlieli",
            "Meiryo UI"
    };

    private static FontUIResource customFont;
    private static FontUIResource customTitlebarFont;

    /**
     * {@link #DEFAULT_FONTS}を基にデフォルトのフォントを返します。
     * @param fontSize フォントの大きさ
     * @return デフォルトのフォント
     */
    public static FontUIResource getDefaultFontUIResource(int fontSize) {
        return new FontUIResource(enabledFont(DEFAULT_FONTS, Font.PLAIN, fontSize));
    }

    /**
     * {@link #DEFAULT_FONTS}を基にデフォルトのフォントを返します。
     * フォントサイズは24です。
     * @return デフォルトのフォント
     */
    public static FontUIResource getDefaultFontUIResource() {
        return new FontUIResource(enabledFont(DEFAULT_FONTS, Font.PLAIN, 24));
    }

    /**
     * {@link #DEFAULT_TITLEBAR_FONTS}を基に、デフォルトのタイトルバーフォントを返します。
     * @return デフォルトのタイトルバーフォント
     */
    public static FontUIResource getDefaultTitlebarFontUIResource() {
        return new FontUIResource(enabledFont(DEFAULT_TITLEBAR_FONTS, Font.PLAIN, 16));
    }

    /**
     * カスタムフォントを設定します。
     * @param font 新しいカスタムフォント
     */
    public static void setFont(Font font) {
        if(font != null)customFont = new FontUIResource(font);
    }
    /**
     * もしカスタムフォントがnullならばデフォルトフォントを返し、そうでなければカスタムフォントを返します。
     * @return フォント
     */
    public static FontUIResource getFont() {
        return customFont != null ? customFont : getDefaultFontUIResource();
    }
    /**
     * カスタムフォントをリセットします。
     */
    public static void releaseFont() {
        customFont = null;
    }

    /**
     * タイトルバーフォントを設定します。
     * @param font タイトルバーフォント
     */
    public static void setTitlebarFont(Font font) {
        if(font != null)customTitlebarFont = new FontUIResource(font);
    }
    /**
     * もしカスタムタイトルバーフォントがnullならばデフォルトタイトルバーフォントを返し、
     * そうでなければカスタムタイトルバーフォントを返します。
     * @return タイトルバーフォント
     */
    public static FontUIResource getTitlebarFont() {
        return customTitlebarFont != null ? customTitlebarFont : getDefaultTitlebarFontUIResource();
    }
    /**
     * カスタムタイトルバーフォントをリセットします。
     */
    public static void releaseTitlebarFont() {
        customTitlebarFont = null;
    }

    /**
     * 現在の環境で使用可能なフォント一覧です。
     * @return 使用可能なフォント一覧
     */
    static Font[] availableFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return ge.getAllFonts();
    }
    /**
     * 使用したいフォントを順に列挙して最初に見つけた使用可能なフォントを返します。
     * これは"css"の"font-family:"のような結果です。
     * 例えば<br>
     * <code>
     * String[] usingFont = {"Meiryo UI","Arial","GEORGIA","Hiragino Sans"};<br>
     * enebledFont(usingFont,Font.PLAIN,24);
     * </code>
     * <br>とすると、まず、"Meiryo UI"が環境に存在するか調べ、
     * もし"Meiryo UI"が環境に存在すれば"Meiryo UI"を返し、それがなければ
     * "Arial"が環境に存在するか調べ.....と繰り返します。
     * もしすべて存在しなければnullを返します。
     * このメソッドの判断基準はフォント名ではなくフォントファミリーのため、
     * 例えばロケールが日本の場合、"Meiryo"は"メイリオ"に置き換わっていますが、
     * フォントファミリーは同一なので、<br>
     * <code>enabledFont(new String[]{"Meiryo","Arial"},Font.PLAIN,24)</code>
     * <br>は"メイリオ"を返します。
     * @param fonts 使用したいフォントの列挙
     * @param fontStyle 使用したいフォントのスタイル
     * @param fontSize 使用したいフォントの大きさ
     * @return 最初に見つけた使用可能なフォント 存在しなければnull
     */
    static Font enabledFont(String[] fonts,int fontStyle,int fontSize) {
        List<String> sortedAvailableFonts =
                Arrays.asList(availableFonts())
                .stream()
                .sorted((f1,f2) -> f1.getFamily().compareTo(f2.getFontName()))
                .map(f -> f.getFontName())
                .collect(Collectors.toList());

        for(String font : fonts) {
            if(font == null || font.equals("")) {
                continue;
            }
            final String want = new Font(font,0,0).getFamily();
            int low = 0;
            int high = sortedAvailableFonts.size();
            int initial = 0;
            char fontInitial;
            char availableInitial;
            while(low <= high) {
                int mid = low + (high - low) / 2;
                String get = sortedAvailableFonts.get(mid);
                if(want.equals(get)) {
                    return new Font(get,fontStyle,fontSize);
                }else {
                    if(get.length() <= initial) {
                        low = mid + 1;
                    }else if(want.length() - 1 <= initial) {
                        break;
                    }else {
                        fontInitial = want.charAt(initial);
                        availableInitial = get.charAt(initial);
                        if(fontInitial < availableInitial) {
                            high = mid - 1;
                        }else if(fontInitial > availableInitial) {
                            low = mid + 1;
                        }else if(want.length() > get.length()){
                            initial++;
                        }else {
                            List<String> localList =
                                    sortedAvailableFonts.stream()
                                    .filter(s -> s.startsWith(want))
                                    .collect(Collectors.toList());
                            for(String localFont : localList) {
                                if(want.equals(localFont)) {
                                    return new Font(localFont,fontStyle,fontSize);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        return null;
    }
}
