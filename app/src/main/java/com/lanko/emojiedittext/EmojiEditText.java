package com.lanko.emojiedittext;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Lex Luther on 8/14/15.
 */
public class EmojiEditText extends EditText {

    public EmojiEditText(Context context) {
        super(context);
        init();
    }

    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {

        setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (EmojiRule.getInstance(getContext()).hasEmoji(source)) {
                            return EmojiRule.getInstance(getContext()).convertToSpannable(source);
                        }
                        return source;
                    }
                }
        });

    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (type == null) {
            type = BufferType.EDITABLE;
        }
        super.setText(EmojiRule.getInstance(getContext()).convertToSpannable(text), type);
    }

}
