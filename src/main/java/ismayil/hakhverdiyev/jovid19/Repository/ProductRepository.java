package ismayil.hakhverdiyev.jovid19.Repository;

import ismayil.hakhverdiyev.jovid19.Domain.Customer;
import ismayil.hakhverdiyev.jovid19.Domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(value = "select u from Product u where u.customer.id= :customerID")
    List<Product> findByCustomerId(long customerID);

    @Query(value = "select u from Product u where u.category= :cate")
    List<Product> findByCategory(String cate);


}
