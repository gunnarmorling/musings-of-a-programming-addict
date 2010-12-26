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
package de.gmorling.moapa.jaxwsonspring.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A dummy web service implementation for <code>products.wsdl</code> to be used
 * for integration tests of the products web service client. It uses the
 * {@link Endpoint} API of JAX-WS to publish that service on
 * <code>localhost</code>.
 * 
 * @author Gunnar Morling
 * 
 */
public class ShopServer {

	private static final String NAMESPACE = "http://www.gunnarmorling.de/moapa/shop/products";

	private static Logger logger = LoggerFactory.getLogger(ShopServer.class);

	private String endPointUrl;

	private String wsdlName;

	private Endpoint endpoint;

	private InputStream wsdlStream;

	public void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}

	public void setWsdlName(String wsdlName) {
		this.wsdlName = wsdlName;
	}

	@PostConstruct
	public void start() {

		wsdlName = "/wsdl/products.wsdl";
		URL wsdlResource = ShopServer.class.getResource(wsdlName);

		try {
			wsdlStream = wsdlResource.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Source source = new StreamSource(wsdlStream);
		source.setSystemId(wsdlResource.toExternalForm());

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Endpoint.WSDL_PORT, new QName(NAMESPACE, "ProductsPort"));
		properties.put(Endpoint.WSDL_SERVICE, new QName(NAMESPACE, "ProductsService"));

		endpoint = Endpoint.create(new ProductsPort());

		endpoint.setMetadata(Arrays.asList(source));
		endpoint.setProperties(properties);
		endpoint.publish(endPointUrl);

		logger.info("Published shop service at " + endPointUrl);
	}

	@PreDestroy
	public void stop() {
		logger.info("Stopping shop service");

		if (endpoint != null) {
			endpoint.stop();
		}

		try {
			if (wsdlStream != null) {
				wsdlStream.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
