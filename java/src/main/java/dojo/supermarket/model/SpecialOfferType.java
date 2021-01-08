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

    public String getDescription(double argument) {
        switch (this){
            case ThreeForTwo:
                return "3 for 2";
            case TwoForAmount:
                return "2 for " + argument;
            case FiveForAmount:
                return "5 for " + argument;
            case TenPercentDiscount:
                return "10.0% off";
            default:
                return "";
        }
    }

    public double getDiscountPercent() {
        if (this == SpecialOfferType.TenPercentDiscount)
            return 10.0 / 100.0;
        return 0;
    }

    public boolean haveMinimumRequiredAmount(double quantity) {
        if (this == SpecialOfferType.TenPercentDiscount)
            return true;
        return  (int)quantity >= getDiscountUnit();
    }

}
