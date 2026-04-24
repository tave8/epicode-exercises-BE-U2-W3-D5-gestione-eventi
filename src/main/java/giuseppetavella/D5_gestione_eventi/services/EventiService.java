package giuseppetavella.D5_gestione_eventi.services;

import giuseppetavella.D5_gestione_eventi.entities.Evento;
import giuseppetavella.D5_gestione_eventi.entities.Utente;
import giuseppetavella.D5_gestione_eventi.exceptions.CreazioneEventiNonAutorizzataException;
import giuseppetavella.D5_gestione_eventi.exceptions.NonAutorizzatoException;
import giuseppetavella.D5_gestione_eventi.exceptions.NonTrovatoException;
import giuseppetavella.D5_gestione_eventi.helpers.UtenteHelper;
import giuseppetavella.D5_gestione_eventi.payloads.in_request.EventoAggiornatoMandatoDTO;
import giuseppetavella.D5_gestione_eventi.payloads.in_request.NuovoEventoMandatoDTO;
import giuseppetavella.D5_gestione_eventi.repositories.EventiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventiService {
    
    @Autowired
    private EventiRepository eventiRepository;
    
    @Autowired
    private UtentiService utentiService;


    /**
     * Trova un evento per ID.
     */
    public Evento findById(UUID userId) throws NonTrovatoException {
        return this.eventiRepository.findById(userId).orElseThrow(() -> new NonTrovatoException(userId, "evento"));
    }
    
    /**
     * Trova eventi.
     */
    public Page<Evento> find(int page, int size, String sortBy) {
        // / the size of each page (how many elements in each page)
        int finalSize = Math.min(10, size);
        // the page number
        int finalPage = Math.max(0, page);
        // page is the function that will get translated to SQL,
        // that will in turn filter the result set
        Pageable pageable = PageRequest.of(finalPage, finalSize, Sort.by(sortBy));
        return this.eventiRepository.findAll(pageable);
    }
    

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


    /**
     * Modifica evento associato all'utente in input.
     */
    public Evento modificaEventoDiUtente(UUID eventoId, 
                                         EventoAggiornatoMandatoDTO bodyEvento,
                                         Utente presuntoCreatoreEvento) throws NonTrovatoException,
                                                                                NonAutorizzatoException
    {

        Evento eventoDaModificare;
        
        // se l'evento non esiste
        try {

            eventoDaModificare = this.findById(eventoId);

        } catch(NonTrovatoException ex) {
            throw new NonTrovatoException("Evento non esiste. ID era: " + eventoId);
        }
        
        
        // se il presunto creatore evento non esiste
        try {
            
            this.utentiService.findById(presuntoCreatoreEvento.getUtenteId());

        } catch(NonTrovatoException ex) {
            throw new NonTrovatoException("Presunto creatore dell'evento non esiste. Presunto creatore: " + presuntoCreatoreEvento);
        }
        
        boolean creatoreEventoCoincide = UtenteHelper.creatoreEventoCoincide(eventoDaModificare.getCreatoDa(), presuntoCreatoreEvento);
        
        // se il creatore reale dell'evento
        // non è uguale al presunto creatore dell'evento
        // (cioè si sta cercando di modificare un evento "non mio")
        if(!creatoreEventoCoincide) {
            throw new NonAutorizzatoException("Non hai il permesso di modificare l'evento con ID "+eventoId);
        }
        
        // si può modificare l'evento
        eventoDaModificare.setTitolo(bodyEvento.titolo());
        eventoDaModificare.setDescrizione(bodyEvento.descrizione());

        return this.eventiRepository.save(eventoDaModificare);

    }



    /**
     * Rimuovi evento associato all'utente in input.
     */
    public void rimuoviEventoDiUtente(UUID eventoId, 
                                      Utente presuntoCreatoreEvento) throws NonTrovatoException, 
                                                                            NonAutorizzatoException
    {

        Evento eventoDaRimuovere;

        // se l'evento non esiste
        try {

            eventoDaRimuovere = this.findById(eventoId);

        } catch(NonTrovatoException ex) {
            throw new NonTrovatoException("Evento non esiste. ID era: " + eventoId);
        }


        // se il presunto creatore evento non esiste
        try {

            this.utentiService.findById(presuntoCreatoreEvento.getUtenteId());

        } catch(NonTrovatoException ex) {
            throw new NonTrovatoException("Presunto creatore dell'evento non esiste. Presunto creatore: " + presuntoCreatoreEvento);
        }

        boolean creatoreEventoCoincide = UtenteHelper.creatoreEventoCoincide(eventoDaRimuovere.getCreatoDa(), presuntoCreatoreEvento);

        // se il creatore reale dell'evento
        // non è uguale al presunto creatore dell'evento
        // (cioè si sta cercando di modificare un evento "non mio")
        if(!creatoreEventoCoincide) {
            throw new NonAutorizzatoException("Non hai il permesso di modificare l'evento con ID "+eventoId);
        }

        // si può rimuovere l'evento
  

        this.eventiRepository.delete(eventoDaRimuovere);

    }
    
    
}
