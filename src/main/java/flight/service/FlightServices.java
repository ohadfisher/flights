package flight.service;

import flight.model.CacheResult;
import flight.model.CouponResponse;
import flight.model.DestinationAndBaggageTuple;
import flight.repository.Warehouse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FlightServices {
    private static Logger log = Logger.getLogger(FlightServices.class);
    private static final long MAX_TIME_OF_CACHE = 1;

    private Map<Long, CacheResult> ticketCache = new ConcurrentHashMap();
    private Map<Long, CacheResult> couponCache = new ConcurrentHashMap();
    private Map<DestinationAndBaggageTuple, CacheResult> baggageCheckInCache = new ConcurrentHashMap();



    private Warehouse warehouse;

    @Autowired
    public FlightServices(Warehouse warehouse) {
        this.warehouse = warehouse;
    }


    public Boolean isTicketAvailable(long ticketId) {
        if(ticketCache.containsKey(ticketId)){
            CacheResult cacheResult = ticketCache.get(ticketId);
            if(isStillValidCache(cacheResult.getDate())){
                log.info("ticketId "+ ticketId+", was taking from cache");
                return (Boolean) cacheResult.getResult();
            }else {
                ticketCache.remove(ticketId);
                log.info("ticketId "+ ticketId+", was *remove* from cache");
            }
        }

        boolean result = warehouse.isTicketAvailable(ticketId);
        ticketCache.put(ticketId,new CacheResult(LocalDateTime.now(),result));
        log.info("ticketId "+ ticketId+", add to cache");
        return result;
    }




    public Boolean isBaggageCheckIn(long destinationId, String baggageId) {
        DestinationAndBaggageTuple destinationAndBaggageTuple =
                new DestinationAndBaggageTuple(destinationId,baggageId);

        if(baggageCheckInCache.containsKey(destinationAndBaggageTuple)){
            CacheResult cacheResult = baggageCheckInCache.get(destinationAndBaggageTuple);
            if(isStillValidCache(cacheResult.getDate())){
                log.info("destinationAndBaggageTuple "+ destinationAndBaggageTuple+", was taken from cache");
                return (Boolean) cacheResult.getResult();
            }else {
                ticketCache.remove(destinationAndBaggageTuple);
                log.info("destinationAndBaggageTuple "+ destinationAndBaggageTuple+", remove from cache");
            }
        }

        boolean result =  warehouse.isBaggageCheckIn(destinationId, baggageId);
        baggageCheckInCache.put(destinationAndBaggageTuple,new CacheResult(LocalDateTime.now(),result));
        log.info("destinationAndBaggageTuple "+ destinationAndBaggageTuple+", add to cache");
        return result;
    }


    public CouponResponse priceAfterCouponDiscount(Long couponId, double originalPrice) {
        Optional<Integer> discount;
        if(couponCache.containsKey(couponId)){
            CacheResult cacheResult = couponCache.get(couponId);
            if(isStillValidCache(cacheResult.getDate())){
                discount = (Optional<Integer>) cacheResult.getResult();
                log.info("couponId "+ couponId+", was taken from cache");
                return CouponResponse.CreateCouponResponse(originalPrice, discount);
            }else {
                couponCache.remove(couponId);
                log.info("couponId "+ couponId+", remove from cache");

            }
        }
        discount = warehouse.getCouponDiscount(couponId);
        couponCache.put(couponId,new CacheResult(LocalDateTime.now(),discount));
        log.info("couponId "+ couponId+", add to cache");
        return CouponResponse.CreateCouponResponse(originalPrice,discount);
    }



    private static boolean isStillValidCache(LocalDateTime cacheLastUpdate) {
        return Duration.between(cacheLastUpdate, LocalDateTime.now()).toMinutes()<MAX_TIME_OF_CACHE;
    }
}
