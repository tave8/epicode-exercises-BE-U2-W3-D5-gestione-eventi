
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
    ruolo?: str
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


## /eventi


### GET / 

Response 

```
content: [
    {
        titolo: str
        dataQuandoEvento: str (YYYY-mm-dd)
        luogo: str
        numeroPostiDisponibili: int
        descrizione: str
        createdAt: str (timestamp)
    }
]
```



### POST /me

Request

```
{
    titolo: str
    dataQuandoEvento: str (YYYY-mm-dd)
    luogo: str
    numeroPostiDisponibili: int
    descrizione: str
}
```

Response

```
{
    titolo: str
    dataQuandoEvento: str (YYYY-mm-dd)
    luogo: str
    numeroPostiDisponibili: int
    descrizione: str
    createdAt: str (timestamp)
}
```


### PUT /:eventoId


```
{
    titolo: str
    descrizione: str
}
```

Response

```
{
    titolo: str
    dataQuandoEvento: str (YYYY-mm-dd)
    luogo: str
    numeroPostiDisponibili: int
    descrizione: str
    createdAt: str (timestamp)
}
```

### DELETE /:eventoId

Request

```
empty body
```

Response

```
empty body
```


## /prenotazioni


### POST /eventi/me

Request

```
{
    eventoId: str
}
```

Response

```
{
    prenotazioneId: str
    createdAt: str (timestamp)
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

## Un utente puo prenotare un evento?

```

INPUT: evento, utente

OUTPUT: si/no

```


```

Un utente può prenotare un evento se:
    
    A) l'utente non ha già prenotato quello stesso evento
    
    B)  AND il numero di posti desiderati è >= della differenza tra i posti totali e quelli occupati
    
    
NE SEGUE:

A)

    NON (
        esiste una prenotazione per
        l'utente in input AND l'evento in input
    )
    
    
B)

    il numero di posti è maggiore della differenza tra i posti totali (inizialmente fissati dall'organizzatore)
       e da i posti occupati (cioè dalla somma dei posti nelle prenotazioni, per questo evento)
    
    
   
    
```
