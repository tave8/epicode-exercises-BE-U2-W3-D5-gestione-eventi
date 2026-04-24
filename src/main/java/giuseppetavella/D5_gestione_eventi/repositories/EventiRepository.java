package giuseppetavella.D5_gestione_eventi.repositories;

import giuseppetavella.D5_gestione_eventi.entities.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventiRepository extends JpaRepository<Evento, UUID> {

    /**
     * Trova eventi.
     */
    Page<Evento> findAll(Pageable pageable);
    
}
