package dojo.supermarket.model;

import java.util.List;

public class Offer {
    SpecialOfferType offerType;
    Product product;
    List<ProductQuantity> bundleProducts;
    double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    public Offer(SpecialOfferType offerType, List<ProductQuantity> products, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.bundleProducts = products;
    }

    Product getProduct() {
        return this.product;
    }

}
