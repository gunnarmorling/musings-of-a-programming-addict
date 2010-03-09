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

package de.gmorling.moapa.videorental.metro;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPBinding;

import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.assembler.ClientTubelineAssemblyContext;
import com.sun.xml.ws.assembler.ServerTubelineAssemblyContext;
import com.sun.xml.ws.assembler.TubeFactory;
import com.sun.xml.ws.assembler.jaxws.ValidationTubeFactory;
import com.sun.xml.ws.developer.SchemaValidationFeature;

/**
 * A tube factory that creates {@link ConfigurableServerSchemaValidationTube}s on
 * the endpoint side and delegates to the standard {@link ValidationTubeFactory}
 * on the client side.
 * 
 * @author Gunnar Morling.
 * 
 */
public class ConfigurableValidationTubeFactory implements TubeFactory {

	private ValidationTubeFactory delegate = new ValidationTubeFactory();
	
	public Tube createTube(ClientTubelineAssemblyContext context)
			throws WebServiceException {

		return delegate.createTube(context);
	}

	/**
	 * Creates a {@link ConfigurableServerSchemaValidationTube}. Implementation
	 * based on Metro's ServerTubeAssemblerContext.
	 */
	public Tube createTube(ServerTubelineAssemblyContext context)
			throws WebServiceException {

    	ServerTubeAssemblerContext wrappedContext = context.getWrappedContext();
		WSEndpoint<?> endpoint = wrappedContext.getEndpoint();
		WSBinding binding = endpoint.getBinding();
		Tube next = context.getTubelineHead();
        WSDLPort wsdlModel = wrappedContext.getWsdlModel();
		SEIModel seiModel = wrappedContext.getSEIModel();
		
		if (binding instanceof SOAPBinding && binding.isFeatureEnabled(SchemaValidationFeature.class) && wsdlModel != null) {
			return new ConfigurableServerSchemaValidationTube(endpoint, binding, seiModel, wsdlModel, next);
		} else
            return next;
    }
}
