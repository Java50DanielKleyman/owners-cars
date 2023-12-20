package telran.cars;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.cars.dto.*;
import static telran.cars.api.ValidationConstants.*;
import telran.cars.exceptions.NotFoundException;
import telran.cars.service.CarsService;

@WebMvcTest // inserting into Application Context Mock WEB server instead of real WebServer
class CarsControllerTest {
	private static final long PERSON_ID = 123000l;
	private static final String CAR_NUMBER = "123-01-002";
	private static final String PERSON_NOT_FOUND_MESSAGE = "person not found";
	private static final String PERSON_ALREADY_EXISTS_MESSAGE = "person already exists";
	private static final String CAR_ALREADY_EXISTS_MESSAGE = "car already exists";
	private static final String CAR_NOT_FOUND_MESSAGE = "car not found";
	static final String WRONG_EMAIL_ADDRESS = "kuku";
	static final String EMAIL_ADDRESS = "vasya@gmail.com";
	private static final Long WRONG_PERSON_ID = 123l;
	@MockBean // inserting into Application Context Mock instead of real Service
				// implementation
	CarsService carsService;
	@Autowired // for injection of MockMvc from Application Context
	MockMvc mockMvc;
	CarDto carDto = new CarDto(CAR_NUMBER, "model");
	CarDto carDto1 = new CarDto("car123", "mode123");
	CarDto carDto2 = new CarDto("123-01-002", null);
	CarDto carDto3 = new CarDto("car123", null);

	@Autowired // for injection of ObjectMapper from Application context
	ObjectMapper mapper; // object for getting JSON from object and object from JSON
	private PersonDto personDto = new PersonDto(PERSON_ID, "Vasya", "2000-10-10", EMAIL_ADDRESS);
	PersonDto personDtoUpdated = new PersonDto(PERSON_ID, "Vasya", "2000-10-10", "vasya@tel-ran.com");
	PersonDto personWrongEmail = new PersonDto(PERSON_ID, "Vasya", "2000-10-10", WRONG_EMAIL_ADDRESS);
	PersonDto personNoId = new PersonDto(null, "Vasya", "2000-10-10", EMAIL_ADDRESS);
	PersonDto personWrongId = new PersonDto(123l, "Vasya", "2000-10-10", EMAIL_ADDRESS);

	TradeDealDto tradeDeal = new TradeDealDto(CAR_NUMBER, PERSON_ID);
	TradeDealDto tradeDeal1 = new TradeDealDto("123", PERSON_ID);
	TradeDealDto tradeDeal2 = new TradeDealDto(CAR_NUMBER, 22222222222l);

	@Test
	void testAddCar() throws Exception {
		when(carsService.addCar(carDto)).thenReturn(carDto);
		String jsonCarDto = mapper.writeValueAsString(carDto); //conversion from carDto object to string JSON
		String actualJSON = mockMvc.perform(post("http://localhost:8080/cars").contentType(MediaType.APPLICATION_JSON)
				.content(jsonCarDto)).andExpect(status().isOk()).andReturn().getResponse()
		.getContentAsString();
		assertEquals(jsonCarDto, actualJSON );
		
	}

