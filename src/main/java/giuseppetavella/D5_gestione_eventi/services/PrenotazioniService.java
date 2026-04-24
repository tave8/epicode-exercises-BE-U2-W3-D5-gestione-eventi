package giuseppetavella.D5_gestione_eventi.services;

import giuseppetavella.D5_gestione_eventi.entities.Evento;
import giuseppetavella.D5_gestione_eventi.entities.Prenotazione;
import giuseppetavella.D5_gestione_eventi.entities.Utente;
import giuseppetavella.D5_gestione_eventi.exceptions.NonTrovatoException;
import giuseppetavella.D5_gestione_eventi.exceptions.PrenotazioneNonDisponibileException;
import giuseppetavella.D5_gestione_eventi.payloads.in_request.NuovaPrenotazioneMandataDTO;
import giuseppetavella.D5_gestione_eventi.repositories.PrenotazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        
        UUID eventoId = evento.getEventoId();
        UUID utenteId = proprietarioPrenotazione.getUtenteId();
        Integer numeroPostiDesiderato = bodyPrenotazione.numeroPostiDesiderato();
        
        // se non c'è il numero di posti desiderato, si assume che sia 1
        if(numeroPostiDesiderato == null) {
            numeroPostiDesiderato = 1;
        }
        
        // verifica che l'utente può prenotare il dato evento 
        // per il numero di posti desiderato
        if(!this.puoPrenotare(eventoId, utenteId, numeroPostiDesiderato)) {
            throw new PrenotazioneNonDisponibileException(eventoId, utenteId);
        }
        
        // l'utente può prenotare
        Prenotazione nuovaPrenotazione = new Prenotazione(
                proprietarioPrenotazione,
                evento
        );
        
        return this.prenotazioniRepository.save(nuovaPrenotazione);    
    }

    /**
     * L'utente può prenotare l'evento?
     */
    public boolean puoPrenotare(UUID eventoId, UUID utenteId, int numeroPostiDesiderato) {
        return this.prenotazioniRepository.puoPrenotare(eventoId, utenteId, numeroPostiDesiderato);
    }
    
    
}
