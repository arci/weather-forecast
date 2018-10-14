# Assumptions

## City country
Since city name is ambiguous for OpenWeatherMap APIs, I suppose I can add another parameter called *country* that the user is required to specify along with the city name (for example: *Milan* and *IT*)

It would be possible to avoid this by exposing an additional API for searching a city given just its name and retrieving its unique ID, then the `/data` endpoint would accept those unique ID to interrogate the underlying service.

## Dates and timezones
I assumed the timezone of the OpenWeatherMap response refer to the client timezone since I do not found in the documentation a reference to the used timezone available within the JSON response.

# Environment

The application has been developed and tested using Java 8 and deployed on a Tomcat 9.0 Server, the application is packaged as a _war_.

# Endpoints

The application consist of a single GET endpoint `/data` that accepts two query parameters: *city* and *country*.

```
/data?city=Milan&country=IT
```

## Successful response
A successful response (status code **200**) for the above request would be:

```
{
  forecast: {
    2018-10-15: {
      daily: 18.84,
      nightly: 13.386000000000001,
      pressure: 1006.19125
    },
    2018-10-19: {
      daily: 20.706666666666667,
      nightly: 13.826666666666668,
      pressure: 1006.9983333333333
    },
    2018-10-18: {
      daily: 19.426666666666666,
      nightly: 14.847999999999999,
      pressure: 1003.615
    },
    2018-10-17: {
      daily: 19.279999999999998,
      nightly: 12.102,
      pressure: 1004.8849999999999
    },
    2018-10-16: {
      daily: 16.8,
      nightly: 12.856,
      pressure: 1006.51125
    }
  }
}
```

## Bad request response
The parameter *city* and *country* are validated using Java Bean Validation. 

In case of any validation error the response will be a JSON and the status code **400**.

For example the request:  

```
GET /data
```
will have the following response:

```
[
  {
    message: "country may not be null"
  },
  {
    message: "city may not be null"
  }
]
```
If *city* and *country* does not match the regular expressions used for validation, as in the request:

```
GET /data?city=Milan&country=malicious
```
the response will be:

```
[
  {
    message: "country code should have two characters"
  }
]
```

## City not found
If the given pair: *city* and *country* does not match any city on OpenWeatherMap then a **404** is returned by OpenWeatherMap and to the user too.

For example, given the request:

```
GET /data?city=Unknown&country=IT
```
the response will be:

```
{
  message: "city not found"
}
```

# Documentation
OpenAPI 3.x documentation is available while the server is running at `/openapi.json`. OpenAPI specification is produced using Swagger annotations.

A static version can be found at the root of the project with the same name.

# Project structure
The project uses *Jersey* for exposing REST APIs.

- application is initialized within class `it.arcidiacono.weatherforecast.WeatherApplication`
- the REST resource is at `it.arcidiacono.weatherforecast.resource.WeatherForecastResource`

Dependency injection is used to inject dependencies within classes, the composition root is at `it.arcidiacono.weatherforecast.ApplicationBinder`.

Request bean are available within the package `it.arcidiacono.weatherforecast.request` while response bean in `it.arcidiacono.weatherforecast.response`.

To decouple web tier from the application logic an *operation* class is injected within the resource: `it.arcidiacono.weatherforecast.operation.WeatherOperation` and to further separate the application logic from the specific underlying service an instance of the service implementing the interface `it.arcidiacono.weatherforecast.service.WeatherService` is injected within the operation.

The service interface is binded to the OpenWeatherMap implementation in the composition root. The OpenWeatherMap implementation resides in the packages `it.arcidiacono.weatherforecast.OpenWeatherMap.*` that can be moved to a separate maven project.

## Further work
I leaved some *TODO* comments within classes to indicate where improvements can be made.

All methods relative to time manipulation may be moved to an utility class.

# Tests

A unit test namely `WeatherOperationTest` can be found within the test package. Just run it as unit test.

# Source Code

Source code can be found on [GitHub](https://github.com/Arci/weather-forecast).
