package pl.jbsoft.money_transfer.business.transfer;

import pl.jbsoft.money_transfer.business.account.Account;
import pl.jbsoft.money_transfer.business.account.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
public class Transfer {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID transferId;

    @NotNull
    @OneToOne
    private Account fromAccount;

    @NotNull
    @OneToOne
    private Account toAccount;

    @Embedded
    @NotNull
    private Money balance;

    @NotNull
    private Date transferDate;

    public UUID getTransferId() {
        return transferId;
    }

    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }
}
