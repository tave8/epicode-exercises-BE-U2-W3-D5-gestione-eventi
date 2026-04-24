package giuseppetavella.D5_gestione_eventi.services;


import giuseppetavella.D5_gestione_eventi.entities.Utente;
import giuseppetavella.D5_gestione_eventi.exceptions.NonAutorizzatoException;
import giuseppetavella.D5_gestione_eventi.exceptions.NonTrovatoException;
import giuseppetavella.D5_gestione_eventi.payloads.in_request.LoginMandatoDTO;
import giuseppetavella.D5_gestione_eventi.security.TokenTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private TokenTools tokenTools;

    @Autowired
    private PasswordEncoder bcrypt;


    public String checkCredentialsAndGenerateToken(LoginMandatoDTO body) throws NonTrovatoException {


        try {

            Utente userFound = this.utentiService.findByEmail(body.email());

            // we compare the password coming from the request's body
            // with the actual password found in the database
            boolean isPasswordMatch = this.bcrypt.matches(body.password(), userFound.getPassword());

            // se la password dell'utente corrisponde a quella che si trova
            // nell'utente che ha questa email, vuol dire che l'utente si è loggato
            // con successo, quindi crea il token
            if (isPasswordMatch) {
                return tokenTools.generateToken(userFound);
            } else {
                throw new NonAutorizzatoException("Wrong credentials.");
            }

        } catch (NonTrovatoException ex) {
            throw new NonAutorizzatoException("Wrong credentials.");
        }


    }
}