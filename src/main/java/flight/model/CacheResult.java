package flight.model;

import java.time.LocalDateTime;

public class CacheResult {
    private final LocalDateTime date;
    private final Object result;

    public CacheResult(LocalDateTime date, Object result) {
        this.date = date;
        this.result = result;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Object getResult() {
        return result;
    }
}
