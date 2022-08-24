package com.company.controller;

import com.company.container.ComponentContainer;
import com.company.entity.Resume;
import com.company.enums.Language;
import com.company.enums.UserStatus;
import com.company.pdf.ObjectToPdf;
import com.company.util.InlineButtonUtil;
import com.company.util.KeyboardButtonUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class MainController {
    private final Map<String, Resume> resumeMap = new HashMap<>();
    Resume resume = null;
    ResumeController resumeController = new ResumeController();

    public void handleCallBack(User user, Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(String.valueOf(user.getId()));
        editMessageText.setMessageId(message.getMessageId());

        if (text.equals("_language_uz")) {
            sendMessage.setText("Menu");
            sendMessage.setReplyMarkup(KeyboardButtonUtil.menuUZ());
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (text.equals("_language_ru")) {
            sendMessage.setText("Mеню");
            sendMessage.setReplyMarkup(KeyboardButtonUtil.menuRU());
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);

        } else if (text.equals("true")) {
            Resume resume = resumeMap.get(String.valueOf(user.getId()));
            String response = ObjectToPdf.createCV(resume, user.getId());

            if (resume.getSelectedLanguage().equals(Language.UZ))
                editMessageText.setText("Sizning Resumingiz tayyorlanmoqda !");
            else editMessageText.setText("Ваше резюме готовится!");

            ComponentContainer.MY_TELEGRAM_BOT.send(editMessageText);

            if (response != null) {
                SendDocument sendDocumentRequest = new SendDocument();
                sendDocumentRequest.setChatId(String.valueOf(message.getChatId()));
                java.io.File file = new java.io.File(response);
                InputFile inputFile = new InputFile();
                inputFile.setMedia(file);
                sendDocumentRequest.setDocument(inputFile);
                if (resume.getSelectedLanguage().equals(Language.UZ)) {
                    sendDocumentRequest.setCaption("Resumingiz tayyor !\nResume " +
                            "[@VacanciesDifferentJob_bot](tg://VacanciesDifferentJob_bot) yordamida tayyorlandi.\n");
                } else {
                    sendDocumentRequest.setCaption("Ваше резюме готово !\nРезюме подготовлено с помощью " +
                            "[@VacanciesDifferentJob_bot](tg://VacanciesDifferentJob_bot)\n");
                }
                sendDocumentRequest.setParseMode("Markdown");
                ComponentContainer.MY_TELEGRAM_BOT.send(sendDocumentRequest);
                sendDocumentToAdmin(inputFile, user);
                file.delete();
                java.io.File imgs = new java.io.File("src/main/UserImg");
                System.out.println(Arrays.toString(imgs.listFiles()));
                for (File listFile : Objects.requireNonNull(imgs.listFiles())) {
                    System.out.println("------------------------ok-------------------------");
                    if (listFile.getName().contains(String.valueOf(user.getId()))) {
                        listFile.delete();
                    }
                }
                if (resume.getSelectedLanguage().equals(Language.UZ)) {
                    sendMessage.setText("Siz bilan tez orada aloqaga chiqamiz !");
                    sendMessage.setReplyMarkup(KeyboardButtonUtil.menuUZ());
                } else {
                    sendMessage.setText("Мы свяжемся с вами в ближайшее время !");
                    sendMessage.setReplyMarkup(KeyboardButtonUtil.menuRU());
                }

            } else {
                if (resume.getSelectedLanguage().equals(Language.UZ)) {
                    sendMessage.setReplyMarkup(KeyboardButtonUtil.menuUZ());
                    sendMessage.setText("Xatolik qaytadan urinib ko'ring !");
                } else {
                    sendMessage.setReplyMarkup(KeyboardButtonUtil.menuRU());
                    sendMessage.setText("Ошибка Попробуйте еще раз !");
                }

            }
            resumeMap.remove(String.valueOf(user.getId()));
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);

        } else if (text.equals("false")) {
            if (resume.getSelectedLanguage().equals(Language.UZ))
                editMessageText.setText("E'tiboringiz uchun rahmat !");
            else editMessageText.setText("Спасибо за Ваше внимание !");
            resumeMap.remove(String.valueOf(user.getId()));
            ComponentContainer.MY_TELEGRAM_BOT.send(editMessageText);
        }

    }

    public void handleText(User user, Message message, Contact contact) {
        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText("Недоступно❗\nЕсли вам нужна помощь, нажмите /help \nMavjud emas❗\nYordam kerak bo'lsa /help ni bosing.");

//        ---------- States
        switch (text) {
            case "Menu":
                sendMessage.setText("Menu");
                sendMessage.setReplyMarkup(KeyboardButtonUtil.menuUZ());
                ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
                ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.MENU);
                break;
            case "Меню":
                sendMessage.setText("Mеню");
                sendMessage.setReplyMarkup(KeyboardButtonUtil.menuRU());
                ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
                ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.MENU);
                break;
            case "/start":
                resume = new Resume();
