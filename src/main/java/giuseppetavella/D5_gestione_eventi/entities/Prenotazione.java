package giuseppetavella.D5_gestione_eventi.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "prenotazioni",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"utente_id", "evento_id"})
        }
)
public class Prenotazione {
    
    @Id
    @GeneratedValue
    @Column(name = "prenotazione_id")
    private UUID prenotazioneId;
    
    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;
    
    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;
    
    @Column(name = "numero_partecipanti", nullable = false)
    private int numeroPartecipanti;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    protected Prenotazione() {}
    
    public Prenotazione(Utente utente, Evento evento) {
        this.evento = evento;
        this.utente = utente;
        this.numeroPartecipanti = 1;
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    

    public Evento getEvento() {
        return evento;
    }
    
    public int getNumeroPartecipanti() {
        return numeroPartecipanti;
    }
    

    public UUID getPrenotazioneId() {
        return prenotazioneId;
    }

    public Utente getUtente() {
        return utente;
    }


    @Override
    public String toString() {
        return "Prenotazione{" +
                "createdAt=" + createdAt +
                ", prenotazioneId=" + prenotazioneId +
                ", utente=" + utente +
                ", evento=" + evento +
                '}';
    }
}
