package giuseppetavella.D5_gestione_eventi.repositories;

import giuseppetavella.D5_gestione_eventi.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {
    
    
    
}
