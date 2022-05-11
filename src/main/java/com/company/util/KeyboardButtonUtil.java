package com.company.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;

public class KeyboardButtonUtil {
    public static KeyboardButton button(String text) {
        return new KeyboardButton(text);
    }

    public static KeyboardRow row(KeyboardButton... buttons) {
        KeyboardRow row = new KeyboardRow();
        row.addAll(Arrays.asList(buttons));
        return row;
    }

    public static ReplyKeyboardMarkup menuUZ() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(Arrays.asList(
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("\uD83D\uDCBC Rezyume")),
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("\uD83D\uDCD2 Biz haqimizda"), KeyboardButtonUtil.button("\uD83C\uDFDE Galereya")),
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("\uD83D\uDDFA Bizning Manzil"), KeyboardButtonUtil.button("\uD83D\uDCE8 Izoh qoldirish")),
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("⚙ Sozlamalar")))
        );
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup menuRU() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(Arrays.asList(
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("\uD83D\uDCBC Резюме")),
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("\uD83D\uDCD2 О нас"), KeyboardButtonUtil.button("\uD83C\uDFDE Галерея")),
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("\uD83D\uDDFA Наше местоположение"), KeyboardButtonUtil.button("\uD83D\uDCE8 Оставить комментарий")),
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("⚙ Настройки")))
        );
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup twinsButtonUZ() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("O'tkazib yuborish")),
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("⬅ Ortga"), KeyboardButtonUtil.button("Menu"))
        ));
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup twinsButtonUZnoBack() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("⬅ Ortga"), KeyboardButtonUtil.button("Menu"))
        ));
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup shareContactUZ() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardButton btn1 = new KeyboardButton("Kontakt ulashish");
        btn1.setRequestContact(true);
        KeyboardButton btn2 = new KeyboardButton("⬅ Ortga");
        KeyboardButton btn3 = new KeyboardButton("Menu");
        KeyboardRow row1 = new KeyboardRow();
        row1.add(btn1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(btn2);
        row2.add(btn3);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(Arrays.asList(row1, row2));
        return replyKeyboardMarkup;
    }


    public static ReplyKeyboardMarkup twinsButtonRU() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("Пропускать")),
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("⬅ Назад"), KeyboardButtonUtil.button("Меню"))
        ));
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup twinsButtonRUnoBack() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("⬅ Назад"), KeyboardButtonUtil.button("Меню"))
        ));
        return replyKeyboardMarkup;
    }


    public static ReplyKeyboardMarkup onlyMenuUZ() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("Menu"))
        ));
        return replyKeyboardMarkup;
    }
    public static ReplyKeyboardMarkup onlyMenuRU() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("Меню"))
        ));
        return replyKeyboardMarkup;
    }
//    поделиться контактом
public static ReplyKeyboardMarkup shareContactRU() {
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    KeyboardButton btn1 = new KeyboardButton("Поделиться контактом");
    btn1.setRequestContact(true);
    KeyboardButton btn2 = new KeyboardButton("⬅ Назад");
    KeyboardButton btn3 = new KeyboardButton("Меню");
    KeyboardRow row1 = new KeyboardRow();
    row1.add(btn1);
    KeyboardRow row2 = new KeyboardRow();
    row2.add(btn2);
    row2.add(btn3);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setKeyboard(Arrays.asList(row1, row2));
    return replyKeyboardMarkup;
}



    public static ReplyKeyboardMarkup agreePhotoButtonUZ() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("✅ Tugatish"))
        ));
        return replyKeyboardMarkup;
    }


    public static ReplyKeyboardMarkup agreePhotoButtonRU() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(
                KeyboardButtonUtil.row(KeyboardButtonUtil.button("✅ Завершение"))
        ));
        return replyKeyboardMarkup;
    }




}
