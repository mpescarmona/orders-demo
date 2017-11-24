package com.orders.ordersdemo;

import com.orders.ordersdemo.domain.Order;
import com.orders.ordersdemo.domain.Vendor;
import com.orders.ordersdemo.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrdersDemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private OrderRepository orderRepository;

	@Before
	public void deleteAllBeforeTests() throws Exception {
		orderRepository.deleteAll();

		Vendor vendor = new Vendor("1", "Test Vendor");
		Order order = new Order("1", "1", "PENDING", "TEST", vendor, new Date(),null);

		orderRepository.save(order);
	}

	@Test
	public void contextLoads() {
	}

}
