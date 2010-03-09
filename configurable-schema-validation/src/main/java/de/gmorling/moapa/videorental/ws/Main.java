/**
 *  Copyright 2010 Gunnar Morling (http://www.gunnarmorling.de/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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


public class Main {

	private static final String URL = "http://localhost:8080/video-rental/";
	private static final String NAMESPACE = "http://www.gunnarmorling.de/moapa/videorental";
	private static final String WSDL_NAME = "/wsdl/videorental.wsdl";

	public static void main(String[] args) throws Exception {

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
