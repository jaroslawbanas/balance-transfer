package pl.jbsoft.money_transfer.controller.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.jbsoft.money_transfer.business.account.Account;
import pl.jbsoft.money_transfer.business.account.Money;
import pl.jbsoft.money_transfer.business.transfer.Transfer;
import pl.jbsoft.money_transfer.controller.account.AccountService;
import pl.jbsoft.money_transfer.controller.date.DateProvider;
import pl.jbsoft.money_transfer.controller.rest.transfer.CreateTransferModel;

@Controller
public class TransferService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private DateProvider dateProvider;

    public Transfer createFromRequest(CreateTransferModel createTransferModel) throws Exception {
        Account fromAccount = accountService.findAccount(createTransferModel.getFromAccountId());
        Account toAccount = accountService.findAccount(createTransferModel.getToAccountId());

        validateBalance(createTransferModel, fromAccount, toAccount);

        Transfer transfer = new Transfer();
        transfer.setFromAccount(fromAccount);
        transfer.setToAccount(toAccount);
        transfer.setBalance(Money.of(createTransferModel.getAmount(), createTransferModel.getCurrency()));
        transfer.setTransferDate(dateProvider.getCurrentDate());

        return transfer;
    }

    private void validateBalance(CreateTransferModel createTransferModel, Account fromAccount, Account toAccount) throws Exception {
        if (!fromAccount.getBalance().getCurrency().equals(toAccount.getBalance().getCurrency())) {
            throw new TransferValidationException("Transfer between difference currency is not supported");
        }

        if (createTransferModel.getAmount().compareTo(fromAccount.getBalance().getAmount()) >= 0) {
            throw new TransferValidationException("From account amount is to low");
        }

        if (createTransferModel.getAmount().signum() < 1) {
            throw new TransferValidationException("Amount transfer should be positive");
        }
    }
}
