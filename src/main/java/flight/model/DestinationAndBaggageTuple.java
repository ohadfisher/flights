package flight.model;

public class DestinationAndBaggageTuple {
    private final long destination;
    private final String baggage;

    public DestinationAndBaggageTuple(long destination, String baggage) {
        this.destination = destination;
        this.baggage = baggage;
    }

    public long getDestination() {
        return destination;
    }

    public String getBaggage() {
        return baggage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DestinationAndBaggageTuple that = (DestinationAndBaggageTuple) o;

        if (destination != that.destination) return false;
        return baggage != null ? baggage.equals(that.baggage) : that.baggage == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (destination ^ (destination >>> 32));
        result = 31 * result + (baggage != null ? baggage.hashCode() : 0);
        return result;
    }
}
