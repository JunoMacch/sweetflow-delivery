package com.sweetflow.order_ms.repository;

import com.sweetflow.order_ms.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByClientIdOrderByDateTimeDesc(UUID clientId);
}
