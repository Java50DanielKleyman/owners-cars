package telran.cars.dto;

import jakarta.validation.constraints.*;
import static telran.cars.api.ValidationConstants.*;

import java.util.Objects;

public record CarDto(
		@NotEmpty(message = MISSING_CAR_NUMBER_MESSAGE) @Pattern(regexp = CAR_NUMBER_REGEXP, message = WRONG_CAR_NUMBER_MESSAGE) 
		String number,
		@NotEmpty(message = MISSING_CAR_MODEL_MESSAGE) 
		String model,
		@NotNull(message = MISSING_YEAR_MESSAGE) @Min(value = MIN_YEAR, message = WRONG_MIN_YEAR_MESSAGE) @Max(value = MAX_YEAR, message = WRONG_MAX_YEAR_MESSAGE) 
		int year,
		@NotNull(message = MISSING_PERSON_ID_MESSAGE) @Min(value = MIN_PERSON_ID_VALUE, message = WRONG_MIN_PERSON_ID_VALUE) @Max(value = MAX_PERSON_ID_VALUE, message = WRONG_MAX_PERSON_ID_VALUE) 
		Long id,
		@NotEmpty(message = MISSING_COLOR_MESSAGE) 
		String color,
		@NotNull(message = MISSING_KILOMETERS_MESSAGE) @Min(value = MIN_KILOMETERS, message = WRONG_MIN_KILOMETERS_MESSAGE) @Max(value = MAX_KILOMETERS, message = WRONG_MAX_KILOMETERS_MESSAGE) 
		int kilometers,
		@NotNull(message = MISSING_CAR_STATE_MESSAGE) 
		CarState carState)

{
	public CarDto {
		validateCarState(carState);
		validateColor(color);
	}

	private void validateColor(String color) {
			if (!isValidColor(color)) {
			throw new IllegalArgumentException(INVALID_COLOR_MESSAGE);
		}

	}

	private boolean isValidColor(String color) {
		Colors[] allowedColors = { Colors.RED, Colors.SILVER, Colors.WHITE };

		for (Colors allowedColor : allowedColors) {
			if (allowedColor.name().equals(color)) {
				return true;
			}
		}
		return false;
	}

	private void validateCarState(CarState carState) {
		if(carState != null) {
		if (!isValidCarState(carState)) {
			throw new IllegalArgumentException(INVALID_CAR_STATE_MESSAGE);
		}}
	}

	private boolean isValidCarState(CarState carState) {

		CarState[] allowedStates = { CarState.OLD, CarState.NEW, CarState.GOOD, CarState.MIDDLE, CarState.BAD };

		for (CarState allowedState : allowedStates) {
			if (allowedState.equals(carState)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarDto other = (CarDto) obj;
		return Objects.equals(number, other.number);
	}

}