//                resume.setSelectedLanguage(Language.UZ);
                resumeMap.put(String.valueOf(user.getId()), resume);
//                resume.setUserId(String.valueOf(user.getId()));
                StringBuilder greetingMes = new StringBuilder();
                greetingMes.append("Добро пожаловать в бот <b>\"Find Vacancy\"</b> !");
                greetingMes.append("\n");
                greetingMes.append("Пожалуйста, выберите язык общения");
                greetingMes.append("\n\n");
                greetingMes.append("Assalomu alaykum <b>\"Find Vacancy\"</b> botiga xush kelibsiz !");
                greetingMes.append("\n");
                greetingMes.append("Iltimos muloqot tilini tanlang");
                sendMessage.setText(String.valueOf(greetingMes));
                sendMessage.setParseMode("HTML");

                sendMessage.setReplyMarkup(InlineButtonUtil.choiceLanguage());
                ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);

                break;
            case "\uD83D\uDCBC Rezyume":
                resume = new Resume();
                resume.setSelectedLanguage(Language.UZ);
                resumeMap.put(String.valueOf(user.getId()), resume);
                sendMessage.setText("Savollarga aniq va na'munada ko'rsatilganidek javob bering !");
                resume.setId(ComponentContainer.nextId());
                ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.RESUME);
                ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
                break;
            case "\uD83D\uDCD2 Biz haqimizda":
                java.io.File file = new java.io.File("src/main/AboutCompany/work4.jpg");
                InputFile inputFile = new InputFile();
                inputFile.setMedia(file);
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setPhoto(inputFile);
                sendPhoto.setCaption("Kompaniyaning afzalliklari va kamchiliklari Kompaniyani ochishning afzalliklari daromadlarni diversifikatsiya qilish, mehnat va mukofot o'rtasidagi yaqin munosabatlar, ijodiy erkinlik va moslashuvchanlikni o'z ichiga oladi. Korxonalarning yana bir afzalligi shundaki, ularda ish o‘rinlari yaratiladi. Agar shaxs kompaniyani yaratsa va u rivojlansa, ko'pincha u xodimlarni yollashi kerak. Bu mamlakatda mavjud bo'lgan ish o'rinlari sonini oshiradi, odamlarni ish bilan ta'minlaydi, ishsizlikni kamaytiradi va iqtisodiyotga boylik keltiradi. Ko'pincha o'z kompaniyangizni ochish katta shaxsiy mamnuniyat keltiradi. Bu sizning orzularingiz va ehtiroslaringizga ergashish va kompaniyani tashkil etishdan meros qoldirishni o'z ichiga oladi, jumladan, moliyaviy mas'uliyatni oshirish, qonuniy javobgarlikni oshirish, ishda uzoq vaqt ishlash, stress tufayli sog'liq uchun xavflar, xodimlar va ma'muriy xodimlar uchun javobgarlik, qoidalar va soliq masalalari.");
                sendPhoto.setChatId(String.valueOf(user.getId()));
                ComponentContainer.MY_TELEGRAM_BOT.send(sendPhoto);
                ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.ABOUTCOMPANY);
                break;
            case "\uD83D\uDDFA Bizning Manzil":
                sendMessage.setText("" +
                        "[\uD83C\uDFE2 Bizning manzil](https://www.google.com/maps/place/PDP+Academy/@41.3264751,69.2284847,15z/data=!4m5!3m4!1s0x0:0xf9e01b5d45fc68cd!8m2!3d41.3264751!4d69.2284847)\n" +
                        "[\uD83E\uDD35\u200D♂ Admin bilan bo'glanish](tg://user?id=1438229631)\n" +
                        "\uD83D\uDCDE +99888-222-12-12;\n" +
                        "\uD83D\uDCDE +99871-254-00-00\n" +
                        "⏱ 10:00 - 22:00\n" +
                        "\uD83D\uDCCD  Yunusobod tumani," +
                        " Tinchlik ko'chasi");
                sendMessage.enableMarkdown(true);
                ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.ADDRESS);
                ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
                break;
            case "\uD83C\uDFDE Галерея":
            case "\uD83C\uDFDE Galereya":
                File file1 = new File("src/main/AboutCompany/");
                List<File> files = new java.util.ArrayList<>(List.of(file1.listFiles()));

                Collections.shuffle(files);

                for (File f : files) {
                    sendShufflePhotos(f.getPath(), user);
                }
                ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.GALLERY);
                break;
            case "\uD83D\uDCE8 Izoh qoldirish":
                admin_contactUZ(user, sendMessage);
                break;
            case "⚙ Sozlamalar":
                sendMessage.setText("Iltimos muloqot tilini tanlang");
                sendMessage.setReplyMarkup(InlineButtonUtil.choiceLanguage());
                ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
                ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.SETTINGS);
                break;

            case "\uD83D\uDCBC Резюме":
                resume = new Resume();
                resume.setSelectedLanguage(Language.RU);
                resumeMap.put(String.valueOf(user.getId()), resume);
                sendMessage.setText("Отвечайте на вопросы четко и так, как показано в примере !");
                resume.setId(ComponentContainer.nextId());
                ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.RESUME);
                ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
                break;
            case "\uD83D\uDCD2 О нас":
                java.io.File fileru = new java.io.File("src/main/AboutCompany/work4.jpg");
                InputFile inputFile1 = new InputFile();
                inputFile1.setMedia(fileru);
                SendPhoto sendPhoto1 = new SendPhoto();
                sendPhoto1.setPhoto(inputFile1);
                sendPhoto1.setCaption("Преимущества и недостатки компании К преимуществам создания компании относятся диверсификация доходов, тесная взаимосвязь между усилиями и вознаграждением, свобода творчества и гибкость. Еще одним преимуществом компаний является то, что они создают рабочие места. Если физическое лицо создает компанию и она растет, чаще всего ему приходится нанимать сотрудников. Это увеличивает количество рабочих мест, доступных в стране, дает работу людям, снижает безработицу и приносит богатство в экономику. Часто создание собственной компании приносит огромное личное удовлетворение. Это включает в себя следование вашим мечтам и страстям и оставление наследия от создания компании, включая повышенную финансовую ответственность, повышенную юридическую ответственность, долгий рабочий день, риски для здоровья из-за стресса, ответственность за сотрудников и административный персонал, правила и налоговые вопросы.");
                sendPhoto1.setChatId(String.valueOf(user.getId()));
                ComponentContainer.MY_TELEGRAM_BOT.send(sendPhoto1);
                ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.ABOUTCOMPANY);
                break;
            case "\uD83D\uDDFA Наше местоположение":
                sendMessage.setText("" +
                        "[\uD83C\uDFE2 Наше местоположение](https://www.google.com/maps/place/PDP+Academy/@41.3264751,69.2284847,15z/data=!4m5!3m4!1s0x0:0xf9e01b5d45fc68cd!8m2!3d41.3264751!4d69.2284847)\n" +
                        "[\uD83E\uDD35\u200D♂ Связаться с администратором](tg://user?id=1438229631)\n" +
                        "\uD83D\uDCDE +99888-222-12-12;\n" +
                        "\uD83D\uDCDE +99871-254-00-00\n" +
                        "⏱ 10:00 - 22:00\n" +
                        "\uD83D\uDCCD  Юнусабадский район," +
                        " Улица Мира");
                sendMessage.enableMarkdown(true);
                ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.ADDRESS);
                ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
                break;

            case "\uD83D\uDCE8 Оставить комментарий":
                admin_contactRU(user, sendMessage);
                break;
            case "⚙ Настройки":
                sendMessage.setText("Пожалуйста, выберите язык общения");
                sendMessage.setReplyMarkup(InlineButtonUtil.choiceLanguage());
                ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
                ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.SETTINGS);
                break;
            case "/settings":
                StringBuilder settingsMess = new StringBuilder();
                settingsMess.append("Пожалуйста, выберите язык общения");
                settingsMess.append("\n");
                settingsMess.append("Iltimos muloqot tilini tanlang");
                sendMessage.setText(String.valueOf(settingsMess));
                sendMessage.setParseMode("HTML");

                sendMessage.setReplyMarkup(InlineButtonUtil.choiceLanguage());
                ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
                break;
            case "/help":
                sendMessage.setText("Batafsil ma'lumot uchun <b>+998992222222</b>");
                sendMessage.setParseMode("HTML");
                ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
                break;

        }
