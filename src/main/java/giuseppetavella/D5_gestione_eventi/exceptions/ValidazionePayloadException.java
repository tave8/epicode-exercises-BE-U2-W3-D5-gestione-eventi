package giuseppetavella.D5_gestione_eventi.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidazionePayloadException extends RuntimeException {
    private List<String> errors = new ArrayList<>();

    public ValidazionePayloadException(String message) {
        super(message);
    }

    public ValidazionePayloadException(List<String> errors) {
        super("Errore di validazione del payload: Almeno un campo non è valido.");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}