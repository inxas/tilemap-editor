package net.inxas.tilemap.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

import net.inxas.tilemap.gui.laf.TilemapTheme;

/**
 * フレームのボーダーを表現するコンポーネントたちのルートクラスです。
 * @author inxas
 * @version 0.0-alpha
 */
public abstract class TilemapFrameBorderBar extends JComponent implements MouseListener,MouseMotionListener{
    /** ボーダーの太さ */
    protected int thickness = 2;
    /** ボーダーが属するウィンドウ */
    protected Window parent;
    /** マウスカーソルばほばーした時のマウスカーソルのデザイン */
    protected Cursor hoveringCursor;
    /** 手前斜め方向にドラッグするときのマウスカーソルのデザイン */
    protected Cursor nearCursor;
    /** 後ろ斜め方向にドラッグするときのマウスカーソルのデザイン */
    protected Cursor behindCursor;
    /** デフォルトのマウスカーソルのデザイン */
    protected Cursor defaultCursor;
    /** マウスを押したときの座標 */
    protected Point pressedPoint;
    /** マウスをドラッグ中の座標 */
    protected Point draggedPoint;
    /** マウスを押したときのx座標 */
    protected int pressedX;
    /** マウスを押したときのy座標 */
    protected int pressedY;
    /** 斜め方向への移動をサポートしているならtrue */
    protected boolean isSupportedCursor;
    /** マウスカーソルのデザインが{@value #nearCursor}に切り替わる指標 */
    protected int near;
    /** マウスカーソルのデザインが{@value #behindCursor}に切り替わる指標 */
    protected int behind;
    /** 現在のマウスカーソルのデザインの指標 */
    protected int nowCursorPoint;
    /** 現在のマウスカーソルが{@value #nearCursor}であることを示す値 */
    protected static final int NEAR = 0;
    /** 現在のマウスカーソルが{@value #hoveringCursor}であることを示す値 */
    protected static final int NORMAL = 1;
    /** 現在のマウスカーソルが{@value #behindCursor}であることを示す値 */
    protected static final int BEHIND = 2;
    /** 現在のマウスカーソルが何なのかを示す */
    protected int nowCursorMode;
    /** 現在使用しているボーダーを記録 */
    private static TilemapFrameBorderBar lock;
    private boolean topMin,bottomMin,leftMin,rightMin;
    
