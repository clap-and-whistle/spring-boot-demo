package page.clapandwhistle.demo.spring.bizlogic.ec.UseCase.Adm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import page.clapandwhistle.demo.spring.bizlogic.ec.Aggregate.ItemBasicInfo;
import page.clapandwhistle.demo.spring.infrastructure.ec.AggregateRepository.ItemBasicInfo.ItemBasicInfoAggregateRepository;

@Component
final public class UpdateItemBasicInfoUseCase {
    private final ItemBasicInfoAggregateRepository itemBasicInfoRepos;

    @Autowired
    public UpdateItemBasicInfoUseCase(ItemBasicInfoAggregateRepository itemBasicInfoRepos) {
        this.itemBasicInfoRepos = itemBasicInfoRepos;
    }

    public void execute(
            long id,
            String name,
            float price,
            String vendor
    ) {
        try {
            this.itemBasicInfoRepos.save(new ItemBasicInfo(
                    id,
                    name,
                    price,
                    vendor
            ));
        } catch (Exception e) {
            System.out.println("UpdateItemBasicInfoUseCase::execute: " + e.getClass() + ": message: " + e.getMessage());
        }
    }
}
