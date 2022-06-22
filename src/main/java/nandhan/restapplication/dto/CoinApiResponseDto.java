package nandhan.restapplication.dto;

public class CoinApiResponseDto {

    private CoinDataDto data;
    private long timestamp;

    public CoinApiResponseDto(){}

    public CoinApiResponseDto(CoinDataDto data, long timestamp) {
        this.data = data;
        this.timestamp = timestamp;
    }

    public CoinDataDto getData() {
        return data;
    }

    public void setData(CoinDataDto data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
