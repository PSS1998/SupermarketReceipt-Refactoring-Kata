package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    private Map<Product, Double> productQuantities = new HashMap<>();


    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    private Map<Product, Double> productQuantities() {
        return productQuantities;
    }


    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p : productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {

                Offer offer = offers.get(p);
                double unitPrice = catalog.getUnitPrice(p);
                Discount discount = null;

                if (offer.offerType == SpecialOfferType.TwoForAmount && offer.offerType.haveMinimumRequiredAmount(quantity)) {
                    discount = new Discount(p, offer.offerType.getDescription() + offer.argument, - calDiscountAmount(quantity, offer, unitPrice));
                }
                if (offer.offerType == SpecialOfferType.ThreeForTwo && offer.offerType.haveMinimumRequiredAmount(quantity)) {
                    discount = new Discount(p, offer.offerType.getDescription(), - calDiscountAmount(quantity, offer, unitPrice));
                }
                if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
                    discount = new Discount(p, offer.offerType.getDescription(),  - calDiscountAmount(quantity, offer, unitPrice));
                }
                if (offer.offerType == SpecialOfferType.FiveForAmount && offer.offerType.haveMinimumRequiredAmount(quantity)) {
                    discount = new Discount(p, offer.offerType.getDescription() + offer.argument, - calDiscountAmount(quantity, offer, unitPrice));
                }
                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }

    private double calDiscountAmount(double quantity, Offer offer, double unitPrice) {
        switch (offer.offerType){
            case TenPercentDiscount:
                return quantity * unitPrice * offer.offerType.getDiscountPercent() / 100.0;
            case ThreeForTwo:
                double ThreeForTowTotal = (quantity / offer.offerType.getDiscountUnit() * 2 * unitPrice) + quantity % offer.offerType.getDiscountUnit() * unitPrice;
                return quantity * unitPrice - ThreeForTowTotal;
            default:
                double defaultTotal = offer.argument * (quantity / offer.offerType.getDiscountUnit()) + quantity % offer.offerType.getDiscountUnit() * unitPrice;
                return unitPrice * quantity - defaultTotal;
        }
    }
}
