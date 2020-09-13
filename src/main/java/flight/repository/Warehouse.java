package flight.repository;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class Warehouse {

    Set<Long> availableTickets = Stream.of(1L,2L,3L,4L,5L,6L,7L,8L,9L,10L)
            .collect(Collectors.toCollection(HashSet::new));

    Map<Long,Set<String>> baggageCheckIn = new ConcurrentHashMap<>();

    Map<Long,Integer> coupons = new ConcurrentHashMap<>();


    @PostConstruct
    public void setUp() {
        Set<String> baggageDestination101= Stream.of("a","b","c","d")
                .collect(Collectors.toCollection(HashSet::new));
        Set<String> baggageDestination102= Stream.of("e","f","g","h","i")
                .collect(Collectors.toCollection(HashSet::new));
        Set<String> baggageDestination103= Stream.of("j","k","l","m","n")
                .collect(Collectors.toCollection(HashSet::new));

        baggageCheckIn.put(101L,baggageDestination101);
        baggageCheckIn.put(102L,baggageDestination102);
        baggageCheckIn.put(103L,baggageDestination103);

        coupons.put(4000L,40);
        coupons.put(5000L,50);
        coupons.put(6000L,60);
        coupons.put(4004L,40);
        coupons.put(5005L,50);
        coupons.put(6006L,60);
    }

    public Set<Long> getAvailableTickets() {
        return new HashSet(availableTickets);
    }

    public Map<Long, Set<String>> getBaggageCheckIn() {
        return new HashMap<>(baggageCheckIn);
    }

    public Map<Long, Integer> getCoupons() {
        return new HashMap<>(coupons);
    }



    public boolean isTicketAvailable(Long ticketNumber) {
        return availableTickets.contains(ticketNumber);
    }

    public boolean isBaggageCheckIn(Long destinationId,String baggageId) {
        if(baggageCheckIn.containsKey(destinationId)){
            if(baggageCheckIn.get(destinationId).contains(baggageId)){
                return true;
            }
            return false;
        }
        return false;
    }

    public Optional<Integer> getCouponDiscount(Long couponId) {
        if(coupons.containsKey(couponId)){
            return Optional.of(coupons.get(couponId));
        }
        return Optional.empty();
    }
}

