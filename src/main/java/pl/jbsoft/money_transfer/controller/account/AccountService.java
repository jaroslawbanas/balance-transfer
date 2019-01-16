package pl.jbsoft.money_transfer.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.jbsoft.money_transfer.business.account.Account;
import pl.jbsoft.money_transfer.business.account.Owner;
import pl.jbsoft.money_transfer.controller.date.DateProvider;
import pl.jbsoft.money_transfer.controller.owner.OwnerService;
import pl.jbsoft.money_transfer.controller.repository.AccountRepository;
import pl.jbsoft.money_transfer.controller.rest.account.CreateAccountRequest;

@Controller
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DateProvider dateProvider;

    @Autowired
    private OwnerService ownerService;

    public Account createFromRequest(CreateAccountRequest createAccountRequest) {
        Account account = Account.of(createAccountRequest.getCurrency());
        Owner owner = ownerService.findOwner(createAccountRequest.getMainOwnerId());

        account.setCreationDate(dateProvider.getCurrentDate());
        account.setMainOwner(owner);

        return account;
    }

    public Account findAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }
}
