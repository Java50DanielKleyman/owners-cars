package telran.cars.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import telran.cars.service.CarsService;
import telran.cars.dto.*;

@RestController
@RequestMapping("cars")
@RequiredArgsConstructor
public class CarsController {
	final CarsService carsService;
	@PostMapping
	CarDto addCar(@RequestBody CarDto carDto) {
		//annotation @RequstBody informs Spring about conversion of JSON inside a request to the given parameter
		return carsService.addCar(carDto);
	}
	@PostMapping("person")
	PersonDto addPerson(@RequestBody PersonDto personDto) {
		//TODO HW #53
		return carsService.addPerson(personDto);
	}
	@PutMapping("person")
	PersonDto updatePerson(@RequestBody PersonDto personDto) {
		return carsService.updatePerson(personDto);
	}
	@PutMapping("trade")
	TradeDealDto purchase(@RequestBody TradeDealDto tradeDealDto) {
		//TODO HW #53
		return carsService.purchase(tradeDealDto);
	}
	@DeleteMapping("person/{id}")
	PersonDto deletePerson(@PathVariable(name="id") long id) {
		return carsService.deletePerson(id);
	}
	@DeleteMapping("{carNumber}")
	CarDto deleteCar(@PathVariable(name="carNumber") String carNumber) {
		//TODO HW #53
		return carsService.deleteCar(carNumber);
		
	}
	@GetMapping("person/{id}")
	List<CarDto> getOwnerCars(@PathVariable long id) {
		return carsService.getOwnerCars(id);
	}
	@GetMapping("{carNumber}")
	PersonDto getCarOwner(@PathVariable String carNumber) {
		return carsService.getCarOwner(carNumber);
	}
}