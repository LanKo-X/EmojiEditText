package com.lanko.emojiedittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.util.ArrayMap;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lex Luther on 8/13/15.
 */
public class EmojiRule {

    private static EmojiRule instance;

    private Context context;

    /**
     * emojiMap< Emoji字符串, 表情文件路径 >
     */
    private ArrayMap<String, String> emojiMap;
    private String regExp;

    public static EmojiRule getInstance(Context context) {
        if (instance == null) {
            synchronized (EmojiRule.class) {
                if (instance == null)
                    instance = new EmojiRule(context);
            }
        }
        return instance;
    }

    private EmojiRule(Context context) {

        this.context = context;

        /**
         * 初始化 emojiMap
         * 此处代码对表情文件命名要求非常严格
         * 表情文件命名格式为：[十六进制 Unicode].png
         */
        emojiMap = new ArrayMap<>();

        /**
         * 将表情文件放在 assets 中的目录中，
         * 是因为在 assets 中获取文件列表会有一些默认的文件夹出现，
         * 即使实际上并没有那些目录
         */
        String folderName = "emoji";
        String[] files = {};
        try {
            files = this.context.getAssets().list(folderName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String file : files) {
            int unicode = Integer.parseInt(file.split("[.]")[0], 16);
            emojiMap.put(new String(Character.toChars(unicode)), folderName + "/" + file);
        }

    }

    public ArrayMap<String, String> getEmojiMap() {
        return emojiMap == null ? new ArrayMap<String, String>() : emojiMap;
    }

    public String getRegExp() {
        if (regExp == null || regExp.length() <= 2) {
            Iterator<String> iterator = emojiMap.keySet().iterator();
            StringBuilder s = new StringBuilder("[");
            while (iterator.hasNext()) {
                s.append(iterator.next());
            }
            s.append("]");
            regExp = s.toString();
        }

        return regExp;
    }

    public boolean hasEmoji(CharSequence text) {
        Pattern pattern = Pattern.compile(this.getRegExp(), Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        return matcher.find();
    }

    public SpannableStringBuilder convertToSpannable(CharSequence text, int lineHeight) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        Pattern pattern = Pattern.compile(this.getRegExp(), Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            InputStream is = null;
            String emoji = matcher.group();
            try {
                is = context.getAssets().open(getEmojiMap().get(emoji));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Drawable drawable = Drawable.createFromStream(is, null);

            drawable.setBounds(0, 0, lineHeight, lineHeight);

            int start = matcher.start();
            int end = matcher.end();
            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            builder.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return builder;
    }
}
