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
package de.gmorling.moapa.jaxwsonspring;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.gmorling.moapa.jaxwsonspring.ShopClient;
import de.gmorling.moapa.jaxwsonspring.ShopClientPlainJaxWs;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ShopClientIntegrationTest {

	@Inject
	private ShopClientPlainJaxWs shopClientPlainJaxWs;

	@Inject
	private ShopClient shopClient;

	@Test
	public void getProductNameUsingPlainJaxWs() {
		assertEquals("Jeans", shopClientPlainJaxWs.getProductNameByid(1));
	}

	@Test
	public void getProductName() {
		assertEquals("Jeans", shopClient.getProductNameByid(1));
	}

}