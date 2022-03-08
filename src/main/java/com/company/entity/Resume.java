package com.company.entity;

import com.company.enums.Language;
import lombok.Data;

@Data
public class Resume {
    //    private UserStatus status;
    private Integer backCount;
    private Language selectedLanguage;
    private Integer id;
    private String userId;
    private String NSF;                          // Name Surname Firstname
    private String position;                     // Sizni qiziqtirgan lavozimni kiriting?:

    private String dateOfBirth;                  // 01.12.2022
    private String address;                      // address
    private String phoneNumber;                  // tel nomeri
    private String email;                        // email

    private String levelOfEducation;             // ma'lumotingiz   button chiqadi.    o'rta, o'rta maxsus, oliy

    private String nameOfTheInstitution;         // ta'lim muassasa nomi
    private String faculty;                      // fakulteti                     if qo'yiladi faqat oliy bo'lsa ochiladi.
    private String profession;                   // mutahasisligingiz
    private String yearOfStudy;                  // ta'lim olgan yili
    private String nameOfStudiedCity;            // ta'lim olgan shahri

    private String youLastWork;                  // Oxirgi ishlagan joyingiz ? Oxirgi ishlagan lavozimingiz yili bilan?
    private String functionalResponsibilities;   // ishlagan joyingizdagi majburiyatlaringiz
    private String yourAchievements;             // erishgan yutuqlaringiz vergul bilan

    //kasbiy mahorat
    private String computerUseRate;              // Komyuterdan foydalnish darajsi
    private String knowledgeOfLanguages;         // tillarnii bilishi
    private String yourAbility;                  // qobiliyatingiz

    private String personalQuality;              // Shaxsiy sifatlaringiz  qo'lda to'ldiradi vergul bilan

    private String maritalStatus;                // Oilaviy ahvoli uylanganligi
    private String children;                     // bolalari
    private String driversLicense;               // haydovchilik guvohnomasi
    private String auto;                         // mashinangiz bormi bo'lsa rusumi
    private String internationalPassport;        // xalqaro pasport
    private String readyForBusinessTrip;         // Xizmat safariga tayyormi
    private String affiliationToMilitary;        // Harbiy xizmatga tegishlilik:: Yo'q ❌
    private String hobbies;                      // Sevimli mashg'ulotlar

    private Boolean consentOfUser; /*  Вы согласны с тем, что ваша личная информация будет отправлена? */

    public void setBackCount() {
        ++this.backCount;
    }
}