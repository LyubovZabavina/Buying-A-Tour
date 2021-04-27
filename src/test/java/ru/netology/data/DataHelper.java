package ru.netology.data;

import lombok.Value;


public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String owner;
        String cvv;
    }

    public static String getApprovedCardNumber() {
        return new String("4444 4444 4444 4441");
    }

    public static String getDeclinedCardNumber() {
        return new String("4444 4444 4444 4442");
    }

    public static String getInvalidCardNumber() {
        return new String("3333 3333 3333 2222");
    }



}