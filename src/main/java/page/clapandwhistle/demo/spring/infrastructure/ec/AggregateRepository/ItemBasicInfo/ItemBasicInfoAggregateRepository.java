package page.clapandwhistle.demo.spring.infrastructure.ec.AggregateRepository.ItemBasicInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import page.clapandwhistle.demo.spring.bizlogic.ec.Aggregate.ItemBasicInfo;
import page.clapandwhistle.demo.spring.bizlogic.ec.Aggregate.ItemBasicInfoRepositoryInterface;
import page.clapandwhistle.demo.spring.infrastructure.ec.AggregateRepository.Exception.RegistrationProcessFailedException;
import page.clapandwhistle.demo.spring.infrastructure.ec.TableModel.ItemMaster;
import page.clapandwhistle.demo.spring.infrastructure.ec.TableModel.ItemMasterRepository;

import java.util.Optional;

@Component
public class ItemBasicInfoAggregateRepository implements ItemBasicInfoRepositoryInterface {
    final private ItemMasterRepository dbItemMasterRepository;

    @Autowired
    public ItemBasicInfoAggregateRepository(ItemMasterRepository dbItemMasterRepository) {
        this.dbItemMasterRepository = dbItemMasterRepository;
    }

    @Override
    public ItemBasicInfo find(long id) {
        Optional<ItemMaster> dbItem = dbItemMasterRepository.findById(id);

        return dbItem.isEmpty()
                ? null
                : new ItemBasicInfo(
                    dbItem.get().getId(),
                    dbItem.get().getName(),
                    dbItem.get().getPrice(),
                    dbItem.get().getVendor()
                );
    }

    @Override
    public void save(ItemBasicInfo itemBasicInfo) throws RegistrationProcessFailedException {
        try {
            if (itemBasicInfo.id() != null && itemBasicInfo.id() > 0) {
                this.update(itemBasicInfo);
            } else {
                this.create(itemBasicInfo);
            }
        } catch (RuntimeException e) {
            System.out.println("ItemBasicInfoAggregateRepository: save(): message: " + e.getMessage());
            throw new RegistrationProcessFailedException();
        }
    }

    private void create(ItemBasicInfo itemBasicInfo) {
        System.out.println("ItemBasicInfoAggregateRepository: create()");
        ItemMaster itemMasterEntity = new ItemMaster();
        itemMasterEntity.setName(itemBasicInfo.name());
        itemMasterEntity.setPrice(itemBasicInfo.price());
        itemMasterEntity.setVendor(itemBasicInfo.vendor());
        dbItemMasterRepository.save(itemMasterEntity);
    }

    private void update(ItemBasicInfo itemBasicInfo) throws RuntimeException {
        System.out.println("ItemBasicInfoAggregateRepository: update()");
        Optional<ItemMaster> opt = dbItemMasterRepository.findById(itemBasicInfo.id());
        if (opt.isEmpty()) {
            throw new RuntimeException("対象データが見つかりませんでした");
        }
        ItemMaster itemMasterEntity = opt.get();
        itemMasterEntity.setName(itemBasicInfo.name());
        itemMasterEntity.setPrice(itemBasicInfo.price());
        itemMasterEntity.setVendor(itemBasicInfo.vendor());
        dbItemMasterRepository.save(itemMasterEntity);
    }
}
