package dojo.supermarket;

import dojo.supermarket.model.*;

import java.util.List;
import java.util.Locale;

public class ReceiptPrinter {

    private final int columns;
    private final int defaultColumnsValue = 40;

    public ReceiptPrinter() {
        this.columns = defaultColumnsValue;
    }

    public ReceiptPrinter(int columns) {
        this.columns = columns;
    }

    public String printReceipt(Receipt receipt) {
        StringBuilder result = new StringBuilder();
        for (ReceiptItem item : receipt.getItems()) {
            String receiptItem = presentReceiptItem(item);
            result.append(receiptItem);
        }
        for (Discount discount : receipt.getDiscounts()) {
            String discountPresentation = presentDiscount(discount);
            result.append(discountPresentation);
        }

        result.append("\n");
        result.append(presentTotal(receipt));
        return result.toString();
    }

    private String presentReceiptItem(ReceiptItem item) {
        String totalPricePresentation = presentPrice(item.getTotalPrice());
        String name = item.getProduct().getName();

        String line = formatLineWithWhitespace(name, totalPricePresentation);

        if (item.getQuantity() != 1) {
            line += "  " + presentPrice(item.getPrice()) + " * " + presentQuantity(item) + "\n";
        }
        return line;
    }

    private String presentDiscount(Discount discount) {
        String name;
        String value;
        if (discount.getProduct() != null) {
            name = discount.getDescription() + "(" + discount.getProduct().getName() + ")";
            value = presentPrice(discount.getDiscountAmount());
        }
        else {
            List<ProductQuantity> discountBundle = discount.getProductList();
            String productNames = "";
            for (ProductQuantity productQuantity : discountBundle){
                productNames += productQuantity.getProduct().getName() + ":" + productQuantity.getQuantity() + ", ";
            }
            productNames = productNames.substring(0, productNames.length() - 3);
            name = discount.getDescription() + "(" + productNames + ") ";
            value = presentPrice(discount.getDiscountAmount());
        }

        return formatLineWithWhitespace(name, value);
    }

    private String presentTotal(Receipt receipt) {
        String name = "Total: ";
        String value = presentPrice(receipt.getTotalPrice());
        return formatLineWithWhitespace(name, value);
    }

    private String formatLineWithWhitespace(String name, String value) {
        StringBuilder line = new StringBuilder();
        line.append(name);
        int whitespaceSize = this.columns - name.length() - value.length();
        for (int i = 0; i < whitespaceSize; i++) {
            line.append(" ");
        }
        line.append(value);
        line.append('\n');
        return line.toString();
    }

    private static String presentPrice(double price) {
        return String.format(Locale.UK, "%.2f", price);
    }

    private static String presentQuantity(ReceiptItem item) {
        return ProductUnit.Each.equals(item.getProduct().getUnit())
                ? String.format("%x", (int)item.getQuantity())
                : String.format(Locale.UK, "%.3f", item.getQuantity());
    }

}
