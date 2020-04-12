package ismayil.hakhverdiyev.jovid19.Repository;

import ismayil.hakhverdiyev.jovid19.Domain.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {


//    @Query(value = "select u from Customer u where u.category.id= :categoryID")
//    List<Customer> findByCategoryId(String categoryID);


    @Query("SELECT c FROM Customer c where c.login = :login and c.password=:pass")
    Optional<Customer> findByCredentials(@Param("login") String login, @Param("pass") String pass);


}
