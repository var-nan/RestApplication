package nandhan.restapplication.pricerepository;

import nandhan.restapplication.entity.CoinEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RepositoryDefinition(domainClass = CoinEntity.class, idClass = String.class)
public interface CoinRepo{

    public CoinEntity findBySymbol(String symbol);

    @Modifying
    @Query(nativeQuery = true,value = "INSERT INTO coincap values (?1,?2)")
    public int insertSymbol(String symbol, String price);
}
