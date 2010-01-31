package de.gmorling.moapa.videorental.ws;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Endpoint;

import de.gmorling.moapa.videorental.ws.jaxb.VideoRentalPort;
import de.gmorling.moapa.videorental.ws.xmlbeans.VideoRentalProvider;

public class Main {

    private static final String URL = "http://localhost:8080/video-rental/";
	private static final String NAMESPACE = "http://www.gunnarmorling.de/moapa/videorental";
	private static final String WSDL_NAME = "/wsdl/videorental.wsdl";

	public static void main(String[] args) throws Exception {

		publishVideoRentalEndpoint(new VideoRentalProvider(), "VideoRentalXmlBeans");
		publishVideoRentalEndpoint(new VideoRentalPort(), "VideoRentalJaxb");
    }

	private static void publishVideoRentalEndpoint(Object endpointImplementor, String endpointName) throws IOException {
		
		URL wsdlResource = Main.class.getResource(WSDL_NAME);

        List<Source> metadata = new ArrayList<Source>();
        Source source = new StreamSource(wsdlResource.openStream());
        source.setSystemId(wsdlResource.toExternalForm());
        metadata.add(source);

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(Endpoint.WSDL_PORT, new QName(NAMESPACE, "VideoRentalPort"));
        properties.put(Endpoint.WSDL_SERVICE, new QName(NAMESPACE, "VideoRentalService"));

        Endpoint endpoint = Endpoint.create(endpointImplementor);
        endpoint.setMetadata(metadata);
        endpoint.setProperties(properties);
        endpoint.publish(URL + endpointName);
        
        System.out.println("Published service at " + URL + endpointName);
	}

}
