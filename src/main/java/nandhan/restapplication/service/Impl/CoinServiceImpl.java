package nandhan.restapplication.service.Impl;

import nandhan.restapplication.dto.AllCoinsApiResponseDto;
import nandhan.restapplication.dto.CoinApiResponseDto;
import nandhan.restapplication.dto.CoinDataDto;
import nandhan.restapplication.entity.CoinEntity;
import nandhan.restapplication.pricerepo.CoinRepo;
import nandhan.restapplication.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CoinServiceImpl implements CoinService {

    @Value("${coincap.baseURL}")
    private String uri;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CoinRepo coinRepo;

    @Override
    public Map<String, String> getPrices(List<String> symbols) {

        Map<String, String> prices = new HashMap<>();
        for (String s : symbols) {
            String price = "Unable to get the price. Invalid Symbol";
            // hit database
            Optional<CoinEntity> optionalPrice = coinRepo.findById(s);

            if (optionalPrice.isPresent()) {
                price = optionalPrice.get().getPrice();
            } else {
                // hit api
                ResponseEntity<CoinApiResponseDto> responseEntity = restTemplate.getForEntity(uri + s, CoinApiResponseDto.class);
                if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.hasBody()) {
                        CoinApiResponseDto dto = responseEntity.getBody();
                        if (Objects.nonNull(dto) && Objects.nonNull(dto.getData())) {
                            price = dto.getData().getRateUsd();
                            // add to database
                            coinRepo.save(new CoinEntity(s,price));
                        }
                }
            }
            prices.put(s,price);
        }
        return prices;
    }

    /**
     * This method calls the given api to get data about all the coins.
     * After recieving response, it will store the prices of all coins.
     * @return String - Success message
     */
    @Override
    public String insertAllSymbols() {
        ResponseEntity<AllCoinsApiResponseDto> responseEntity =
                restTemplate.getForEntity(uri,AllCoinsApiResponseDto.class);
        // convert List<CoinDataDto> -> List<CoinEntity>
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.hasBody()) {
            AllCoinsApiResponseDto allData = responseEntity.getBody();
            List<CoinDataDto> allCoinDto = allData.getData();
            List<CoinEntity> allCoinEntity = new ArrayList<>();
            allCoinDto.forEach(entity -> allCoinEntity.add(
                    new CoinEntity(entity.getId(), entity.getRateUsd())));
            // save in database
            coinRepo.saveAll(allCoinEntity);
        }
        return "All prices updated successfully!";
    }
}
