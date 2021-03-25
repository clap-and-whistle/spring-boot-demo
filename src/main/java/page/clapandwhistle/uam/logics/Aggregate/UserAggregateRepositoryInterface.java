package page.clapandwhistle.uam.logics.Aggregate;

import page.clapandwhistle.uam.infrastructure.AggregateRepository.Exception.RegistrationProcessFailedException;
import page.clapandwhistle.uam.logics.Aggregate.User.User;

public interface UserAggregateRepositoryInterface {
    public long isExist(String email);

    public boolean isApplying(long userId);

    public long save(User user) throws RegistrationProcessFailedException;

    public User findById(long id);
    
    public long getUserIdAllowedToLogIn(String email, String password);
}
