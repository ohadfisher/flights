package flight.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import flight.model.CouponResponse;
import flight.repository.Warehouse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FlightServicesTest {


    @Spy
    private Warehouse warehouse = new Warehouse();

    @Before
    public void setUp() {
        warehouse.setUp();

    }


    /*@Rule
    public ExpectedException expectedEx = ExpectedException.none();*/

    @InjectMocks
    private FlightServices flightServices;



    @Test
    public void isTicketAvailable() {
        assertFalse(flightServices.isTicketAvailable(0));
        assertTrue(flightServices.isTicketAvailable(1));
        assertTrue(flightServices.isTicketAvailable(2));
        assertTrue(flightServices.isTicketAvailable(3));
        assertTrue(flightServices.isTicketAvailable(4));
        assertTrue(flightServices.isTicketAvailable(5));
        assertTrue(flightServices.isTicketAvailable(6));
        assertTrue(flightServices.isTicketAvailable(7));
        assertTrue(flightServices.isTicketAvailable(8));
        assertTrue(flightServices.isTicketAvailable(9));
        assertTrue(flightServices.isTicketAvailable(10));
        assertFalse(flightServices.isTicketAvailable(11));

    }

    @Test
    public void isBaggageCheckIn() {
        assertTrue(flightServices.isBaggageCheckIn(101L,"a"));
        assertTrue(flightServices.isBaggageCheckIn(101L,"b"));
        assertTrue(flightServices.isBaggageCheckIn(101L,"c"));
        assertTrue(flightServices.isBaggageCheckIn(101L,"d"));

        assertTrue(flightServices.isBaggageCheckIn(102L,"e"));
        assertTrue(flightServices.isBaggageCheckIn(102L,"f"));
        assertTrue(flightServices.isBaggageCheckIn(102L,"g"));
        assertTrue(flightServices.isBaggageCheckIn(102L,"h"));
        assertTrue(flightServices.isBaggageCheckIn(102L,"i"));

        assertTrue(flightServices.isBaggageCheckIn(103L,"j"));
        assertTrue(flightServices.isBaggageCheckIn(103L,"k"));
        assertTrue(flightServices.isBaggageCheckIn(103L,"l"));
        assertTrue(flightServices.isBaggageCheckIn(103L,"m"));
        assertTrue(flightServices.isBaggageCheckIn(103L,"n"));

    }

    @Test
    public void priceAfterCouponDiscount() {

        assertEquals(CouponResponse.CreateCouponResponse(100,Optional.of(40)),flightServices.priceAfterCouponDiscount(4000L,100));
        assertEquals(CouponResponse.CreateCouponResponse(100,Optional.of(50)),flightServices.priceAfterCouponDiscount(5000L,100));
        assertEquals(CouponResponse.CreateCouponResponse(100,Optional.of(60)),flightServices.priceAfterCouponDiscount(6000L,100));

        assertEquals(CouponResponse.CreateCouponResponse(100,Optional.of(40)),flightServices.priceAfterCouponDiscount(4004L,100));
        assertEquals(CouponResponse.CreateCouponResponse(100,Optional.of(50)),flightServices.priceAfterCouponDiscount(5005L,100));
        assertEquals(CouponResponse.CreateCouponResponse(100,Optional.of(60)),flightServices.priceAfterCouponDiscount(6006L,100));

        assertNotEquals(CouponResponse.CreateCouponResponse(100,Optional.of(40)),flightServices.priceAfterCouponDiscount(4002L,100));
        assertNotEquals(CouponResponse.CreateCouponResponse(100,Optional.of(50)),flightServices.priceAfterCouponDiscount(5002L,100));
        assertNotEquals(CouponResponse.CreateCouponResponse(100,Optional.of(60)),flightServices.priceAfterCouponDiscount(6002L,100));


    }
}