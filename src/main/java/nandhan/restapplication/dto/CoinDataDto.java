package nandhan.restapplication.dto;

public class CoinDataDto {

    private String id;
    private String symbol;
    private String currencySymbol;
    private String type;
    private String rateUsd;

    public CoinDataDto(){}

    public CoinDataDto(String id, String symbol, String currencySymbol, String type, String rateUsd) {
        this.id = id;
        this.symbol = symbol;
        this.currencySymbol = currencySymbol;
        this.type = type;
        this.rateUsd = rateUsd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRateUsd() {
        return rateUsd;
    }

    public void setRateUsd(String rateUsd) {
        this.rateUsd = rateUsd;
    }
}
