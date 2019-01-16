package pl.jbsoft.money_transfer.controller.rest.account;

import org.apache.commons.collections4.IteratorUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.jbsoft.money_transfer.business.account.Account;
import pl.jbsoft.money_transfer.controller.account.AccountService;
import pl.jbsoft.money_transfer.controller.repository.AccountRepository;
import pl.jbsoft.money_transfer.controller.rest.V1Constatnts;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@RestController
public class AccountRestController {

    private final static Logger LOGGER = Logger.getAnonymousLogger();

    public static final String ACCOUNT_URL = V1Constatnts.BASE_URL + "/accounts";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @GetMapping(ACCOUNT_URL)
    public @ResponseBody
    ResponseEntity<Collection<AccountRestModel>> getAll() {
        List<AccountRestModel> accounts = IteratorUtils.toList(accountRepository.findAll().iterator())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(accounts);
    }

    @GetMapping(value = ACCOUNT_URL, params = "id")
    public @ResponseBody
    ResponseEntity<AccountRestModel> getSingle(@RequestParam("id") long id) {
        return accountRepository.findById(id)
                .map(this::convertToDto)
                .map(account -> ResponseEntity.ok().body(account))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(ACCOUNT_URL)
    public @ResponseBody
    ResponseEntity<AccountRestModel> create(@RequestBody CreateAccountRequest createAccountRequest) {
        try {
        Account account = accountService.createFromRequest(createAccountRequest);
        Account save = accountRepository.save(account);

        return ResponseEntity.ok()
                .body(convertToDto(save));
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Action failure", ex);
        }
    }

    private AccountRestModel convertToDto(Account account) {
        return modelMapper.map(account, AccountRestModel.class);
    }

}
