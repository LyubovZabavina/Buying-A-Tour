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

public class PaymentByCardTest {
    OfferPage offerPage = new OfferPage();
    String dataSQLPayment = SQLHelper.getPaymentStatus();
    String dataSQLPayAmount = SQLHelper.getPaymentAmount();

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
        offerPage.payByDebitCard();
    }

    @Test
    void payByApprovedCardWithAllValidValues() {
        val payForm = offerPage.payByDebitCard();
        val approvedInfo = DataHelper.getApprovedCardInfo();
        payForm.fillingForm(approvedInfo);
        payForm.operationIsApproved();
        assertEquals("APPROVED", dataSQLPayment);
    }

    @Test
    void payByApprovedCardWithAllValidValuesAndAmountSQLTest() {
        val payForm = offerPage.payByDebitCard();
        val approvedInfo = DataHelper.getApprovedCardInfo();
        payForm.fillingForm(approvedInfo);
        payForm.operationIsApproved();
        assertEquals("4500000", dataSQLPayAmount);
    }

    @Test
    void payByDeclinedCardWithAllValidValues() {
        val payForm = offerPage.payByDebitCard();
        val declinedInfo = DataHelper.getDeclinedCardInfo();
        payForm.fillingForm(declinedInfo);
        payForm.errorNotification();
        assertEquals("DECLINED", dataSQLPayment);
    }

    @Test
    void payByInvalidCardNumber() {
        val payForm = offerPage.payByDebitCard();
        val invalidCardNumber = DataHelper.getInvalidCardNumberInfo();
        payForm.fillingForm(invalidCardNumber);
        payForm.errorNotification();
    }

    @Test
    void payByApprovedCardWithInvalidMonthValue() {
        val payForm = offerPage.payByDebitCard();
        val invalidMonth = DataHelper.getInvalidMonthInfo();
        payForm.fillFormNoSendRequest(invalidMonth);
        payForm.invalidExpirationDate();
    }

    @Test
    void payByApprovedCardWithExpiredYearValue() {
        val payForm = offerPage.payByDebitCard();
        val expiredYear = DataHelper.getExpiredYearInfo();
        payForm.fillFormNoSendRequest(expiredYear);
        payForm.cardExpired();
    }

    @Test
    void payByApprovedCardWithInvalidYearValue() {
        val payForm = offerPage.payByDebitCard();
        val invalidYear = DataHelper.getInvalidYearInfo();
        payForm.fillFormNoSendRequest(invalidYear);
        payForm.invalidExpirationDate();
    }

    @Test
    void payByApprovedCardWithInvalidOwnerValue() {
        val payForm = offerPage.payByDebitCard();
        val invalidOwner = DataHelper.getInvalidOwnerInfo();
        payForm.fillFormNoSendRequest(invalidOwner);
        payForm.wrongFormat();
    }

    @Test
    void sendFormWithEmptyFields() {
        val payForm = offerPage.payByDebitCard();
        val emptyFields = DataHelper.getEmptyFields();
        payForm.fillFormNoSendRequest(emptyFields);
        payForm.wrongFormat();
        payForm.requiredField();
    }

    @Test
    void validValuesOfFormAfterSendAnEmptyForm() {
        val payForm = offerPage.payByDebitCard();
        val emptyFields = DataHelper.getEmptyFields();
        val approvedInfo = DataHelper.getApprovedCardInfo();
        payForm.fillFormNoSendRequest(emptyFields);
        payForm.wrongFormat();
        payForm.requiredField();
        payForm.fillingForm(approvedInfo);
        payForm.operationIsApproved();
    }

    @Test
    void invalidValuesOfAllFieldsForm() {
        val payForm = offerPage.payByDebitCard();
        val invalidValue = DataHelper.getInvalidCardForm();
        payForm.fillFormNoSendRequest(invalidValue);
        payForm.invalidMonthT();
        payForm.invalidYearT();
        payForm.invalidOwnerT();
        payForm.invalidCVVT();
    }
}

