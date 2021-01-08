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

    public String getDescription() {
        switch (this){
            case ThreeForTwo:
                return "3 for 2";
            case TwoForAmount:
                return "2 for ";
            case FiveForAmount:
                return "5 for ";
            case TenPercentDiscount:
                return "10.0 off";
            default:
                return "";
        }
    }

    public double getDiscountPercent() {
        if (this == SpecialOfferType.TenPercentDiscount)
            return 10.0;
        return 0;
    }

    public boolean haveMinimumRequiredAmount(double quantity) {
        return  (int)quantity >= getDiscountUnit();
    }

}
