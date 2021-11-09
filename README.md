**Note:** VIND has been discontinued

---

# Vind in Spring Boot Applications
This repo shows how to integrate VIND in Spring Boot applications. It is not about the functionality of VIND itself, which is exhaustively described [in the repo](https://github.com/RBMHTechnology/vind)
and the linked [feature documentation](https://rbmhtechnology.github.io/vind/). It contains a simple demo application including some best practices regarding development, testing, dependency management etc.

## The demo
The demo shows how to create a simple search scenario using VIND. It indexes 3 documents on a local solr server and provides a simple API to issue queries.

### Run the demo
1. start local vind server: `docker-compose up`
2. start application `./gradlew bootRun`

### Use the search webservice
The search webservice is described via [Open API spec](./src/main/resources/api.yaml). Some simple request:

* Search for `first`: `curl -X GET "http://localhost:8080/search?q=first&limit=10" -H "accept: application/json"`
* Search for articles of the last 24 hours: `curl -X GET "http://localhost:8080/search?range=PAST_DAY&limit=10" -H "accept: application/json"`
* Search for articles with tag `cat`: `curl -X GET "http://localhost:8080/search?tag=cat&limit=10" -H "accept: application/json"`

## The application

### Dependency and Spring AutoConfiguration
If you use VIND you use (currently) Solr, which mean that you have to deal with some Spring Magic (as Spring Boot has some support of Solr as well, which leads to version conflicts).
Therefore:
* force the VIND related solr-core and solrj-core version (you can look this up in the [VIND github repo](https://github.com/RBMHTechnology/vind) easily)
* exclude Solr Auto Configuration for the application `@SpringBootApplication(exclude = SolrAutoConfiguration.class)`
* try to use embedded solr server with caution (see the paragraph about testing)

### Search Server Connection
The simplest way to use a VIND search server is to outcheck a bean, like in [VindConfiguration](./src/main/java/at/redlink/vinddemo/configuration/VindConfiguration.java).
In the demo case a local search server is linked.

### Development and local setup
#### Run local VIND server
The easiest way to run a local VIND server is to use the [vind docker image](https://hub.docker.com/r/redlinkgmbh/vind-solr-server).
The repository includes a [docker-compose file](./docker-compose.yaml) that starts a solr server on `localhost:8983/solr` and creates a vind core named `localdocs`.

### Create a VIND collection on a running solr cloud
VIND includes a collection management tool, that allows to create and update vind collections with a single command line call.
The tool is described [here](https://github.com/RBMHTechnology/vind/tree/master/backend/solr-ext/collection-managment-tool).

### Indexing
The demo shows how to index articles by using a simple [document factory](./src/main/java/at/redlink/vinddemo/model/DocFactory.java). Here the fields that are used for indexing are defined.
In addition helper functions for mapping from and to VIND documents are implemented. The indexing istelf is triggered in application start (in the [main](./src/main/java/at/redlink/vinddemo/VindDemoApplication.java)).

### Search
The search is encapsulated in a [service](./src/main/java/at/redlink/vinddemo/service/SearchService.java). The example shows how to use fulltext search, filters (term and time range) and how to generate facets.

## Testing

### Simple Unit Tests
A simple unit test is shown [here](./src/test/java/at/redlink/vinddemo/service/ServiceTest.java). VIND provides a embedded server for testing. But be aware that you
might end up in dependency hell if you want to use it for more complex test (that uses Spring Boot context e.g.). Therefore we suggest to use testcontainers.

### Complex tests using VIND testcontainers


## Heath Check
In  order to check the health of the VIND endpoint you can use the VIND built in status check and [expose it as health indicator](./src/main/java/at/redlink/vinddemo/health/VindHealthIndicator.java).
In the demo you can check the endpoint on http://localhost:8082/actuator/health.
