package com.lanko.emojiedittext;

/**
 * Created by Lex Luther on 8/13/15.
 */
public class EmojiRule {
    public static String getRegExp() {
        StringBuilder s = new StringBuilder("[");
        s.append(Character.toChars(0x270A))
                .append(Character.toChars(0x1F601))
                .append(Character.toChars(0x1F602))
                .append("]");
        return s.toString();
    }
}
