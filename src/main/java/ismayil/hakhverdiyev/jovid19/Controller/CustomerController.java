package ismayil.hakhverdiyev.jovid19.Controller;


import ismayil.hakhverdiyev.jovid19.Domain.*;
import ismayil.hakhverdiyev.jovid19.Repository.*;
import ismayil.hakhverdiyev.jovid19.config.SMSSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.LogManager;

@Controller
public class CustomerController {

    private CustomerRepository customerRepository;
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private BuyerRepository buyerRepository;

    public CustomerController(CustomerRepository customerRepository, CategoryRepository categoryRepository, ProductRepository productRepository, OrderRepository orderRepository, BuyerRepository buyerRepository) {
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.buyerRepository = buyerRepository;
    }


    @GetMapping(value = "/")
    public String listOfproducts(Model model) {
        Buyer buyer = new Buyer();

        model.addAttribute("buyer", buyer);

        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("category", categoryRepository.findAll());

        return "products";


    }

    @GetMapping(value = "/products/{id}")
    public String listOfproductsCategory(@PathVariable String id, Model model) {
        Category category = categoryRepository.findById(Long.parseLong(id)).get();
        Buyer buyer = new Buyer();
        model.addAttribute("products", productRepository.findByCategory(category.getValue()));
        model.addAttribute("category", categoryRepository.findAll());

        model.addAttribute("buyer", buyer);

        return "products";


    }


    @GetMapping(value = "/customer/{id}/order")
    public String orderList(@PathVariable String id, Model model) {
        Customer customer = customerRepository.findById(Long.parseLong(id)).get();
        model.addAttribute("customer", customer);
        model.addAttribute("productOrder", orderRepository.findByCustomerId(Long.parseLong(id)));
        return "orders";


    }

    @GetMapping(value = "/customer/{id}/order/{oid}/done")
    public String orderDone(@PathVariable String oid, @PathVariable String id, Model model) {

        OrderPro orderPro = orderRepository.findById(Long.parseLong(oid)).get();
        orderPro.setStatus(Status.Done);

        orderRepository.save(orderPro);

        return "redirect:/customer/" + id + "/order";


    }


    @PostMapping(value = "/customer/register")
    public String loginCustomer(@Valid @ModelAttribute Customer customerr, HttpSession httpSession) {

        httpSession.setAttribute("customer", customerr);
        customerRepository.save(customerr);


        return "/login";


    }

    @PostMapping(value = "/customer/login")
    public String loginCustomer(@Valid @ModelAttribute Customer customerr, HttpSession httpSession, Model model) {

        return "dashboard";
    }

    @RequestMapping(value = "/login")
    public String login() {


        return "login";
    }


    @RequestMapping(value = "/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();

        return "redirect:/login";
    }

    @RequestMapping(value = "/customer/{id}/products")
    public String products(@PathVariable(name = "id", required = true) String id, Model model, HttpSession session) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("category", categoryRepository.findAll());
        model.addAttribute("products", productRepository.findByCustomerId(Long.parseLong(id)));
        model.addAttribute("customer", customerRepository.findById(Long.parseLong(id)).get());


        return "myservices";
    }

    @RequestMapping(value = "/customer/{cid}/products/{id}/remove")
    public String removeProducts(@PathVariable(name = "id", required = true) String id, @PathVariable(name = "cid", required = true) String cid, Model model) {
        Optional<Product> crs = productRepository.findById(Long.parseLong(id));
        productRepository.delete(crs.get());


        return "redirect:/customer/" + cid + "/products";
    }


    @RequestMapping(value = "/customer/products/{id}/edit")
    public String editProducts(@PathVariable(name = "id", required = true) String id, Model model) {
        Optional<Product> crs = productRepository.findById(Long.parseLong(id));
        model.addAttribute("product", crs.get());
        model.addAttribute("customer", customerRepository.findById(Long.parseLong(id)).get());


        return "myservices";
    }


    @PostMapping("/customer/products/{id}/save")
    public String saveCourse(@PathVariable(name = "id", required = true) String id, @Valid @ModelAttribute Product product) {

        product.setPicture("https://images.unsplash.com/photo-1517837317028-6ce34fd27c34?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80");
        product.setCustomer(customerRepository.findById(Long.parseLong(id)).get());
        productRepository.save(product);
        return "redirect:/customer/" + id + "/products";
    }


    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public String buy(@RequestParam("proid") String id, @Valid @ModelAttribute Buyer buyer) {
        buyerRepository.save(buyer);
        System.out.println(id);
        System.out.println(buyer.getFullname());
        System.out.println(buyer.getEmaail());
        int code = codeSMS();
        SMSSender.smsSender(buyer.getPhoneNo(), code);
        System.out.println("1");
        Product a = productRepository.findById(Long.parseLong(id)).get();
        System.out.println("2");

        OrderPro orderPro = new OrderPro();
        System.out.println("3");
        orderPro.setStatus(Status.New);
        orderPro.setCode(String.valueOf(code));
        System.out.println("4");

        orderPro.setBuyer(buyer);
        System.out.println("5");

        orderPro.setProduct(a);
        System.out.println("6");

        orderRepository.save(orderPro);
        System.out.println("7");


        return "redirect:/";
    }


    public static int codeSMS() {
        Random sc = new Random();
        return (sc.nextInt(89999) + 10000);
    }


    @PostMapping("/login")
    public String doLogin(@ModelAttribute("customer") Customer customerr, Model model, HttpSession session) {
        Customer customer = customerRepository.findByCredentials(customerr.getLogin(), customerr.getPassword()).get();
        if (customer == null) {
            model.addAttribute("error", true);
            model.addAttribute("errorinfo", "User not found");
            return "login";
        }
        session.setAttribute("admin", customer);
        model.addAttribute("admin", customer);

        return "redirect:dashboard";


    }

    @RequestMapping(value = "/register")
    public String newCustomer(Model model, HttpSession session) {

        Customer crs = new Customer();
        model.addAttribute("customer", crs);


        return "register";

    }


    @PostMapping("/regis")
    public String saveCourse(@Valid @ModelAttribute Customer customer, Model model, HttpSession session) {

        customerRepository.save(customer);
        model.addAttribute("customer", customer);
        session.setAttribute("customerr", customer);

        return "redirect:/customer/" + customer.getId();

    }


    @GetMapping("/customer/{id}")
    public String dashboard(@PathVariable(name = "id", required = true) String id, Model model) {


        model.addAttribute("customer", customerRepository.findById(Long.parseLong(id)).get());

        return "dashboard";

    }


}




