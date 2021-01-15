package semweb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

public class Hospitals {
	public static void main(String[] args) {
		String csvFile = "src/main/resources/france_hospitals_point.csv";
		String line = "";
		String cvsSplitBy = ",";
		Model model = ModelFactory.createDefaultModel();
		String hospitalURIPrefix = "http://www.example.com/";
		String nameURIPrefix = "http://dbpedia.org/ontology/name#";
		String rdfUriPrefix = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
		String operatorURIPrefix = "http://vocab.deri.ie/cogs#Operator";
		String opertypeURIPrefix = "https://www.w3.org/1999/02/22-rdf-syntax-ns#type";
		String emergURIPrefix = "https://www.w3.org/2006/vcard/ns#Emergency";
		String capacityURIPrefix = "http://purl.org/openorg/capacity";
		String wheelchairURIPrefix = "http://vocab.gtfs.org/gtfs.ttl#wheelchairAccessible";
		String webURIPrefix = "http://data.lirmm.fr/ontologies/passim#webSite";
		String phoneURIPrefix = "http://xmlns.com/foaf/0.1/phone";
		String faxURIPrefix = "http://ogp.me/ns#fax_number";
		String strURIPrefix = "https://www.w3.org/2006/vcard/ns#street-address";
		String cityURIPrefix = "http://dbpedia.org/ontology/city";
		String postURIPrefix = "http://www.w3.org/ns/locn#postCode";
		String wikiURIPrefix = "https://w3id.org/i40/sto#hasWikidataEntity";
		String osmtypeURIPrefix = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type#";
		String osmversionURIPrefix = "https://schema.org/Integer#";
		String timestampURIPrefix = "http://securitytoolbox.appspot.com/stac#Timestamp";
		String specialityURIPrefix = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type#";
		String osm_geomURIPrefix = "http://data.ign.fr/def/geometrie/20190212.htm#Geometry#";
		String osm_orig_geomURIPrefix = "http://data.ign.fr/def/geometrie/20190212.htm#Geometry#";
		String geoURIprefix = "https://www.w3.org/2003/01/geo/wgs84_pos#";

		/* Properties */
		Property name = model.createProperty(nameURIPrefix);
		Property rdfType = model.createProperty(rdfUriPrefix + "type");
		Property operator = model.createProperty(operatorURIPrefix);
		Property opertype = model.createProperty(opertypeURIPrefix);
		Property emerg = model.createProperty(emergURIPrefix);
		Property capacity = model.createProperty(capacityURIPrefix);
		Property wheelchair = model.createProperty(wheelchairURIPrefix);
		Property web = model.createProperty(webURIPrefix);
		Property phone = model.createProperty(phoneURIPrefix);
		Property fax = model.createProperty(faxURIPrefix);
		Property str = model.createProperty(strURIPrefix);
		Property city = model.createProperty(cityURIPrefix);
		Property post = model.createProperty(postURIPrefix);
		Property wiki = model.createProperty(wikiURIPrefix);
		Property osmtype = model.createProperty(osmtypeURIPrefix + "osmtype");
		Property osmversion = model.createProperty(osmversionURIPrefix + "osmversion");
		Property timestamp = model.createProperty(timestampURIPrefix);
		Property speciality = model.createProperty(specialityURIPrefix + "speciality");
		Property osm_geom = model.createProperty(osm_geomURIPrefix + "osm_geom");
		Property osm_orig_geom = model.createProperty(osm_orig_geomURIPrefix + "osm_orig_geom");

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null) {
				String[] france_hospitals_point = line.split(cvsSplitBy);
				Resource resource1 = model
						.createResource(hospitalURIPrefix + france_hospitals_point[0].replaceAll(" ", "_"));
				Resource spatialThing = model.createResource(geoURIprefix + "SpatialThing");

				/* Model for france_hospitals_point.csv */
				model.add(resource1, rdfType, spatialThing);
				model.add(resource1, name, france_hospitals_point[3].replaceAll("\\\"", ""));
				model.add(resource1, operator, france_hospitals_point[8]);
				model.add(resource1, opertype, france_hospitals_point[9]);
				model.add(resource1, emerg, france_hospitals_point[10]);
				model.add(resource1, capacity, france_hospitals_point[11]);
				model.add(resource1, wheelchair, france_hospitals_point[12]);// 12
				model.add(resource1, web, france_hospitals_point[17]);
				model.add(resource1, phone, france_hospitals_point[19]);
				model.add(resource1, fax, france_hospitals_point[21]);
				model.add(resource1, str, france_hospitals_point[27]);
				model.add(resource1, city, france_hospitals_point[28]);
				model.add(resource1, post, france_hospitals_point[29]);
				model.add(resource1, wiki, france_hospitals_point[30]);
				model.add(resource1, osmtype, france_hospitals_point[36]);
				model.add(resource1, osmversion, france_hospitals_point[37]);
				model.add(resource1, timestamp, france_hospitals_point[38]);
				model.add(resource1, speciality, france_hospitals_point[39]);
				model.add(resource1, osm_geom, france_hospitals_point[40]);
				/*model.add(resource1, osm_orig_geom, france_hospitals_point[41].replaceAll("\\\"", ""));*/

				// connection to triplestore

				String datasetURL = "http://localhost:3030/HibaProject";
				String sparqlEndpoint = datasetURL + "/sparql";
				String sparqlUpdate = datasetURL + "/update";
				String graphStore = datasetURL + "/data";
				RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint, sparqlUpdate, graphStore);
				conneg.load(model);

				model.write(System.out, "Turtle");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
