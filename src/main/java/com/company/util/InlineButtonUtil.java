package com.company.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InlineButtonUtil {

    public static InlineKeyboardButton button(String text, String callBack) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callBack);
        return button;
    }

    public static List<InlineKeyboardButton> row(InlineKeyboardButton... buttons) {
        return new LinkedList<>(Arrays.asList(buttons));
    }

    public static List<List<InlineKeyboardButton>> rowList(List<InlineKeyboardButton>... rows) {
        return new LinkedList<>(Arrays.asList(rows));
    }

    public static InlineKeyboardMarkup keyboard(List<List<InlineKeyboardButton>> rows) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }


    /**
     * Utils Keyboards
     */
    public static ReplyKeyboard choiceLanguage() {
        InlineKeyboardButton button1 = InlineButtonUtil.button("\uD83C\uDDF7\uD83C\uDDFA  Pусский", "_language_ru");
        InlineKeyboardButton button2 = InlineButtonUtil.button("\uD83C\uDDFA\uD83C\uDDFF  Узбекский", "_language_uz");
        List<InlineKeyboardButton> row1 = InlineButtonUtil.row(button1,button2);
        return InlineButtonUtil.keyboard(InlineButtonUtil.rowList(row1));
    }

    public static ReplyKeyboard isAgreeRU() {
        InlineKeyboardButton button1 = InlineButtonUtil.button("Да, я согласен ✅", "true");
        InlineKeyboardButton button2 = InlineButtonUtil.button("Нет, я не согласен ❌", "false");
        List<InlineKeyboardButton> row1 = InlineButtonUtil.row(button1);
        List<InlineKeyboardButton> row2 = InlineButtonUtil.row(button2);
        return InlineButtonUtil.keyboard(InlineButtonUtil.rowList(row1,row2));
    }

    public static ReplyKeyboard isAgreeUZ() {
        InlineKeyboardButton button1 = InlineButtonUtil.button("Roziman ✅", "true");
        InlineKeyboardButton button2 = InlineButtonUtil.button("Rozi emasman ❌", "false");
        List<InlineKeyboardButton> row1 = InlineButtonUtil.row(button1);
        List<InlineKeyboardButton> row2 = InlineButtonUtil.row(button2);
        return InlineButtonUtil.keyboard(InlineButtonUtil.rowList(row1,row2));
    }
}
