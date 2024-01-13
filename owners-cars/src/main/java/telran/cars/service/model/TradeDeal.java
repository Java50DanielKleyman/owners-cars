package telran.cars.service.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import telran.cars.dto.TradeDealDto;

@Entity
@Table(name = "trade_deals")
public class TradeDeal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	@ManyToOne
	@JoinColumn(name = "car_number", nullable = false)
	Car car;
	@ManyToOne
	@JoinColumn(name = "owner_id")
	CarOwner carOwner;
	@Temporal(TemporalType.DATE)
	LocalDate date;
	 public TradeDeal() {
	        // Default constructor required by JPA
	    }

	public TradeDeal(TradeDealDto tradeDealDto) {
		this.carOwner = new CarOwner(); 
		this.carOwner.id = tradeDealDto.id();
		 this.car = new Car();
		this.car.number = tradeDealDto.carNumber();
		this.date = LocalDate.parse(tradeDealDto.date()); }
	
		public static TradeDealDto build (TradeDeal tradeDeal) {
			return new TradeDealDto(tradeDeal.car.number, tradeDeal.carOwner.id, tradeDeal.date.toString());
		}
	}
}