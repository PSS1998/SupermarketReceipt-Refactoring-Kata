package dojo.supermarket.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teller {

    private final SupermarketCatalog catalog;
    private Map<Product, Offer> offers = new HashMap<>();
    private Map<List<ProductQuantity>, Offer> bundleOffers = new HashMap<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        this.offers.put(product, new Offer(offerType, product, argument));
    }

    public void addSpecialBundleOffer(SpecialOfferType offerType, List<ProductQuantity> products, double argument) {
        this.bundleOffers.put(products, new Offer(offerType, products, argument));
    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();

        theCart.getItems().forEach(pq -> receipt.addProduct(pq,
                this.catalog.getUnitPrice(pq.getProduct())));
        theCart.handleOffers(receipt, this.offers, this.bundleOffers, this.catalog);

        return receipt;
    }

}
