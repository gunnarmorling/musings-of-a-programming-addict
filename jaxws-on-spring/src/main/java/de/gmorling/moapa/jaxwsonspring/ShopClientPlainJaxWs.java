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