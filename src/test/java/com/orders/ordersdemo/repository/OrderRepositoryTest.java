package com.orders.ordersdemo.repository;

import com.orders.ordersdemo.domain.Order;
import com.orders.ordersdemo.domain.Vendor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
@Ignore
public class OrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private MongoTemplate template;

	@Before
	public void deleteAllBeforeTests() throws Exception {
		orderRepository.deleteAll();

		Vendor vendor = new Vendor("1", "Test Vendor");
		Order order = new Order("1", "1", "PENDING", "TEST", vendor, new Date(),null);

		orderRepository.save(order);

	}

//	private static final String LOCALHOST = "127.0.0.1";
//	private static final String DB_NAME = "itest";
//	private static final int MONGO_TEST_PORT = 27028;
//
//	private static MongodProcess mongoProcess;
//	private static Mongo mongo;
//
//
//	@BeforeClass
//	public static void initializeDB() throws IOException {
//
//		RuntimeConfigBuilder runtimeConfigBuilder = new RuntimeConfigBuilder();
//		runtimeConfigBuilder.build();
//		IRuntimeConfig config = runtimeConfigBuilder.build();
////		config.setExecutableNaming(new UserTempNaming());
//
//		MongodStarter starter = MongodStarter.getInstance(config);
//
//		MongodExecutable mongoExecutable = starter.prepare(new MongodConfig(Version.V2_2_0, MONGO_TEST_PORT, false));
//		mongoProcess = mongoExecutable.start();
//
//		mongo = new Mongo(LOCALHOST, MONGO_TEST_PORT);
//		mongo.getDB(DB_NAME);
//	}
//
//	@AfterClass
//	public static void shutdownDB() throws InterruptedException {
//		mongo.close();
//		mongoProcess.stop();
//	}
//
//
//	@Before
//	public void setUp() throws Exception {
//		repoImpl = new SampleRepositoryMongoImpl();
//		template = new MongoTemplate(mongo, DB_NAME);
//		repoImpl.setMongoOps(template);
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		template.dropCollection(Order.class);
//	}

	@Test
	public void testSave() {
		int samplesInCollection = template.findAll(Order.class).size();

		assertEquals("Only 1 Order should exist in collection, but there are "
				+ samplesInCollection, 1, samplesInCollection);
	}

}
