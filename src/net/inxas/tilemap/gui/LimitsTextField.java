package net.inxas.tilemap.gui;

import java.awt.event.FocusEvent;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * 上下限の制限付き、数値のみ入力可能なヒント付きテキストフィールドです。
 * @author inxas
 * @version 0.0-alpha
 */
public class LimitsTextField extends TilemapTextField{
    private int upper,lower;
    
    /**
     * ヒント無しで、上下限を設定したテキストフィールドを作成します。
     * @param upper 数値の上限
     * @param lower 数値の下限
     */
    LimitsTextField(int upper,int lower){
        this(upper,lower,null);
    }
    /**
     * ヒント付きで、上下限を設定したテキストフィールドを作成します。
     * @param upper 数値の上限
     * @param lower 数値の下限
     * @param hint ヒントメッセージ
     */
    LimitsTextField(int upper,int lower,String hint){
        super(null,hint);
        setLimits(upper,lower);
        setDocument(new LimitDocument());
    }

    /**
     * 現在入力されている値をintに変換して返します。
     * @return 現在入力されている値
     */
    public int getInt() {
        return Integer.parseInt(getText());
    }

    /**
     * 上下限を設定します。
     * @param upper 数値の上限
     * @param lower 数値の下限
     */
    public void setLimits(int upper,int lower) {
        if(upper < lower) {
            throw new IllegalArgumentException("upper(" + upper + ") is smaller than lower(" + lower + ").");
        }
        this.upper = upper;
        this.lower = lower;
        String text = getText();
        if(text != null && !text.equals("")) {
            int num = Integer.parseInt(text);
            if(upper < num) setText(String.valueOf(upper));
            else if(lower > num) setText(String.valueOf(lower));
        }
    }

    /**
     * 値が数値かどうか返します。
     * @param value 確認したい文字列
     * @return もし数値に変換可能ならtrue
     */
    private boolean isNumber(String value) {
        try {
            Integer.parseInt(value);
        }catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * 入力を数値に限定するドキュメントです。
     * @author inxas
     * @version 0.0-alpha
     */
    protected class LimitDocument extends PlainDocument {
        @Override
        public void insertString(int offs,String str,AttributeSet a) throws BadLocationException{
            if(isNumber(str)) {
                super.insertString(offs, str, a);
            }
        }
    }

    /**
     * テキストフィールドがフォーカスを失ったときに上下限を超えているか調べ、越えていたら上下限の値に修正します。
     */
    @Override
    public void focusLost(FocusEvent e) {
        try {
            if(isNumber(getText())){
                int value = Integer.parseInt(getText());
                if(value < lower) setText(String.valueOf(lower));
                else if (value > upper) setText(String.valueOf(upper));
            }
        }finally {
            super.focusLost(e);
        }
    }
}
