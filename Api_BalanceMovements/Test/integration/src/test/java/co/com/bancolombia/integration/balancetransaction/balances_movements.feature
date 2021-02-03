Feature: Test API balanceMovements

  Background:
    * url 'http://localhost:8081/balance/movements'
    * def var_body = '{"data": [{"account": {"type": "CUENTA_DE_AHORRO","number": "40678098765"},"transaction": {"startDate": "2020-01-01","endDate": "2020-01-30","minAmount": "2","maxAmount": "4", "type": "DEBITO", "checkNumber": "123","group": "nada","description": "nada"},"pagination": {"size": "30","key": "1"},"office": {"code": "2005678","name": "2005678"}}]}'

  Scenario: Consulta saldo mas movimientos  exitosa
    Given headers {Content-Type: 'application/json', Channel:'APP',  Date: '2020/07/24', DateTime:'20:18:15', Ip:'127.0.0.1', ClientID:'1234', ClientType:'CC'}
    When request var_body
    When method post
    Then status 200
    And match $.data[0].account.number == '40678098765'

  Scenario: Consultar saldo mas movimientos  sin el header Channel
    Given headers { Content-Type: 'application/json', Date:'2020/11/05', DateTime:'10:37:15', Ip:'127.0.0.1', ClientID:'1234', ClientType:'CC'}
    When request var_body
    And method post
    Then status 400
    And match response == {code: Error, message: La cabecera Channel Es requerida.}

  Scenario: Consultar saldo mas movimientos  con el header Channel de longitud invalida
    Given headers { Content-Type: 'application/json', Channel:'dfdgg', Date:'2020/11/05', DateTime:'10:37:15', Ip:'127.0.0.1', ClientID:'1234', ClientType:'CC'}
    When request var_body
    And method post
    Then status 400
    And match response == {code: Error, message: La cabecera Channel Debe cumplir con la longitud requerida entre 1 y 3 caracteres.} || response == {code: Error, message: La cabecera Channel Solo acepta letras mayusculas.}

  Scenario: Consultar saldo mas movimientos  con el header Channel en letras minusculas
    Given headers { Content-Type: 'application/json', Channel:'hj', Date:'2020/11/05', DateTime:'10:37:15', Ip:'127.0.0.1', ClientID:'1234', ClientType:'CC'}
    When request var_body
    And method post
    Then status 400
    And match response == {code: Error, message: La cabecera Channel Solo acepta letras mayusculas.} || response == {code: Error, message: La cabecera Channel Debe cumplir con la longitud requerida entre 1 y 3 caracteres.}


  Scenario: Consultar saldo mas movimientos  sin el header Date
    Given headers { Content-Type: 'application/json',  Channel:'APP', DateTime:'10:37:15', Ip:'127.0.0.1', ClientID:'1234', ClientType:'CC'}
    When request var_body
    And method post
    Then status 400
    And match response == {code: Error,message: La cabecera Date Es requerida.}


  Scenario: Consultar saldo mas movimientos  sin el header DateTime
    Given headers { Content-Type: 'application/json',   Channel:'APP', Date:'2020/11/05', Ip:'127.0.0.1' , ClientID:'1234', ClientType:'CC' }
    When request var_body
    And method post
    Then status 400
    And match response == {code: Error, message: La cabecera DateTime Es requerida.} || response == {code: Error, message: La cabecera DateTime Debe cumplir con el formato HH:mm:ss}

  Scenario: Consultar saldo mas movimientos  con el header DateTime invalido
    Given headers { Content-Type: 'application/json',   Channel:'APP', Date:'2020/11/05', DateTime:'10:37:15ddf', Ip:'127.0.0.1' , ClientID:'1234', ClientType:'CC' }
    When request var_body
    And method post
    Then status 400
    And match response == {code: Error, message: La cabecera DateTime Debe cumplir con el formato HH:mm:ss} || response == {code: Error, message: La cabecera DateTime Es requerida.}

  Scenario: Consultar saldo mas movimientos  sin el header Ip
    Given headers { Content-Type: 'application/json',  Channel:'APP', Date:'2020/11/05', DateTime:'10:37:15', ClientID:'1234', ClientType:'CC' }
    When request var_body
    And method post
    Then status 400
    And match response == {code: Error, message: La cabecera Ip Es requerida.}


  Scenario: Consultar saldo mas movimientos  sin el ClientID
    Given headers { Content-Type: 'application/json',  Channel:'APP', Date:'2020/11/05', DateTime:'10:37:15', Ip:'127.0.0.1', ClientType:'CC' }
    When request var_body
    And method post
    Then status 400
    And match response == {code: Error,message: La cabecera ClientID Es requerida.}

  Scenario: Consultar saldo mas movimientos  sin el clientType
    Given headers { Content-Type: 'application/json',  Channel:'APP', Date:'2020/11/05', DateTime:'10:37:15', Ip:'127.0.0.1', ClientID:'1234' }
    When request var_body
    And method post
    Then status 400
    And match response == {code: Error,message: La cabecera ClientType Es requerida.}


