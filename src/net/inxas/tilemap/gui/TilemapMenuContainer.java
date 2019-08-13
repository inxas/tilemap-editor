package net.inxas.tilemap.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.Border;

import net.inxas.tilemap.gui.laf.FontToolkit;
import net.inxas.tilemap.gui.laf.TilemapIcons;
import net.inxas.tilemap.gui.laf.TilemapMenuContainerUI;
import net.inxas.tilemap.gui.laf.TilemapTheme;
import net.inxas.tilemap.gui.laf.TilemapUtils;

/**
 * メニューのコンテナです。
 * @author inxas
 * @version 0.0-alpha
 */
public class TilemapMenuContainer extends JComponent {
    private static final String uiClassID = "MenuContainerUI";

    /**
     * コンストラクタ
     */
    public TilemapMenuContainer() {
        init();
    }

    /**
     * 初期化処理をします。通常はコンストラクタからのみ呼ばれるべきです。
     */
    public void init() {
        setLayout(createLayout());
        setBorder(createBorder());

        initComponent();

        updateUI();
    }

    /**
     * このコンテナに適したレイアウトを返します。
     * @return 適したレイアウト
     */
    protected LayoutManager createLayout() {
        return new FlowLayout();
    }

    /**
     * コンテナに配置するコンポーネントの初期化処理をします。
     */
    protected void initComponent() {

    }

    /**
     * ファイル操作に適したコンテナを作成します。
     * @return 作成したコンテナ
     */
    public static TilemapMenuContainer createFileContainer() {
        return new FileContainer();
    }

    public void updateUI() {
        setUI((TilemapMenuContainerUI)UIManager.getUI(this));
    }
    public TilemapMenuContainerUI getUI() {
        return (TilemapMenuContainerUI)ui;
    }
    public void setUI(TilemapMenuContainerUI ui) {
        super.setUI(ui);
    }
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * コンテナ用のボーダーを作成します。
     * @return 作成したボーダー
     */
    protected Border createBorder() {
        return new ContainerBorder();
    }

