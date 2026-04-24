package giuseppetavella.D5_gestione_eventi.exceptions;

public class RuoloUtenteNonValidoException extends RuntimeException {
    public RuoloUtenteNonValidoException(String message) {
        super("Il ruolo utente non è valido. DETTAGLI: " + message);
    }
}
