package pl.jbsoft.money_transfer.business.account;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Currency;

@Embeddable
public class Money {

    private BigDecimal amount = BigDecimal.ZERO;

    private Currency currency;

    public static Money of(BigDecimal amount, Currency currency) {
        Money money = of(currency);
        money.setAmount(amount);
        return money;
    }

    public static Money of(Currency currency) {
        Money money = new Money();
        money.setCurrency(currency);
        return money;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
