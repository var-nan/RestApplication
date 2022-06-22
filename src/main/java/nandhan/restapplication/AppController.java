package nandhan.restapplication;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AppController {

    // coincap api url
    private String uri = "https://api.coincap.io/V2/rates/";

    /**
     * This method returns the price of the given cryptocurrencies as dictionary object.
     * @param symbols (GET parameter)
     * @return (all the prices as a dictionary and converted to JSON by HttpMessageConverter)
     */
    @GetMapping(value="/price", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> price (@RequestParam("symbols") List<String> symbols) {

        Map<String, String> prices = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();

        for (String s: symbols) {
            System.out.println(s);
            ResponseEntity<Map> responseEntity = restTemplate.getForEntity(uri+s,Map.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Map bodyMap = (Map) responseEntity.getBody();
                if (bodyMap.containsKey("data")) {
                    Map dataMap = (Map) bodyMap.get("data");
                    prices.put(s, dataMap.get("rateUsd").toString());
                } else
                    prices.put(s,"Unable to get the Price. Invalid Symbol");
            }
        }
        return new ResponseEntity<Map<String,String>>(prices, HttpStatus.OK);
    }
}

/**
 * NOTE:
 * 1. All the prices for the symbols are extracted from the given coincap api.
 * 2. Since, there is an api available, No database is used to store the symbols.
 * 3. Multiple symbols can be passed in the request seperated by ','.
 * 3. Incase of incorrect symbol, response will be "Invalid symbol", and continues to next symbol.
 */
