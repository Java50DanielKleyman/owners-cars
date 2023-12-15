package telran.cars;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.cars.dto.CarDto;
import telran.cars.dto.PersonDto;
import telran.cars.dto.TradeDealDto;
import telran.cars.service.CarsService;

@WebMvcTest // inserting into Application Context Mock WEB server instead of real WebServer
class CarsControllerTest {
	@MockBean // inserting into Application Context Mock instead of real Service
				// implementation
	CarsService carsService;
	@Autowired // for injection of MockMvc from Application Context
	MockMvc mockMvc;
	CarDto carDto = new CarDto("car", "model");
	CarDto carDto1 = new CarDto("car123", "mode123");
	PersonDto personDto = new PersonDto(123, "David", "12.15.1985", "david@gmail.com");
	PersonDto personDto1 = new PersonDto(123, "David", "12.15.1985", "david1@gmail.com");
	TradeDealDto tradeDealDto = new TradeDealDto("car123", 123);
	@Autowired // for injection of ObjectMapper from Application context
	ObjectMapper mapper; // object for getting JSON from object and object from JSON

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
	void testAddPerson() throws UnsupportedEncodingException, Exception {
		when(carsService.addPerson(personDto)).thenReturn(personDto);
		String jsonPersonDto = mapper.writeValueAsString(personDto); //conversion from carDto object to string JSON
		String actualJSON = mockMvc.perform(post("http://localhost:8080/cars/person").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPersonDto)).andExpect(status().isOk()).andReturn().getResponse()
		.getContentAsString();
		assertEquals(jsonPersonDto, actualJSON );
	}

	@Test
	void testUpdatePerson() throws UnsupportedEncodingException, Exception {
		when(carsService.updatePerson(personDto)).thenReturn(personDto);
		String jsonPersonDto = mapper.writeValueAsString(personDto); //conversion from carDto object to string JSON
		String actualJSON = mockMvc.perform(put("http://localhost:8080/cars/person").contentType(MediaType.APPLICATION_JSON)
				.content(jsonPersonDto)).andExpect(status().isOk()).andReturn().getResponse()
		.getContentAsString();
		assertEquals(jsonPersonDto, actualJSON );
	}

	@Test
	void testPurchase() throws UnsupportedEncodingException, Exception {
		when(carsService.purchase(tradeDealDto)).thenReturn(tradeDealDto);
		String jsonTradeDealDto = mapper.writeValueAsString(tradeDealDto); //conversion from carDto object to string JSON
		String actualJSON = mockMvc.perform(put("http://localhost:8080/cars/trade").contentType(MediaType.APPLICATION_JSON)
				.content(jsonTradeDealDto)).andExpect(status().isOk()).andReturn().getResponse()
		.getContentAsString();
		assertEquals(jsonTradeDealDto, actualJSON );
	}

	@Test
	void testDeletePerson() throws UnsupportedEncodingException, Exception {
		long id = personDto.id();
		when(carsService.deletePerson(id)).thenReturn(personDto);
		String jsonPersonDto = mapper.writeValueAsString(personDto); // conversion from carDto object to string JSON
		String actualJSON = mockMvc
				.perform(delete("http://localhost:8080/cars/person/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(jsonPersonDto, actualJSON);
	}

	@Test
	void testDeleteCar() throws UnsupportedEncodingException, Exception {
		String carNumber = carDto.number();
		when(carsService.deleteCar(carNumber)).thenReturn(carDto);
		String jsonCarDto = mapper.writeValueAsString(carDto); // conversion from carDto object to string JSON
		String actualJSON = mockMvc
				.perform(delete("http://localhost:8080/cars/{carNumber}", carNumber)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(jsonCarDto, actualJSON);
	}

	@Test
	void testGetOwnerCars() throws UnsupportedEncodingException, Exception {
		long id = personDto.id();
		List<CarDto> expectedCars = Arrays.asList(new CarDto("ABC123", "Toyota"), new CarDto("XYZ789", "Honda"));
		when(carsService.getOwnerCars(id)).thenReturn(expectedCars);
		String jsonExpectedCars = mapper.writeValueAsString(expectedCars);
		String actualJSON = mockMvc
				.perform(get("http://localhost:8080/cars/person/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(jsonExpectedCars, actualJSON);
	}

	@Test
	void testGetCarOwner() throws UnsupportedEncodingException, Exception {
		String carNumber = carDto.number();
		when(carsService.getCarOwner(carNumber)).thenReturn(personDto);
		String jsonPersonDto = mapper.writeValueAsString(personDto);
		String actualJSON = mockMvc
				.perform(get("http://localhost:8080/cars/{carNumber}", carNumber)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(jsonPersonDto, actualJSON);
	}

}