package dojo.supermarket;

import dojo.supermarket.model.*;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class ReceiptPrinterTest {

    Product toothbrush = new Product("toothbrush", ProductUnit.Each);
    Product apples = new Product("apples", ProductUnit.Kilo);
    Receipt receipt = new Receipt();

    @Test
    public void oneLineItem() {
        ProductQuantity item = new ProductQuantity(toothbrush,1);
        receipt.addProduct(item, 0.99);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    public void quantityTwo() {
        ProductQuantity item = new ProductQuantity(toothbrush,2);
        receipt.addProduct(item, 0.99);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    public void looseWeight() {
        ProductQuantity item = new ProductQuantity(apples,2.3);
        receipt.addProduct(item, 1.99);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    public void total() {
        ProductQuantity toothbrush_item = new ProductQuantity(toothbrush,1);
        ProductQuantity apple_item = new ProductQuantity(apples,0.75);

        receipt.addProduct(toothbrush_item, 0.99);
        receipt.addProduct(apple_item, 1.99);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    public void discounts() {
        receipt.addDiscount(new Discount(apples, "3 for 2", -0.99));
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    public void printWholeReceipt() {
        ProductQuantity one_toothbrush_quantity = new ProductQuantity(toothbrush,1);
        ProductQuantity two_toothbrush_quantity = new ProductQuantity(toothbrush,2);
        ProductQuantity one_apple_quantity = new ProductQuantity(apples,0.75);

        receipt.addProduct(one_toothbrush_quantity, 0.99);
        receipt.addProduct(two_toothbrush_quantity, 0.99);
        receipt.addProduct(one_apple_quantity, 1.99);
        receipt.addDiscount(new Discount(toothbrush, "3 for 2", -0.99));
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

}