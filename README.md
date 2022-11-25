# ElementarySchool for Coincydence

Applicazione per la gestione di una scuola elementare per progetto Coincydence

## Struttura del progetto

Il progetto è stato creato con l'utilizzo del tool `JHipster` e in particolare del tool `JDL Studio`.
Il file jdl della struttura si può trovare nella struttura del progetto denominato `jdl-structure.jdl`.

### Scelte strutturali

La cancellazione dei record non è stata permessa se quest'ultimi hanno dei record collegati. E' stato considerato
l'inserimento di una data per la gestione annuale della scuola ma è stata ritenuta eccessiva per l'entità dell'esercizio.

## Setup ambiente

Prima di procedere con il test delle API è necessario creare il container per il database che può essere fatto con il comando:

```
docker-compose -f src/main/docker/mysql.yml up -d
```

## Test delle API

Nella struttura del progetto è disponibile la collection di postman dove sono presenti tutte le API da utilizzare con un'esempio di richiesta e risposta in modo da semplificare l'inserimento dei dati.
Nella collection di postman l'unica cosa da fare dopo averne fatto l'import è creare un ambiente settando la variabile `baseUrl` per il base path dell'applicazione, in dev `http://localhost:8080/api`.
L'API di login ha uno script che setta la variabile di ambiente `accessToken` che verrà usata nelle richieste successive come token di autenticazione.
Una volta avviata l'applicazione senza profili (dev viene abilitato di default) i changeset del database verranno eseguiti, insieme alle 25 classi aggiunte.
L'utente da utilizzare per il test dell'applicazione è quello di default identificato come `user` e `user` oppure possono essere registrati
nuovi utenti tramite l'API di registrazione che crea utenti già verificati.

Le API sotto il path `/api/public/**` sono API pubbliche quindi non necessitano dell'autenticazione.
