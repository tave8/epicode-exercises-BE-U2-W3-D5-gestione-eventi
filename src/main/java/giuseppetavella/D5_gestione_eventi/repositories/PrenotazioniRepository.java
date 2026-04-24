package giuseppetavella.D5_gestione_eventi.repositories;

import giuseppetavella.D5_gestione_eventi.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {
    
    @Query(nativeQuery = true, value = """
        WITH Q_gia_prenotato_evento AS (
            SELECT
                (COUNT(*) > 0) AS gia_prenotato_evento
            FROM prenotazioni
            WHERE utente_id = :utenteId
              AND evento_id = :eventoId
        ),
        Q_posti_occupati_evento AS (
            SELECT
                COALESCE(SUM(numero_partecipanti), 0) AS posti_occupati
            FROM prenotazioni
            WHERE evento_id = :eventoId
        ),
        Q_posti_totali_evento AS (
            SELECT
                COALESCE(numero_posti_disponibili, 0) AS posti_totali
            FROM eventi
            WHERE evento_id = :eventoId
        ),
        Q_disponibilita AS (
            SELECT *
            FROM Q_gia_prenotato_evento
            CROSS JOIN Q_posti_totali_evento
            CROSS JOIN Q_posti_occupati_evento
        )
        SELECT
            COALESCE(
                (gia_prenotato_evento = false
                 AND :numeroPostiDesiderati <= posti_totali - posti_occupati),
                false
            ) AS puo_prenotare
        FROM Q_disponibilita
    """)
    Boolean puoPrenotare(@Param("eventoId") UUID eventoId, 
                         @Param("utenteId") UUID utenteId,
                         @Param("numeroPostiDesiderati") int numeroPostiDesiderati);
    
}
