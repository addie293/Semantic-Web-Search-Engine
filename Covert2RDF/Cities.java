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

public class Cities {
	public static void main(String[] args) {
		String csvFile = "src/main/resources/fr.csv";
		String line = "";
		String cvsSplitBy = ",";
		Model model = ModelFactory.createDefaultModel();
		String nameURIPrefix = "http://dbpedia.org/ontology/name#";
		String geoURIprefix = "https://www.w3.org/2003/01/geo/wgs84_pos#";
		String capURIprefix = "http://dbpedia.org/ontology/capital";
		String popURIprefix = "http://dbpedia.org/ontology/populationTotal#";
		String rdfUriPrefix = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
		// Properties
		Property lat = model.createProperty(geoURIprefix + "lat");
		Property lon = model.createProperty(geoURIprefix + "lon");
		Property admin_name = model.createProperty(nameURIPrefix + "admin_name");
		Property cap = model.createProperty(capURIprefix);
		Property pop = model.createProperty(popURIprefix + "pop");
		Property rdfType = model.createProperty(rdfUriPrefix + "type");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null) {
				String[] fr = line.split(cvsSplitBy);
				Resource resource1 = model.createResource(nameURIPrefix + fr[0].replaceAll(" ", "_"));
				Resource spatialThing = model.createResource(geoURIprefix + "SpatialThing");
				/* Model for fr.csv */
				model.add(resource1, rdfType, spatialThing);
				model.add(resource1, admin_name, fr[5].replaceAll("\\\"", ""));
				model.add(resource1, lat, fr[1]);
				model.add(resource1, cap, fr[6]);
				model.add(resource1, lon, fr[2]);
				model.add(resource1, pop, fr[7]);

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
