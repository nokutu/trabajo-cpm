package homework.models;

/**
 * Represents the extra options a cruise can offer.
 */
public class Extra {

    private String code;
    private String name;
    private int price;

    public Extra(String code, String name, int price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    @Override
    public int hashCode() {
        return this.code.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Extra) {
            return code.equals(((Extra) obj).code);
        }
        return false;
    }
}
