package ru.netology.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
       dashboardPage = verificationPage.validVerify(verificationCode);
    }
    @AfterEach
    void cleanUp() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }
    @Test
    void shouldTransferFromFirstCardToSecondCard() {
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getFirstCardBalance(0);
        var secondCardBalance = dashboardPage.getSecondCardBalance(1);
        var amount = generateValidAmount(firstCardBalance);
        var firstCardBalanceAfterTransfer = firstCardBalance - amount;
        var secondCardBalanceAfterTransfer = secondCardBalance + amount;
        var moneyTransferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = moneyTransferPage.successfulTransfer(String.valueOf(amount),firstCardInfo);
        var actualFirstCardBalanceAfterTransfer = dashboardPage.getCardBalance(firstCardInfo);
        var actualFSecondCardBalanceAfterTransfer = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(firstCardBalanceAfterTransfer, actualFirstCardBalanceAfterTransfer);
        assertEquals(secondCardBalanceAfterTransfer, actualFSecondCardBalanceAfterTransfer);

    }

    @Test
    void shouldTransferFromSecondCardToFirst() {
    }


}