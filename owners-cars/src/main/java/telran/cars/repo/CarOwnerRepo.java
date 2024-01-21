package telran.cars.repo;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.cars.dto.EnginePowerCapacity;
import telran.cars.service.model.*;

public interface CarOwnerRepo extends JpaRepository<CarOwner, Long> {
	
} 