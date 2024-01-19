package telran.cars.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.cars.dto.*;
import telran.cars.exceptions.*;
import telran.cars.repo.*;
import telran.cars.service.model.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarsServiceImpl implements CarsService {
	final CarRepo carRepo;
	final CarOwnerRepo carOwnerRepo;
	final ModelRepo modelRepo;
	final TradeDealRepo tradeDealRepo;

	@Override
	@Transactional
	public PersonDto addPerson(PersonDto personDto) {
		if (carOwnerRepo.existsById(personDto.id())) {
			throw new IllegalPersonsStateException();
		}
		CarOwner carOwner = CarOwner.of(personDto);
		carOwnerRepo.save(carOwner);
		log.debug("person {} has been saved", personDto);
		return personDto;
	}

	@Override
	@Transactional
	public CarDto addCar(CarDto carDto) {
		if (carRepo.existsById(carDto.number())) {
			throw new IllegalCarsStateException();
		}
		Model model = modelRepo.findById(new ModelYear(carDto.model(), carDto.year()))
				.orElseThrow(() -> new ModelNotFoundException());
		Car car = Car.of(carDto);
		car.setModel(model);
		carRepo.save(car);
		log.debug("car {} has been saved", carDto);
		return carDto;
	}

	@Override
	@Transactional
	public PersonDto updatePerson(PersonDto personDto) {
		CarOwner carOwner = carOwnerRepo.findById(personDto.id()).orElseThrow(() -> new PersonNotFoundException());
		carOwner.setEmail(personDto.email());
		return carOwner.build();
	}

	@Override
	@Transactional
	public PersonDto deletePerson(long id) {
		CarOwner carOwner = carOwnerRepo.findById(id).orElseThrow(() -> new PersonNotFoundException());
		List<Car> cars = carRepo.findByCarOwnerId(id);
		List<TradeDeal> tradeDeals = tradeDealRepo.findByCarOwner_Id(id);
		tradeDealRepo.deleteAll(tradeDeals);
		if (!cars.isEmpty()) {
			cars.forEach(car -> car.setCarOwner(null));
		}
		carOwnerRepo.deleteById(id);
		log.debug("Carowner {} has been deleted", carOwner);
		// TODO
		// HW #63
		// find Car having being deleted owner
		// If such car exists, set null as the car owner
		// after that delete by the method deleteById from carOwnerRepo
		return carOwner.build();
	}

	@Override
	public CarDto deleteCar(String carNumber) {

		if (!carRepo.existsById(carNumber)) {
			throw new CarNotFoundException();
		}
		List<TradeDeal> tradeDeals = tradeDealRepo.findByCarNumber(carNumber);
		tradeDealRepo.deleteAll(tradeDeals);
		Car car = carRepo.findByNumber(carNumber);
		carRepo.deleteById(carNumber);
		log.debug("Car {} has been deleted", car);
		// TODO
		// HW #63
		// find all TradeDeal entities for a given Car
		// delete all such entities
		// delete by the method deleteById from CarRepo
		return car.build();
	}

	@Override
	public TradeDealDto purchase(TradeDealDto tradeDealDto) {
		Car car = carRepo.findById(tradeDealDto.carNumber()).orElseThrow(() -> new CarNotFoundException());
		CarOwner carOwner = null;
		Long personId = tradeDealDto.personId();
		if (personId != null) {
			carOwner = carOwnerRepo.findById(personId).orElseThrow(() -> new PersonNotFoundException());
			if (car.getCarOwner().getId() == personId) {
				throw new TradeDealIllegalStateException();
			}
		}
		TradeDeal tradeDeal = new TradeDeal();
		tradeDeal.setCar(car);
		tradeDeal.setCarOwner(carOwner);
		tradeDeal.setDate(LocalDate.parse(tradeDealDto.date()));
		car.setCarOwner(carOwner);
		carRepo.save(car);
		tradeDealRepo.save(tradeDeal);
		return tradeDealDto;
	}

	@Override
	public List<CarDto> getOwnerCars(long id) {
		if (!carOwnerRepo.existsById(id)) {
			throw new PersonNotFoundException();
		}
		List<Car> cars = carRepo.findByCarOwnerId(id);
		List<CarDto> carsDto = new ArrayList<>();
		cars.forEach(car -> carsDto.add(car.build()));
		return carsDto;
	}

	@Override
	public PersonDto getCarOwner(String carNumber) {
		if (!carRepo.existsById(carNumber)) {
			throw new CarNotFoundException();
		}
		Car car = carRepo.findByNumber(carNumber);
		if (car.getCarOwner() != null) {
			Long id = car.getCarOwner().getId();
			return carOwnerRepo.findById(id).orElseThrow(() -> new PersonNotFoundException()).build();
		}
		return null;
	}

	@Override
	public List<String> mostPopularModels() {
		List<Car> cars = carRepo.findAll();
		 Map<String, Long> modelOccurrences = cars.stream()
		            .map(car -> car.getModel().getModelYear().getName())
		            .collect(Collectors.groupingBy(modelName -> modelName, Collectors.counting()));
		 List<String> mostPopularModels = modelOccurrences.entrySet().stream()
		            .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
		            .limit(2)
		            .map(Map.Entry::getKey)
		            .collect(Collectors.toList());

		    return mostPopularModels; 
	}

	@Override
	public ModelDto addModel(ModelDto modelDto) {
		if (modelRepo.existsById(new ModelYear(modelDto.getModelName(), modelDto.getModelYear()))) {
			throw new IllegalCarsStateException();
		}
		Model model = Model.of(modelDto);
		modelRepo.save(model);
		log.debug("Model {} has been saved", model);
		// TODO Auto-generated method stub
		// HW #63 Write the method similar to the method addPerson
		return modelDto;
	}

}