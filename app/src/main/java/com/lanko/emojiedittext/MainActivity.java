package com.lanko.emojiedittext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    TextView textView;
    EmojiTextView emojiTextView;
    EditText editText;
    EmojiEditText emojiEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.display_text_view);
        emojiTextView = (EmojiTextView) findViewById(R.id.display_emoji_text_view);
        editText = (EditText) findViewById(R.id.edit_text);
        emojiEditText = (EmojiEditText) findViewById(R.id.emoji_edit_text);

        display(textView, new String(Character.toChars(0x1f1e8)) +  new String(Character.toChars(0x1F1F3)));
        display(emojiTextView, new String(Character.toChars(0x1f1e8)) +  new String(Character.toChars(0x1F1F3)));

        editText.setOnEditorActionListener(this);
        emojiEditText.setOnEditorActionListener(this);

    }

    private void display(TextView textView, String s) {
        textView.setText(s);
    }

    private int checkEmoji(EditText editText) {
        String s = editText.getText().toString();

        Log.d("Chars", Arrays.toString(s.toCharArray()));

        return 0;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {

            textView.setText(v.getText().toString());
            emojiTextView.setText(v.getText().toString());

            checkEmoji((EditText)v);
        }
        return false;
    }
}
