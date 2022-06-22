package nandhan.restapplication.service.Impl;

import nandhan.restapplication.dto.CoinApiResponseDto;
import nandhan.restapplication.entity.CoinEntity;
import nandhan.restapplication.pricerepository.CoinRepo;
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
            Optional<CoinEntity> optionalPrice = Optional.ofNullable(coinRepo.findBySymbol(s));

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
                            coinRepo.insertSymbol(s,price);
                        }
                }
            }
            prices.put(s,price);
        }
        return prices;
    }
}
