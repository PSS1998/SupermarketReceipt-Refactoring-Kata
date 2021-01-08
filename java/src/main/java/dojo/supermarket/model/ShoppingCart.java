package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ShoppingCart {
    private final List<ProductQuantity> items = new ArrayList<>();
    private Map<Product, Double> productQuantities = new HashMap<>();

    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        productQuantities.keySet().forEach(product -> {
            Offer offer = offers.get(product);
            if (haveOffer(product, offer)) {
                receipt.addDiscount(new Discount(product, offer.offerType.getDescription(offer.argument), calDiscountAmount(productQuantities.get(product), offer, catalog.getUnitPrice(product))));
            }
        });
    }

    private boolean haveOffer(Product product, Offer offer) {
        return offer != null && offer.offerType.haveMinimumRequiredAmount(productQuantities.get(product));
    }

    private double calDiscountAmount(double quantity, Offer offer, double unitPrice) {
        switch (offer.offerType) {
            case TenPercentDiscount:
                return -(getBasePrice(quantity, unitPrice) * offer.offerType.getDiscountPercent() / 100.0);
            case ThreeForTwo:
                double ThreeForTowTotal = (quantity / offer.offerType.getDiscountUnit() * 2 * unitPrice) + quantity % offer.offerType.getDiscountUnit() * unitPrice;
                return -(getBasePrice(quantity, unitPrice) - ThreeForTowTotal);
            default:
                double defaultTotal = offer.argument * (quantity / offer.offerType.getDiscountUnit()) + quantity % offer.offerType.getDiscountUnit() * unitPrice;
                return -(getBasePrice(quantity, unitPrice)  - defaultTotal);
        }
    }

    private double getBasePrice(double quantity, double unitPrice) {
        return quantity * unitPrice;
    }
}
