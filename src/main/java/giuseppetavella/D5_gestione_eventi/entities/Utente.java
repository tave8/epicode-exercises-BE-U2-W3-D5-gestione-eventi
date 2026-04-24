package giuseppetavella.D5_gestione_eventi.entities;

import giuseppetavella.D5_gestione_eventi.enums.RuoloUtente;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "utenti")
public class Utente {
    
    @Id
    @GeneratedValue
    private UUID utenteId;
    
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RuoloUtente ruolo;
    
    protected Utente() {}
    
    public Utente(String nome, String cognome, String email, String password, RuoloUtente ruolo) {
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        this.setNome(nome);
        this.setCognome(cognome);
    }

    /**
     * Non specificare il ruolo utente significa che
     * il ruolo default è un utente normale.
     */
    public Utente(String nome, String cognome, String email, String password) {
        this(nome, cognome, email, password, RuoloUtente.UTENTE_NORMALE);
    }
    

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }
    

    public String getEmail() {
        return email;
    }
    

    public RuoloUtente getRuolo() {
        return ruolo;
    }
    

    public UUID getUtenteId() {
        return utenteId;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "cognome='" + cognome + '\'' +
                ", utenteId=" + utenteId +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", ruolo=" + ruolo +
                '}';
    }
}
