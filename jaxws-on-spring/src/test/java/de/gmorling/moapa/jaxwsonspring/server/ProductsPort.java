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
