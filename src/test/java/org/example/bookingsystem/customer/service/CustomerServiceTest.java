package org.example.bookingsystem.customer.service;

import org.example.bookingsystem.customer.model.Customer;
import org.example.bookingsystem.customer.model.dto.CreateCustomerRequest;
import org.example.bookingsystem.customer.model.dto.CustomerUpdateRequest;
import org.example.bookingsystem.customer.repository.CustomerRepository;
import org.example.bookingsystem.exceptionhandler.customexeptions.AlreadyExistException;
import org.example.bookingsystem.exceptionhandler.customexeptions.HaveReservationException;
import org.example.bookingsystem.exceptionhandler.customexeptions.WrongEmailOrPasswordException;
import org.example.bookingsystem.reservation.model.Reservation;
import org.example.bookingsystem.reservation.service.ReservationService;
import org.example.bookingsystem.security.password.PasswordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PasswordService passwordService;
    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createNewCustomerSuccessfully() {
        CreateCustomerRequest request = new CreateCustomerRequest(
                "Mia",
                "Andersson",
                "901121-4455",
                "test@mail.com",
                "password",
                "070-8761234"
        );

        when(customerRepository.existsByEmail("test@mail.com")).thenReturn(false);
        when(customerRepository.existsByIdentificationNumber("901121-4455")).thenReturn(false);
        when(customerRepository.existsByPhoneNumber("070-8761234")).thenReturn(false);
        when(passwordService.hash("password")).thenReturn("hashed");
        when(customerRepository.save(any(Customer.class))).thenAnswer(i -> i.getArgument(0));

        Customer result = customerService.createNewCustomer(request);

        assertEquals("Mia", result.getFirstname());
        assertEquals("hashed", result.getPassword());
    }

    @Test
    void createNewCustomer_emailExists_throwsException() {
        CreateCustomerRequest request = new CreateCustomerRequest(
                "John", "Andersen", "920501-7385",
                "test@mail.com", "password", "070-8776723"
        );

        when(customerRepository.existsByEmail("test@mail.com")).thenReturn(true);

        assertThrows(AlreadyExistException.class,
                () -> customerService.createNewCustomer(request));
    }

    @Test
    void loginCustomerSuccessfully() {
        Customer customer = new Customer();
        customer.setEmail("test@mail.com");
        customer.setPassword("hashed");

        when(customerRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(customer));

        when(passwordService.matches("password", "hashed"))
                .thenReturn(true);

        Customer result = customerService.loginCustomer("test@mail.com", "password");

        assertEquals("test@mail.com", result.getEmail());
    }

    @Test
    void loginCustomer_wrongPassword_throwsException() {
        Customer customer = new Customer();
        customer.setPassword("hashed");

        when(customerRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(customer));

        when(passwordService.matches("wrong", "hashed"))
                .thenReturn(false);

        assertThrows(WrongEmailOrPasswordException.class,
                () -> customerService.loginCustomer("test@mail.com", "wrong"));
    }


    @Test
    void updateCustomerInfo_updatesEmail() {
        Customer customer = new Customer();
//        customer.setId(1L);

        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "new@mail.com", null, null
        );

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.existsByEmail("new@mail.com")).thenReturn(false);

        customerService.updateCustomerInfo(1L, request);

        assertEquals("new@mail.com", customer.getEmail());
    }

    @Test
    void deleteCustomer_hasReservation_throwsException() {
        when(reservationService.getActiveReservationByCustomerId(1L))
                .thenReturn(List.of(new Reservation()));

        assertThrows(HaveReservationException.class,
                () -> customerService.deleteCustomer(1L));
    }

    @Test
    void deleteCustomer_success() {
        when(reservationService.getActiveReservationByCustomerId(1L))
                .thenReturn(Collections.emptyList());

        customerService.deleteCustomer(1L);

        verify(customerRepository).deleteById(1L);
    }
}