package telran.cars.service.model;

import lombok.Getter;
import telran.cars.dto.CarDto;
import telran.cars.dto.CarState;
import jakarta.persistence.*;

@Entity
@Getter
@Table(name = "cars")
public class Car {
	@Id
	String number;
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "model_name", nullable = false),
			@JoinColumn(name = "model_year", nullable = false) })
	Model model;
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = true)
	CarOwner carOwner;
	String color;
	int kilometers;
	@Enumerated(EnumType.STRING) // value in the table will be a string (by default a number)
	CarState state;

	public Car() {
		
	}
	public Car(CarDto carDto) {
		this.number = carDto.number();
		this.model.modelYear = new ModelYear(carDto.model(), carDto.year());
		this.carOwner = new CarOwner(); 
		this.carOwner.id = carDto.id();
		this.color = carDto.color();
		this.kilometers = carDto.kilometers();
		this.state = carDto.carState();
	}

	public static CarDto build(Car car) {
		return new CarDto(car.number, car.model.modelYear.getName(), car.model.modelYear.getYear(), car.carOwner.id,
				car.color, car.kilometers, car
				.state);
	}
}