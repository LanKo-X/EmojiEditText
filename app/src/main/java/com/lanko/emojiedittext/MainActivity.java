package com.lanko.emojiedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

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

        Log.d("Chars", Arrays.toString(s.toCharArray()));

        Pattern pattern = Pattern.compile(EmojiRule.getRegExp(), Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            s = s.replace(matcher.group(), "[EMOJI]");
            Log.d("Found!", matcher.group());
        }

        textView.setText(s);

        return 0;
    }
}
