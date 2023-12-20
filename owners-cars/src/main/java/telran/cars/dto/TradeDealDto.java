package telran.cars.dto;
import jakarta.validation.constraints.*;
import static telran.cars.api.ValidationConstants.*;
public record TradeDealDto(@NotEmpty(message=MISSING_CAR_NUMBER_MESSAGE)
@Pattern(regexp=CAR_NUMBER_REGEXP) String carNumber, 
@Min(value=MIN_PERSON_ID_VALUE, message=WRONG_MIN_PERSON_ID_VALUE) @Max(value=MAX_PERSON_ID_VALUE,
message=WRONG_MAX_PERSON_ID_VALUE )Long personId) {

}
//personId - buyer, if personId==null - this is sale only;

	//if car has no owner - then it is buying
//if car has owner and personId - then it is tradedeal