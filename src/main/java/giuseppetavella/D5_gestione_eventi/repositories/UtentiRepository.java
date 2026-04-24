package giuseppetavella.D5_gestione_eventi.repositories;

import giuseppetavella.D5_gestione_eventi.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UtentiRepository extends JpaRepository<Utente, UUID> {

    /**
     * Find a user by email.
     */
    @Query("SELECT u FROM Utente u WHERE LOWER(u.email) = LOWER(:email)")
    Utente findByEmail(String email);

    /**
     * The user with the given email exists?
     */
    @Query("SELECT COUNT(u) > 0 FROM Utente u WHERE LOWER(u.email) = LOWER(:email)")
    boolean existsByEmail(String email);
    
}
