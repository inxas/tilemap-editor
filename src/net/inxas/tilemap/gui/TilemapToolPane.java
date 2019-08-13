package net.inxas.tilemap.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.UIManager;

import net.inxas.tilemap.gui.laf.TilemapToolPaneUI;
import net.inxas.tilemap.gui.laf.TilemapUtils;

public class TilemapToolPane extends JComponent {
    private static final String uiClassID = "ToolPaneUI";

    private static final int EMPTY = 0;
    private static final int NORMAL = 1;
    private static final int MINIMUM = 2;

    private int state;

    private ToolPaneBorder border;
    private ResizePanel resizePanel;
    private LinkedList<TilemapToolContainer> tools;

    private int emptyWidth = 5;
    private int normalWidth = 100;
    private int normalMinWidth = 10;
    private int minimunWidth = TilemapToolContainer.ICON_SIZE;

    public TilemapToolPane(TilemapToolContainer... defaultTools) {
        super();
        init(defaultTools);
    }

    protected void init(TilemapToolContainer... defaultTools) {
        tools = new LinkedList<>();

        setLayout(createLayout());

        border = new ToolPaneBorder();
        setToolPaneBorder(border);

        resizePanel = new ResizePanel();
        setResizePanel(resizePanel);

        if(defaultTools != null) {
            for(int i = 0,l = defaultTools.length;i < l;i++) {
                addToolContainer(defaultTools[i],i);
            }
        }

        if(defaultTools != null && defaultTools.length > 0)setState(NORMAL);
        else setState(EMPTY);

        updateUI();
    }

    public void setState(int state) {
        switch(state) {
        case EMPTY:
            if(tools.isEmpty())this.state = EMPTY;
            break;
        case NORMAL:
            if(!tools.isEmpty())this.state = NORMAL;
            break;
        case MINIMUM:
            if(!tools.isEmpty())this.state = MINIMUM;
            break;
        }
    }
    public int getState() {
        return state;
    }

    /**
     * このメソッドは{@link TilemapEditorPane}によって呼び出されます。
     * 通常は呼び出さないでください。
     * @param normalWidth normalWidth
     */
    void setNormalWidth(int normalWidth) {
        this.normalWidth = normalWidth;
        getParent().revalidate();
        getParent().repaint();
    }

    public Dimension getPreferredSize() {
        if(tools.isEmpty())return new Dimension(emptyWidth,TilemapUtils.getDisplaySize().height);
        if(getState() == NORMAL) {
            int min = normalMinWidth;
            for(TilemapToolContainer tool : tools) {
                min = Math.max(min, tool.getMinimumSize().width);
            }
            normalWidth = Math.max(min, normalWidth);
            return new Dimension(normalWidth,TilemapUtils.getDisplaySize().height);
        }else if(getState() == MINIMUM) {
            return new Dimension(minimunWidth,TilemapUtils.getDisplaySize().height);
        }
        return new Dimension(0,TilemapUtils.getDisplaySize().height);
    }

    protected void setToolPaneBorder(ToolPaneBorder border) {
        if(this.border != null)remove(this.border);
        add(border,ToolPaneLayout.BORDER);
    }

    protected void setResizePanel(ResizePanel resizePanel) {
        if(this.resizePanel != null)remove(this.resizePanel);
        add(resizePanel,ToolPaneLayout.RESIZE);
    }

    /**
     * {@link #add(Component)}は使わずにこれを使います。
     * @param tool tool conteiner
     * @param index i
     */
    public void addToolContainer(TilemapToolContainer tool,int index) {
        tools.add(index, tool);
        add(tool,ToolPaneLayout.TOOL);
        if(!tools.isEmpty() && getState() == EMPTY) {
            setState(NORMAL);
        }
    }
    /**
     * {@link #remove(Component)}は使わずにこれを使います。
     * @param tool tool container
     */
    public void removeToolContainer(TilemapToolContainer tool) {
        tools.remove(tool);
        remove(tool);
        if(tools.isEmpty()) {
            setState(EMPTY);
        }
    }

