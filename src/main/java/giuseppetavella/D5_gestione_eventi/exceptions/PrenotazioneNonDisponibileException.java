package giuseppetavella.D5_gestione_eventi.exceptions;

import java.util.UUID;

public class PrenotazioneNonDisponibileException extends RuntimeException {
    public PrenotazioneNonDisponibileException(UUID eventoId, UUID utenteId) {
        super("Non puoi prenotare. Possibili cause: A) ha già prenotato questo evento "
                +"B) numero di posti dell'evento non sufficiente.");
    }
}
