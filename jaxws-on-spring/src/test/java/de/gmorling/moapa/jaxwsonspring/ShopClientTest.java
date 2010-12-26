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
public class ShopClientTest {

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