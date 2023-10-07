package com.example.demo.Bootstrap;

import com.example.demo.dao.CountryRepository;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Country;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;
    private final DivisionRepository divisionRepository;

    public BootStrapData(CustomerRepository customerRepository, CountryRepository countryRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.countryRepository = countryRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Division divRandy = new Division(4L);
        Customer randy = new Customer("Randy", "Moss", "123 W Way", "33900", "(916)345-6523", divRandy);
        divRandy.getCustomers().add(randy); //add customer to division
        customerRepository.save(randy);

        Division divBrock = new Division(15L);
        Customer brock = new Customer("Brock", "Purdy", "123 SB Lane", "33902", "(916)345-6543", divBrock);
        divRandy.getCustomers().add(brock); //add customer to division
        customerRepository.save(brock);

        Division divShilo = new Division(27L);
        Customer shilo = new Customer("Shilo", "Sanders", "123 OG Street", "33906", "(916)345-6543", divShilo);
        divRandy.getCustomers().add(shilo); //add customer to division
        customerRepository.save(shilo);

        Division divJoel = new Division(9L);
        Customer joel = new Customer("Joel", "Embeed", "123 NBA Court", "33907", "(916)345-9843", divJoel);
        divRandy.getCustomers().add(joel); //add customer to division
        customerRepository.save(joel);

        Division divFrank = new Division(19L);
        Customer frank = new Customer("Frank", "Clark", "123 HW Blvd", "33982", "(916)345-0000", divFrank);
        divRandy.getCustomers().add(frank); //add customer to division
        customerRepository.save(frank);

    }

}
