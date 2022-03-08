package com.company.controller;

import com.company.container.ComponentContainer;
import com.company.entity.Resume;
import com.company.enums.Language;
import com.company.enums.UserStatus;
import com.company.util.InlineButtonUtil;
import com.company.util.KeyboardButtonUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

public class ResumeController {

    public void handleResumeStatusContact(Resume currentUser, UserStatus currentStatus, User user, Contact contact) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));

        System.out.println("TEST ----------- s5 da");
        currentUser.setPhoneNumber(contact.getPhoneNumber());
        ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s6);
        if (currentUser.getSelectedLanguage().equals(Language.UZ))
            sendMessage.setText("\uD83D\uDCE7 Emailgizni kiriting.\nNamuna: qwert@gmail.com, ...");
        else
            sendMessage.setText("\uD83D\uDCF1 Введите свой номер телефона или нажмите «Поделиться контактом».\nОбразец +998901112233, ....");


        ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
    }

    public void handleResumeStatus(Resume currentUser, UserStatus currentStatus, User user, Message message, Contact contact) {
        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
//
        if (text.equals("O'tkazib yuborish") ||
                text.equals("⬅ Назад") ||
                text.equals("⬅ Ortga") ||
                text.equals("Пропускать")
        ) {
            text = "";
        }


        if (currentStatus.equals(UserStatus.RESUME) ||
                currentStatus.equals(UserStatus.s2) ||
                currentStatus.equals(UserStatus.s3) ||
                currentStatus.equals(UserStatus.s4) ||
                currentStatus.equals(UserStatus.s5) ||
                currentStatus.equals(UserStatus.s6) ||
                currentStatus.equals(UserStatus.s7) ||
                currentStatus.equals(UserStatus.s8) ||
                currentStatus.equals(UserStatus.s9) ||
                currentStatus.equals(UserStatus.s10) ||
                currentStatus.equals(UserStatus.s11) ||
                currentStatus.equals(UserStatus.s12) ||
                currentStatus.equals(UserStatus.s19) ||
                currentStatus.equals(UserStatus.s24)
        ) {
            if (currentUser.getSelectedLanguage().equals(Language.UZ))
                sendMessage.setReplyMarkup(KeyboardButtonUtil.twinsButtonUZnoBack());
            else sendMessage.setReplyMarkup(KeyboardButtonUtil.twinsButtonRUnoBack());
        } else {
            if (currentUser.getSelectedLanguage().equals(Language.UZ))
                sendMessage.setReplyMarkup(KeyboardButtonUtil.twinsButtonUZ());
            else sendMessage.setReplyMarkup(KeyboardButtonUtil.twinsButtonRU());

        }
        System.out.println("USer Language -----------------------" + currentUser.getSelectedLanguage());

        if (currentUser.getSelectedLanguage().equals(Language.UZ)) {
            checkStatusUZ(text, sendMessage, currentUser, currentStatus, user);

        } else {
            checkStatusRU(text, sendMessage, currentUser, currentStatus, user);
        }
    }

    private void checkStatusUZ(String text, SendMessage sendMessage, Resume currentUser, UserStatus currentStatus, User user) {
        if (currentStatus.equals(UserStatus.RESUME)) {
            sendMessage.setText("\uD83D\uDC64 Iltimos Familiya Ism Sharifingizni kiriting.\nNamuna: Aliyev Alijon Vali og'li.");
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s1);
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s1)) {
            currentUser.setNSF(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s2);
            sendMessage.setText("Siz hohlayotgan Lavozimni kiriting.\nNamuna: Project Manager");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s2)) {
            currentUser.setPosition(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s3);
            sendMessage.setText("\uD83D\uDCC6 Tug'ulgan kun oy va yilingizni kiriting.\nNamuna: 01.01.2000");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s3)) {
            currentUser.setDateOfBirth(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s4);
            sendMessage.setText("\uD83C\uDFE0 Yashash manzilingizni kiriting.\nNamuna:Toshkent, Shayxontohur, Tinchlik ko'chasi 9-uy");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s4)) {
            currentUser.setAddress(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s5);
            sendMessage.setReplyMarkup(KeyboardButtonUtil.shareContactUZ());
            sendMessage.setText("\uD83D\uDCF1 Telefon raqamingizni kiriting yoki «Kontakt ulashish» ni bosing.\nNamuna +998901112233, ....");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s5)) {
            currentUser.setPhoneNumber(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s6);
            sendMessage.setText("\uD83D\uDCE7 Emailgizni kiriting.\nNamuna: qwert@gmail.com, ...");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s6)) {
            currentUser.setEmail(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s7);
            sendMessage.setText("Ma'lumotingiz ? \nNamuna: Oliy, O'rta, ...");  // tugma chiqadi
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s7)) {
            currentUser.setLevelOfEducation(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s8);
            sendMessage.setText("\uD83C\uDFDB Ta'lim muassasa nomi ?\nNamuna: Toshkent Axborot Texnologiyalari Universiteti");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s8)) {
            currentUser.setNameOfTheInstitution(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s9);
            sendMessage.setText("Fakultet nomi ?\nNamuna: Axborot Xavfsizligi");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s9)) {
            currentUser.setFaculty(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s10);
            sendMessage.setText("Mutahasisligingizni kiriting ?\nNamuna:Biolog");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s10)) {
            currentUser.setProfession(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s11);
            sendMessage.setText("\uD83D\uDCC6 Mutahasislik bo'yicha o'qigan yillaringiz ?\nNamuna: 2015-2019");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s11)) {
            currentUser.setYearOfStudy(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s12);
            sendMessage.setText("Ta'lim olgan shahringiz ?\nNamuna: Paris");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s12)) {
            currentUser.setNameOfStudiedCity(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s13);
            sendMessage.setText("Oxirgi ishlagan lavozimingiz ?\nQayerda ishlagansiz ?\nNamuna: Toshkent shahar 35-IDUM, Direktor o'rinbosari.");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s13)) {
            currentUser.setYouLastWork(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s14);
            sendMessage.setText("Ishlagan joyingizdagi asosiy majburiyatlaringiz ?\nNamuna: O'quvchilar intizomini nazorat qilish, ...");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s14)) {
            currentUser.setFunctionalResponsibilities(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s15);
            sendMessage.setText("Erishgan yutuqlaringiz ?\nNamuna: «Yil O'qituvchisi» Respublika bosqichi sovrindori, ...");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s15)) {
            currentUser.setYourAchievements(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s16);
            sendMessage.setText("\uD83E\uDDD1\uD83C\uDFFD\u200D\uD83D\uDCBB Komyuterdan foydalanish darajangiz ?\nNamuna: MSOffice, Adobe Photoshop, ...");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s16)) {
            currentUser.setComputerUseRate(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s17);
            sendMessage.setText("Qaysi tillarni bilasiz ?\nNamuna: Rus tili, Engliz tili (EILTS 7.0), ...");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s17)) {
            currentUser.setKnowledgeOfLanguages(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s18);
            sendMessage.setText("Ko'nikmalaringiz (Qobiliyat) ?\nNamuna: Ko‘nikmalar – muzokaralar olib borish va muzokaralar olib borish qobiliyati");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s18)) {
            currentUser.setYourAbility(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s19);
            sendMessage.setText("Shaxsiy sifatlaringiz ?\nNamuna: E'tiborli, Tirishqoq, Ma'suliyatli, ...");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s19)) {
            currentUser.setPersonalQuality(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s20);
            sendMessage.setText("\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67 Oilaviy ahvolingiz ?\nNamuna: Bo'ydoq");   // keyboard button
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s20)) {
            currentUser.setMaritalStatus(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s21);
            sendMessage.setText("\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66 Bolalaringiz bormi ?\n(Agar bo'lsa ularning sonini kiriting)");   // 1..9 button chiqarish.
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s21)) {
            currentUser.setChildren(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s22);
            sendMessage.setText("\uD83D\uDCB3 Xaydovchilik guvohnomangiz bo'lsa toifasini kiriting ?\nNamuna: B,C / yo'q");   // keyboard B   C  D  A  yo'q
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s22)) {
            currentUser.setDriversLicense(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s23);
            sendMessage.setText("\uD83D\uDE98 Mashinangiz bormi bo'lsa nomini kiriting ?\nNamuna: Gentra / yo'q");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s23)) {
            currentUser.setAuto(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s24);
            sendMessage.setText("\uD83D\uDEC2 Xalqaro passportingiz bormi ?");   // ha yo'q  button
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s24)) {
            currentUser.setInternationalPassport(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s25);
            sendMessage.setText("Ish yuzasidan, xizmat safariga tayyormisiz ❓");    // ha yo'q  button
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s25)) {
            currentUser.setReadyForBusinessTrip(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s26);
            sendMessage.setText("\uD83D\uDC6E\u200D Harbiy xizmatga tegishliligingiz ?\nNamuna: 1 oylik Harbiy xizmatga borganman.");  // button bo'lsa zo'r bolardi.
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s26)) {
            currentUser.setAffiliationToMilitary(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s27);
            sendMessage.setText("♥ Sevimli  mashg'ulotlaringiz ?\nNamuna: Kitob o'qish, Taom tayyorlash, ...");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s27)) {
            sendMessage.setReplyMarkup(KeyboardButtonUtil.agreePhotoButtonUZ());
            currentUser.setHobbies(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s28);
            sendMessage.setText("Rasmingizni yuboring ?\n \uD83D\uDCCE belgini bosish orqali rasmingizni yuboring.\n'Tugatish' tugmasini bosmaguncha rasmni qayta yuklash mumkin !");
            System.out.println(currentUser);
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s28)) {
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.RESUMEEND);
            sendMessage.setText("\uD83D\uDCD6 Eslatma ❗️\nHurmatli foydalanuvchi biz sizning rezyumeingizni boshqa kompaniyalar yoki ish beruvchilarga ish takliflari bo'yicha siz bilan bog'lanish uchun yuborishimiz mumkin, siz bizning shartlarimizga rozimisiz ?");
            sendMessage.setReplyMarkup(InlineButtonUtil.isAgreeUZ());
            System.out.println(currentUser);
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        }
    }

    private void checkStatusRU(String text, SendMessage sendMessage, Resume currentUser, UserStatus currentStatus, User user) {


        if (currentStatus.equals(UserStatus.RESUME)) {
            sendMessage.setText("\uD83D\uDC64 Пожалуйста, введите свой Ф.И.О\nПример: Алиев Али Валиевич");
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s1);
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s1)) {
            currentUser.setNSF(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s2);
            sendMessage.setText("Введите желаемую позицию.\nПример: менеджер проекта");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s2)) {
            currentUser.setPosition(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s3);
            sendMessage.setText("\uD83D\uDCC6 Введите дату своего рождения, месяц и год.\nПример: 01.01.2000");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s3)) {
            currentUser.setDateOfBirth(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s4);
            sendMessage.setText("\uD83C\uDFE0 Введите свой адрес.\nПример: Ташкент, Шайхантахур, улица Тинчлик 9");
            sendMessage.setReplyMarkup(KeyboardButtonUtil.shareContactRU());
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s4)) {
            currentUser.setAddress(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s5);
            sendMessage.setText("\uD83D\uDCF1 Введите свой номер телефона или нажмите «Поделиться контактом».\nОбразец +998901112233, ....");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s5)) {
            currentUser.setPhoneNumber(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s6);
            sendMessage.setText("\uD83D\uDCE7 Введите адрес электронной почты.\nПример: qwert@gmail.com, ...");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s6)) {
            currentUser.setEmail(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s7);
            sendMessage.setText("Образование\nПример:Средне специальный, Высший");  // tugma chiqadi
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s7)) {
            currentUser.setLevelOfEducation(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s8);
            sendMessage.setText("\uD83C\uDFDB Название учебного заведения ?\nПример: Ташкентский университет информационных технологий");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s8)) {
            currentUser.setNameOfTheInstitution(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s9);
            sendMessage.setText("Название факультета ?\nПример: информационная безопасность");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s9)) {
            currentUser.setFaculty(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s10);
            sendMessage.setText("Укажите свою специализацию ?\nПример: биолог");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s10)) {
            currentUser.setProfession(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s11);
            sendMessage.setText("\uD83D\uDCC6 Годы обучения по специальности ?\nПример: 2015-2019 гг.");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s11)) {
            currentUser.setYearOfStudy(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s12);
            sendMessage.setText("В каком городе вы учились ?\nПример: Париж");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s12)) {
            currentUser.setNameOfStudiedCity(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s13);
            sendMessage.setText("Ваша последняя позиция ?\nГде вы работали ?\nПример: ИДУМ 35, г.Ташкент, заместитель директора.");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s13)) {
            currentUser.setYouLastWork(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s14);
            sendMessage.setText("Ваши основные обязанности на работе ?\nПример: Контроль дисциплины учащихся,...");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s14)) {
            currentUser.setFunctionalResponsibilities(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s15);
            sendMessage.setText("Ваши достижения ?\nПример: Победитель республиканского этапа «Учитель года», ...");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s15)) {
            currentUser.setYourAchievements(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s16);
            sendMessage.setText("\uD83E\uDDD1\uD83C\uDFFD\u200D\uD83D\uDCBB Ваш уровень владения компьютером ?\nПример: MSOffice, Adobe Photoshop, ...");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s16)) {
            currentUser.setComputerUseRate(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s17);
            sendMessage.setText("Какие языки вы знаете ?\nПример: русский, английский (IELTS 7.0),...");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s17)) {
            currentUser.setKnowledgeOfLanguages(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s18);
            sendMessage.setText("Ваши навыки ?\nПример: Умение вести переговоры и вести переговоры");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s18)) {
            currentUser.setYourAbility(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s19);
            sendMessage.setText("Ваши личные качества ?\nПример: Внимательный, Прилежный, Ответственный, ...");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s19)) {
            currentUser.setPersonalQuality(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s20);
            sendMessage.setText("\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67 Ваше семейное положение ❓\nПример: одиночный");   // keyboard button
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s20)) {
            currentUser.setMaritalStatus(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s21);
            sendMessage.setText("\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66 У вас есть дети ?\n(Введите их количество, если есть)");   // 1..9 button chiqarish.
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s21)) {
            currentUser.setChildren(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s22);
            sendMessage.setText("\uD83D\uDCB3 Если у вас есть водительское удостоверение, войдите в категорию ?\nПример: В, С / нет");   // keyboard B   C  D  A  yo'q
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s22)) {
            currentUser.setDriversLicense(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s23);
            sendMessage.setText("\uD83D\uDE98 Если у вас есть автомобиль, введите его название ?\n" +
                    "Пример: Джентра / нет");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s23)) {
            currentUser.setAuto(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s24);
            sendMessage.setText("\uD83D\uDEC2 У вас есть загранпаспорт ?");   // ha yo'q  button
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s24)) {
            currentUser.setInternationalPassport(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s25);
            sendMessage.setText("По делам, готовы ли вы к командировке ?");    // ha yo'q  button
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s25)) {
            currentUser.setReadyForBusinessTrip(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s26);
            sendMessage.setText("\uD83D\uDC6E\u200D Ваша военная служба ?\nПример: Я ушел на военную службу на 1 месяц.");  // button bo'lsa zo'r bolardi.
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s26)) {
            currentUser.setAffiliationToMilitary(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s27);
            sendMessage.setText("♥ Ваши увлечения ?\nПример: Чтение книги, приготовление пищи, ...");
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s27)) {
            sendMessage.setReplyMarkup(KeyboardButtonUtil.agreePhotoButtonRU());
            currentUser.setHobbies(text);
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.s28);
            sendMessage.setText("Присылайте свою фотографию ?\n \uD83D\uDCCE Отправьте свое изображение, нажав на значок.\nВы можете перезагружать изображение, пока не нажмете кнопку «Готово» !");
            System.out.println(currentUser);
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        } else if (currentStatus.equals(UserStatus.s28)) {
            ComponentContainer.userStatusMap.put(String.valueOf(user.getId()), UserStatus.RESUMEEND);
            sendMessage.setText("\uD83D\uDCD6 Примечание ❗️\nУважаемый пользователь, согласны ли вы с нашими условиями, согласно которым мы можем отправлять ваше резюме другим компаниям или работодателям для связи с вами по предложениям на свободные вакансии ?");
            sendMessage.setReplyMarkup(InlineButtonUtil.isAgreeRU());
            System.out.println(currentUser);
            ComponentContainer.MY_TELEGRAM_BOT.send(sendMessage);
        }
    }
}
