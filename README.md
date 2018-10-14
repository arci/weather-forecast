# Assumptions

## City country
Since city name is ambiguous for OWM APIs, I suppose I can add another parameter called *country* that the user is required to specify along with the city name (for example: *Milan* and *IT*)

It would be possible to avoid this by exposing an additional API for searching a city given just its name and retrieving its unique ID, then the `/data` endpoint would accept those unique ID to interrogate the underlying service.

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
  daily: 18.984666666666666,
  nightly: 13.412399999999998,  
  pressure: 1005.5625
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
If the given pair: *city* and *country* does not match any city on OWM then a **404** is returned by OWM and to the user too.

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

# Development process

TODO

# Tests

A unit test namely `WeatherOperationTest` can be found within the test package. Just run it as unit test.

Sorry I did not have time for an integration test :(