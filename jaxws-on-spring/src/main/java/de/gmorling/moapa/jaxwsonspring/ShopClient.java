package de.gmorling.moapa.jaxwsonspring;

import javax.inject.Inject;

import de.gunnarmorling.moapa.shop.products.ProductsPortType;
import de.gunnarmorling.moapa.shop.products.types.GetProductByIdRequest;

public class ShopClient {

	@Inject
	private ProductsPortType productsPort;

	public String getProductNameByid(int productId) {

		GetProductByIdRequest request = new GetProductByIdRequest();
		request.setId(productId);

		return productsPort.getProductById(request).getProduct().getName();
	}

}