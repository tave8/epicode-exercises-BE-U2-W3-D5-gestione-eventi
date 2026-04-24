package giuseppetavella.D5_gestione_eventi.entities;

import giuseppetavella.D5_gestione_eventi.enums.RuoloUtente;
import giuseppetavella.D5_gestione_eventi.exceptions.CreazioneEventiNonAutorizzataException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "eventi")
public class Evento {
    
    @Id
    @GeneratedValue
    @Column(name = "evento_id")
    private UUID eventoId;
    
    @ManyToOne
    @JoinColumn(name = "creato_da_id", nullable = false)
    private Utente creatoDa;
    
    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String descrizione;

    @Column(name = "data_quando_evento", nullable = false)
    private LocalDate dataQuandoEvento;
    
    @Column(nullable = false)
    private String luogo;

    @Column(name = "numero_posti_disponibili", nullable = false)
    private int numeroPostiDisponibili;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    protected Evento() {}
    
    public Evento(Utente creatoDa, 
                  LocalDate dataQuandoEvento, 
                  int numeroPostiDisponibili, 
                  String titolo,
                  String luogo,
                  String descrizione) throws CreazioneEventiNonAutorizzataException
    {
        boolean eAutorizzatoACreareEventi = creatoDa.getRuolo().equals(RuoloUtente.ORGANIZZATORE);
        // verifica che chi crea l'evento sia un'utente 
        // con un ruolo autorizzato a creare l'evento
        if(!eAutorizzatoACreareEventi) {
            throw new CreazioneEventiNonAutorizzataException(creatoDa);
        }
        this.creatoDa = creatoDa;
        this.dataQuandoEvento = dataQuandoEvento;
        this.numeroPostiDisponibili = numeroPostiDisponibili;
        this.luogo = luogo;
        this.createdAt = LocalDateTime.now();
        this.setTitolo(titolo);
        this.setDescrizione(descrizione);
    }

    /**
     * Omettere la descrizione dell'evento significa che
     * la descrizione sarà vuota.
     */
    public Evento(Utente creatoDa,
                  LocalDate dataQuandoEvento,
                  int numeroPostiDisponibili,
                  String titolo,
                  String luogo) 
    {
        this(creatoDa, dataQuandoEvento, numeroPostiDisponibili, titolo, luogo, "");
    }
    

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    

    public LocalDate getDataQuandoEvento() {
        return dataQuandoEvento;
    }
    

    public Utente getCreatoDa() {
        return creatoDa;
    }
    

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public UUID getEventoId() {
        return eventoId;
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

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public String toString() {
        return "Evento{" +
                ", eventoId=" + eventoId +
                ", creatoDa=" + creatoDa +
                ", titolo='" + titolo + '\'' +
                ", dataQuandoEvento=" + dataQuandoEvento +
                ", luogo='" + luogo + '\'' +
                ", numeroPostiDisponibili=" + numeroPostiDisponibili +
                '}';
    }
}
