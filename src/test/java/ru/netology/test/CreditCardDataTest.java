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
        open("http://localhost:8080");
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void downloadFormPaymentByCard() {
        offerPage.payCreditByCard();
    }

    @Test
    void creditByApprovedCardWithAllValidValues() {
        val payCreditForm = offerPage.payCreditByCard();
        val approvedInfo = DataHelper.getApprovedCardInfo();
        payCreditForm.fillingForm(approvedInfo);
        payCreditForm.operationIsApproved();
        assertEquals("APPROVED", dataSQLCredit);
    }

    @Test
    void creditByDeclinedCardWithAllValidValues() {
        val payCreditForm = offerPage.payCreditByCard();
        val declinedInfo = DataHelper.getDeclinedCardInfo();
        payCreditForm.fillingForm(declinedInfo);
        payCreditForm.errorNotification();
        assertEquals("DECLINED", dataSQLCredit);
    }

    @Test
    void creditByInvalidCardNumber() {
        val payCreditForm = offerPage.payCreditByCard();
        val invalidCardNumber = DataHelper.getInvalidCardNumberInfo();
        payCreditForm.fillingForm(invalidCardNumber);
        payCreditForm.errorNotification();
    }

    @Test
    void creditByApprovedCardWithInvalidMonthValue() {
        val payCreditForm = offerPage.payCreditByCard();
        val invalidMonth = DataHelper.getInvalidMonthInfo();
        payCreditForm.fillFormNoSendRequest(invalidMonth);
        payCreditForm.invalidExpirationDate();
    }

    @Test
    void creditByApprovedCardWithExpiredYearValue() {
        val payCreditForm = offerPage.payCreditByCard();
        val expiredYear = DataHelper.getExpiredYearInfo();
        payCreditForm.fillFormNoSendRequest(expiredYear);
        payCreditForm.cardExpired();
    }

    @Test
    void creditByApprovedCardWithInvalidYearValue() {
        val payCreditForm = offerPage.payCreditByCard();
        val invalidYear = DataHelper.getInvalidYearInfo();
        payCreditForm.fillFormNoSendRequest(invalidYear);
        payCreditForm.invalidExpirationDate();
    }

    @Test
    void creditByApprovedCardWithInvalidOwnerValue() {
        val payCreditForm = offerPage.payCreditByCard();
        val invalidOwner = DataHelper.getInvalidOwnerInfo();
        payCreditForm.fillFormNoSendRequest(invalidOwner);
        payCreditForm.wrongFormat();
    }

    @Test
    void sendFormWithEmptyFieldsByCreditForm() {
        val payCreditForm = offerPage.payCreditByCard();
        val emptyFields = DataHelper.getEmptyFields();
        payCreditForm.fillFormNoSendRequest(emptyFields);
        payCreditForm.wrongFormat();
        payCreditForm.requiredField();
    }

    @Test
    void validValuesOfFormAfterSendAnEmptyCreditForm() {
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
        val payCreditForm = offerPage.payCreditByCard();
        val invalidValue = DataHelper.getInvalidCardForm();
        payCreditForm.fillFormNoSendRequest(invalidValue);
        payCreditForm.invalidMonthT();
        payCreditForm.invalidYearT();
        payCreditForm.invalidOwnerT();
        payCreditForm.invalidCVVT();
    }
}
