package pl.jbsoft.money_transfer.controller.repository;

import org.springframework.data.repository.CrudRepository;
import pl.jbsoft.money_transfer.business.transfer.Transfer;

import java.util.UUID;

public interface TransferRepository extends CrudRepository<Transfer, UUID> {

}
