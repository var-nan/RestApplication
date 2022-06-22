package nandhan.restapplication.pricerepo;

import nandhan.restapplication.entity.CoinEntity;
import org.springframework.data.repository.CrudRepository;

public interface CoinRepo extends CrudRepository<CoinEntity,String> {

}
