package com.example.demo.services;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Customer;
import com.example.demo.entities.StatusType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{
    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        System.out.println("here");
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        //retrieve cart
        System.out.println("here");
        Cart cart = purchase.getCart();

        //generate tracking
        String orderTrackingNumber = generateOrdertrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        System.out.println(orderTrackingNumber);

        //populate cart with cartItems
        Set<CartItem> cartItems = purchase.getCartItems();
        cartItems.forEach(item -> cart.add(item));

        //populate customer
        Customer customer = purchase.getCustomer();
        customer.add(cart);

        //save to db
        customerRepository.save(customer);

        //set order status
        cart.setStatus(StatusType.ordered);

        //save cart
        //cartRepository.save(cart);

        //return tracking #
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrdertrackingNumber() {
        // generate a tracking number using uuid
        System.out.println("orderTrackingNumber");

        return UUID.randomUUID().toString();
    }
}
