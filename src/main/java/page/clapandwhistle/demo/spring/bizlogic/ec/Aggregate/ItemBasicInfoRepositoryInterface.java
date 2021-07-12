package page.clapandwhistle.demo.spring.bizlogic.ec.Aggregate;

import page.clapandwhistle.demo.spring.infrastructure.ec.AggregateRepository.Exception.RegistrationProcessFailedException;

public interface ItemBasicInfoRepositoryInterface {
    ItemBasicInfo find(long id);
    void save(ItemBasicInfo itemBasicInfo) throws RegistrationProcessFailedException;
}
