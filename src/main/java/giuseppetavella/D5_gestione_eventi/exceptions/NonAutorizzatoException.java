package giuseppetavella.D5_gestione_eventi.exceptions;

public class NonAutorizzatoException extends RuntimeException {
    public NonAutorizzatoException(String message) {
        super("Non sei autenticato o non autorizzato ad accedere a questa risorsa. DETTAGLI: " + message);
    }
}
