package page.clapandwhistle.demo.spring.bizlogic.ec.Aggregate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

final public class ItemBasicInfo {
    public static final int PRICE_MIN = 50;
    public static final int PRICE_MAX = 1000000;

    private final Long id;

    @NotBlank(message="商品名を入力してください。")
    private final String name;

    @Min(value=PRICE_MIN, message=PRICE_MIN + "以上の数値を入力してください。")
    @Max(value=PRICE_MAX, message=PRICE_MAX + "以下の数値を入力してください。")
    private final float price;

    @Size(max=50, message="ベンダー名は50文字を超えないでください。")
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
