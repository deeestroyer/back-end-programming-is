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
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        try {
            //retrieve cart
            Cart cart = purchase.getCart();

            //generate tracking
            String orderTrackingNumber = generateOrdertrackingNumber();
            cart.setOrderTrackingNumber(orderTrackingNumber);

            //populate cart with cartItems
            Set<CartItem> cartItems = purchase.getCartItems();
            if (cartItems.isEmpty()) {
                throw new IllegalArgumentException("Nothing was added to the cart. Please try again.");
            }
            cartItems.forEach(item -> cart.add(item));

            //populate customer
            Customer customer = purchase.getCustomer();
            if (customer == null) {
                throw new IllegalArgumentException(" Order does not have a customer. ");
            }
            customer.add(cart);

            //set order status
            cart.setStatus(StatusType.ordered);

            //save to db
            customerRepository.save(customer);

            //return tracking #
            return new PurchaseResponse(orderTrackingNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return new PurchaseResponse(" Error: " + e.getMessage());
        }
    }

    private String generateOrdertrackingNumber() {
        // generate a tracking number using uuid
        return UUID.randomUUID().toString();
    }
}
