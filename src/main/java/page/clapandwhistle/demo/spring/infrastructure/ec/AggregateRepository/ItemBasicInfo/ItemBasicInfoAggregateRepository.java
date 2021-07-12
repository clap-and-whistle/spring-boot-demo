package page.clapandwhistle.demo.spring.infrastructure.ec.AggregateRepository.ItemBasicInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import page.clapandwhistle.demo.spring.bizlogic.ec.Aggregate.ItemBasicInfo;
import page.clapandwhistle.demo.spring.bizlogic.ec.Aggregate.ItemBasicInfoRepositoryInterface;
import page.clapandwhistle.demo.spring.infrastructure.ec.TableModel.ItemMaster;
import page.clapandwhistle.demo.spring.infrastructure.ec.TableModel.ItemMasterRepository;

@Component
public class ItemBasicInfoAggregateRepository implements ItemBasicInfoRepositoryInterface {
    final private ItemMasterRepository dbItemMasterRepository;

    @Autowired
    public ItemBasicInfoAggregateRepository(ItemMasterRepository dbItemMasterRepository) {
        this.dbItemMasterRepository = dbItemMasterRepository;
    }

    @Override
    public void save(ItemBasicInfo itemBasicInfo) {
        ItemMaster itemMasterEntity = new ItemMaster();
        itemMasterEntity.setName(itemBasicInfo.name());
        itemMasterEntity.setPrice(itemBasicInfo.price());
        itemMasterEntity.setVendor(itemBasicInfo.vendor());
        dbItemMasterRepository.save(itemMasterEntity);
    }
}
