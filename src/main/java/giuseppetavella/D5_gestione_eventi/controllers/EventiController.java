package giuseppetavella.D5_gestione_eventi.controllers;

import giuseppetavella.D5_gestione_eventi.entities.Evento;
import giuseppetavella.D5_gestione_eventi.entities.Utente;
import giuseppetavella.D5_gestione_eventi.payloads.in_request.NuovoEventoMandatoDTO;
import giuseppetavella.D5_gestione_eventi.payloads.in_response.EventoDaMandareDTO;
import giuseppetavella.D5_gestione_eventi.services.EventiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eventi")
public class EventiController {
    
    @Autowired
    private EventiService eventiService;

    /**
     * Aggiungi mio evento.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE')")
    public EventoDaMandareDTO aggiungiMioEvento(@RequestBody @Validated NuovoEventoMandatoDTO body,
                                                 @AuthenticationPrincipal Utente utenteAttuale) 
    {
        Evento eventoAppenaAggiunto = this.eventiService.aggiungiEventoDiUtente(body, utenteAttuale);
        return new EventoDaMandareDTO(eventoAppenaAggiunto);
    }
    
}
