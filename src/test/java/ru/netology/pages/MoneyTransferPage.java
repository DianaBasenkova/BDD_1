package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {

    private final SelenideElement amountField = $("[data-test-id='amount'] .input__control");
    private SelenideElement cardNumberFrom = $("[data-test-id=from] .input__control");
    private SelenideElement debitButton = $("[data-test-id=action-transfer]");
    private SelenideElement cancelButton = $("[data-test-id=action-cancel]");


    public MoneyTransferPage() {
    }

    public void transfer() {

    }

    public DashboardPage transfer(int amount, String cardNumber) {
        amountField.setValue(String.valueOf(amount));
        cardNumberFrom.setValue(cardNumber);
        debitButton.click();
        return new DashboardPage();
    }
}
