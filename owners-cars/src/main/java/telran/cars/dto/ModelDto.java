package telran.cars.dto;

import static telran.cars.api.ValidationConstants.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ModelDto(@NotNull(message = MISSING_MODEL_MESSAGE) String model,

		@NotNull(message = MISSING_YEAR_MESSAGE) @Min(value = MIN_YEAR, message = WRONG_MIN_YEAR_MESSAGE) @Max(value = MAX_YEAR, message = WRONG_MAX_YEAR_MESSAGE) int year,
		@NotNull(message = MISSING_COMPANY_MESSAGE) String company,
		@NotNull(message = MISSING_ENGINE_POWER_MESSAGE) @Min(value = MIN_ENGINE_POWER, message = WRONG_MIN_ENGINE_POWER_MESSAGE) @Max(value = MAX_ENGINE_POWER, message = WRONG_MAX_ENGINE_POWER_MESSAGE) int enginePower,
		@NotNull(message = MISSING_ENGINE_CAPACITY_MESSAGE) @Min(value = MIN_ENGINE_CAPACITY, message = WRONG_MIN_ENGINE_CAPACITY_MESSAGE) @Max(value = MAX_ENGINE_CAPACITY, message = WRONG_MAX_ENGINE_CAPACITY_MESSAGE)int engineCapacity) {

}
