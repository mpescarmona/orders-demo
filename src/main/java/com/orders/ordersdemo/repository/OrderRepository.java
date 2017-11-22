
package com.orders.ordersdemo.repository;

import com.orders.ordersdemo.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "order", path = "order")
public interface OrderRepository extends MongoRepository<Order, String> {

	Order findByOrderNumber(@Param("orderNumber") String orderNumber);

}
