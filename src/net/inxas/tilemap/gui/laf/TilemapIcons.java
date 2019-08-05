package net.inxas.tilemap.gui.laf;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class TilemapIcons {
	private TilemapIcons() {}
	
	public static final ImageIcon FRAME;
	
	private static final ImageIcon ICONIFY_IMAGE;
	private static final ImageIcon MAX_IMAGE;
	private static final ImageIcon CLOSE_IMAGE;
	static {
		FRAME = new ImageIcon(TilemapIcons.class.getResource("images/tilemapEditorIcon.png"));
		
		ICONIFY_IMAGE = new ImageIcon(TilemapIcons.class.getResource("images/iconify.png"));
		MAX_IMAGE = new ImageIcon(TilemapIcons.class.getResource("images/max.png"));
		CLOSE_IMAGE = new ImageIcon(TilemapIcons.class.getResource("images/close.png"));
	}
	
	public static final InnerIcon ICONIFY = new InnerIcon() {
		public ImageIcon getImageIcon() {
			return ICONIFY_IMAGE;
		}
	};
	public static final InnerIcon MAX = new InnerIcon() {
		public ImageIcon getImageIcon() {
			return MAX_IMAGE;
		}
	};
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
	public static final Icon CLOSE_NORMAL = new CloseIcon() {};
	public static final Icon CLOSE_HOVER = null;
	public static final Icon CLOSE_PRESS = null;
	public static final Icon CLOSE_DISABLE = null;
	
	protected abstract static class InnerIcon implements Icon {
		private ImageIcon image = getImageIcon();
		protected InnerIcon() {
			Image img = image.getImage();
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
						pixels[nowPoint] = TilemapTheme.getTheme().getColor("TitlePaneUI.buttonIconColor").getRGB();
					}
				}
			}
			this.image.setImage(Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(w,h,pixels,0,w)));
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
	protected abstract static class CloseIcon extends ButtonIcon {
		public InnerIcon getInnerIcon() {
			return CLOSE;
		}
		public int getIconWidth() {
			return 48;
		}
	}
}