//        editMessageText.setText("Enter Task Title:");

        if (resumeMap.size() > 0) {
            checkStatus(user, message, contact);
        }
    }

    private void checkStatusContact(User user, Contact contact) {
        Resume currentUser = resumeMap.get(String.valueOf(user.getId()));
        UserStatus currentStatus = ComponentContainer.userStatusMap.get(String.valueOf(user.getId()));
        resumeController.handleResumeStatusContact(currentUser, currentStatus, user, contact);
    }

    private void checkStatus(User user, Message message, Contact contact) {
        Resume currentUser = resumeMap.get(String.valueOf(user.getId()));
        UserStatus currentStatus = ComponentContainer.userStatusMap.get(String.valueOf(user.getId()));
        if (currentStatus.equals(UserStatus.FEEDBACK) && !message.getText().equals("\uD83D\uDCE8 Izoh qoldirish") && !message.getText().equals("\uD83D\uDCE8 Оставить комментарий")) {
            sendToAdmin(message, user);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(user.getId()));
                sendMessage.setText("Сообщение успешно отправлено !\nXabar muvaffaqiyatli yuborildi !");
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.MENU);
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        }

        if (
                message.getText().equals("⬅ Ortga") ||
                        message.getText().equals("⬅ Назад")
        ) {
            String statusChanger = String.valueOf(currentStatus);
            if (statusChanger.startsWith("s")) {
                String number = statusChanger.substring(1);
                System.out.println("---------------------------------------" + number);
                int num = Integer.parseInt(number);
                num -= 2;
                if (num <= 0) currentStatus = UserStatus.RESUME;
                else {
                    System.out.println("number-- : " + num);
                    UserStatus userStatus = UserStatus.valueOf("s" + num);
                    currentStatus = userStatus;
                }
            }
        }
        if (contact != null) {
            System.out.println("checkStatus -------------" + contact.getPhoneNumber());
            System.out.println("checkStatus -------------" + currentStatus);
        }
        System.out.println("TEST ------------ Main Controllerda");
        resumeController.handleResumeStatus(currentUser, currentStatus, user, message, contact);
    }

    private void sendDocumentToAdmin(InputFile inputFile, User user) {
        SimpleDateFormat format4 = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        SendDocument sendDocumentRequest = new SendDocument();
        sendDocumentRequest.setChatId(ComponentContainer.ADMIN);
        sendDocumentRequest.setDocument(inputFile);
        sendDocumentRequest.setCaption( "Sizga " + "[" + user.getFirstName() + "](https://t.me/" + user.getUserName() + ")" + " dan Resume qoldirildi.\nHabar qoldirilgan vaqt: "
                + format4.format(new Date()));
//        https://t.me/Sardorbek_Rakhmatjonov
        sendDocumentRequest.setParseMode("Markdown");
        ComponentContainer.MY_TELEGRAM_BOT.send(sendDocumentRequest);
    }


    private void sendToAdmin(Message message, User user) {
        SimpleDateFormat format4 = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(ComponentContainer.ADMIN);
        sendMessage.setText("Sizga " + "[" + user.getFirstName() + "](tg://user?id=" + user.getId() + ")" + " dan yangi habar qoldirildi.\nHabar qoldirilgan vaqt: "
                + format4.format(new Date()) + "\nHabar matni: " + message.getText()
        );
        sendMessage.enableMarkdown(true);
        ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
    }


    private void admin_contactUZ(User user, SendMessage sendMessage) {
        ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.FEEDBACK);
        sendMessage.setText("Taklif va shikoyatlar qoldirishingiz mumkin.\nHabarni bir martada yozishga harakat qiling !\n«Izoh qoldirish» tugmasini bosgandan keyin bir marta habar yuborish mumkin.");
        ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
    }

    private void admin_contactRU(User user, SendMessage sendMessage) {
        ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.FEEDBACK);
        sendMessage.setText("Вы можете оставлять предложения и жалобы.\nПопробуйте написать сообщение один раз!\nВы можете отправить сообщение один раз, нажав кнопку «Оставить комментарий».");
        ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
    }


    private void log_photo(User user, List<PhotoSize> photoList) {
        String str = String.format(LocalDateTime.now() + ",  userId: %d, firstName: %s, lastName: %s",
                user.getId(), user.getFirstName(), user.getLastName());
        System.out.println(str);
        for (PhotoSize photo : photoList) {
            System.out.println(photo.getWidth() + " " +
                    photo.getHeight() +
                    " FileId: " + photo.getFileId());
        }
    }


    private void sendShufflePhotos(String path, User user) {
        java.io.File file = new java.io.File(path);
        InputFile inputFile = new InputFile();
        inputFile.setMedia(file);

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setChatId(String.valueOf(user.getId()));
        ComponentContainer.MY_TELEGRAM_BOT.send(sendPhoto);
    }

    public void handlePhotoUpload(User user, Message message) {
        log_photo(user, message.getPhoto());
//        checkStatus(user, message);
    }

    public void handleContactUpload(User user, Contact contact) {
        System.out.println("test handleContactUpload------------" + contact.getPhoneNumber());
        checkStatusContact(user, contact);
    }
}
