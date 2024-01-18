package telran.cars.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
		return personDto;
	}

	@Override
	@Transactional
	public PersonDto deletePerson(long id) {
		CarOwner carOwner = carOwnerRepo.findById(id).orElseThrow(() -> new PersonNotFoundException());
		Car car = carRepo.findByCarOwnerId(id);
		List<TradeDeal> tradeDeals = tradeDealRepo.findByCarOwner_Id(id);
		tradeDealRepo.deleteAll(tradeDeals);
		if (car != null) {
			car.setCarOwner(null);
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
		return null;
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
		return tradeDealDto;
	}

	@Override
	public List<CarDto> getOwnerCars(long id) {
		// Not Implemented yet
		return null;
	}

	@Override
	public PersonDto getCarOwner(String carNumber) {
		// Not Implemented yet
		return null;
	}

	@Override
	public List<String> mostPopularModels() {
		// Not Implemented yet

		return null;
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