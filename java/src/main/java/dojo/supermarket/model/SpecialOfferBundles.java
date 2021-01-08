package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecialOfferBundles {

    public List<Discount> getDiscount(List<ProductQuantity> shoppingCartItems, Map<List<ProductQuantity>, Offer> offers, SupermarketCatalog catalog) {
        List<Discount> discountList = new ArrayList<Discount>();
        for (Offer offer : offers.values()) {
            boolean bundleOfferNotFound = false;
            Integer numberOfBundlesInCart = Integer.MAX_VALUE;
            for (ProductQuantity productQuantity : offer.bundleProducts) {
                boolean bundleItemFound = false;
                Product productBundle = productQuantity.getProduct();
                double quantityBundle = productQuantity.getQuantity();
                double quantity = 0;
                for (ProductQuantity product : shoppingCartItems) {
                    if (product.getProduct().equals(productBundle)) {
                        quantity += product.getQuantity();
                    }
                }
                if (quantity >= quantityBundle) {
                    bundleItemFound = true;
                }
                numberOfBundlesInCart = bundleItemFound ? Math.min((int) (quantity / quantityBundle), numberOfBundlesInCart) : 0;
                if (!bundleItemFound) {
                    bundleOfferNotFound = true;
                    break;
                }
            }
            if (bundleOfferNotFound) {
                continue;
            }
            double discountValue = this.calculateDiscount(offer, catalog, numberOfBundlesInCart);
            Discount discount = new Discount(offer.bundleProducts, "Discounted Bundle", discountValue);
            discountList.add(discount);
        }
        return discountList;
    }

    private double calculateDiscount(Offer offer, SupermarketCatalog catalog, Integer numberOfBundles) {
        double price = 0;
        for (ProductQuantity productQuantity : offer.bundleProducts) {
            double unitPrice = catalog.getUnitPrice(productQuantity.getProduct());
            price += unitPrice * productQuantity.getQuantity();
        }
        return -(price * (offer.argument/100.0)) * numberOfBundles;
    }

}
