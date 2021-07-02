package page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.User.User;
import page.clapandwhistle.demo.spring.infrastructure.uam.AggregateRepository.Exception.RegistrationProcessFailedException;

public interface UserAggregateRepositoryInterface {
    public long getUserIdByEmail(String email);

    public boolean isApplying(long userId);

    public long save(User user) throws RegistrationProcessFailedException;

    public User findById(long id);
    
    public long getUserIdAllowedToLogIn(String email, String password);
}
