package com.company;

import com.company.container.ComponentContainer;
import com.company.controller.MainController;
import com.company.enums.UserStatus;
import com.company.imageDownloader.DownloadControl;
import com.company.imageDownloader.ImageDTO;
import com.google.gson.Gson;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private MainController mainController = new MainController();

    @Override
    public String getBotUsername() {
        return "VacanciesDifferentJob_bot";
    }

    @Override
    public String getBotToken() {
        return "5188722391:AAFuO-Pvm15g666JoNUFgIgZd8Z8ACFEL-E";
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            User user = message.getFrom();
            if (message.hasText()) {
                log(user, message.getText());
                Contact contact = null;
                mainController.handleText(user, message, contact);
            } else if (message.hasPhoto()) {
                if (ComponentContainer.userStatusMap.get(String.valueOf(user.getId())).equals(UserStatus.s28)) {
                    List<PhotoSize> photos = update.getMessage().getPhoto();
                    System.out.println("1\t - " + message.getPhoto());
                    String f_id = photos.stream()
                            .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                            .findFirst()
                            .orElse(null).getFileId();
                    System.out.println("fileId : " + f_id);

                    Gson gson = new Gson();
                    String str = DownloadControl.getNewLink(f_id);
                    ImageDTO imageDTO = gson.fromJson(str, ImageDTO.class);
                    System.out.println("mana path - " + imageDTO.getResult().getFile_path());
                    DownloadControl.getImage(imageDTO.getResult().getFile_path(), String.valueOf(user.getId()));
                    mainController.handlePhotoUpload(user, message);
                } else {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(message.getChatId()));
                    sendMessage.setText("Недоступно❗\nЕсли вам нужна помощь, нажмите /help \nMavjud emas❗\nYordam kerak bo'lsa /help ni bosing.");
                    ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
                }
            } else if (message.hasContact()) {
                if (ComponentContainer.userStatusMap.get(String.valueOf(user.getId())).equals(UserStatus.s5)) {
                    Contact contact = message.getContact();
                    System.out.println("test---------------------" + contact.getPhoneNumber());
                    mainController.handleContactUpload(user, contact);
                } else {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(message.getChatId()));
                    sendMessage.setText("Недоступно❗\nЕсли вам нужна помощь, нажмите /help \nMavjud emas❗\nYordam kerak bo'lsa /help ni bosing.");
                    ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
                }
            }

        } else if (update.hasCallbackQuery()) {
            Message message = update.getCallbackQuery().getMessage();
            User user = update.getCallbackQuery().getFrom();
            String text = update.getCallbackQuery().getData();
            log(user, text);
            mainController.handleCallBack(user, message, text);
        }

    }

    public void log(User user, String text) {
        String str = String.format(LocalDateTime.now() + ",  userId: %d, firstName: %s, lastName: %s, text: %s",
                user.getId(), user.getFirstName(), user.getLastName(), text);
        System.out.println(str);
    }

    public void send(Object object) {
        try {
            if (object instanceof SendMessage) {
                execute((SendMessage) object);
            } else if (object instanceof EditMessageText) {
                execute((EditMessageText) object);
            } else if (object instanceof SendPhoto) {
                execute((SendPhoto) object);
            } else if (object instanceof SendVideo) {
                execute((SendVideo) object);
            } else if (object instanceof SendContact) {
                execute((SendContact) object);
            } else if (object instanceof SendDocument) {
                execute((SendDocument) object);
            } else if (object instanceof SendLocation) {
                execute((SendLocation) object);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
