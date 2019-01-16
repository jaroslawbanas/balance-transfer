package pl.jbsoft.money_transfer.controller.repository;

import org.springframework.data.repository.CrudRepository;
import pl.jbsoft.money_transfer.business.account.Account;

import java.util.Collection;

public interface AccountRepository extends CrudRepository<Account, Long> {

}
