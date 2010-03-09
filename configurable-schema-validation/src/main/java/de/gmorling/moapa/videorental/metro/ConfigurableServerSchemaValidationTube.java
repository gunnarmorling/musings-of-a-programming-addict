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

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.server.ServerSchemaValidationTube;

import de.gmorling.moapa.videorental.jmx.EndpointConfiguration;
import de.gmorling.moapa.videorental.jmx.ValidationConfigurationHolder;

/**
 * <p>
 * Extends {@link ServerSchemaValidationTube} with the ability to configure at
 * runtime, whether schema validation shall take place or not by overriding the
 * method <code>isNoValidation()</code>.
 * </p>
 * <p>
 * To control the return value of this method, a JMX MBean with the name
 * ServiceName-PortName is registered with the platform MBeanServer. Setting
 * that MBean's property "SchemaValidationEnabled" activates/disables the
 * validation for this tube's port.
 * </p>
 * 
 * @author Gunnar Morling
 */
public class ConfigurableServerSchemaValidationTube extends ServerSchemaValidationTube {

	private String name;
	
	public ConfigurableServerSchemaValidationTube(WSEndpoint<?> endpoint, WSBinding binding,
            SEIModel seiModel, WSDLPort wsdlPort, Tube next) {
		
		super(endpoint, binding, seiModel, wsdlPort, next);
		
		name = seiModel.getServiceQName().getLocalPart() + "-" + wsdlPort.getName().getLocalPart();
		ValidationConfigurationHolder.setValidationEnabled(name, true);
		registerMBean();
	}

	private void registerMBean() {
		
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); 
	 	 
		try {
 			mbs.registerMBean(
				new EndpointConfiguration(name),
				new ObjectName("de.gmorling.moapa.videorental.jmx:type=Endpoints,name=" + name));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected boolean isNoValidation() {
		return !ValidationConfigurationHolder.isValidationEnabled(name);
	}

    protected ConfigurableServerSchemaValidationTube(ConfigurableServerSchemaValidationTube that, TubeCloner cloner) {
        super(that,cloner);
        this.name = that.name;
    }

    /**
     * {@inheritDoc}
     */
    public ConfigurableServerSchemaValidationTube copy(TubeCloner cloner) {
        return new ConfigurableServerSchemaValidationTube(this, cloner);
    }
    
}
