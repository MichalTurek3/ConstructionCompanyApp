package com.company.CompanyApp.service;

import com.company.CompanyApp.domain.Admin;
import com.company.CompanyApp.domain.Customer;
import com.company.CompanyApp.exception.notAuthenticated.UserNotAuthenticatedException;
import com.company.CompanyApp.exception.notFound.UserNotFoundException;
import com.company.CompanyApp.exception.invalid.InvalidCredentialsException;
import com.company.CompanyApp.repository.AdminRepository;
import com.company.CompanyApp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.company.CompanyApp.util.Constants.CUSTOMER_NOT_AUTHORIZED_ERROR_MESSAGE;
import static com.company.CompanyApp.util.Constants.USER_NOT_FOUND_ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    private final AdminRepository adminRepository;

    public void verifyCustomerAccess() {
        verifyCustomerAccount(customerRepository.findCustomerByUsername(getCurrentUsername())
                .orElseThrow(() -> new AccessDeniedException(CUSTOMER_NOT_AUTHORIZED_ERROR_MESSAGE)));
    }

    public Customer getCurrentCustomer() {
        return customerRepository.findCustomerByUsername(getCurrentUsername())
                .orElseThrow(() -> new AccessDeniedException(CUSTOMER_NOT_AUTHORIZED_ERROR_MESSAGE));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Customer> customerOptional = customerRepository.findCustomerByUsername(username);
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        }

        Optional<Admin> adminOptional = adminRepository.findAdminByUsername(username);
        if (adminOptional.isPresent()) {
            return adminOptional.get();
        }

        throw new UserNotFoundException(USER_NOT_FOUND_ERROR_MESSAGE);
    }

    private void verifyCustomerAccount(Customer customer) {
        if (!customer.isAccountEnabled()) {
            throw new InvalidCredentialsException("Account not enabled");
        }
        if (!customer.isAccountNonExpired()) {
            throw new InvalidCredentialsException("Account expired");
        }
        if (!customer.isAccountNonLocked()) {
            throw new InvalidCredentialsException("Account locked");
        }
        if (!customer.isCredentialsNonExpired()) {
            throw new InvalidCredentialsException("Credentials expired");
        }
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        throw new UserNotAuthenticatedException(CUSTOMER_NOT_AUTHORIZED_ERROR_MESSAGE);
    }
}
