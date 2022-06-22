package nandhan.restapplication.service;

import java.util.List;
import java.util.Map;

public interface CoinService {

    Map<String, String> getPrices(List<String> symbols);
}
