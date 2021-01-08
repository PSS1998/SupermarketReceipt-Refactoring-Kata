package dojo.supermarket.model;

import java.util.List;

public class Discount {
    private final String description;
    private final double discountAmount;
    private final Product product;
    private final List<ProductQuantity> productBundle;

    public Discount(Product product, String description, double discountAmount) {
        this.product = product;
        this.description = description;
        this.discountAmount = discountAmount;
        this.productBundle = null;
    }

    public Discount(List<ProductQuantity> productBundle, String description, double discountAmount) {
        this.productBundle = productBundle;
        this.description = description;
        this.discountAmount = discountAmount;
        this.product = null;
    }

    public String getDescription() {
        return description;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public Product getProduct() {
        return product;
    }

    public List<ProductQuantity> getProductList() {
        return productBundle;
    }

}
