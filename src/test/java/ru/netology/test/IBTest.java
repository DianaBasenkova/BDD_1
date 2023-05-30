package ru.netology.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.DashboardPage;
import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;


public class IBTest {
    LoginPage loginPage;
    DashboardPage dashboardPage;
    @BeforeEach
    void setup() {
        loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        verificationPage.validVerify(verificationCode);
       dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @AfterEach
    void cleanUp() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

    DashboardPage login() {
        DataHelper.AuthInfo authInfo = getAuthInfo();
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        return verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferFromFirstCardToSecondCard() {
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateValidAmount(firstCardBalance);
        var FirstCardBalanceAfterTransfer = firstCardBalance - amount;
        var secondCardBalanceAfterTransfer = secondCardBalance + amount;
        var MoneyTransferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = MoneyTransferPage.successfulTransfer(String.valueOf(amount),firstCardInfo);
        var actualFirstCardBalanceAfterTransfer = dashboardPage.getCardBalance(firstCardInfo);
        var actualFSecondCardBalanceAfterTransfer = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(FirstCardBalanceAfterTransfer, actualFirstCardBalanceAfterTransfer);
        assertEquals(secondCardBalanceAfterTransfer, actualFSecondCardBalanceAfterTransfer);

    }

    @Test
    void shouldTransferFromSecondCardToFirst() {
    }


}