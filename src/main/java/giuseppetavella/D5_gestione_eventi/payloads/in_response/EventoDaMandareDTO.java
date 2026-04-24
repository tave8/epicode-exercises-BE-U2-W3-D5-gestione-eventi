package giuseppetavella.D5_gestione_eventi.payloads.in_response;

import giuseppetavella.D5_gestione_eventi.entities.Evento;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventoDaMandareDTO {
    private final String titolo;
    private final String luogo;
    private final String descrizione;
    private final int numeroPostiDisponibili;
    private final LocalDate dataQuandoEvento;
    private final LocalDateTime createdAt;
    
    public EventoDaMandareDTO(Evento evento) {
        this.titolo = evento.getTitolo();
        this.luogo = evento.getLuogo();
        this.descrizione = evento.getDescrizione();
        this.numeroPostiDisponibili = evento.getNumeroPostiDisponibili();
        this.dataQuandoEvento = evento.getDataQuandoEvento();
        this.createdAt = evento.getCreatedAt();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDate getDataQuandoEvento() {
        return dataQuandoEvento;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getLuogo() {
        return luogo;
    }

    public int getNumeroPostiDisponibili() {
        return numeroPostiDisponibili;
    }

    public String getTitolo() {
        return titolo;
    }
}
