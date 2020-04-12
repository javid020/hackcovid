package ismayil.hakhverdiyev.jovid19.Domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String fullname;


    private String phoneNo;


    private String emaail;


    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<OrderPro> orderPros = new ArrayList<>();


    public Buyer(String fullname, String phoneNo, String emaail) {
        this.fullname = fullname;
        this.phoneNo = phoneNo;
        this.emaail = emaail;
    }

    public Buyer() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmaail() {
        return emaail;
    }

    public void setEmaail(String emaail) {
        this.emaail = emaail;
    }
}