    /**
     * コンテナ用ボーダーです。
     * @author inxas
     * @version 0.0-alpha
     */
    protected class ContainerBorder implements Border{

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(TilemapTheme.getTheme().getColor("MenuContainerUI.borderColor"));
            g.fillRect(0, 0, 2, height);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(0,2,0,0);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }
    }

    /**
     * コンテナで使用できるプルダウンメニューです。
     * @author inxas
     * @version 0.0-alpha
     */
    protected class PullDownMenu extends TilemapPanel {
        private String title;
        private JComponent contents;
        private boolean isOpened;
        private PullDownTitleLabel titleLabel;
        private PullDownButton pullDownButton;
        private final int labelHeight = 30;
        /**
         * プルダウンメニューのタイトル、コンテンツ、デフォルトで開いているかを指定します。
         * @param title タイトル
         * @param contents コンテンツ
         * @param isOpened 開いているならtrue
         */
        protected PullDownMenu(String title,JComponent contents,boolean isOpened) {
            this.title = title;
            this.contents = contents;

            this.isOpened = isOpened;

            titleLabel = new PullDownTitleLabel();
            pullDownButton = new PullDownButton();

            init();
        }

        /**
         * 初期化処理をします。
         */
        private void init() {
            setLayout(new PullDownLayout());

            resize();

            add(titleLabel);
            add(pullDownButton);
            add(contents);
        }

        /**
         * プルダウンの開閉をします。
         */
        private void resize() {
            if(isOpened) {
                setPreferredSize(new Dimension(getWidth(),labelHeight + contents.getPreferredSize().height));
            }else {
                setPreferredSize(new Dimension(getWidth(),labelHeight));
            }
        }

        /**
         * プルダウンメニュー用のレイアウトマネージャです。
         * @author inxas
         * @version 0.0-alpha
         */
        private class PullDownLayout implements LayoutManager2{
            @Override
            public Dimension preferredLayoutSize(Container parent) {
                Dimension dim = new Dimension();

                Dimension parentDim = TilemapMenuContainer.this.getSize();

                Dimension labelDim = titleLabel != null ? titleLabel.getPreferredSize() : null;
                Dimension contDim = contents != null ? contents.getPreferredSize() : null;

                if(isOpened) {
                    int width = parentDim.width;
                    int height = labelDim.height + contDim.height;
                    dim.setSize(width, height);
                }else {
                    int width = parentDim.width;
                    int height = labelDim.height;
                    dim.setSize(width, height);
                }
                return dim;
            }
            @Override
            public Dimension minimumLayoutSize(Container parent) {
                return preferredLayoutSize(parent);
            }
            @Override
            public Dimension maximumLayoutSize(Container target) {
                return preferredLayoutSize(target);
            }
            @Override
            public void layoutContainer(Container parent) {
                Insets i = parent.getInsets();
                int contentY = i.top;
                int labelWidth = 0;
                if(titleLabel != null) {
                    int width = (int)(parent.getWidth() * 0.8) - i.left;
                    titleLabel.setBounds(i.left, contentY, width, labelHeight);
                    contentY += labelHeight;
                    labelWidth = width;
                }
                if(pullDownButton != null) {
                    pullDownButton.setBounds(labelWidth, 0, parent.getWidth() - labelWidth - i.right, contentY);
                }
                if(isOpened && contents != null) {
                    contents.setBounds(0, contentY, parent.getWidth(), parent.getHeight() - contentY);
                }
            }

            @Override
            public void addLayoutComponent(Component comp, Object constraints) {}
            @Override
            public void addLayoutComponent(String name, Component comp) {}
            @Override
            public void removeLayoutComponent(Component comp) {}
            @Override
            public float getLayoutAlignmentX(Container target) {return 0;}
            @Override
            public float getLayoutAlignmentY(Container target) {return 0;}
            @Override
            public void invalidateLayout(Container target) {}
        }

        /**
         * プルダウンメニュー用のタイトル表示用ラベルです。
         * @author inxas
         *
         */
        private class PullDownTitleLabel extends TilemapPanel implements MouseListener{
            PullDownTitleLabel(){
                super();
                addMouseListener(this);
            }
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(TilemapTheme.getTheme().getColor("MenuContainerUI.pullDownBackground"));
                g.fillRect(0, 0, getWidth(), getHeight());

                Graphics2D g2d = (Graphics2D)g;
                g.setColor(TilemapTheme.getTheme().getColor("MenuContainerUI.pullDownForeground"));
                g.setFont(TilemapTheme.getTheme().getFont("MenuContainerUI.pullDownFont"));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                FontRenderContext frc = new FontRenderContext(null, true, true);
                GlyphVector gv = g.getFont().createGlyphVector(frc, title);
                Rectangle2D b = gv.getVisualBounds();
                Point2D p = new Point2D.Double(b.getX() + b.getWidth() / 2d, b.getY() + b.getHeight() / 2d);
                AffineTransform atf = AffineTransform.getTranslateInstance(getWidth() / 2d - p.getX(), getHeight() / 2d - p.getY());
                Shape shape = atf.createTransformedShape(gv.getOutline());
                g2d.fill(shape);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if(pullDownButton != null)pullDownButton.doClick();
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        }

        /**
         * プルダウンを開閉するためのボタンです。
         * @author inxas
         * @version 0.0-alpha
         */
        private class PullDownButton extends JButton {
            PullDownButton(){
                super(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        isOpened = !isOpened;
                        PullDownMenu.this.resize();
                        PullDownMenu.this.revalidate();
                        PullDownMenu.this.repaint();
                    }
                });
                setBorderPainted(false);
            }
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(TilemapTheme.getTheme().getColor("MenuContainerUI.pullDownBackground"));
                g.fillRect(0,0,getWidth(),getHeight());
                g.setColor(TilemapTheme.getTheme().getColor("MenuContainerUI.pullDownForeground"));
                if(isOpened) {
                    TilemapIcons.PULL_DOWN_UP.paintIcon(this, g, 0, 0);
                }else {
                    TilemapIcons.PULL_DOWN_DOWN.paintIcon(this, g, 0, 0);
                }
            }
        }
    }

    /**
     * ファイル操作用のコンテナです。
     * @author inxas
     * @version 0.0-alpha
     */
    public static class FileContainer extends TilemapMenuContainer {
        @Override
        protected LayoutManager createLayout() {
            return new FileContainerLayout();
        }
        @Override
        protected void initComponent() {
            add(new PullDownMenu("新規作成",new NewFileContents(),false));
        }
        private class FileContainerLayout implements LayoutManager2 {
            @Override
            public Dimension preferredLayoutSize(Container parent) {
                int height = 0;
                for(Component c : parent.getComponents()) {
                    height += c.getHeight();
                }
                return new Dimension(getWidth(),height);
            }
            @Override
            public Dimension minimumLayoutSize(Container parent) {
                return preferredLayoutSize(parent);
            }
            @Override
            public Dimension maximumLayoutSize(Container target) {
                return preferredLayoutSize(target);
            }
            @Override
            public void layoutContainer(Container parent) {
                Insets i = parent.getInsets();
                int width = parent.getWidth() - i.left - i.right;
                int contentY = i.top;
                int usingHeight = parent.getHeight() - i.top - i.bottom;
                for(Component c : parent.getComponents()) {
                    c.setBounds(i.left, contentY, width, Math.min(usingHeight, c.getPreferredSize().height));
                    contentY += c.getHeight();
                    usingHeight -= c.getHeight();
                }
            }

            @Override
            public void addLayoutComponent(String name, Component comp) {}
            @Override
            public void removeLayoutComponent(Component comp) {}
            @Override
            public void addLayoutComponent(Component comp, Object constraints) {}
            @Override
            public float getLayoutAlignmentX(Container target) {return 0;}
            @Override
            public float getLayoutAlignmentY(Container target) {return 0;}
            @Override
            public void invalidateLayout(Container target) {}
        }

        /**
         * 新規作成プルダウンメニュー用のコンテンツです。
         * @author inxas
         * @version 0.0-alpha
         */
        private class NewFileContents extends TilemapPanel{
            TilemapLabel titleLabel;
            TilemapLabel widthLabel;
            TilemapLabel heightLabel;
            TilemapLabel hLabel;
            TilemapLabel vLabel;

            TilemapTextField titleText;
            LimitsTextField widthText;
            LimitsTextField heightText;
            LimitsTextField hText;
            LimitsTextField vText;

            JButton createButton;

            NewFileContents(){
                init();
            }
            private void init() {
                GridBagLayout layout = new GridBagLayout();
                GridBagConstraints cons = new GridBagConstraints();
                setLayout(layout);

                titleLabel = new TilemapLabel("タイトル:");
                widthLabel = new TilemapLabel("マップの横幅:");
                heightLabel = new TilemapLabel("マップの縦幅:");
                hLabel = new TilemapLabel("チップの横幅:");
                vLabel = new TilemapLabel("チップの縦幅:");

                titleLabel.setHorizontalAlignment(JLabel.RIGHT);
                widthLabel.setHorizontalAlignment(JLabel.RIGHT);
                heightLabel.setHorizontalAlignment(JLabel.RIGHT);
                hLabel.setHorizontalAlignment(JLabel.RIGHT);
                vLabel.setHorizontalAlignment(JLabel.RIGHT);

                titleLabel.setFont(TilemapTheme.getTheme().getFont("MenuContainerUI.contentsFont"));
                widthLabel.setFont(TilemapTheme.getTheme().getFont("MenuContainerUI.contentsFont"));
                heightLabel.setFont(TilemapTheme.getTheme().getFont("MenuContainerUI.contentsFont"));
                hLabel.setFont(TilemapTheme.getTheme().getFont("MenuContainerUI.contentsFont"));
                vLabel.setFont(TilemapTheme.getTheme().getFont("MenuContainerUI.contentsFont"));

                titleText = new TilemapTextField(null,"map name");
                widthText = new LimitsTextField(255,20,"20~255");
                heightText = new LimitsTextField(255,20,"20~255");
                hText = new LimitsTextField(128,8,"8~128");
                vText = new LimitsTextField(128,8,"8~128");

                titleText.setFont(FontToolkit.getDefaultFontUIResource(12));
                widthText.setFont(FontToolkit.getDefaultFontUIResource(12));
                heightText.setFont(FontToolkit.getDefaultFontUIResource(12));
                hText.setFont(FontToolkit.getDefaultFontUIResource(12));
                vText.setFont(FontToolkit.getDefaultFontUIResource(12));

                createButton = new JButton(new AbstractAction("新規作成") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Window window = TilemapUtils.getWindow(NewFileContents.this);
                        if(window instanceof EditorFrame) {
                            if(titleText.getText() != null && !titleText.getText().equals("")
                                    && widthText.getText() != null && !widthText.getText().equals("")
                                    && heightText.getText() != null && !heightText.getText().equals("")
                                    && hText.getText() != null && !hText.getText().equals("")
                                    && vText.getText() != null && !vText.getText().equals("")) {
                                ((EditorFrame)window).getEditorPane()
                                .getMakerPane()
                                .create(titleText.getText(),
                                        widthText.getInt(),
                                        heightText.getInt(),
                                        hText.getInt(),
                                        vText.getInt());
                                titleText.setText("");
                                widthText.setText("");
                                heightText.setText("");
                                hText.setText("");
                                vText.setText("");
                            }else {
                                Toolkit.getDefaultToolkit().beep();
                            }
                        }
                    }
                });

                cons.weightx = 0.2d;
                cons.weighty = 1d;

                cons.fill = GridBagConstraints.HORIZONTAL;

                cons.gridx = 0;
                cons.gridy = 0;
                add(titleLabel,cons);
                cons.gridy++;
                add(widthLabel,cons);
                cons.gridy++;
                add(heightLabel,cons);
                cons.gridy++;
                add(hLabel,cons);
                cons.gridy++;
                add(vLabel,cons);

                cons.fill = GridBagConstraints.NONE;

                cons.weightx = 1d;
                cons.gridx = 1;
                cons.gridy = 0;
                add(titleText,cons);
                cons.gridy++;
                add(widthText,cons);
                cons.gridy++;
                add(heightText,cons);
                cons.gridy++;
                add(hText,cons);
                cons.gridy++;
                add(vText,cons);

                cons.gridx = 0;
                cons.gridy++;
                cons.gridwidth = 2;
                add(createButton,cons);

                setPreferredSize(new Dimension(0,180));
            }
        }
    }
}
