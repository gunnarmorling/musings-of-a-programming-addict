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

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import javax.jws.WebService;

import de.gunnarmorling.moapa.shop.products.ProductsPortType;
import de.gunnarmorling.moapa.shop.products.types.GetProductByIdRequest;
import de.gunnarmorling.moapa.shop.products.types.GetProductByIdResponse;
import de.gunnarmorling.moapa.shop.products.types.Product;

@WebService(endpointInterface = "de.gunnarmorling.moapa.shop.products.ProductsPortType")
public class ProductsPort implements ProductsPortType {

	private Map<Integer, Product> sampleProducts = new TreeMap<Integer, Product>();

	public ProductsPort() {

		Product product = new Product();
		product.setId(1);
		product.setName("Jeans");
		product.setPrice(new BigDecimal("89.99"));
		product.setSize("L");
		sampleProducts.put(product.getId(), product);

		product = new Product();
		product.setId(2);
		product.setName("Shirt");
		product.setPrice(new BigDecimal("49.99"));
		product.setSize("M");
		sampleProducts.put(product.getId(), product);
	}

	public GetProductByIdResponse getProductById(GetProductByIdRequest request) {
		System.out.println("getProductById");
		GetProductByIdResponse response = new GetProductByIdResponse();
		response.setProduct(sampleProducts.get(request.getId()));
		return response;
	}

}
