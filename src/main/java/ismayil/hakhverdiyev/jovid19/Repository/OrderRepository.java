package ismayil.hakhverdiyev.jovid19.Repository;

import ismayil.hakhverdiyev.jovid19.Domain.OrderPro;
import ismayil.hakhverdiyev.jovid19.Domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends CrudRepository<OrderPro, Long> {

    @Query(value = "select u from OrderPro u where u.product.customer.id= :customerID")
    List<OrderPro> findByCustomerId(long customerID);





}
