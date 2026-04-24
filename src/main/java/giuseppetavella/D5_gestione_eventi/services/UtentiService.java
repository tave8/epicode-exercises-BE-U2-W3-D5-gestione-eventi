package giuseppetavella.D5_gestione_eventi.services;

import giuseppetavella.D5_gestione_eventi.entities.Utente;
import giuseppetavella.D5_gestione_eventi.enums.RuoloUtente;
import giuseppetavella.D5_gestione_eventi.exceptions.NonAutorizzatoException;
import giuseppetavella.D5_gestione_eventi.exceptions.NonTrovatoException;
import giuseppetavella.D5_gestione_eventi.exceptions.RuoloUtenteNonValidoException;
import giuseppetavella.D5_gestione_eventi.helpers.EnumHelper;
import giuseppetavella.D5_gestione_eventi.helpers.RuoloUtenteHelper;
import giuseppetavella.D5_gestione_eventi.payloads.in_request.RegistrazioneMandataDTO;
import giuseppetavella.D5_gestione_eventi.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtentiService {
    
    @Autowired
    private UtentiRepository utentiRepository;

    @Autowired
    private PasswordEncoder bcrypt;

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



    /**
     * A user with the given email exists?
     */
    public boolean existsByEmail(String email) {
        System.out.println("EMAIL MANDATA CON REGISTRAZIONE: " + email);
        return this.utentiRepository.existsByEmail(email);
    }


    /**
     * Add a user.
     * Checks if the email does not exist.
     */
    public Utente aggiungiUtente(Utente utente) throws NonAutorizzatoException  {
        if(this.existsByEmail(utente.getEmail())) {
            throw new NonAutorizzatoException("This email already exists.");
        }
        return this.utentiRepository.save(utente);
    }

    public Utente aggiungiUtente(RegistrazioneMandataDTO body) throws NonAutorizzatoException, RuoloUtenteNonValidoException {
        String hashedPassword = this.bcrypt.encode(body.password());
    
        // il ruolo esiste se: il suo campo non è null OR il suo campo non è vuoto  
        boolean esisteRuolo = RuoloUtenteHelper.esisteRuolo(body.ruolo());
        
        // *************
        // RUOLO ESISTE
        // *************
        
        if(esisteRuolo) {

            boolean ruoloEValido = RuoloUtenteHelper.ruoloEValido(body.ruolo());
            
            // ruolo non è valido
            if(!ruoloEValido) {
                throw new RuoloUtenteNonValidoException(body.ruolo());
            }
            
            // ruolo è valido -> crea utente con questo ruolo 
            Utente newUser = new Utente(
                    body.nome(),
                    body.cognome(),
                    body.email(),
                    hashedPassword,
                    RuoloUtente.valueOf(body.ruolo())
            );
            
            return this.aggiungiUtente(newUser);
        } 
        
        // **************
        // RUOLO NON ESISTE -> crea utente con ruolo default
        // **************
        
        Utente newUser = new Utente(
                body.nome(),
                body.cognome(),
                body.email(),
                hashedPassword
        );
        
        return this.aggiungiUtente(newUser);

    }


}
