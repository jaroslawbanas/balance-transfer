package pl.jbsoft.money_transfer.controller.rest.transfer;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.UUID;

public class TransferRestModel {

    private UUID transferId;

    private Long fromAccountId;

    private Long toAccountId;

    private BigDecimal balanceAmount;

    private Currency balanceCurrency;

    private Date transferDate;

    public UUID getTransferId() {
        return transferId;
    }

    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
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

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }
}
