-- l'utente in input ha già prenotato questo evento?
WITH Q_gia_prenotato_evento AS (
    SELECT
        (COUNT(*) > 0) AS gia_prenotato_evento
    FROM
        prenotazioni
    WHERE
        utente_id = :utenteId
      AND evento_id = :eventoId
),

-- quanti posti sono già occupati dall'evento in input?
 Q_posti_occupati_evento AS (
     SELECT
         COALESCE(SUM(numero_partecipanti), 0) AS posti_occupati
     FROM
         prenotazioni
     WHERE
         evento_id = :eventoId
 ),

-- quanti posti totali aveva inizialmente l'evento in input?
 Q_posti_totali_evento AS (
     SELECT
         COALESCE(numero_posti_disponibili, 0) AS posti_totali
     FROM
         eventi
     WHERE
         evento_id = :eventoId
 ),

-- ottieni tutte le colonne
 Q_disponibilita AS (
     SELECT *
     FROM Q_gia_prenotato_evento
              CROSS JOIN Q_posti_totali_evento
              CROSS JOIN Q_posti_occupati_evento
 )

SELECT
    -- l'utente non ha già prenotato questo evento
    COALESCE(
            (gia_prenotato_evento = false
                -- il numero di posti desiderati è <= dei posti rimasti
                --   posti rimasti = posti totali - posti occupati
                AND :numeroPostiDesiderati <= posti_totali - posti_occupati),
            false
    ) AS puo_prenotare
FROM
    Q_disponibilita;