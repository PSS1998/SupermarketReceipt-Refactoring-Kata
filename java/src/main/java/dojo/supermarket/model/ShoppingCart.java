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
                int quantityAsInt = (int) quantity;
                Discount discount = null;

                int numberOfXs = quantityAsInt / offer.offerType.getDiscountUnit();

                if (offer.offerType == SpecialOfferType.TwoForAmount && quantityAsInt >= 2) {
                    double total = offer.argument * (numberOfXs) + quantityAsInt % 2 * unitPrice;
                    double discountN = unitPrice * quantity - total;
                    discount = new Discount(p, offer.offerType.getDescription() + offer.argument, -discountN);
                }
                if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
                    double total = (numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice;
                    double discountAmount = quantity * unitPrice - total;
                    discount = new Discount(p, offer.offerType.getDescription(), -discountAmount);
                }
                if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
                    double discountAmount = -quantity * unitPrice * offer.offerType.getDiscountPercent() / 100.0;
                    discount = new Discount(p, offer.offerType.getDescription(), discountAmount);
                }
                if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
                    double total = offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice;
                    double discountTotal = unitPrice * quantity - total;
                    discount = new Discount(p, offer.offerType.getDescription() + offer.argument, -discountTotal);
                }
                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }
}
