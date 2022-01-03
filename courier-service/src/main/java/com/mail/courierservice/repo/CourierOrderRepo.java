package com.mail.courierservice.repo;

import com.mail.courierservice.repo.model.CourierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierOrderRepo extends JpaRepository<CourierOrder, Long> {
}
