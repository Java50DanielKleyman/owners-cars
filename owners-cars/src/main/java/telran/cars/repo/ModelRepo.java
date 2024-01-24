package telran.cars.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.cars.dto.ModelNameAmount;
import telran.cars.service.model.*;

public interface ModelRepo extends JpaRepository<Model, ModelYear> {
@Query("select car.model.modelYear.name "
		+ "from Car car "
		+ "join TradeDeal tradeDeal on car.number = tradeDeal.car.number "
		+ "group by car.model.modelYear.name "
		+ "having count(*) = (select max(countModels) "
		+ "from "
		+ "(select count(*) as countModels "
		+ "from Car car1 "
		+ "join TradeDeal tradeDeal1  on car1.number = tradeDeal1.car.number "
		+ "group by car1.model.modelYear.name))")
	List<String> findMostSoldModelNames();
/*************************************************************/
@Query("select c.model.modelYear.name as name, count(*) as amount "
		+ "from Car c group by c.model.modelYear.name order by count(*) desc limit :nModels")
List<ModelNameAmount> findMostPopularModelNames(int nModels);
/*************************************************************************/
@Query(value="select model_name as name, count(*) as amount "
		+ "from (select * from car_owners where birth_date between :birthDate1 and :birthDate2)"
		+ " owners_age"
		+ " join cars where id=owner_id "
		+ "group by model_name order by count(*) desc limit :nModels", nativeQuery=true)
List<ModelNameAmount> findPopularModelNameOwnerAges(int nModels,
		LocalDate birthDate1, LocalDate birthDate2);

}