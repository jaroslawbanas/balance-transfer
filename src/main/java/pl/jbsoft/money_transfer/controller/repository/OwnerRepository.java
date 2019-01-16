package pl.jbsoft.money_transfer.controller.repository;

import org.springframework.data.repository.CrudRepository;
import pl.jbsoft.money_transfer.business.account.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

}