    protected LayoutManager createLayout() {
        return new ToolPaneLayout();
    }

    protected class ToolPaneLayout implements LayoutManager2 {
        public static final String BORDER = "border";
        public static final String RESIZE = "resize";
        public static final String TOOL = "tool";

        private ToolPaneBorder border;
        private ResizePanel resizePanel;

        @Override
        public void addLayoutComponent(Component comp, Object constraints) {
            if(constraints == BORDER && comp instanceof ToolPaneBorder) border = (ToolPaneBorder) comp;
            else if(constraints == RESIZE && comp instanceof ResizePanel) resizePanel = (ResizePanel) comp;
            else if (!(constraints == TOOL && comp instanceof TilemapToolContainer)) {
                TilemapToolPane.this.remove(comp);
            }
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return null;
        }
        @Override
        public Dimension maximumLayoutSize(Container target) {
            return null;
        }
        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return null;
        }

        @Override
        public void layoutContainer(Container parent) {
            int width = parent.getWidth();
            int height = parent.getHeight();
            int contentX = 0;
            int contentY = 0;
            if(border != null) {
                int borderWidth = border.getPreferredSize().width;
                border.setBounds(contentX, contentY, borderWidth, height);
                contentX += borderWidth;
            }
            if(resizePanel != null) {
                int resizePanelHeight = resizePanel.getPreferredSize().height;
                resizePanel.setBounds(contentX, contentY, width, resizePanelHeight);
                contentY += resizePanelHeight;
            }
        }

        @Override
        public void removeLayoutComponent(Component comp) {}
        @Override
        public void addLayoutComponent(String name, Component comp) {}
        @Override
        public float getLayoutAlignmentX(Container target) {return 0;}
        @Override
        public float getLayoutAlignmentY(Container target) {return 0;}
        @Override
        public void invalidateLayout(Container target) {}
    }

    protected class ResizePanel extends TilemapPanel {
        private TilemapToolResizeButton resizeButton;
        protected ResizePanel() {
            init();
        }
        private void init() {
            resizeButton = new TilemapToolResizeButton(TilemapToolPane.this);

            setLayout(createLayout());

            add(resizeButton);

            setPreferredSize(new Dimension(0,20));
        }
        protected LayoutManager createLayout() {
            return new ResizePanelLayout();
        }
        protected class ResizePanelLayout implements LayoutManager{

            @Override
            public void addLayoutComponent(String name, Component comp) {}
            @Override
            public void removeLayoutComponent(Component comp) {}

            @Override
            public Dimension preferredLayoutSize(Container parent) {
                return TilemapToolPane.this.getPreferredSize();
            }
            @Override
            public Dimension minimumLayoutSize(Container parent) {
                return preferredLayoutSize(parent);
            }

            @Override
            public void layoutContainer(Container parent) {
                Dimension pre = resizeButton.getPreferredSize();
                resizeButton.setBounds(parent.getWidth() - pre.width, 0, pre.width, parent.getHeight());
            }
        }
    }

    protected class ToolPaneBorder extends TilemapPanel implements MouseMotionListener{
        protected ToolPaneBorder() {
            super();

            setPreferredSize(new Dimension(5,0));

            setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));

            addMouseMotionListener(this);
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            if(getState() == NORMAL)normalWidth -= e.getX();
            TilemapToolPane.this.getParent().revalidate();
            TilemapToolPane.this.getParent().repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        @Override
        public void mouseMoved(MouseEvent e) {}
    }

    public void updateUI() {
        setUI((TilemapToolPaneUI)UIManager.getUI(this));
    }
    public TilemapToolPaneUI getUI() {
        return (TilemapToolPaneUI)ui;
    }
    public void setUI(TilemapToolPaneUI ui) {
        super.setUI(ui);
    }

    public String getUIClassID() {
        return uiClassID;
    }
}
