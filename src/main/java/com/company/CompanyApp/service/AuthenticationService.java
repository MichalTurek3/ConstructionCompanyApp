package com.company.CompanyApp.service;

import com.company.CompanyApp.command.AdminCommand;
import com.company.CompanyApp.command.CustomerCommand;
import com.company.CompanyApp.config.security.JwtService;
import com.company.CompanyApp.domain.Admin;
import com.company.CompanyApp.domain.Customer;
import com.company.CompanyApp.dto.AdminDTO;
import com.company.CompanyApp.dto.CustomerDTO;
import com.company.CompanyApp.exception.invalid.InvalidCredentialsException;
import com.company.CompanyApp.exception.notFound.AdminNotFoundException;
import com.company.CompanyApp.exception.notFound.CustomerNotFoundException;
import com.company.CompanyApp.mapper.AdminMapper;
import com.company.CompanyApp.mapper.CustomerMapper;
import com.company.CompanyApp.model.AuthenticationRequest;
import com.company.CompanyApp.model.AuthenticationResponse;
import com.company.CompanyApp.model.Role;
import com.company.CompanyApp.repository.AdminRepository;
import com.company.CompanyApp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerRepository customerRepository;

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final AdminMapper adminMapper;

    private final CustomerMapper customerMapper;

    private static final Logger logger = LogManager.getLogger(AuthenticationService.class);

    public CustomerDTO registerCustomer(CustomerCommand request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setUsername(request.getUsername());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setRole(Role.USER);
        customer.setAccountEnabled(true);
        customer.setAccountNonExpired(true);
        customer.setAccountNonLocked(true);
        customer.setCredentialsNonExpired(true);


        return customerMapper.customerToCustomerDTO(customerRepository.save(customer));
    }

    public AuthenticationResponse authenticateCustomer(AuthenticationRequest request) {
        Customer customer = customerRepository.findCustomerByUsername(request.getUsername())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return new AuthenticationResponse(jwtService.generateToken(customer));
    }

    public AdminDTO registerAdmin(AdminCommand request) {
        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        return adminMapper.adminToAdminDTO(adminRepository.save(admin));
    }


    public AuthenticationResponse authenticateAdmin(AuthenticationRequest request) {
        Admin admin = adminRepository.findAdminByUsername(request.getUsername())
                .orElseThrow(() -> new AdminNotFoundException("Admin not found"));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return new AuthenticationResponse(jwtService.generateToken(admin));
    }
}