    /**
     * このボーダーが属するウィンドウを示してボーダーを作成します。
     * @param parent 属するウィンドウ
     */
    public TilemapFrameBorderBar(Window parent) {
        this.parent = parent;
        hoveringCursor = createHoveringCursor();
        nearCursor = createNearCursor();
        behindCursor = createBehindCursor();
        defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        addMouseListener(this);
        addMouseMotionListener(this);
        near = 10;
        behind = 90;
        isSupportedCursor = false;
        nowCursorMode = NORMAL;
        topMin = bottomMin = leftMin = rightMin = false;
    }
    /**
     * {@value #hoveringCursor}を定義するためのメソッドです。
     * @return {@value #hoveringCursor}として適しているカーソル
     */
    public abstract Cursor createHoveringCursor();
    /**
     * {@value #nearCursor}を定義するためのメソッドです。
     * @return {@value #nearCursor}として適しているカーソル
     */
    public Cursor createNearCursor() {
        return null;
    }
    /**
     * {@value #behindCursor}を定義するためのメソッドです。
     * @return {@value #behindCursor}として適しているカーソル
     */
    public Cursor createBehindCursor() {
        return null;
    }
    /**
     * ボーダーをテーマで決めた色で塗りつぶします。
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(TilemapTheme.getTheme().getColor("FrameUI.borderColor"));
        g.fillRect(0,0,getWidth(),getHeight());
    }
    /**
     * ウィンドウの左に配置することに適したボーダーを作成し、返します。
     * @param parent ボーダーが属するウィンドウ
     * @return 作成したボーダー
     */
    public static TilemapFrameBorderBar createLeftBorder(Window parent) {
        return new LeftBorder(parent);
    }
    /**
     * ウィンドウの右に配置することに適したボーダーを作成し、返します。
     * @param parent ボーダーが属するウィンドウ
     * @return 作成したボーダー
     */
    public static TilemapFrameBorderBar createRightBorder(Window parent) {
        return new RightBorder(parent);
    }
    /**
     * ウィンドウの上に配置することに適したボーダーを作成し、返します。
     * @param parent ボーダーが属するウィンドウ
     * @return 作成したボーダー
     */
    public static TilemapFrameBorderBar createTopBorder(Window parent) {
        return new TopBorder(parent);
    }
    /**
     * ウィンドウの下に配置することに適したボーダーを作成し、返します。
     * @param parent ボーダーが属するウィンドウ
     * @return 作成したボーダー
     */
    public static TilemapFrameBorderBar createBottomBorder(Window parent) {
        return new BottomBorder(parent);
    }
    /**
     * ロックがかかっていないか、自分自身でロックをかけている場合に
     * マウスカーソルを現在の状態に合わせたデザインに変更します。
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if(lock != null && lock != this) return;
        if(isSupportedCursor) {
            if(nowCursorPoint <= near) {
                setCursor(nearCursor);
                nowCursorMode = NEAR;
            }else if(nowCursorPoint >= behind) {
                setCursor(behindCursor);
                nowCursorMode = BEHIND;
            }else {
                setCursor(hoveringCursor);
                nowCursorMode = NORMAL;
            }
        }else {
            setCursor(hoveringCursor);
        }
    }
    /**
     * マウスカーソルのデザインをデフォルトに戻します。
     */
    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(defaultCursor);
    }
    /**
     * もしマウスの第一ボタンを押した場合にプレス処理とロックをします。
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            pressedPoint = e.getLocationOnScreen();
            pressedX = e.getX();
            pressedY = e.getY();
            lock = this;
        }
        else pressedPoint = null;
    }
    /**
     * {@link #mouseEntered(MouseEvent)}と同じ処理をします。
     * @see #mouseEntered(MouseEvent)
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseEntered(e);
    }
    /**
     * 何もしません。
     */
    @Override
    public void mouseClicked(MouseEvent e) {}
    /**
     * ロックを外します。
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        lock = null;
    }

    /**
     * 押したポイントに対するドラッグしたポイントのx座標の差を計算します。
     * @return 計算の結果
     */
    protected int xdiff() {
        if(pressedPoint == null || draggedPoint == null)return 0;
        int px = pressedPoint.x;
        int dx = draggedPoint.x;
        return px - dx;
    }
    /**
     * 押したポイントに対するドラッグしたポイントのy座標の差を計算します。
     * @return 計算の結果
     */
    protected int ydiff() {
        if(pressedPoint == null || draggedPoint == null)return 0;
        int py = pressedPoint.y;
        int dy = draggedPoint.y;
        return py - dy;
    }

    /**
     * ウィンドウの大きさを変更した結果、最小サイズを下回っていないか調べます。
     * 但し、ボーダーの属するウィンドウのインスタンスが{@link JFrame}か{@link JDialog}であり、
     * ルートペインが{@link TilemapRootPane}である必要があります。
     * @param bounds 確かめたいサイズ
     * @param isLeft ウィンドウの左側にあるボーダーならtrue
     * @param isTop ウィンドウの上側にあるボーダーならtrue
     * @return 調べた結果、正常ならboundsを返し、異常なら修正して返します。
     */
    protected Rectangle checkBounds(Rectangle bounds,boolean isLeft,boolean isTop) {
        if(parent instanceof JFrame && ((JFrame)parent).getRootPane() instanceof TilemapRootPane) {
            TilemapRootPane pane = (TilemapRootPane)((JFrame)parent).getRootPane();
            TilemapTitlePane titlePane = pane.getTitlePane();
            if(titlePane != null) {
                Dimension dim = titlePane.getMinimumSize();
                int w = dim.width;
                int h = dim.height;

                if(w >= bounds.width) {
                    if(isLeft && parent.getX() < MouseInfo.getPointerInfo().getLocation().x) {
                        leftMin = true;
                    }else if(!isLeft && parent.getX() + parent.getWidth() > MouseInfo.getPointerInfo().getLocation().x) {
                        rightMin = true;
                    }
                }
                if(leftMin) {
                    bounds.x = parent.getX();
                    bounds.width = w;
                    if(parent.getX() > MouseInfo.getPointerInfo().getLocation().x) {
                        leftMin = false;
                    }
                }else if(rightMin) {
                    bounds.x = parent.getX();
                    bounds.width = w;
                    if(parent.getX() + parent.getWidth() < MouseInfo.getPointerInfo().getLocation().x) {
                        rightMin = false;
                    }
                }

                if(h >= bounds.height) {
                    if(isTop && parent.getY() < MouseInfo.getPointerInfo().getLocation().y) {
                        topMin = true;
                    }else if (!isTop && parent.getY() + parent.getHeight() > MouseInfo.getPointerInfo().getLocation().y) {
                        bottomMin = true;
                    }
                }
                if(topMin) {
                    bounds.y = parent.getY();
                    bounds.height = h;
                    if(parent.getY() > MouseInfo.getPointerInfo().getLocation().y) {
                        topMin = false;
                    }
                }else if(bottomMin) {
                    bounds.y = parent.getY();
                    bounds.height = h;
                    if(parent.getY() + parent.getHeight() < MouseInfo.getPointerInfo().getLocation().y) {
                        bottomMin = false;
                    }
                }
            }
        }else if(parent instanceof JDialog && ((JDialog)parent).getRootPane() instanceof TilemapRootPane) {
            TilemapRootPane pane = (TilemapRootPane)((JDialog)parent).getRootPane();
            TilemapTitlePane titlePane = pane.getTitlePane();
            if(titlePane != null) {
                Dimension dim = titlePane.getMinimumSize();
                int w = dim.width;
                int h = dim.height;

                if(w >= bounds.width) {
                    if(isLeft && parent.getX() < MouseInfo.getPointerInfo().getLocation().x) {
                        leftMin = true;
                    }else if(!isLeft && parent.getX() + parent.getWidth() > MouseInfo.getPointerInfo().getLocation().x) {
                        rightMin = true;
                    }
                }
                if(leftMin) {
                    bounds.x = parent.getX();
                    bounds.width = w;
                    if(parent.getX() > MouseInfo.getPointerInfo().getLocation().x) {
                        leftMin = false;
                    }
                }else if(rightMin) {
                    bounds.x = parent.getX();
                    bounds.width = w;
                    if(parent.getX() + parent.getWidth() < MouseInfo.getPointerInfo().getLocation().x) {
                        rightMin = false;
                    }
                }

                if(h >= bounds.height) {
                    if(isTop && parent.getY() < MouseInfo.getPointerInfo().getLocation().y) {
                        topMin = true;
                    }else if (!isTop && parent.getY() + parent.getHeight() > MouseInfo.getPointerInfo().getLocation().y) {
                        bottomMin = true;
                    }
                }
                if(topMin) {
                    bounds.y = parent.getY();
                    bounds.height = h;
                    if(parent.getY() > MouseInfo.getPointerInfo().getLocation().y) {
                        topMin = false;
                    }
                }else if(bottomMin) {
                    bounds.y = parent.getY();
                    bounds.height = h;
                    if(parent.getY() + parent.getHeight() < MouseInfo.getPointerInfo().getLocation().y) {
                        bottomMin = false;
                    }
                }
            }
        }
        return bounds;
    }
    
    /**
     * 上へドラッグしたときの動作を行います。
     * ほかのボーダーによりロックがかかっていた場合は何もしません。
     * @param e ドラッグイベントのマウスイベント
     */
    protected void moveToTop(MouseEvent e) {
        if(lock != this) return;
        draggedPoint = e.getLocationOnScreen();
        int diff = ydiff();
        parent.setBounds(checkBounds(
                new Rectangle(parent.getX(), topMin ? parent.getY() : draggedPoint.y, parent.getWidth(), parent.getHeight() + diff),
                false,true));
        pressedPoint = e.getLocationOnScreen();
    }
    /**
     * 下へドラッグしたときの動作を行います。
     * ほかのボーダーによりロックがかかっていた場合は何もしません。
     * @param e ドラッグイベントのマウスイベント
     */
    protected void moveToBottom(MouseEvent e) {
        if(lock != this) return;
        draggedPoint = e.getLocationOnScreen();
        int diff = ydiff();
        parent.setBounds(checkBounds(
                new Rectangle(parent.getX(), parent.getY(), parent.getWidth(), parent.getHeight() - diff),
                false,false));
        pressedPoint = e.getLocationOnScreen();
    }
    /**
     * 左へドラッグしたときの動作を行います。
     * ほかのボーダーによりロックがかかっていた場合は何もしません。
     * @param e ドラッグイベントのマウスイベント
     */
    protected void moveToLeft(MouseEvent e) {
        if(lock != this) return;
        draggedPoint = e.getLocationOnScreen();
        int diff = xdiff();
        parent.setBounds(checkBounds(
                new Rectangle(draggedPoint.x, parent.getY(), parent.getWidth() + diff, parent.getHeight()),
                true,false));
        pressedPoint = e.getLocationOnScreen();
    }
    /**
     * 右へドラッグしたときの動作を行います。
     * ほかのボーダーによりロックがかかっていた場合は何もしません。
     * @param e ドラッグイベントのマウスイベント
     */
    protected void moveToRight(MouseEvent e) {
        if(lock != this) return;
        draggedPoint = e.getLocationOnScreen();
        int diff = xdiff();
        parent.setBounds(checkBounds(
                new Rectangle(parent.getX(), parent.getY(), parent.getWidth() - diff, parent.getHeight()),
                false,false));
        pressedPoint = e.getLocationOnScreen();
    }
    /**
     * 左上へドラッグしたときの動作を行います。
     * ほかのボーダーによりロックがかかっていた場合は何もしません。
     * @param e ドラッグイベントのマウスイベント
     */
    protected void moveToTopLeft(MouseEvent e) {
        if(lock != this) return;
        draggedPoint = e.getLocationOnScreen();
        int xdiff = xdiff();
        int ydiff = ydiff();
        parent.setBounds(checkBounds(
                new Rectangle(draggedPoint.x - pressedX, draggedPoint.y - pressedY, parent.getWidth() + xdiff, parent.getHeight() + ydiff),
                true,true));
        pressedPoint = e.getLocationOnScreen();
    }
    /**
     * 右上へドラッグしたときの動作を行います。
     * ほかのボーダーによりロックがかかっていた場合は何もしません。
     * @param e ドラッグイベントのマウスイベント
     */
    protected void moveToTopRight(MouseEvent e) {
        if(lock != this) return;
        draggedPoint = e.getLocationOnScreen();
        int xdiff = xdiff();
        int ydiff = ydiff();
        parent.setBounds(checkBounds(
                new Rectangle(parent.getX(), draggedPoint.y - pressedY, parent.getWidth() - xdiff,parent.getHeight() + ydiff),
                false,true));
        pressedPoint = e.getLocationOnScreen();
    }
    /**
     * 左下へドラッグしたときの動作を行います。
     * ほかのボーダーによりロックがかかっていた場合は何もしません。
     * @param e ドラッグイベントのマウスイベント
     */
    protected void moveToBottomLeft(MouseEvent e) {
        if(lock != this) return;
        draggedPoint = e.getLocationOnScreen();
        int xdiff = xdiff();
        int ydiff = ydiff();
        parent.setBounds(checkBounds(
                new Rectangle(draggedPoint.x - pressedX, parent.getY(), parent.getWidth() + xdiff, parent.getHeight() - ydiff),
                true,false));
        pressedPoint = e.getLocationOnScreen();
    }
    /**
     * 右下へドラッグしたときの動作を行います。
     * ほかのボーダーによりロックがかかっていた場合は何もしません。
     * @param e ドラッグイベントのマウスイベント
     */
    protected void moveToBottomRight(MouseEvent e) {
        if(lock != this) return;
        draggedPoint = e.getLocationOnScreen();
        int xdiff = xdiff();
        int ydiff = ydiff();
        parent.setBounds(checkBounds(
                new Rectangle(parent.getX(), parent.getY(), parent.getWidth() - xdiff, parent.getHeight() - ydiff),
                false,false));
        pressedPoint = e.getLocationOnScreen();
    }

    /**
     * ウィンドウの左側に設置されることを想定したボーダーです。
     * @author inxas
     * @version 0.0-alpha
     */
    protected static class LeftBorder extends TilemapFrameBorderBar{
        public LeftBorder(Window parent) {
            super(parent);
            isSupportedCursor = true;
        }
        public Cursor createHoveringCursor() {
            return new Cursor(Cursor.W_RESIZE_CURSOR);
        }
        public Cursor createNearCursor() {
            return new Cursor(Cursor.NW_RESIZE_CURSOR);
        }
        public Cursor createBehindCursor() {
            return new Cursor(Cursor.SW_RESIZE_CURSOR);
        }
        @Override
        public void mouseMoved(MouseEvent e) {
            nowCursorPoint = (int) (e.getPoint().getY() / getHeight() * 100);
            super.mouseMoved(e);
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            if(nowCursorMode == NEAR)moveToTopLeft(e);
            else if(nowCursorMode == BEHIND)moveToBottomLeft(e);
            else moveToLeft(e);
        }
    }
    /**
     * ウィンドウの右側に設置されることを想定したボーダーです。
     * @author inxas
     * @version 0.0-alpha
     */
    protected static class RightBorder extends TilemapFrameBorderBar {
        public RightBorder(Window parent) {
            super(parent);
            isSupportedCursor = true;
        }
        public Cursor createHoveringCursor() {
            return new Cursor(Cursor.E_RESIZE_CURSOR);
        }
        public Cursor createNearCursor() {
            return new Cursor(Cursor.NE_RESIZE_CURSOR);
        }
        public Cursor createBehindCursor() {
            return new Cursor(Cursor.SE_RESIZE_CURSOR);
        }
        @Override
        public void mouseMoved(MouseEvent e) {
            nowCursorPoint = (int) (e.getPoint().getY() / getHeight() * 100);
            super.mouseMoved(e);
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            if(nowCursorMode == NEAR)moveToTopRight(e);
            else if(nowCursorMode == BEHIND)moveToBottomRight(e);
            else moveToRight(e);
        }
    }
    /**
     * ウィンドウの上側に設置されることを想定したボーダーです。
     * @author inxas
     * @version 0.0-alpha
     */
    protected static class TopBorder extends TilemapFrameBorderBar {
        public TopBorder(Window parent) {
            super(parent);
            isSupportedCursor = true;
        }
        public Cursor createHoveringCursor() {
            return new Cursor(Cursor.N_RESIZE_CURSOR);
        }
        public Cursor createNearCursor() {
            return new Cursor(Cursor.NW_RESIZE_CURSOR);
        }
        public Cursor createBehindCursor() {
            return new Cursor(Cursor.NE_RESIZE_CURSOR);
        }
        @Override
        public void mouseMoved(MouseEvent e) {
            nowCursorPoint = (int) (e.getPoint().getX() / getWidth() * 100);
            super.mouseMoved(e);
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            if(nowCursorMode == NEAR)moveToTopLeft(e);
            else if(nowCursorMode == BEHIND)moveToTopRight(e);
            else moveToTop(e);
        }
    }
    /**
     * ウィンドウの下側に設置されることを想定したボーダーです。
     * @author inxas
     * @version 0.0-alpha
     */
    protected static class BottomBorder extends TilemapFrameBorderBar {
        public BottomBorder(Window parent) {
            super(parent);
            isSupportedCursor = true;
        }
        public Cursor createHoveringCursor() {
            return new Cursor(Cursor.S_RESIZE_CURSOR);
        }
        public Cursor createNearCursor() {
            return new Cursor(Cursor.SW_RESIZE_CURSOR);
        }
        public Cursor createBehindCursor() {
            return new Cursor(Cursor.SE_RESIZE_CURSOR);
        }
        @Override
        public void mouseMoved(MouseEvent e) {
            nowCursorPoint = (int) (e.getPoint().getX() / getWidth() * 100);
            super.mouseMoved(e);
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            if(nowCursorMode == NEAR)moveToBottomLeft(e);
            else if(nowCursorMode == BEHIND)moveToBottomRight(e);
            else moveToBottom(e);
        }
    }
}
