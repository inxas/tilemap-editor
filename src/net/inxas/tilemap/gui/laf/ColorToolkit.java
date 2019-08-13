package net.inxas.tilemap.gui.laf;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;

/**
 * 色に関する様々な機能があります。
 * @author inxas
 * @since 2019/07/27
 * @version 0.0-alpha
 */
public class ColorToolkit {
    /** 黒を表します。 */
    public static final ColorUIResource black = new ColorUIResource(0,0,0);
    /** 白を表します。 */
    public static final ColorUIResource white = new ColorUIResource(255,255,255);
    /** 赤を表します。 */
    public static final ColorUIResource red = new ColorUIResource(255,0,0);
    /** 緑を表します。 */
    public static final ColorUIResource green = new ColorUIResource(0,255,0);
    /** 青を表します。 */
    public static final ColorUIResource blue = new ColorUIResource(0,0,255);
    /** 黄を表します。 */
    public static final ColorUIResource yellow = new ColorUIResource(255,255,0);
    /** シアンを表します。 */
    public static final ColorUIResource cyan = new ColorUIResource(0,255,255);
    /** マゼンタを表します。 */
    public static final ColorUIResource magenta = new ColorUIResource(255,0,255);

    /** テキスト用の色 */
    public static final ColorUIResource text = new ColorUIResource(22,22,22);
    /** 停止状態のテキストの色 */
    public static final ColorUIResource disableText = new ColorUIResource(96,96,96);

    /** フレームのボーダーの色 */
    public static final ColorUIResource frameBorder = new ColorUIResource(96,96,96);

    /**
     * 二つの色を混ぜ合わせたものを返します。
     * @param c1 Color
     * @param c2 Color
     * @return それぞれの色の平均
     */
    public static ColorUIResource blend(Color c1,Color c2) {
        int red = (c1.getRed() + c2.getRed()) / 2;
        int green = (c1.getGreen() + c2.getGreen()) / 2;
        int blue = (c1.getBlue() + c2.getBlue()) / 2;
        int alpha = (c1.getAlpha() + c2.getAlpha()) / 2;
        if(alpha == 255)return new ColorUIResource(red,green,blue);
        else return new ColorUIResource(new Color(red,green,blue,alpha));
    }

    /**
     * 二つの色のグラデーションを返します。
     * @param c1 Color
     * @param c2 Color
     * @param stage グラデーションの段階
     * @return 二つの色のグラデーション もしstageが2未満ならnull
     */
    public static ColorUIResource[] gradation(Color c1,Color c2,int stage) {
        if(stage < 2) return null;
        ColorUIResource[] result = new ColorUIResource[stage];
        float redIncrement = (c2.getRed() - c1.getRed()) / (float)stage;
        float greenIncrement = (c2.getGreen() - c1.getGreen()) / (float)stage;
        float blueIncrement = (c2.getBlue() - c1.getBlue()) / (float)stage;
        float alphaIncrement = (c2.getAlpha() - c1.getAlpha()) / (float)stage;
        boolean usingAlpha = !(c1.getAlpha() == 255 && c2.getAlpha() == 255);

        result[0] = new ColorUIResource(c1);
        for(int i = 1,l = stage - 1;i < l;i++) {
            int red = (int)(c1.getRed() + redIncrement * i);
            int green = (int)(c1.getGreen() + greenIncrement * i);
            int blue = (int)(c1.getBlue() + blueIncrement * i);
            if(usingAlpha) {
                int alpha = (int)(c1.getAlpha() + alphaIncrement * i);
                result[i] = new ColorUIResource(new Color(red,green,blue,alpha));
            }else {
                result[i] = new ColorUIResource(red,green,blue);
            }
        }
        result[stage - 1] = new ColorUIResource(c2);

        return result;
    }
}
