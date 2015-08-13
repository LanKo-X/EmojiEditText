package com.lanko.emojiedittext;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Lex Luther on 8/13/15.
 */
public class EmojiRule {

    private static EmojiRule instance;

    /**
     * emojiMap< Emoji字符串, 表情文件路径 >
     */
    private Map<String, String> emojiMap;
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

        /**
         * 初始化 emojiMap
         * 此处代码对表情文件命名要求非常严格
         * 表情文件命名格式为：emoji_0x[十六进制 Unicode].png
         */
        emojiMap = new HashMap<>();

        /**
         * 将表情文件放在 assets 中的目录中，
         * 是因为在 assets 中获取文件列表会有一些默认的文件夹出现，
         * 即使实际上并没有那些目录
         */
        String folderName = "emoji";
        String[] files = {};
        try {
            files = context.getAssets().list(folderName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String file : files) {
            int unicode = Integer.parseInt(file.split("[x.]")[1], 16);
            emojiMap.put(new String(Character.toChars(unicode)), folderName + "/" + file);
        }

    }

    public Map<String, String> getEmojiMap() {
        return emojiMap == null ? new HashMap<String, String>() : emojiMap;
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
}
