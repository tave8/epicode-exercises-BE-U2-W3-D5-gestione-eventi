package giuseppetavella.D5_gestione_eventi.services;

import giuseppetavella.D5_gestione_eventi.entities.Evento;
import giuseppetavella.D5_gestione_eventi.entities.Utente;
import giuseppetavella.D5_gestione_eventi.exceptions.CreazioneEventiNonAutorizzataException;
import giuseppetavella.D5_gestione_eventi.exceptions.NonTrovatoException;
import giuseppetavella.D5_gestione_eventi.helpers.UtenteHelper;
import giuseppetavella.D5_gestione_eventi.payloads.in_request.NuovoEventoMandatoDTO;
import giuseppetavella.D5_gestione_eventi.repositories.EventiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventiService {
    
    @Autowired
    private EventiRepository eventiRepository;
    
    @Autowired
    private UtentiService utentiService;

    /**
     * Aggiungi evento associato all'utente in input.
     */
    public Evento aggiungiEventoDiUtente(NuovoEventoMandatoDTO bodyEvento, 
                                         Utente creatoreEvento) throws NonTrovatoException, 
                                                                        CreazioneEventiNonAutorizzataException
    {
        
        // se il creatore evento non esiste
        try {
        
            this.utentiService.findById(creatoreEvento.getUtenteId());
        
        } catch(NonTrovatoException ex) {
            throw new NonTrovatoException("Creatore dell'evento non esiste. Creatore: " + creatoreEvento);
        }
        
        // se il creatore evento non è autorizzato
        // a creare eventi
        if(!UtenteHelper.eAutorizzatoACreareEventi(creatoreEvento)) {
            throw new CreazioneEventiNonAutorizzataException(creatoreEvento);
        }
        
        // si può creare l'evento, associando l'evento al creatore evento     
        Evento evento = new Evento(
                creatoreEvento,
                bodyEvento.dataQuandoEvento(),
                bodyEvento.numeroPostiDisponibili(),
                bodyEvento.titolo(),
                bodyEvento.luogo(),
                bodyEvento.descrizione()
        );
        
        return this.eventiRepository.save(evento);
        
    }
    
}
