package telran.cars.service.model;

import jakarta.persistence.*;
import lombok.*;
import telran.cars.dto.ModelDto;

@NoArgsConstructor
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

	public static Model of(ModelDto modelDto) {
		Model model = new Model();
		model.modelYear = new ModelYear(modelDto.model(), modelDto.year());
		model.company = modelDto.company();
		model.enginePower = modelDto.enginePower();
		model.engineCapacity = modelDto.engineCapacity();
		return model;
	}

	public ModelDto build(Model model) {
		return new ModelDto(modelYear.getName(), modelYear.getYear(), company, enginePower, engineCapacity);
	}
}