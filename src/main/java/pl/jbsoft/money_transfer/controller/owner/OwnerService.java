package pl.jbsoft.money_transfer.controller.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.jbsoft.money_transfer.business.account.Owner;
import pl.jbsoft.money_transfer.controller.repository.OwnerRepository;

@Controller
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public Owner findOwner(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }
}
