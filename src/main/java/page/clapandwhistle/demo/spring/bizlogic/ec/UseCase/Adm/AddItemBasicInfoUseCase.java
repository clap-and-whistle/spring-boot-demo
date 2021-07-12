package page.clapandwhistle.demo.spring.bizlogic.ec.UseCase.Adm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import page.clapandwhistle.demo.spring.bizlogic.ec.Aggregate.ItemBasicInfo;
import page.clapandwhistle.demo.spring.infrastructure.ec.AggregateRepository.ItemBasicInfo.ItemBasicInfoAggregateRepository;

@Component
public class AddItemBasicInfoUseCase {
    private final ItemBasicInfoAggregateRepository itemBasicInfoRepos;

    @Autowired
    public AddItemBasicInfoUseCase(ItemBasicInfoAggregateRepository itemBasicInfoRepos) {
        this.itemBasicInfoRepos = itemBasicInfoRepos;
    }

    public void execute(
            String name,
            float price,
            String vendor
    ) {
        try {
            this.itemBasicInfoRepos.save(new ItemBasicInfo(
                    null,
                    name,
                    price,
                    vendor
                ));
        } catch (Exception e) {
            System.out.println("AddItemBasicInfoUseCase::execute: " + e.getClass() + ": message: " + e.getMessage());
        }
    }

}
