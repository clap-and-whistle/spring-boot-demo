package page.clapandwhistle.demo.spring.bizlogic.ec.Aggregate;

final public class ItemBasicInfo {
    public static final int PRICE_MIN = 50;
    public static final int PRICE_MAX = 1000000;

    private final Long id;
    private final String name;
    private final float price;
    private final String vendor;

    public ItemBasicInfo(Long id, String name, float price, String vendor) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.vendor = vendor;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public float price() {
        return price;
    }

    public String vendor() {
        return vendor;
    }

}
