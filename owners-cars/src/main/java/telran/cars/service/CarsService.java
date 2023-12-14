package telran.cars.service;

import java.util.List;

import telran.cars.dto.CarDto;
import telran.cars.dto.PersonDto;
import telran.cars.dto.TradeDealDto;

public interface CarsService {
PersonDto addPerson(PersonDto personDto);
CarDto addCar(CarDto carDto);
PersonDto updatePerson(PersonDto personDto);
PersonDto deletePerson(long id);
CarDto deleteCar(String carNumber);
TradeDealDto purchase(TradeDealDto tradeDeal);
List<CarDto> getOwnerCars(long id);
PersonDto getCarOwner(String carNumber);
}
