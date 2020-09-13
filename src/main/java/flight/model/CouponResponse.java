package flight.model;

import org.springframework.stereotype.Component;

import java.util.Optional;

public class CouponResponse {
    private String message;
    private double price;

    private CouponResponse(String message, double price) {
        this.message = message;
        this.price = price;
    }



    public static CouponResponse CreateCouponResponse(double price, Optional<Integer> discountPresent){
        if(discountPresent.isPresent()) {
            double updatePrice = price - (price * discountPresent.get() / 100);
            return new CouponResponse("The coupon is valid:), the new price is: " + updatePrice, updatePrice);
        }
        return new CouponResponse("The coupon not valid - the price stay the same",price);
    }

    public String getMessage() {
        return message;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponResponse that = (CouponResponse) o;

        if (Double.compare(that.price, price) != 0) return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = message != null ? message.hashCode() : 0;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
