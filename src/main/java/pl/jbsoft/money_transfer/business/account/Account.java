package pl.jbsoft.money_transfer.business.account;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Currency;
import java.util.Date;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NotNull
    private Owner mainOwner;

    @Embedded
    private Money balance;

    private Date creationDate;

    public static Account of(Currency currency) {
        Account account = new Account();
        account.setBalance(Money.of(currency));
        return account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Owner getMainOwner() {
        return mainOwner;
    }

    public void setMainOwner(Owner mainOwner) {
        this.mainOwner = mainOwner;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
