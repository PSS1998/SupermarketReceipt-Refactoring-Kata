package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecialOfferBundles {

    public List<Discount> getDiscount(List<ProductQuantity> shoppingCartItems, Map<List<ProductQuantity>, Offer> offers, SupermarketCatalog catalog) {
        List<Discount> discountList = new ArrayList<Discount>();
        for (Offer offer : offers.values()) {
            Integer numberOfBundlesInCart = calculateNumberOfBundlesInCart(shoppingCartItems, offer);
            if (numberOfBundlesInCart != 0) {
                double discountValue = this.calculateDiscount(offer, catalog, numberOfBundlesInCart);
                Discount discount = new Discount(offer.bundleProducts, "Discounted Bundle", discountValue);
                discountList.add(discount);
            }
        }
        return discountList;
    }

    private Integer calculateNumberOfBundlesInCart(List<ProductQuantity> shoppingCartItems, Offer offer) {
        Integer numberOfBundlesInCart = Integer.MAX_VALUE;
        for (ProductQuantity productQuantity : offer.bundleProducts) {
            double quantity = 0;
            for (ProductQuantity product : shoppingCartItems) {
                if (product.getProduct().equals(productQuantity.getProduct())) {
                    quantity += product.getQuantity();
                }
            }
            numberOfBundlesInCart = (quantity >= productQuantity.getQuantity()) ? Math.min((int) (quantity / productQuantity.getQuantity()), numberOfBundlesInCart) : 0;
            if (numberOfBundlesInCart == 0) {
                break;
            }
        }
        return numberOfBundlesInCart;
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
