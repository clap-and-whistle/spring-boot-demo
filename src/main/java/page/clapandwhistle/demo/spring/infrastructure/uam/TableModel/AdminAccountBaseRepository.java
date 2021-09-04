package page.clapandwhistle.demo.spring.infrastructure.uam.TableModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminAccountBaseRepository extends JpaRepository<AdminAccountBase, Long> {
    List<AdminAccountBase> findByEmail(String email);
    Optional<AdminAccountBase> findTopByOrderByIdDesc();
}