	@Test
	void testAddPerson() throws Exception {
		when(carsService.addPerson(personDto)).thenReturn(personDto);
		String jsonPersonDto = mapper.writeValueAsString(personDto); //conversion from carDto object to string JSON
		String actualJSON = mockMvc.perform(post("http://localhost:8080/cars/person").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPersonDto)).andExpect(status().isOk()).andReturn().getResponse()
		.getContentAsString();
		assertEquals(jsonPersonDto, actualJSON );
	}

	@Test
	void testUpdatePerson() throws Exception{
		when(carsService.updatePerson(personDtoUpdated)).thenReturn(personDtoUpdated);
		String jsonPersonDtoUpdated = mapper.writeValueAsString(personDtoUpdated); //conversion from carDto object to string JSON
		String actualJSON = mockMvc.perform(put("http://localhost:8080/cars/person").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPersonDtoUpdated)).andExpect(status().isOk()).andReturn().getResponse()
		.getContentAsString();
		assertEquals(jsonPersonDtoUpdated, actualJSON );
	}

	@Test
	void testPurchase() throws Exception{
		when(carsService.purchase(tradeDeal)).thenReturn(tradeDeal);
		String jsonTradeDeal = mapper.writeValueAsString(tradeDeal);
		String actualJSON = mockMvc.perform(put("http://localhost:8080/cars/trade")
				.contentType(MediaType.APPLICATION_JSON).content(jsonTradeDeal))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(jsonTradeDeal, actualJSON);
	}

	@Test
	void testDeletePerson() throws Exception{
		when(carsService.deletePerson(PERSON_ID)).thenReturn(personDto);
		String jsonExpected = mapper.writeValueAsString(personDto);
		String actualJSON = mockMvc.perform(delete("http://localhost:8080/cars/person/" + PERSON_ID))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(jsonExpected, actualJSON);
	}

	@Test
	void testDeleteCar() throws Exception {
		when(carsService.deleteCar(CAR_NUMBER)).thenReturn(carDto);
		String jsonExpected = mapper.writeValueAsString(carDto);
		String actualJSON = mockMvc.perform(delete("http://localhost:8080/cars/" + CAR_NUMBER))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(jsonExpected, actualJSON);
	}

	@Test
	void testGetOwnerCars() throws Exception {
		CarDto[] expectedArray = { carDto, carDto1 };
		String jsonExpected = mapper.writeValueAsString(expectedArray);
		when(carsService.getOwnerCars(PERSON_ID)).thenReturn(Arrays.asList(expectedArray));
		String actualJSON = mockMvc.perform(get("http://localhost:8080/cars/person/" + PERSON_ID))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(jsonExpected, actualJSON);
	}

	@Test
	void testGetCarOwner() throws Exception{
		when(carsService.getCarOwner(CAR_NUMBER)).thenReturn(personDto);
		String jsonExpected = mapper.writeValueAsString(personDto);
		String actualJSON = mockMvc.perform(get("http://localhost:8080/cars/" + CAR_NUMBER)).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertEquals(jsonExpected, actualJSON);
	}

	/******************************************************************/
	/*********** ALternative flows - Service Exceptions Handling *************/
	@Test
	void testDeletePersonNotFound() throws Exception  {
		when(carsService.deletePerson(PERSON_ID)).thenThrow(new NotFoundException(PERSON_NOT_FOUND_MESSAGE));
		
		String actualJSON = mockMvc.perform(delete("http://localhost:8080/cars/person/" + PERSON_ID))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		assertEquals(PERSON_NOT_FOUND_MESSAGE, actualJSON);
		
	}

	@Test
	void testAddPersonAlreadyExists() throws Exception {
		when(carsService.addPerson(personDto)).thenThrow(new IllegalStateException(PERSON_ALREADY_EXISTS_MESSAGE));
		String jsonPersonDto = mapper.writeValueAsString(personDto); //conversion from carDto object to string JSON
		String response = mockMvc.perform(post("http://localhost:8080/cars/person").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPersonDto)).andExpect(status().isBadRequest()).andReturn().getResponse()
		.getContentAsString();
		assertEquals(PERSON_ALREADY_EXISTS_MESSAGE, response);
	}

	@Test
	void testAddCarAlreadyExists() throws Exception {
		when(carsService.addCar(carDto)).thenThrow(new IllegalStateException(CAR_ALREADY_EXISTS_MESSAGE));
		String jsonCarDto = mapper.writeValueAsString(carDto); //conversion from carDto object to string JSON
		String response = mockMvc.perform(post("http://localhost:8080/cars").contentType(MediaType.APPLICATION_JSON)
				.content(jsonCarDto)).andExpect(status().isBadRequest()).andReturn().getResponse()
		.getContentAsString();
		assertEquals(CAR_ALREADY_EXISTS_MESSAGE, response );
		
	}

	@Test
	void testUpdatePersonNotFound() throws Exception{
		when(carsService.updatePerson(personDtoUpdated)).thenThrow(new NotFoundException(PERSON_NOT_FOUND_MESSAGE));
		String jsonPersonDtoUpdated = mapper.writeValueAsString(personDtoUpdated); //conversion from carDto object to string JSON
		String response = mockMvc.perform(put("http://localhost:8080/cars/person").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPersonDtoUpdated)).andExpect(status().isNotFound()).andReturn().getResponse()
		.getContentAsString();
		assertEquals(PERSON_NOT_FOUND_MESSAGE, response );
	}

	@Test
	void testPurchaseCarNotFound() throws Exception {
		testPurchaseNotFound(CAR_NOT_FOUND_MESSAGE);
	}

	@Test
	void testPurchasePersonNotFound() throws Exception {
		testPurchaseNotFound(PERSON_NOT_FOUND_MESSAGE);
	}

	private void testPurchaseNotFound(String message)
			throws JsonProcessingException, UnsupportedEncodingException, Exception {
		when(carsService.purchase(tradeDeal)).thenThrow(new NotFoundException(message));
		String jsonTradeDeal = mapper.writeValueAsString(tradeDeal);
		String response = mockMvc.perform(put("http://localhost:8080/cars/trade")
				.contentType(MediaType.APPLICATION_JSON).content(jsonTradeDeal))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		assertEquals(message, response);
	}

	@Test
	void testDeleteCarNotFound() throws Exception {
		when(carsService.deleteCar(CAR_NUMBER)).thenThrow(new NotFoundException(CAR_NOT_FOUND_MESSAGE));
		String response = mockMvc.perform(delete("http://localhost:8080/cars/" + CAR_NUMBER))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		assertEquals(CAR_NOT_FOUND_MESSAGE, response);
	}

	@Test
	void testGetOwnerCarsPersonNotFound() throws Exception {
		
		when(carsService.getOwnerCars(PERSON_ID)).thenThrow(new NotFoundException(PERSON_NOT_FOUND_MESSAGE));
		String response = mockMvc.perform(get("http://localhost:8080/cars/person/" + PERSON_ID))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		assertEquals(PERSON_NOT_FOUND_MESSAGE, response);
	}

	@Test
	void testGetCarOwnerCarNotFound() throws Exception{
		when(carsService.getCarOwner(CAR_NUMBER)).thenThrow(new NotFoundException(CAR_NOT_FOUND_MESSAGE));
		String response = mockMvc.perform(get("http://localhost:8080/cars/" + CAR_NUMBER)).andExpect(status().isNotFound())
				.andReturn().getResponse().getContentAsString();
		assertEquals(CAR_NOT_FOUND_MESSAGE, response);
	}

	/*****************************************************************************/
	/* Alternative flows - Validation exceptions handling ***********************/
	@Test
	void addPersonWrongEmailTest() throws Exception {
		String jsonPersonDto = mapper.writeValueAsString(personWrongEmail);
		String response = mockMvc
				.perform(post("http://localhost:8080/cars/person").contentType(MediaType.APPLICATION_JSON)
						.content(jsonPersonDto))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertEquals(WRONG_EMAIL_FORMAT, response);
	}

	@Test
	void addPersonWrongIdTest() throws Exception {
		String jsonPersonDto = mapper.writeValueAsString(personWrongId);
		String response = mockMvc
				.perform(post("http://localhost:8080/cars/person").contentType(MediaType.APPLICATION_JSON)
						.content(jsonPersonDto))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertEquals(WRONG_MIN_PERSON_ID_VALUE, response);
	}

	@Test
	void uoDatePersonWrongIdTest() throws Exception {
		String jsonPersonDto = mapper.writeValueAsString(personWrongId);
		String response = mockMvc
				.perform(post("http://localhost:8080/cars/person").contentType(MediaType.APPLICATION_JSON)
						.content(jsonPersonDto))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertEquals(WRONG_MIN_PERSON_ID_VALUE, response);
	}

	@Test
	void addPersonNoIdTest() throws Exception {
		String jsonPersonDto = mapper.writeValueAsString(personNoId);
		String response = mockMvc
				.perform(post("http://localhost:8080/cars/person").contentType(MediaType.APPLICATION_JSON)
						.content(jsonPersonDto))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertEquals(MISSING_PERSON_ID_MESSAGE, response);
	}

	@Test
	void addCarWrongNumberTest() throws Exception {
		String jsonCarDto = mapper.writeValueAsString(carDto1);
		String response = mockMvc
				.perform(post("http://localhost:8080/cars").contentType(MediaType.APPLICATION_JSON).content(jsonCarDto))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertEquals(WRONG_CAR_NUMBER_MESSAGE, response);
	}

	@Test
	void addCarNoModelTest() throws Exception {
		String jsonCarDto = mapper.writeValueAsString(carDto2);
		String response = mockMvc
				.perform(post("http://localhost:8080/cars").contentType(MediaType.APPLICATION_JSON).content(jsonCarDto))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertEquals(MISSING_CAR_MODEL_MESSAGE, response);
	}

	// @Test
	void addCarWrongNumberNoModelTest() throws Exception {
		String jsonCarDto = mapper.writeValueAsString(carDto3);
		String response = mockMvc
				.perform(post("http://localhost:8080/cars").contentType(MediaType.APPLICATION_JSON).content(jsonCarDto))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		String errorMessage = "Incorrect Car Number;Missing car model";
		assertEquals(errorMessage, response);
	}

	@Test
	void testPurchaseWrongCarNumber() throws Exception {
		String jsonTradeDeal = mapper.writeValueAsString(tradeDeal1);
		String response = mockMvc
				.perform(put("http://localhost:8080/cars/trade").contentType(MediaType.APPLICATION_JSON)
						.content(jsonTradeDeal))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertEquals(WRONG_CAR_NUMBER_MESSAGE, response);
	}

	@Test
	void testPurchaseWrongIdNumber() throws Exception {
		String jsonTradeDeal = mapper.writeValueAsString(tradeDeal2);
		String response = mockMvc
				.perform(put("http://localhost:8080/cars/trade").contentType(MediaType.APPLICATION_JSON)
						.content(jsonTradeDeal))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertEquals(WRONG_MAX_PERSON_ID_VALUE, response);
	}

	@Test
	void deletePersonWrongIdTest() throws Exception {
		String actualJSON = mockMvc.perform(delete("http://localhost:8080/cars/person/" + WRONG_PERSON_ID))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertEquals(WRONG_MIN_PERSON_ID_VALUE, actualJSON);
	}

}