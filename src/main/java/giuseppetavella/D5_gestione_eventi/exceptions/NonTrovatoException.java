package giuseppetavella.D5_gestione_eventi.exceptions;

import java.util.UUID;

public class NonTrovatoException extends RuntimeException {
    public NonTrovatoException(UUID itemId) {
        super("The item with ID " + itemId + " has not been found.");
    }


    public NonTrovatoException(UUID itemId, String informalEntity) {
        super("The item '" + informalEntity + "' with ID " + itemId + " has not been found.");
    }

    public NonTrovatoException(String msg) {
        super(msg);
    }
}
