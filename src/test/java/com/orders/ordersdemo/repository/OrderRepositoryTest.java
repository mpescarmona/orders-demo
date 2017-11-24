package com.orders.ordersdemo.repository;

import com.orders.ordersdemo.domain.Order;
import com.orders.ordersdemo.domain.Vendor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class OrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private MongoTemplate template;

	@Before
	public void prepareDataBeforeTests() throws Exception {
		orderRepository.deleteAll();

		Vendor vendor1 = new Vendor("1", "NIKE");
		Order order1 = new Order("1", "1", "PENDING", "PROMPT", vendor1, new Date(), null);
		orderRepository.save(order1);

		Vendor vendor2 = new Vendor("2", "ADIDAS");
		Order order2 = new Order("2", "2", "PENDING", "PROMPT", vendor2, new Date(), null);
		orderRepository.save(order2);

		Vendor vendor3 = new Vendor("2", "PUMA");
		Order order3 = new Order("3", "3", "ACTIVE", "PROMPT", vendor3, new Date(), null);
		orderRepository.save(order3);

		Vendor vendor4 = new Vendor("4", "REEBOK");
		Order order4 = new Order("4", "4", "PENDING", "PROMPT", vendor4, new Date(), null);
		orderRepository.save(order4);
	}

	@Test
	public void testFindAll() {
		int samplesInCollection = template.findAll(Order.class).size();

		assertEquals("Only 4 Orders should exist in collection, but there are " + samplesInCollection, 4,
				samplesInCollection);
	}

	@Test
	public void testUpdateOrderAndVerifyTheChanges() {
		Order foundOrder = orderRepository.findByOrderNumber("1");

		assertEquals("The order status should be 'PENDING', but there is " + foundOrder.getStatus(), "PENDING",
				foundOrder.getStatus());

		foundOrder.setStatus("ACTIVE");

		assertEquals("The order status should be 'ACTIVE', but there is " + foundOrder.getStatus(), "ACTIVE",
				foundOrder.getStatus());
	}

	@Test
	public void testFindByOrderShouldReturnValidOrder() {
		Order foundOrder = orderRepository.findByOrderNumber("1");

		assertEquals("The order status should be 'PENDING', but there is " + foundOrder.getStatus(), "PENDING",
				foundOrder.getStatus());
	}

	@Test
	public void testFindByOrderShouldReturnNullIfNotExists() {
		Order foundOrder = orderRepository.findByOrderNumber("NotExistingOrder");

		assertNull(foundOrder);
	}
}
