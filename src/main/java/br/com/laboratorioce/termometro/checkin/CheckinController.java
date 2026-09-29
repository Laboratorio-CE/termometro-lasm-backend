package br.com.laboratorioce.termometro.checkin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/checkins")
public class CheckinController {

    private final CheckinRepository repository;

    public CheckinController(CheckinRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrar(@RequestBody @Valid CheckinForm form) {
        repository.save(new Checkin(form));
    }
}
