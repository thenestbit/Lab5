package CollectionObject;

public enum StandardOfLiving {
    HIGH("Высокий"),
    LOW("Низкий"),
    NIGHTMARE("Ужасный");

    private final String standard;
    StandardOfLiving(String standard) {
        this.standard = standard;
    }

    public String getType() {
        return standard;
    }
}