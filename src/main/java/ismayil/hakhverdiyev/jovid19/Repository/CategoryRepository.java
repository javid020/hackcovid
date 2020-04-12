package ismayil.hakhverdiyev.jovid19.Repository;

import ismayil.hakhverdiyev.jovid19.Domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
