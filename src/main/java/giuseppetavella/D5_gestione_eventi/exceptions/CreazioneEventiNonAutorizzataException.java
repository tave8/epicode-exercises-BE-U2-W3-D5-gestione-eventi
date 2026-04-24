package giuseppetavella.D5_gestione_eventi.exceptions;

import giuseppetavella.D5_gestione_eventi.entities.Utente;

public class CreazioneEventiNonAutorizzataException extends RuntimeException {
    public CreazioneEventiNonAutorizzataException(Utente utente) {
        super("L'utente non è autorizzato a creare eventi. L'utente era: " + utente);
    }
}
