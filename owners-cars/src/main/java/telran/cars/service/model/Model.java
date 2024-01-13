package telran.cars.service.model;

import jakarta.persistence.*;
import lombok.*;
import telran.cars.dto.ModelDto;

@Entity
@Table(name = "models")
@Getter
public class Model {
	@EmbeddedId
	ModelYear modelYear;
	@Column(nullable = false)
	String company;
	@Column(name = "engine_power", nullable = false)
	int enginePower;
	@Column(name = "engine_capacity", nullable = false)
	int engineCapacity;

	public Model(ModelDto modelDto) {
		this.modelYear = new ModelYear(modelDto.model(), modelDto.year());
		this.company = modelDto.company();
		this.enginePower = modelDto.enginePower();
		this.engineCapacity = modelDto.engineCapacity();

	}

	public ModelDto build(Model model) {
		return new ModelDto(modelYear.getName(), modelYear.getYear(), company, enginePower, engineCapacity);
	}
}