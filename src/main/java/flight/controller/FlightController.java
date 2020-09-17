package flight.controller;

import flight.model.CouponResponse;
import flight.service.FlightServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/flight/")
public class FlightController {
    private final FlightServices flightServices;

    @Autowired
    public FlightController(FlightServices flightServices) {
        this.flightServices = flightServices;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/is-ticket-available/{id}")
    public Boolean isTicketAvailable(@PathVariable("id") long ticketId) {
        return flightServices.isTicketAvailable(ticketId);
    }
    @GetMapping("/is-baggage-check-in/{destination-id}/{baggage-id}")
    public Boolean isBaggageCheckIn(@PathVariable("destination-id") Long destinationId,
                                     @PathVariable("baggage-id") String baggageId) {
        return flightServices.isBaggageCheckIn(destinationId,baggageId);
    }
    @GetMapping("/price-after-coupon-discount/{coupon-id}/{price-id}")
    public CouponResponse priceAfterCouponDiscount(@PathVariable("coupon-id") Long couponId,
                                            @PathVariable("price-id") double price) {
        return flightServices.priceAfterCouponDiscount(couponId,price);
    }

}
