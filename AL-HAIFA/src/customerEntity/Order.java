package customerEntity;

import java.util.Date;

import customerUtils.PaymentMethod;

public class Order {
	private long orderID;
	private Date bookingDate;
	private PaymentMethod paymentMethod;

	public Order(long orderID, Date bookingDate, PaymentMethod paymentMethod) {
		super();
		this.orderID = orderID;
		this.bookingDate = bookingDate;
		this.paymentMethod = paymentMethod;
	}
	
	public Order(long orderID) {
		super();
		this.orderID = orderID;
	}

	public long getOrderID() {
		return orderID;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (orderID ^ (orderID >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Order))
			return false;
		Order other = (Order) obj;
		if (orderID != other.orderID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Order [orderID=%s, bookingDate=%s, paymentMethod=%s]", orderID, bookingDate,
				paymentMethod);
	}
}
