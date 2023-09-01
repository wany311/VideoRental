public enum PriceCode {
    REGULAR(0),
    NEW_RELEASE(1);

    private final int intValue;

    private PriceCode(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public static PriceCode valueOfInt(int intValue) {
        for (PriceCode priceCode : values()) {
            if (priceCode.getIntValue() == intValue) {
                return priceCode;
            }
        }
        throw new IllegalArgumentException("Invalid integer value for PriceCode");
    }
}