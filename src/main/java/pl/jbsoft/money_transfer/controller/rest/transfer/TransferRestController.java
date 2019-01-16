package pl.jbsoft.money_transfer.controller.rest.transfer;

import org.apache.commons.collections4.IteratorUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.jbsoft.money_transfer.business.transfer.Transfer;
import pl.jbsoft.money_transfer.controller.repository.TransferRepository;
import pl.jbsoft.money_transfer.controller.rest.V1Constants;
import pl.jbsoft.money_transfer.controller.transfer.TransferService;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class TransferRestController {

    private final static Logger LOGGER = Logger.getAnonymousLogger();

    public static final String TRANSFER_URL = V1Constants.BASE_URL + "/transfers";

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransferService transferService;

    @Autowired
    private TransferRepository transferRepository;

    @GetMapping(TRANSFER_URL)
    public @ResponseBody
    ResponseEntity<Collection<TransferRestModel>> getAll() {
        List<TransferRestModel> accounts = IteratorUtils.toList(transferRepository.findAll().iterator())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(accounts);
    }

    @GetMapping(value = TRANSFER_URL, params = "id")
    public @ResponseBody
    ResponseEntity<TransferRestModel> getSingle(@RequestParam("id") String id) {
        try {
            return transferRepository.findById(UUID.fromString(id))
                    .map(this::convertToDto)
                    .map(owner -> ResponseEntity.ok().body(owner))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @PostMapping(TRANSFER_URL)
    public @ResponseBody
    ResponseEntity<TransferRestModel> create(@RequestBody CreateTransferModel createTransferModel) {
        try {
            Transfer fromRequest = transferService.createFromRequest(createTransferModel);
            Transfer save = transferRepository.save(fromRequest);

            return ResponseEntity.ok()
                    .body(convertToDto(save));
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    private TransferRestModel convertToDto(Transfer transfer) {
        return modelMapper.map(transfer, TransferRestModel.class);
    }

}
