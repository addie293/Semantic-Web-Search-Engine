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

public class TrainStation {
	public static void main(String[] args) {
		//String csvFile = "src/main/resources/stops.csv";
		String csvFile = "src/main/resources/stop_times.csv";
		String line = "";
		String cvsSplitBy = ",";
		Model model = ModelFactory.createDefaultModel();
		String stationURIPrefix = "http://www.example.com/";
		String nameofstatURIPrefix = "http://dbpedia.org/ontology/name#";
		String geoURIprefix = "https://www.w3.org/2003/01/geo/wgs84_pos#";
		String deptimeURIprefix = "http://vocab.org/transit/terms/departureTime";
		String arrivtimeURIprefix = "http://www.w3.org/2006/time#hasEnd#";
		String rdfUriPrefix = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
		String stopseqURIPrefix ="http://www.w3.org/2003/01/geo/wgs84_pos#location";
										/*Property for stops.csv*/
		Property name = model.createProperty(nameofstatURIPrefix);
		Property rdfType = model.createProperty(rdfUriPrefix + "type");
		Property geoLat = model.createProperty(geoURIprefix + "lat");
		Property geoLong = model.createProperty(geoURIprefix + "long");
										/*Property for stop_times*/
		Property deptime = model.createProperty(deptimeURIprefix);
		Property arivtime = model.createProperty(arrivtimeURIprefix+"arrivaltime");
		Property stopseq =  model.createProperty(stopseqURIPrefix+"#stopseq");
		Property stopsid =  model.createProperty(stationURIPrefix +"stop_id");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null) {
				//String[] stops = line.split(cvsSplitBy);
				String[] stop_times = line.split(cvsSplitBy);
														/*Resources for Stops*/
				/*Resource resource1 = model.createResource(stationURIPrefix + stops[0].replaceAll(" ", "_"));
				Resource spatialThing = model.createResource(geoURIprefix + "SpatialThing");
										/*Model for stops.csv*/
				/*model.add(resource1, rdfType, spatialThing);
				model.add(resource1,name,stops[1].replaceAll("\\\"", ""));
				model.add(resource1,geoLat,stops[3]);
				model.add(resource1,geoLong,stops[4]);*/
				
				
				/*Resources for stop_times*/
				Resource resource1 = model.createResource(stationURIPrefix+"trip_id" + stop_times[0].replaceAll(" ", "_"));//tripid
				//Resource resource2 = model.createResource(stopsid+ stop_times[3].replaceAll(" ", "_"));//stopid
				Resource spatialThing = model.createResource(geoURIprefix + "SpatialThing");
							
				
				/*Model for stop_times.csv*/
				model.add(resource1,arivtime,stop_times[1]);
				model.add(resource1,stopsid,stop_times[3].replaceAll(" ", "_"));
				model.add(resource1, rdfType, spatialThing);
				model.add(resource1,deptime,stop_times[2]);
				model.add(resource1,stopseq,stop_times[4].replaceAll("\\\"", ""));
				
				
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String datasetURL = "http://localhost:3030/HibaProject";
		String sparqlEndpoint = datasetURL + "/sparql";
		String sparqlUpdate = datasetURL + "/update";
		String graphStore = datasetURL + "/data";
		RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint,sparqlUpdate,graphStore);
		conneg.load(model);
		       	
		
		model.write(System.out,"Turtle");
		
	}
}
