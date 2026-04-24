package giuseppetavella.D5_gestione_eventi.payloads.in_response;

import giuseppetavella.D5_gestione_eventi.entities.Utente;

import java.util.UUID;

public class DopoRegistrazioneDTO {
    private final UUID utenteId;

    public DopoRegistrazioneDTO(Utente utente) {
        this.utenteId = utente.getUtenteId();
    }

    public UUID getUtenteId() {
        return utenteId;
    }
}