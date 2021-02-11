##Microservicio consulta de saldos mas movimientos
Microservicio desarrolado en el taller practico canales personas.

## Descripcion
 El microservicio de consulta saldos mas moviminetos es usado para obterner informacion financiera del 
 usuario respeto a una cuenta, nos da informacion como saldo disponible,
 saldo en bolsillos, saldo bloqueado, intereses, suspencion de interes entre otros, adicionalmente,
 retorna  una lista de movimientos del usuario de acuerdo a unos parametros tales como: rango de fechas, cantidad de movimientos,
 tipo de cuenta entre otros.

#### Consumidores del servicio

No aplica

#### Componentes que consume


Se realiza consumo de dos endpoint expuesto en sandbox son:

######Para la cosulta de movimientos de un cliente
POST: https://practicabanco.getsandbox.com:443/movements

######Para la consulta de saldo de un ciente
POST: https://practicabanco.getsandbox.com:443/balances

## Definición de mensajeria

Link de acceso a la mensajeria: https://github.com/santiVY/RetoCirculoTecnico/tree/main/firmas

Metodo de consumo POST
ENDPOINT: http://xxxx:8081/balance/movements
Headers:

    Content-Type:application/json
    
    Channel:APP
    
    Date:2020/01/01
    
    DateTime:11:00:00
    
    Ip:127.0.0.1
    
    ClientID:1234
    
    ClientType:cc

Request body:

    {
      "data": [
        {
          "account": {
            "type": "CUENTA_DE_AHORRO",
            "number": "40678098765"
          },
          "transaction": {
            "startDate": "2020-01-01",
            "endDate": "'2020-01-30",
            "minAmount": "2",
            "maxAmount": "4",
            "type": "DEBITO",
            "checkNumber": "",
            "group": "",
            "description": ""
          },
          "pagination": {
            "size": "30",
            "key": "2"
          },
          "office": {
            "code": "2005678",
            "name": "2005678"
          }
        }
      ]
    }

## Estructura

Se nombra cada proyecto gradle y su función dentro de la arquitectura limpia:

    Arranque de la aplicacion
        app-services
    Entry-points
        reactive-web
    Use case y modelos
        usecase
        model
    Driven Adapters
        restproduct

### Diseño de estructura de paquetes

![](https://bancolombia-my.sharepoint.com/personal/sanvel_bancolombia_com_co/Documents/Archivos%20de%20chat%20de%20Microsoft%20Teams/SaldoMasMovimientos.png)

<center>

**Historial de Versiones**

| Versión | Área                   | Participante | Observaciones   | Fecha      |
| ------- | ---------------------- | ------------ | --------------- | ---------- |
| 1.0   | Canales digitales personas | Brayan Durango Suarez - Jorge antonio Sierra - Santiago Velez yepes       | Versión Inicial | 07/05/2020 |
</center>