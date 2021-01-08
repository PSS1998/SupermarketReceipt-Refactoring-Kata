package dojo.supermarket.model;

import java.util.Objects;

public class ReceiptItem {
    private final ProductQuantity item;
    private final double price;
    //private double totalPrice;

    ReceiptItem(ProductQuantity item, double price) {
        this.item = item;
        this.price = price;
        //this.totalPrice = totalPrice;
    }

    public double getPrice() {
        return this.price;
    }

    public Product getProduct() {
        return item.getProduct();
    }

    public double getQuantity() {
        return item.getQuantity();
    }

    public double getTotalPrice() {
        return price*item.getQuantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiptItem that = (ReceiptItem) o;
        return Double.compare(that.price, price) == 0 &&
                Double.compare(that.item.getQuantity(), item.getQuantity()) == 0 &&
                Objects.equals(item.getProduct(), that.item.getProduct());
    }

    @Override
    public int hashCode() {

        return Objects.hash(item.getProduct(), price, item.getQuantity());
    }


}
