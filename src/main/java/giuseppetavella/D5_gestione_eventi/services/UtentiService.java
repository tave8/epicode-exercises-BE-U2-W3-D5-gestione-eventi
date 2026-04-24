package giuseppetavella.D5_gestione_eventi.services;

import giuseppetavella.D5_gestione_eventi.entities.Utente;
import giuseppetavella.D5_gestione_eventi.exceptions.NonTrovatoException;
import giuseppetavella.D5_gestione_eventi.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtentiService {
    
    @Autowired
    private UtentiRepository utentiRepository;

    /**
     * Find a user by ID.
     */
    public Utente findById(UUID userId) throws NonTrovatoException {
        return this.utentiRepository.findById(userId).orElseThrow(() -> new NonTrovatoException(userId, "utente"));
    }

    /**
     * Find a user by email.
     */
    public Utente findByEmail(String email) throws NonTrovatoException {
        Utente userFound = this.utentiRepository.findByEmail(email);
        if (userFound == null) {
            throw new NonTrovatoException("User with email " + email + " was not found.");
        }
        return userFound;
    }


}
