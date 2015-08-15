package com.lanko.emojiedittext;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Lex Luther on 8/13/15.
 */
public class EmojiTextView extends TextView {

    public EmojiTextView(Context context) {
        super(context);
        init();
    }

    public EmojiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmojiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (type == null) {
            type = BufferType.NORMAL;
        }
        super.setText(EmojiRule.getInstance(getContext()).convertToSpannable(text, getLineHeight()), type);
    }

}
