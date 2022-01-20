
# EsameOOP_Gennaio_2022
## Twitter Statistics

Il nostro programma permette di anallizzare e fare statistiche sul numero di tweet che vengono inviati dalle varie città del mondo tramite un codice di riconoscimento.
Si potrà inoltre visualizzare tramite l'apposito filtro la percentuale della determinata città.
Purtroppo gli utenti di Twitter non forniscono tutte le volte la geo-localizzazione, perciò buona parte delle statistiche è 'null'.
 

## Diagramma

![Diagramma di flusso](https://github.com/GiulioFriuli/EsameOOP_Gennaio_2022/blob/main/Screenshots/Diagramma%20di%20flusso.png)
## Installation

Installazione tramite prompt dei comandi

```bash
  git clone https://github.com/GiulioFriuli/EsameOOP_Gennaio_2022.git
```
    
## API Reference

```http
  TIPO http:localhost:8080/ROTTA/INPUT
```

| TIPO      | ROTTA      | INPUT               |DESCRIZIONE|
| :-------- | :-------   | :--------------     | :--       |
| `GET`     | `/control` |                     | Restituisce le statistiche generali della giornata|
| `GET`     | `/control` |     Nome città      | Restituisce la percentuale di tweet giornalieri della città specificata|
| `PUT`     | `/control` |                     | Avvia la ricerca e il salvataggio dei tweet odierni|




## Screenshots

Esempio della rotta GET /control 
![POSTMAN Screenshot](https://github.com/GiulioFriuli/EsameOOP_Gennaio_2022/blob/main/Screenshots/GET%20CONTROL.png)

Esempio della rotta GET /control/{nome città}
![POSTMAN Screenshot](https://github.com/GiulioFriuli/EsameOOP_Gennaio_2022/blob/main/Screenshots/GET%20CONTROL%20KAMPALA.png)

Relativo file di testo dell'esempio
![FILE TXT Screenshot](https://github.com/GiulioFriuli/EsameOOP_Gennaio_2022/blob/main/Screenshots/STATISTICJSON.png)


## Authors

- [Nicholas Bradach](https://www.github.com/GiulioFriuli)
- [Andrea Colonnini](https://www.github.com/andreaColonnini)


## Contatti
|NOME            |MAIL                        |CONTRIBUTO|
| :--            | :--                        | :--      |
|Nicholas Bradach| S1097719@studenti.univpm.it| 50%      |
|Andrea Colonnini| S1099988@studenti.univpm.it| 50%      |
