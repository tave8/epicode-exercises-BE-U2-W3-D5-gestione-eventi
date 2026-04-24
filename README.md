
- All'iscrizione, l'utente può scegliere se iscriversi come organizzatore o utente normale


# Endpoint

## /auth

### POST /register

Request

```
{
    nome: str
    cognome: str
    email: str
    password: str
}
```

Response 

```
{
    utenteId: str
}
```


### POST /login


Request

```
{
    email: str
    password: str
}
```

Response

```
{
    accessToken: str
}
```


# Sicurezza

Ruoli Utente

```

- utente normale
- organizzatore evento
    può creare un evento; non è un admin

codificati come:

- UTENTE_NORMALE
- ORGANIZZATORE

```


# Object design

## Entità

```

Utente
    - utenteId: uuid
    - nome: str
    - cognome: str
    - email: str
    - password: str
    - ruolo: enum(RuoloUtente)


Evento
    - eventoId: uuid
    - creatoDa: Utente
    - titolo: str
    - descrizione: str
    - dataQuandoEvento: date
    - luogo: str
    - numeroPostiDisponibili: int
    - createdAt: timestamp


Prenotazione
    prenotazioneId: uuid
    evento: Evento
    utente: Utente
    numeroPartecipanti: int, default: 1
    createdAt: timestamp

```

## Relazioni di entità

```

1 utente (organizzatore) --crea--> N eventi

1 evento --creato da--> 1 utente (organizzatore)

1 utente (normale) --prenota--> N prenotazioni

1 prenotazione --prenotata da--> 1 utente (normale)

1 prenotazione --contiene--> 1 evento

1 evento --associato a--> N prenotazioni


```

## Vincoli


```

- Un utente non può prenotare lo stesso evento più di una volta

- Un evento non può essere prenotato dallo stesso utente più di una volta

NE SEGUE:

Le prenotazioni hanno un vincolo di unicità nei campi ID dell'evento e ID dell'utente, quindi:

UNIQUE(evento_id, utente_id)


```


# Logica query

## Un evento ha almeno 1 posto disponibili?

```

INPUT: eventoId

OUTPUT: si/no

```


```


```


disponibilità eventi = numero di posti rimanenti dell'evento


per dimostrare che abbiamo usato le branch, le lasciamo, quindi non le cancelliamo


allegare screen di postman e database