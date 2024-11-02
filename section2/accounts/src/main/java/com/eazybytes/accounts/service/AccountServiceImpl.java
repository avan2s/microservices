package com.eazybytes.accounts.service;

import com.eazybytes.accounts.controller.AccountConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Account;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

/**
 * Service implementation for account-related operations.
 */
@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * Creates a new account for the given customer.
     *
     * @param customerDto the customer details
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> existingCustomer = this.customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with the given mobile number" + customerDto.getMobileNumber());
        }
        Customer savedCustomer = this.customerRepository.save(customer);
        Account account = createNewAccount(savedCustomer);
        this.accountsRepository.save(account);
    }

    private Account createNewAccount(Customer customer) {
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        return Account.builder()
                .customerId(customer.getCustomerId())
                .accountNumber(randomAccNumber)
                .accountType(AccountConstants.SAVINGS)
                .branchAddress(AccountConstants.ADDRESS)
                .build();
    }
}
