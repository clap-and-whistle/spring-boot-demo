package page.clapandwhistle.demo.spring.infrastructure.uam.TableModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountBaseRepository extends JpaRepository<UserAccountBase, Long> {
    List<UserAccountBase> findByEmail(String email);
    Optional<UserAccountBase> findTopByOrderByIdDesc();
}
