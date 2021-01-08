package dojo.supermarket.model;

public enum SpecialOfferType {
    ThreeForTwo, TenPercentDiscount, TwoForAmount, FiveForAmount;

    public int getDiscountUnit() {
        switch (this){
            case ThreeForTwo:
                return 3;
            case TwoForAmount:
                return 2;
            case FiveForAmount:
                return 5;
            default:
                return 1;
        }
    }

}
