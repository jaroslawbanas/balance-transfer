package pl.jbsoft.money_transfer.controller.rest.account;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class AccountRestModel {

    private Long id;

    private Long mainOwnerId;

    private BigDecimal balanceAmount;

    private Currency balanceCurrency;

    private Date creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMainOwnerId() {
        return mainOwnerId;
    }

    public void setMainOwnerId(Long mainOwnerId) {
        this.mainOwnerId = mainOwnerId;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Currency getBalanceCurrency() {
        return balanceCurrency;
    }

    public void setBalanceCurrency(Currency balanceCurrency) {
        this.balanceCurrency = balanceCurrency;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
