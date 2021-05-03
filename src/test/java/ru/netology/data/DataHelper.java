package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String owner;
        String cvv;
    }

    //карты
    public static String getApprovedCardNumber() {
        return new String("4444 4444 4444 4441");
    }

    public static String getDeclinedCardNumber() {
        return new String("4444 4444 4444 4442");
    }

    public static String getInvalidCardNumber() {
        return new String("3333 3333 3333 2222");
    }

    public static String getEmptyCardNumberValue() {
        return new String(" ");
    }

    //месяц
    public static String getValidMonth() {
        return "08";
    }

    public static String getInvalidMonth() {
        return "99";
    }

    public static String getEmptyMonthValue() {
        return " ";
    }

    //год
    public static String getValidYear() {
        LocalDate year = LocalDate.now();
        LocalDate newYear = year.plusYears(4);
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("YY");
        return newYear.format(yearFormatter);
    }

    public static String getExpiredYear() {
        LocalDate year = LocalDate.now();
        LocalDate newYear = year.minusYears(11);
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("YY");
        return newYear.format(yearFormatter);
    }

    public static String getInvalidYear() {
        LocalDate year = LocalDate.now();
        LocalDate newYear = year.plusYears(78);
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("YY");
        return newYear.format(yearFormatter);
    }

    public static String getEmptyYearValue() {
        return " ";
    }

    //владелец
    public static String getValidOwner() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().fullName();
    }

    public static String getInvalidOwner() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getEmptyOwnerValue() {
        return " ";
    }

    public static String getValidCVV() {
        return "854";
    }

    public static String getInvalidCVV() {
        return "66";
    }

    public static String getEmptyCVVValue() {
        return " ";
    }

    //вместе
    public static CardInfo getApprovedCardInfo() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidOwner(), getValidCVV());
    }

    public static CardInfo getDeclinedCardInfo() {
        return new CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidOwner(), getValidCVV());
    }

    public static CardInfo getInvalidCardNumberInfo() {
        return new CardInfo(getInvalidCardNumber(), getValidMonth(), getValidYear(), getValidOwner(), getValidCVV());
    }

    public static CardInfo getInvalidMonthInfo() {
        return new CardInfo(getApprovedCardNumber(), getInvalidMonth(), getValidYear(), getValidOwner(), getValidCVV());
    }

    public static CardInfo getExpiredYearInfo() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getExpiredYear(), getValidOwner(), getValidCVV());
    }

    public static CardInfo getInvalidYearInfo() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getInvalidYear(), getValidOwner(), getValidCVV());
    }

    public static CardInfo getInvalidOwnerInfo() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getInvalidOwner(), getValidCVV());
    }

    public static CardInfo getEmptyFields() {
        return new CardInfo(getEmptyCardNumberValue(), getEmptyMonthValue(), getEmptyYearValue(),
                getEmptyOwnerValue(), getEmptyCVVValue());
    }

    public static CardInfo getInvalidValuesOfForm() {
        return new CardInfo(getInvalidCardNumber(), getInvalidMonth(), getInvalidYear(),
                getInvalidOwner(), getInvalidCVV());
    }

    public static CardInfo getInvalidCardForm() {
        return new CardInfo(getInvalidCardNumber(), getInvalidMonth(), getInvalidYear(), getInvalidOwner(), getInvalidCVV());
    }
}
