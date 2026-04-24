package giuseppetavella.D5_gestione_eventi.services;

import giuseppetavella.D5_gestione_eventi.entities.Evento;
import giuseppetavella.D5_gestione_eventi.entities.Prenotazione;
import giuseppetavella.D5_gestione_eventi.entities.Utente;
import giuseppetavella.D5_gestione_eventi.exceptions.NonTrovatoException;
import giuseppetavella.D5_gestione_eventi.payloads.in_request.NuovaPrenotazioneMandataDTO;
import giuseppetavella.D5_gestione_eventi.repositories.PrenotazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioniService {
    
    @Autowired
    private PrenotazioniRepository prenotazioniRepository;
    
    @Autowired
    private EventiService eventiService;


    /**
     * Aggiungi una prenotazione di utente in input
     */
    public Prenotazione aggiungiPrenotazioneDiUtente(NuovaPrenotazioneMandataDTO bodyPrenotazione, 
                                                     Utente proprietarioPrenotazione) throws NonTrovatoException 
    {
        // trova l'evento che l'utente vuole aggiungere alla prenotazione
        Evento evento = this.eventiService.findById(bodyPrenotazione.eventoId());
        
        // verifica che si può prenotare
        
        
        Prenotazione nuovaPrenotazione = new Prenotazione(
                proprietarioPrenotazione,
                evento
        );
        
        return this.prenotazioniRepository.save(nuovaPrenotazione);    
    }
    
}
