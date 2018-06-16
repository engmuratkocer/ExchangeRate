------------------------------------Exchange Rate Application------------------------------------

This application used to retrieve exchange rate EUR to USD and saves it to db for historical purposes.
Application has two rest service; first one is being used to retrieve latest exchange Rate,
Second one is being used to retrieve exchange rate history for given dates.

To create project structure spring initializer used.
https://start.spring.io/

To easily run and test application in memory database H2 used.

ExchangeRateService service uses ExchangeRateRepository to access DB. ExchangeRateRepository is a spring data
CrudRepository. Controller and exchange rate updater services uses ExchangeRateService service for
database operations.

ExchangeController is a spring controller used to expose rest services.

ExchangeRateAsynchronousService is a task scheduler. Takes period and currencies from configuration.

ExchangeRateUpdater worker thread is used to retrieve exchange rate and saves to DB via ExchangeRateService.

Project works with in memory db H2. Below link can be used to access this DB via browsers.
http://localhost:8080/h2-console.

Sample address for retrieving current exchange rate for EUR to USD
http://localhost:8080/getExchangeRate

Sample address for retrieving exchange rate history for given dates.
http://localhost:8080/getExchangeRateHistory?from=2018-06-15 17:00:00&to=2018-06-30 22:00:00