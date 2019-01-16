package pl.jbsoft.money_transfer.controller.rest.owner;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jbsoft.money_transfer.business.account.Owner;
import pl.jbsoft.money_transfer.controller.repository.OwnerRepository;
import pl.jbsoft.money_transfer.controller.rest.V1Constants;

import java.util.Collection;
import java.util.List;

@RestController
public class OwnerRestController {

    public static final String OWNERS_URL = V1Constants.BASE_URL + "/owners";

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping(OWNERS_URL)
    public @ResponseBody
    ResponseEntity<Collection<Owner>> getAll() {
        List<Owner> owners = IteratorUtils.toList(ownerRepository.findAll().iterator());
        return ResponseEntity.ok()
                .body(owners);
    }

    @GetMapping(value = OWNERS_URL, params = "id")
    public @ResponseBody
    ResponseEntity<Owner> getSingle(@RequestParam("id") long id) {
        return ownerRepository.findById(id)
                .map(owner -> ResponseEntity.ok().body(owner))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(OWNERS_URL)
    public @ResponseBody
    ResponseEntity<Owner> create(@RequestBody CreateOwnerRequest createOwnerRequest) {
        Owner saveOwner = ownerRepository.save(Owner.of(createOwnerRequest.getOwnerName()));
        return ResponseEntity.ok()
                .body(saveOwner);
    }

    @PutMapping(OWNERS_URL)
    public @ResponseBody
    ResponseEntity<Owner> update(@RequestBody Owner owner) {
        Owner saveOwner = ownerRepository.save(owner);
        return ResponseEntity.ok()
                .body(saveOwner);
    }
}
