package net.inxas.tilemap.gui.laf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.inxas.tilemap.gui.TilemapMenuIcon;

/**
 * このルックアンドフィールで使用するアイコン一覧です。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapIcons {
    private TilemapIcons() {}

    /** フレームで使用するアイコンです。 */
    public static final ImageIcon FRAME;

    private static final ImageIcon ICONIFY_IMAGE;
    private static final ImageIcon MAX_IMAGE;
    private static final ImageIcon RESTORE_IMAGE;
    private static final ImageIcon CLOSE_IMAGE;

    private static final ImageIcon FILE_IMAGE;

    private static final ImageIcon PULL_DOWN_UP_IMAGE;
    private static final ImageIcon PULL_DOWN_DOWN_IMAGE;
    static {
        FRAME = new ImageIcon(TilemapIcons.class.getResource("images/tilemapEditorIcon.png"));

        ICONIFY_IMAGE = new ImageIcon(TilemapIcons.class.getResource("images/iconify.png"));
        MAX_IMAGE = new ImageIcon(TilemapIcons.class.getResource("images/max.png"));
        RESTORE_IMAGE = new ImageIcon(TilemapIcons.class.getResource("images/restore.png"));
        CLOSE_IMAGE = new ImageIcon(TilemapIcons.class.getResource("images/close.png"));

        FILE_IMAGE = new ImageIcon(TilemapIcons.class.getResource("images/file.png"));

        PULL_DOWN_UP_IMAGE = new ImageIcon(TilemapIcons.class.getResource("images/pullDownUp.png"));
        PULL_DOWN_DOWN_IMAGE = new ImageIcon(TilemapIcons.class.getResource("images/pullDownDown.png"));
    }

    /**
     * タイトルペインの最小化ボタン用のアイコン
     */
    public static final InnerIcon ICONIFY = new InnerIcon() {
        public ImageIcon getImageIcon() {
            return ICONIFY_IMAGE;
        }
    };
    /**
     * タイトルペインの最大化ボタン用のアイコン
     */
    public static final InnerIcon MAX = new InnerIcon() {
        public ImageIcon getImageIcon() {
            return MAX_IMAGE;
        }
    };
    /**
     * タイトルペインのリストア用のアイコン
     */
    public static final InnerIcon RESTORE = new InnerIcon() {
        public ImageIcon getImageIcon() {
            return RESTORE_IMAGE;
        }
    };
    /**
     * タイトルペインのクローズ用のアイコン
     */
    public static final InnerIcon CLOSE = new InnerIcon() {
        public ImageIcon getImageIcon() {
            return CLOSE_IMAGE;
        }
    };
    public static final Icon ICONIFY_NORMAL = new IconifyIcon() {};
    public static final Icon ICONIFY_HOVER = null;
    public static final Icon ICONIFY_PRESS = null;
    public static final Icon ICONIFY_DISABLE = null;
    public static final Icon MAX_NORMAL = new MaxIcon() {};
    public static final Icon MAX_HOVER = null;
    public static final Icon MAX_PRESS = null;
    public static final Icon MAX_DISABLE = null;
    public static final Icon RESTORE_NORMAL = new RestoreIcon() {};
    public static final Icon CLOSE_NORMAL = new CloseIcon() {};
    public static final Icon CLOSE_HOVER = null;
    public static final Icon CLOSE_PRESS = null;
    public static final Icon CLOSE_DISABLE = null;

    public static final Icon MENU_FILE = new MenuFileIcon();
    public static final Icon MENU_FILE_HOVER = new MenuFileHoverIcon();

    public static final Icon PULL_DOWN_UP = new PullDownUpIcon();
    public static final Icon PULL_DOWN_DOWN = new PullDownDownIcon();

    private static Image createMonochromeImage(Image img,Color color) {
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        int[] pixels = new int[w * h];
        PixelGrabber pg = new PixelGrabber(img,0,0,w,h,pixels,0,w);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int y = 0;y < h;y++) {
            for(int x = 0;x < w;x++) {
                int nowPoint = y * w + x;
                int pixel = pixels[nowPoint];
                if(((pixel >> 24) & 0xff) != 0) {
                    pixels[nowPoint] = color.getRGB();
                }
            }
        }
        return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(w,h,pixels,0,w));
    }
    protected abstract static class InnerIcon implements Icon {
        private ImageIcon image = getImageIcon();
        protected InnerIcon() {
            Image img = image.getImage();
            this.image.setImage(createMonochromeImage(img,TilemapTheme.getTheme().getColor("TitlePaneUI.buttonIconColor")));
        }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            int width = c.getWidth();
            int height = c.getHeight();
            int imageW = image.getIconWidth();
            int imageH = image.getIconHeight();

            g.drawImage(image.getImage(), (width - imageW) / 2, (height - imageH) / 2, null);
        }
        public abstract ImageIcon getImageIcon();
        public int getIconWidth() {
            return 16;
        }
        public int getIconHeight() {
            return 16;
        }
    }
    protected abstract static class ButtonIcon implements Icon {
        Icon icon = getInnerIcon();
        public void paintIcon(Component c, Graphics g, int x, int y) {
            int w = c.getWidth();
            int h = c.getHeight();
            icon.paintIcon(c, g, (w - icon.getIconWidth()) / 2, (h - icon.getIconHeight()) / 2);
        }
        public abstract InnerIcon getInnerIcon();
        public int getIconHeight() {
            return 20;
        }
    }
    protected abstract static class IconifyIcon extends ButtonIcon {
        public InnerIcon getInnerIcon() {
            return ICONIFY;
        }
        public int getIconWidth() {
            return 28;
        }
    }
    protected abstract static class MaxIcon extends ButtonIcon {
        public InnerIcon getInnerIcon() {
            return MAX;
        }
        public int getIconWidth() {
            return 28;
        }
    }
    protected abstract static class RestoreIcon extends ButtonIcon {
        public InnerIcon getInnerIcon() {
            return RESTORE;
        }
        public int getIconWidth() {
            return 28;
        }
    }
    protected abstract static class CloseIcon extends ButtonIcon {
        public InnerIcon getInnerIcon() {
            return CLOSE;
        }
        public int getIconWidth() {
            return 48;
        }
    }

    protected abstract static class MenuIcon implements Icon{
        Image image;
        Color color;
        public MenuIcon() {
            image = createMonochromeImage(getImageIcon().getImage(),getColor());
        }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            if(c instanceof TilemapMenuIcon) {
                int size = Math.min(c.getWidth(), c.getHeight());
                g.drawImage(image,(c.getWidth() - size) / 2,(c.getHeight() - size) / 2,size,size,c);
            }else {
                g.drawImage(image, x, y, c);
            }
        }
        public int getIconHeight() {
            return image.getHeight(null);
        }

        public int getIconWidth() {
            return image.getWidth(null);
        }
        abstract ImageIcon getImageIcon();
        abstract Color getColor();
    }
    protected static class MenuFileIcon extends MenuIcon{
        public ImageIcon getImageIcon() {
            return FILE_IMAGE;
        }
        public Color getColor() {
            return TilemapTheme.getTheme().getColor("MenuIconUI.iconColor");
        }
    }
    protected static class MenuFileHoverIcon extends MenuFileIcon{
        public Color getColor() {
            return TilemapTheme.getTheme().getColor("MenuIconUI.hoverIconColor");
        }
    }

    protected abstract static class PullDownIcon implements Icon{
        Image image;
        Color color;
        public PullDownIcon() {
            image = createMonochromeImage(getImageIcon().getImage(),getColor());
        }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.drawImage(image,(c.getWidth() - getIconWidth()) / 2,(c.getHeight() - getIconHeight()) / 2,c);
        }
        public int getIconHeight() {
            return image.getHeight(null);
        }

        public int getIconWidth() {
            return image.getWidth(null);
        }
        abstract ImageIcon getImageIcon();
        abstract Color getColor();
    }
    protected static class PullDownUpIcon extends PullDownIcon{
        @Override
        ImageIcon getImageIcon() {
            return PULL_DOWN_UP_IMAGE;
        }
        @Override
        Color getColor() {
            return TilemapTheme.getTheme().getColor("MenuContainerUI.pullDownForeground");
        }
    }
    protected static class PullDownDownIcon extends PullDownIcon{
        @Override
        ImageIcon getImageIcon() {
            return PULL_DOWN_DOWN_IMAGE;
        }
        @Override
        Color getColor() {
            return TilemapTheme.getTheme().getColor("MenuContainerUI.pullDownForeground");
        }
    }
}
