package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

// главная страница предложения
public class OfferPage {
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));
    private SelenideElement paymentByCard = $(byText("Оплата по карте"));
    private SelenideElement paymentByCreditCard = $(byText("Кредит по данным карты"));

    public PaymentPage payByDebitCard() {
        buyButton.click();
        paymentByCard.shouldHave(Condition.visible);
        return new PaymentPage();
    }

    public PaymentPage payCreditByCard() {
        creditButton.click();
        paymentByCreditCard.shouldHave(Condition.visible);
        return new PaymentPage();
    }
}