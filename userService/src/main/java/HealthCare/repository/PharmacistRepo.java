package HealthCare.repository;

import HealthCare.entity.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacistRepo extends JpaRepository<Pharmacist, Long> {
}
