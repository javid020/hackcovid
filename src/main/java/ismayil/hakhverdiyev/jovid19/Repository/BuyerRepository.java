package ismayil.hakhverdiyev.jovid19.Repository;

import ismayil.hakhverdiyev.jovid19.Domain.Buyer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BuyerRepository extends CrudRepository<Buyer, Long> {
}
