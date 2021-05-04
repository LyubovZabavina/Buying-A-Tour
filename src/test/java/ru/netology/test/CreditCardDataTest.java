package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.OfferPage;
import ru.netology.data.SQLHelper;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardDataTest {
    OfferPage offerPage = new OfferPage();
    String dataSQLCredit = SQLHelper.getCreditStatus();

    @BeforeAll
    static void setUpAll() {
//        open("http://localhost:8080");
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void downloadFormPaymentByCard() {
        open("http://localhost:8080");
        offerPage.payCreditByCard();
    }

    @Test
    void creditByApprovedCardWithAllValidValues() {
        open("http://localhost:8080");
        val payCreditForm = offerPage.payCreditByCard();
        val approvedInfo = DataHelper.getApprovedCardInfo();
        payCreditForm.fillingForm(approvedInfo);
        payCreditForm.operationIsApproved();
        assertEquals("APPROVED", dataSQLCredit);
    }

    @Test
    void creditByDeclinedCardWithAllValidValues() {
        open("http://localhost:8080");
        val payCreditForm = offerPage.payCreditByCard();
        val declinedInfo = DataHelper.getDeclinedCardInfo();
        payCreditForm.fillingForm(declinedInfo);
        payCreditForm.errorNotification();
        assertEquals("DECLINED", dataSQLCredit);
    }

    @Test
    void creditByInvalidCardNumber() {
        open("http://localhost:8080");
        val payCreditForm = offerPage.payCreditByCard();
        val invalidCardNumber = DataHelper.getInvalidCardNumberInfo();
        payCreditForm.fillingForm(invalidCardNumber);
        payCreditForm.errorNotification();
    }

    @Test
    void creditByApprovedCardWithInvalidMonthValue() {
        open("http://localhost:8080");
        val payCreditForm = offerPage.payCreditByCard();
        val invalidMonth = DataHelper.getInvalidMonthInfo();
        payCreditForm.fillFormNoSendRequest(invalidMonth);
        payCreditForm.invalidExpirationDate();
    }

    @Test
    void creditByApprovedCardWithExpiredYearValue() {
        open("http://localhost:8080");
        val payCreditForm = offerPage.payCreditByCard();
        val expiredYear = DataHelper.getExpiredYearInfo();
        payCreditForm.fillFormNoSendRequest(expiredYear);
        payCreditForm.cardExpired();
    }

    @Test
    void creditByApprovedCardWithInvalidYearValue() {
        open("http://localhost:8080");
        val payCreditForm = offerPage.payCreditByCard();
        val invalidYear = DataHelper.getInvalidYearInfo();
        payCreditForm.fillFormNoSendRequest(invalidYear);
        payCreditForm.invalidExpirationDate();
    }

    @Test
    void creditByApprovedCardWithInvalidOwnerValue() {
        open("http://localhost:8080");
        val payCreditForm = offerPage.payCreditByCard();
        val invalidOwner = DataHelper.getInvalidOwnerInfo();
        payCreditForm.fillFormNoSendRequest(invalidOwner);
        payCreditForm.wrongFormat();
    }

    @Test
    void sendFormWithEmptyFieldsByCreditForm() {
        open("http://localhost:8080");
        val payCreditForm = offerPage.payCreditByCard();
        val emptyFields = DataHelper.getEmptyFields();
        payCreditForm.fillFormNoSendRequest(emptyFields);
        payCreditForm.wrongFormat();
        payCreditForm.requiredField();
    }

    @Test
    void validValuesOfFormAfterSendAnEmptyCreditForm() {
        open("http://localhost:8080");
        val payCreditForm = offerPage.payCreditByCard();
        val emptyFields = DataHelper.getEmptyFields();
        val approvedInfo = DataHelper.getApprovedCardInfo();
        payCreditForm.fillFormNoSendRequest(emptyFields);
        payCreditForm.wrongFormat();
        payCreditForm.requiredField();
        payCreditForm.fillingForm(approvedInfo);
        payCreditForm.operationIsApproved();
    }

    @Test
    void invalidValuesOfAllFieldsForm() {
        open("http://localhost:8080");
        val payCreditForm = offerPage.payCreditByCard();
        val invalidValue = DataHelper.getInvalidCardForm();
        payCreditForm.fillFormNoSendRequest(invalidValue);
        payCreditForm.invalidMonthT();
        payCreditForm.invalidYearT();
        payCreditForm.invalidOwnerT();
        payCreditForm.invalidCVVT();
    }
}
