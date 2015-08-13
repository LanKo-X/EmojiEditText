package com.lanko.emojiedittext;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView displayText;
    EditText emojiEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayText = (TextView) findViewById(R.id.display_text_view);
        emojiEditText = (EditText) findViewById(R.id.emoji_edit_text);

        emojiEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    display(displayText, emojiEditText.getText().toString());
                    checkEmoji(displayText);
                }
                return false;
            }
        });
    }

    private void display(TextView textView, String s) {
        textView.setText(s);
    }

    private int checkEmoji(TextView textView) {
        String s = textView.getText().toString();
        SpannableStringBuilder spannableString = new SpannableStringBuilder(s);

        Log.d("Chars", Arrays.toString(s.toCharArray()));

        EmojiRule rule = EmojiRule.getInstance(this);

        Pattern pattern = Pattern.compile(rule.getRegExp(), Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            InputStream is = null;
            String emoji = matcher.group();
            try {
                is = getAssets().open(rule.getEmojiMap().get(emoji));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Drawable drawable = Drawable.createFromStream(is, null);
            drawable.setBounds(0, 0, 56, 56);

            int start = matcher.start();
            int end = matcher.end();
            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            spannableString.setSpan(span, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        textView.setText(spannableString);

        return 0;
    }
}
