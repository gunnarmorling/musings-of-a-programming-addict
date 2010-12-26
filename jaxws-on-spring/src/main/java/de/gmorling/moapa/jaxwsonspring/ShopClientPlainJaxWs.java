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

import javax.xml.ws.BindingProvider;

import de.gunnarmorling.moapa.shop.products.ProductsPortType;
import de.gunnarmorling.moapa.shop.products.ProductsService;
import de.gunnarmorling.moapa.shop.products.types.GetProductByIdRequest;

public class ShopClientPlainJaxWs {

	private String endPointUrl;

	public String getProductNameByid(int productId) {

		GetProductByIdRequest request = new GetProductByIdRequest();
		request.setId(productId);

		ProductsService productsService = new ProductsService();
		ProductsPortType productsPort = productsService.getProductsPort();
		((BindingProvider) productsPort).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointUrl);

		return productsPort.getProductById(request).getProduct().getName();
	}

	public void setEndPointUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}
}