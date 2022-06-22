package nandhan.restapplication.dto;

import java.util.List;

public class AllCoinsApiResponseDto {

    private List<CoinDataDto> data;
    private long timestamp;

    public AllCoinsApiResponseDto() {}

    public List<CoinDataDto> getData() {
        return data;
    }

    public void setData(List<CoinDataDto> data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
