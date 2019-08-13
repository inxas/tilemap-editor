package net.inxas.tilemap;

/**
 * 一つの作品を管理するクラスです。
 * @author inxas
 * @since 2019/08/12
 * @version 0.0-alpha
 */
public class Work {
    private String titie;
    private int width,height,hSize,vSize;
    /**
     * 作品を新規作成します。
     * @param title 作品のタイトル
     * @param width マップの横幅
     * @param height マップの縦幅
     * @param hSize マップチップ一つ当たりの横幅
     * @param vSize マップチップ一つ当たりの縦幅
     */
    public Work(String title,int width,int height,int hSize,int vSize) {
        this.titie = title;
        this.width = width;
        this.height = height;
        this.hSize = hSize;
        this.vSize = vSize;
    }
}
