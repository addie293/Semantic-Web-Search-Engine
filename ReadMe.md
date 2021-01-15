# The Semantic Web Ontological Project
## Abstract
This work involved services that were provided using different kinds of Semantic Technology. Such services entailed heterogeneous data accessing from different kinds of sources such as data on hospitals, train services, city statistics or/and bicycling. This heterogeneity of the data is then converted to a vivid and unique ontological model that is easy to understand and visualise. We considered a fixed model for such services  convert and the provided data to RDF format. The process of mapping the data to the triple store (database) is undertaken at the backend (along with server querying)  which leads to a proper human-readable and visual data on the website.

## System Model
In this section, we briefly explain the system model or the different component-wise architecture of our designed prototype of this technology.

### Description

This section provides for a detailed description of our model architecture with regards to this project that has been undertaken. Starting with a source of data we provide a mechanisim where we convert the data to RDF which is a data representation in Semantic Web for linked data with meaning (expressed as triples).  For better understanding, raw data is extracted and converted to RDF triples and stored in a triple store.  From the triple store, RDF data is queried and visually represented on a website designed by us. 

The Fuseki database triple store is used to store the generated RDF data from raw graph. We further created a website using which we display a map-viewed location of many stations across the stations. All of these can also be queried from the same website smoothly. In case of data regarding locomotive aspects, there is dynamism involved which makes the data real-time and subject to changes with the passing of time.

## Semantic Technology Usage(s)

The varied technologies used in this project can be listed as following for the sake of record and understanding of the project:

* A Fuseki triple store has been setup which runs on the localhost so that our code can be effectively tested. In case of our work, we have seen that that the triple store is accepting external queries by effectively employing the usage of end-point provided by the triple store.
* Because of the fact that we used heterogeneous data from different kinds of sources, an ontology was defined to represent all of them through effective predicates. Because of the relative complexity with regards to graphically displaying the ontology, it has been included in the descriptive full report only (the Ontologies: you will find them in an link for image (draw.io)). It uses properties and classes of other ontologies.  
* The website implementation wherein we explained how to add new data conforming with the current ontology but might potentially be expansive. The data can be seen also as list where it contains embedded linked data using RDF-a that can be extracted.

## Queries that can be used for data visualisation

The SPARQL queries can be found in the file  Services.java .

## Currently Observed Limitations

* Slow performance, which we believe can be attributed to the triple store we selected.
* There has been limitations on the kind of data that's acceptable to the web app.



## Conclusion and future work

The current system is a start where it aims to be more generic than normal cases using a user-friendly interface to help the parsing scenario allowing exceptions and extra information. However, certain limitations mentions above should be over come.

##  



