package telran.cars.service.model;

import java.time.LocalDate;
import java.util.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.cars.dto.PersonDto;
import jakarta.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "car_owners")
public class CarOwner {
	@Id
	Long id;
	String name;
	@Column(nullable = false, name = "birth_date")
	@Temporal(TemporalType.DATE)
	LocalDate birthDate;
	String email;

	public static CarOwner of(PersonDto personDto) {
		CarOwner carOwner = new CarOwner();
		carOwner.id = personDto.id();
		carOwner.name = personDto.name();
		carOwner.birthDate = LocalDate.parse(personDto.birthDate());
		carOwner.email = personDto.email();
		return carOwner;
	}

	public PersonDto build() {
		return new PersonDto(id, name, birthDate.toString(), email);
	}

	public void setEmail(String email) {
		this.email = email;
	}
}