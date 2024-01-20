package telran.cars.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.cars.dto.ModelNameAmount;
import telran.cars.service.model.*;

public interface CarRepo extends JpaRepository<Car, String> {

Car findByNumber(String number);
List<Car>findByCarOwnerId(long id);
@Query(value = "select m.model_name as name, count(*) as amount "
        + "from cars c join car_owners co on c.owner_id = co.id "
        + "join models m on c.model_name = m.model_name and c.model_year = m.model_year "
        + "where DATEDIFF('YEAR', co.birth_date, CURRENT_DATE()) between :ageFrom and :ageTo "
        + "group by m.model_name order by count(*) desc limit :nModels", nativeQuery = true)
List<ModelNameAmount> findMostPopularModelNameByOwnerAges(int nModels, int ageFrom, int ageTo);
}