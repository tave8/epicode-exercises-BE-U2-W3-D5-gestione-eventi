package giuseppetavella.D5_gestione_eventi.controllers;

import giuseppetavella.D5_gestione_eventi.entities.Evento;
import giuseppetavella.D5_gestione_eventi.entities.Prenotazione;
import giuseppetavella.D5_gestione_eventi.entities.Utente;
import giuseppetavella.D5_gestione_eventi.payloads.in_request.NuovaPrenotazioneMandataDTO;
import giuseppetavella.D5_gestione_eventi.payloads.in_request.NuovoEventoMandatoDTO;
import giuseppetavella.D5_gestione_eventi.payloads.in_response.EventoDaMandareDTO;
import giuseppetavella.D5_gestione_eventi.payloads.in_response.PrenotazioneDaMandareDTO;
import giuseppetavella.D5_gestione_eventi.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
    
    @Autowired
    private PrenotazioniService prenotazioniService;

    /**
     * Aggiungi una prenotazione associata al mio profilo.
     * Non c'è autorizzazione, perché anche anche un'organizzatore 
     * può partecipare a sua volta ad un evento.
     */
    @PostMapping("/eventi/me")
    @ResponseStatus(HttpStatus.CREATED)
    public PrenotazioneDaMandareDTO aggiungiMiaPrenotazioneEvento(@RequestBody @Validated NuovaPrenotazioneMandataDTO body,
                                                                  @AuthenticationPrincipal Utente utenteAttuale)
    {
        Prenotazione prenotazioneAppenaAggiunta = this.prenotazioniService.aggiungiPrenotazioneDiUtente(body, utenteAttuale);
        return new PrenotazioneDaMandareDTO(prenotazioneAppenaAggiunta);
    }
    
    
}
