package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.OfferPage;
import ru.netology.data.SQLHelper;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentByCardTest {
    OfferPage offerPage = new OfferPage();

    @BeforeEach
    void openForTests() {
        open("http://localhost:8080");
    }

    @BeforeAll
    static void setUpAll() {
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
        payForm.checkOperationIsApproved();
        String dataSQLPayment = SQLHelper.getPaymentStatus();
        assertEquals("APPROVED", dataSQLPayment);
    }

    @Test
    void payByApprovedCardWithAllValidValuesAndAmountSQLTest() {
        val payForm = offerPage.payByDebitCard();
        val approvedInfo = DataHelper.getApprovedCardInfo();
        payForm.fillingForm(approvedInfo);
        payForm.checkOperationIsApproved();
        String dataSQLPayAmount = SQLHelper.getPaymentAmount();
        assertEquals("45000", dataSQLPayAmount);
    }

    @Test
    void payByDeclinedCardWithAllValidValues() {
        val payForm = offerPage.payByDebitCard();
        val declinedInfo = DataHelper.getDeclinedCardInfo();
        payForm.fillingForm(declinedInfo);
        payForm.checkErrorNotification();
        String dataSQLPayment = SQLHelper.getPaymentStatus();
        assertEquals("DECLINED", dataSQLPayment);
    }

    @Test
    void payByInvalidCardNumber() {
        val payForm = offerPage.payByDebitCard();
        val invalidCardNumber = DataHelper.getInvalidCardNumberInfo();
        payForm.fillingForm(invalidCardNumber);
        payForm.checkErrorNotification();
    }

    @Test
    void payByApprovedCardWithInvalidMonthValue() {
        val payForm = offerPage.payByDebitCard();
        val invalidMonth = DataHelper.getInvalidMonthInfo();
        payForm.fillFormNoSendRequest(invalidMonth);
        payForm.checkInvalidExpirationDate();
    }

    @Test
    void payByApprovedCardWithExpiredYearValue() {
        val payForm = offerPage.payByDebitCard();
        val expiredYear = DataHelper.getExpiredYearInfo();
        payForm.fillFormNoSendRequest(expiredYear);
        payForm.checkCardExpired();
    }

    @Test
    void payByApprovedCardWithInvalidYearValue() {
        val payForm = offerPage.payByDebitCard();
        val invalidYear = DataHelper.getInvalidYearInfo();
        payForm.fillFormNoSendRequest(invalidYear);
        payForm.checkInvalidExpirationDate();
    }

    @Test
    void payByApprovedCardWithInvalidOwnerValue() {
        val payForm = offerPage.payByDebitCard();
        val invalidOwner = DataHelper.getInvalidOwnerInfo();
        payForm.fillFormNoSendRequest(invalidOwner);
        payForm.checkWrongFormat();
    }

    @Test
    void sendFormWithEmptyFields() {
        val payForm = offerPage.payByDebitCard();
        val emptyFields = DataHelper.getEmptyFields();
        payForm.fillFormNoSendRequest(emptyFields);
        payForm.checkWrongFormat();
        payForm.checkRequiredField();
    }

    @Test
    void validValuesOfFormAfterSendAnEmptyForm() {
        val payForm = offerPage.payByDebitCard();
        val emptyFields = DataHelper.getEmptyFields();
        val approvedInfo = DataHelper.getApprovedCardInfo();
        payForm.fillFormNoSendRequest(emptyFields);
        payForm.checkWrongFormat();
        payForm.checkRequiredField();
        payForm.fillingForm(approvedInfo);
        payForm.checkOperationIsApproved();
    }

    @Test
    void invalidValuesOfAllFieldsForm() {
        val payForm = offerPage.payByDebitCard();
        val invalidValue = DataHelper.getInvalidCardForm();
        payForm.fillFormNoSendRequest(invalidValue);
        payForm.checkInvalidMonthT();
        payForm.checkInvalidYearT();
        payForm.checkInvalidOwnerT();
        payForm.checkInvalidCVVT();
    }
}

