public enum VideoType {
    VHS(0),
    CD(1),
    DVD(2);

    private final int intValue;

    private VideoType(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public static VideoType valueOfInt(int intValue) {
        for (VideoType videoType : values()) {
            if (videoType.getIntValue() == intValue) {
                return videoType;
            }
        }
        throw new IllegalArgumentException("Invalid integer value for VideoType");
    }
}
