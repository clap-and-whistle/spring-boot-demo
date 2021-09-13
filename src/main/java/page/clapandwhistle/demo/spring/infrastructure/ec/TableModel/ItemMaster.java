package page.clapandwhistle.demo.spring.infrastructure.ec.TableModel;

import page.clapandwhistle.demo.spring.bizlogic.ec.Aggregate.ItemBasicInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
final public class ItemMaster {
    private @Id @GeneratedValue Long id;

    @NotBlank(message="商品名を入力してください。")
    private String name;

    @Min(value= ItemBasicInfo.PRICE_MIN, message=ItemBasicInfo.PRICE_MIN + "以上の数値を入力してください。")
    @Max(value=ItemBasicInfo.PRICE_MAX, message=ItemBasicInfo.PRICE_MAX + "以下の数値を入力してください。")
    private float price;

    @Size(max=50, message="ベンダー名は50文字を超えないでください。")
    private String vendor;

    public ItemMaster() {}

    public ItemMaster(String name, float price, String vendor) {
        this.name = name != null ? name : "";
        this.price = price;
        this.vendor = vendor != null ? vendor : "";
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + Float.floatToIntBits(price);
        result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ItemMaster other = (ItemMaster) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
            return false;
        if (vendor == null) {
            if (other.vendor != null)
                return false;
        } else if (!vendor.equals(other.vendor))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", name=" + name + ", price=" + price + ", vendor=" + vendor + "]";
    }

}
