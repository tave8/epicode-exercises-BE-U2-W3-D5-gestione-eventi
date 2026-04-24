package giuseppetavella.D5_gestione_eventi.controllers;

import giuseppetavella.D5_gestione_eventi.entities.Utente;
import giuseppetavella.D5_gestione_eventi.exceptions.ValidazionePayloadException;
import giuseppetavella.D5_gestione_eventi.payloads.in_request.LoginMandatoDTO;
import giuseppetavella.D5_gestione_eventi.payloads.in_request.RegistrazioneMandataDTO;
import giuseppetavella.D5_gestione_eventi.payloads.in_response.DopoLoginDTO;
import giuseppetavella.D5_gestione_eventi.payloads.in_response.DopoRegistrazioneDTO;
import giuseppetavella.D5_gestione_eventi.services.AuthService;
import giuseppetavella.D5_gestione_eventi.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UtentiService utentiService;


    // @PostMapping("/login")
    // public DopoLoginDTO login(@RequestBody @Validated LoginMandatoDTO body) {
    //     String accessToken = authService.checkCredentialsAndGenerateToken(body);
    //     return new DopoLoginDTO(accessToken);
    // }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public DopoRegistrazioneDTO register(@RequestBody @Validated RegistrazioneMandataDTO body,
                                         BindingResult validation) {

        if (validation.hasErrors()) {
            List<String> errors = validation.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidazionePayloadException(errors);
        }

        Utente newUser = this.utentiService.aggiungiUtente(body);

        return new DopoRegistrazioneDTO(newUser);

    }

}