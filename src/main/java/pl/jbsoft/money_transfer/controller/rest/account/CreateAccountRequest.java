package pl.jbsoft.money_transfer.controller.rest.account;

import java.util.Currency;

public class CreateAccountRequest {

    private Long mainOwnerId;

    private Currency currency;

    public Long getMainOwnerId() {
        return mainOwnerId;
    }

    public void setMainOwnerId(Long mainOwnerId) {
        this.mainOwnerId = mainOwnerId;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
