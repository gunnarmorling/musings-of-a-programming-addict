package de.gmorling.moapa.jaxwsonspring;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.gmorling.moapa.jaxwsonspring.ShopClient;
import de.gunnarmorling.moapa.shop.products.ProductsPortType;
import de.gunnarmorling.moapa.shop.products.types.GetProductByIdRequest;
import de.gunnarmorling.moapa.shop.products.types.GetProductByIdResponse;
import de.gunnarmorling.moapa.shop.products.types.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ShopClientMockTest {

	@Inject
	private ProductsPortType productsPort;

	@Inject
	private ShopClient shopClient;

	@Before
	public void instructMock() {
		GetProductByIdResponse response = new GetProductByIdResponse();
		Product product = new Product();
		product.setId(1);
		product.setName("Jeans");
		product.setPrice(new BigDecimal("89.99"));
		product.setSize("L");
		response.setProduct(product);

		when(productsPort.getProductById(any(GetProductByIdRequest.class)))
				.thenReturn(response);
	}

	@Test
	public void getProductName() {
		assertEquals("Jeans", shopClient.getProductNameByid(1));
	}

}