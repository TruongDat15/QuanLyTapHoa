package com.example.demo.repository;

import com.example.demo.entity.InventoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface InventoryReservationRepository extends JpaRepository<InventoryReservation, UUID> {
}
