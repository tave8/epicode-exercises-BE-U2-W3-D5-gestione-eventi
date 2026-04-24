package giuseppetavella.D5_gestione_eventi.payloads.in_response;

import giuseppetavella.D5_gestione_eventi.entities.Prenotazione;

import java.time.LocalDateTime;
import java.util.UUID;

public class PrenotazioneDaMandareDTO {
    private final UUID prenotazioneId;
    private final LocalDateTime createdAt;
    
    public PrenotazioneDaMandareDTO(Prenotazione prenotazione) {
        this.prenotazioneId = prenotazione.getPrenotazioneId();
        this.createdAt = prenotazione.getCreatedAt();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getPrenotazioneId() {
        return prenotazioneId;
    }
}
