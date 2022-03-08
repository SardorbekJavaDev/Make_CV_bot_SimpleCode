package com.company.pdf;

import com.company.entity.Resume;
import com.company.enums.Language;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.text.*;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static com.itextpdf.tool.xml.html.HTML.Tag.FONT;

public class ObjectToPdf {
    static BaseColor fontColor1 = WebColors.getRGBColor("#160A47");
    static BaseColor fontColor2 = WebColors.getRGBColor("#323232");
    static BaseColor bgColorCell2 = WebColors.getRGBColor("#F3F2F2");
    static BaseColor borderColor = WebColors.getRGBColor("#222F4C");

    static Font font1 = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, fontColor1);
    static Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, fontColor2);
    static Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD, fontColor1);

    private static String createCVUZ(Resume resume, Long id) {
        Document document = new Document();
        String pdfName = null;
        try {
            pdfName = "src/main/resources/Pdfs/" + id + "CV.pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfName));
            document.open();

            PdfPTable table = new PdfPTable(2); // 3 columns.
            table.setWidthPercentage(90); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

            float[] columnWidths = {3f, 1f};
            table.setWidths(columnWidths);
            Phrase phrase = new Phrase();
            phrase.add(new Chunk(resume.getNSF(), font1));
            PdfPCell cell1 = new PdfPCell(phrase);
            cell1.setBorder(0);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);


            File userImg = getUserIMGByID(String.valueOf(id));
            if (userImg == null) System.out.println("Rasm topilmadi");
            else {
                System.out.println(userImg.getPath());
                Image image = Image.getInstance(userImg.getPath());
                System.out.println(image);
                PdfPCell cell2 = new PdfPCell(image, true);
                cell2.setBorderColor(borderColor);
                cell2.setPadding(1);
                cell2.setBorderWidth(2);
                table.addCell(cell1);
                table.addCell(cell2);
                document.add(table);
            }


            PdfPTable table1 = getTableWithCell1("Shaxsiy ma'lumotlar");
            PdfPCell cell2a = new PdfPCell();
            cell2a.setBackgroundColor(bgColorCell2);
            cell2a.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2a.setBorder(0);
            cell2a.addElement(getPhrase("F.I.O: ", resume.getNSF()));
            cell2a.addElement(getPhrase("Istalgan lavozim: ", resume.getPosition()));
            cell2a.addElement(getPhrase("Tug'ulgan kun oy va yil: ", resume.getDateOfBirth()));
            cell2a.addElement(getPhrase("Yashash manzili: ", resume.getAddress()));
            cell2a.addElement(getPhrase("Telefon: ", resume.getPhoneNumber()));
            cell2a.addElement(getPhrase("Email: ", resume.getEmail()));
            cell2a.setPadding(10);
            table1.addCell(cell2a);
            document.add(table1);


            PdfPTable table2 = getTableWithCell1("Ta'lim");
            PdfPCell cell2b = new PdfPCell();
            cell2b.setBackgroundColor(bgColorCell2);
            cell2b.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2b.setBorder(0);
            cell2b.addElement(getPhrase("Ma'lumoti: ", resume.getLevelOfEducation()));
            cell2b.addElement(getPhrase("Ta'lim muassasasi: ", resume.getNameOfTheInstitution()));
            cell2b.addElement(getPhrase("Fakultet: ", resume.getFaculty()));
            cell2b.addElement(getPhrase("Mutaxasisligi: ", resume.getProfession()));
            cell2b.addElement(getPhrase("Ta'lim olgan yili: ", resume.getYearOfStudy()));
            cell2b.addElement(getPhrase("Ta'lim olgan shahri: ", resume.getNameOfStudiedCity()));
            cell2b.setPadding(10);
            table2.addCell(cell2b);
            document.add(table2);


            PdfPTable table3 = getTableWithCell1("Ish tajribasi");
            PdfPCell cell2c = new PdfPCell();
            cell2c.setBackgroundColor(bgColorCell2);
            cell2c.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2c.setBorder(0);
            cell2c.addElement(getPhrase("Lavozim: ", resume.getYouLastWork()));
            cell2c.addElement(getPhrase("Funktsional majburiyatlar: ", resume.getFunctionalResponsibilities()));
            cell2c.addElement(getPhrase("Yutuqlar: ", resume.getYourAchievements()));
            cell2c.setPadding(10);
            table3.addCell(cell2c);
            document.add(table3);


            PdfPTable table4 = getTableWithCell1("Kasbiy mahorat");
            PdfPCell cell2d = new PdfPCell();
            cell2d.setBackgroundColor(bgColorCell2);
            cell2d.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2d.setBorder(0);
            cell2d.addElement(getPhrase("Kompyuterdan foydalanish darajasi: ", resume.getComputerUseRate()));
            cell2d.addElement(getPhrase("Tillarni bilishi: ", resume.getKnowledgeOfLanguages()));
            cell2d.addElement(getPhrase("Qobiliyatlar: ", resume.getYourAbility()));
            cell2d.setPadding(10);
            table4.addCell(cell2d);
            document.add(table4);


            PdfPTable table5 = getTableWithCell1("Shaxsiy sifatlar");
            PdfPCell cell2e = new PdfPCell();
            cell2e.setBackgroundColor(bgColorCell2);
            cell2e.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2e.setBorder(0);
            cell2e.addElement(getPhrase("Shaxsiy sifatlar: ", resume.getPersonalQuality()));
            cell2e.setPadding(10);
            table5.addCell(cell2e);
            document.add(table5);


            PdfPTable table6 = getTableWithCell1("Qo'shimcha ma'lumotlar");
            PdfPCell cell2f = new PdfPCell();
            cell2f.setBackgroundColor(bgColorCell2);
            cell2f.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2f.setBorder(0);
            cell2f.addElement(getPhrase("Oilaviy ahvoli: ", resume.getMaritalStatus()));
            cell2f.addElement(getPhrase("Farzandlar: ", resume.getChildren()));
            cell2f.addElement(getPhrase("Xaydovchilik guvoxnomasi: ", resume.getAuto()));
            cell2f.addElement(getPhrase("Auto: ", resume.getAuto()));
            cell2f.addElement(getPhrase("Xalqaro passpord: ", resume.getInternationalPassport()));
            cell2f.addElement(getPhrase("Xizmat safariga tayyormisiz: ", resume.getReadyForBusinessTrip()));
            cell2f.addElement(getPhrase("Harbiy xizmatga tegishlilik: ", resume.getAffiliationToMilitary()));
            cell2f.addElement(getPhrase("Sevimli mashg'ulotlari: ", resume.getHobbies()));
            cell2f.setPadding(10);
            table6.addCell(cell2f);
            document.add(table6);


            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdfName;
    }

    private static String createCVRU(Resume resume, Long id) {
        Document document = new Document();
        String pdfName = null;
        BaseFont ru = null;
        try {
            ru = BaseFont.createFont("src/main/resources/fonts/NotoSans-Regular.ttf", "CP1251", true);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        Font fontru1 = new Font(ru, 16, Font.BOLD, fontColor1);
        try {
            pdfName = "src/main/resources/Pdfs/" + id + "CV.pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfName));
            document.open();

            PdfPTable table = new PdfPTable(2); // 3 columns.
            table.setWidthPercentage(90); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

            float[] columnWidths = {3f, 1f};
            table.setWidths(columnWidths);
            Phrase phrase = new Phrase();
            phrase.add(new Chunk(resume.getNSF(), fontru1));
            PdfPCell cell1 = new PdfPCell(phrase);
            cell1.setBorder(0);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);


            File userImg = getUserIMGByID(String.valueOf(id));
            if (userImg == null) System.out.println("Rasm topilmadi");
            else {
                System.out.println(userImg.getPath());
                Image image = Image.getInstance(userImg.getPath());
                System.out.println(image);
                PdfPCell cell2 = new PdfPCell(image, true);
                cell2.setBorderColor(borderColor);
                cell2.setPadding(1);
                cell2.setBorderWidth(2);
                table.addCell(cell1);
                table.addCell(cell2);
                document.add(table);
            }

            PdfPTable table1 = getTableWithCell1("Личная информация");
            PdfPCell cell2a = new PdfPCell();
            cell2a.setBackgroundColor(bgColorCell2);
            cell2a.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2a.setBorder(0);
            cell2a.addElement(getPhrase("Ф.И.О: ", resume.getNSF()));
            cell2a.addElement(getPhrase("Желаемая должность: ", resume.getPosition()));
            cell2a.addElement(getPhrase("Дата рождения месяц и год: ", resume.getDateOfBirth()));
            cell2a.addElement(getPhrase("Адрес проживания: ", resume.getAddress()));
            cell2a.addElement(getPhrase("Телефон: ", resume.getPhoneNumber()));
            cell2a.addElement(getPhrase("Эл. адрес: ", resume.getEmail()));
            cell2a.setPadding(10);
            table1.addCell(cell2a);
            document.add(table1);


            PdfPTable table2 = getTableWithCell1("Образование");
            PdfPCell cell2b = new PdfPCell();
            cell2b.setBackgroundColor(bgColorCell2);
            cell2b.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2b.setBorder(0);
            cell2b.addElement(getPhrase("Образование: ", resume.getLevelOfEducation()));
            cell2b.addElement(getPhrase("Образовательное учреждение: ", resume.getNameOfTheInstitution()));
            cell2b.addElement(getPhrase("Факультет: ", resume.getFaculty()));
            cell2b.addElement(getPhrase("Специальность: ", resume.getProfession()));
            cell2b.addElement(getPhrase("Год обучения: ", resume.getYearOfStudy()));
            cell2b.addElement(getPhrase("Город учебы: ", resume.getNameOfStudiedCity()));
            cell2b.setPadding(10);
            table2.addCell(cell2b);
            document.add(table2);


            PdfPTable table3 = getTableWithCell1("Рабочий стаж");
            PdfPCell cell2c = new PdfPCell();
            cell2c.setBackgroundColor(bgColorCell2);
            cell2c.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2c.setBorder(0);
            cell2c.addElement(getPhrase("Позиция: ", resume.getYouLastWork()));
            cell2c.addElement(getPhrase("Функциональные обязанности: ", resume.getFunctionalResponsibilities()));
            cell2c.addElement(getPhrase("Достижения: ", resume.getYourAchievements()));
            cell2c.setPadding(10);
            table3.addCell(cell2c);
            document.add(table3);


            PdfPTable table4 = getTableWithCell1("Профессиональные навыки");
            PdfPCell cell2d = new PdfPCell();
            cell2d.setBackgroundColor(bgColorCell2);
            cell2d.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2d.setBorder(0);
            cell2d.addElement(getPhrase("Использование компьютера: ", resume.getComputerUseRate()));
            cell2d.addElement(getPhrase("Знание языков: ", resume.getKnowledgeOfLanguages()));
            cell2d.addElement(getPhrase("Способности: ", resume.getYourAbility()));
            cell2d.setPadding(10);
            table4.addCell(cell2d);
            document.add(table4);


            PdfPTable table5 = getTableWithCell1("Личные качества");
            PdfPCell cell2e = new PdfPCell();
            cell2e.setBackgroundColor(bgColorCell2);
            cell2e.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2e.setBorder(0);
            cell2e.addElement(getPhrase("Личные качества: ", resume.getPersonalQuality()));
            cell2e.setPadding(10);
            table5.addCell(cell2e);
            document.add(table5);


            PdfPTable table6 = getTableWithCell1("Дополнительная информация");
            PdfPCell cell2f = new PdfPCell();
            cell2f.setBackgroundColor(bgColorCell2);
            cell2f.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2f.setBorder(0);
            cell2f.addElement(getPhrase("\u0393\u0394\u03b6Семейный статус: ", resume.getMaritalStatus()));
            cell2f.addElement(getPhrase("Дети: ", resume.getChildren()));
            cell2f.addElement(getPhrase("Водительское удостоверение: ", resume.getAuto()));
            cell2f.addElement(getPhrase("Авто: ", resume.getAuto()));
            cell2f.addElement(getPhrase("Заграничный паспорт: ", resume.getInternationalPassport()));
            cell2f.addElement(getPhrase("Вы готовы к командировке: ", resume.getReadyForBusinessTrip()));
            cell2f.addElement(getPhrase("Принадлежность к военной службе: ", resume.getAffiliationToMilitary()));
            cell2f.addElement(getPhrase("Увлечения: ", resume.getHobbies()));
            cell2f.setPadding(10);
            table6.addCell(cell2f);
            document.add(table6);

            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdfName;
    }

    public static String createCV(Resume resume, @NonNull Long id) {
        System.out.println("test ------------- " + resume.getNSF());

        if (resume.getSelectedLanguage().equals(Language.UZ)) {
            return createCVUZ(resume, id);
        } else if (resume.getSelectedLanguage().equals(Language.RU)) {
            System.out.println("test ------------- " + resume.getNSF());
            return createCVRU(resume, id);
        }

        return null;
    }

    private static File getUserIMGByID(String userId) {
        java.io.File imgs = new java.io.File("src/main/resources/UserImg");
        System.out.println(Arrays.toString(imgs.listFiles()));
        for (File listFile : Objects.requireNonNull(imgs.listFiles())) {
            System.out.println(listFile.getName());
            if (listFile.getName().contains(userId)) {
                return listFile;
            }
        }
        return null;
    }

    private static PdfPTable getTableWithCell1(String mainName) throws DocumentException {
        BaseFont ru = null;
        try {
            ru = BaseFont.createFont("src/main/resources/fonts/NotoSans-Regular.ttf", "CP1251", true);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        Font fontru1 = new Font(ru, 16, Font.BOLD, fontColor1);
        PdfPTable table = new PdfPTable(2); // 3 columns.
        table.setWidthPercentage(100); //Width 100%
        table.setSpacingBefore(10f); //Space before table
        table.setSpacingAfter(10f); //Space after table
        float[] columnWidths = {1f, 2f};
        table.setWidths(columnWidths);
        Paragraph paragraph = new Paragraph(mainName, fontru1);
        PdfPCell cell1 = new PdfPCell(new Paragraph(paragraph));
        BaseColor bgColorCell1 = WebColors.getRGBColor("#DAEEF3");
        cell1.setBackgroundColor(bgColorCell1);
        cell1.setPadding(10);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell1.setBorder(0);
        table.addCell(cell1);
        return table;
    }

//    private static Phrase getPhrase(String phraseName, String phraseValue) {
//        Phrase phrase = new Paragraph();
//        phrase.add(new Chunk(phraseName, font3));
//        phrase.add(new Chunk(phraseValue, font2));
//        return phrase;
//    }

    private static Phrase getPhrase(String phraseName, String phraseValue) {
        try {
            BaseFont ru = BaseFont.createFont("src/main/resources/fonts/NotoSans-Regular.ttf", "CP1251", true);
            Font fontru3 = new Font(ru, 11, Font.BOLD, fontColor1);
            Font fontru2 = new Font(ru, 12, Font.NORMAL, fontColor2);

            Phrase phrase = new Paragraph();
            phrase.add(new Chunk(phraseName, fontru3));
            phrase.add(new Chunk(phraseValue, fontru2));
            return phrase;
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
