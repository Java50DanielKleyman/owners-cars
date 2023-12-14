package telran.cars.dto;

public record TradeDealDto(String carNumber, long personId) { // personId - buyer, if personId==null - this is sale only;
	
	//if car has no owner - then it is buying
// if car has owner and personId - then it is tradedeal
}
