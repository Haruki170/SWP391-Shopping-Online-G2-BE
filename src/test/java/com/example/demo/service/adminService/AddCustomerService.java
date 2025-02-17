package com.example.demo.service.adminService;

import com.example.demo.entity.Customer;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class AddCustomerService {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    //success
    //email : bi trung , dung dinh dang , khong rong
    //password: khong rong , dung dinh dang
    void addCustomer_Exist() {
        Customer c = new Customer();
        c.setId(1);
        c.setEmail("hieu123");
        // khi nao goi ham check exist thi no tra ra 1
        // ket qua mong muon
        Mockito.when(customerRepository.checkExist(c.getEmail())).thenReturn(1);
        AppException exception = Assertions.assertThrows(AppException.class, () -> {
            customerService.addCustomer(c);
        }) ;
        //ket qua test mong doi
        Assertions.assertEquals(exception.getErrorCode(), ErrorCode.USER_EXIST);
    }

    @Test
    void addCustomer_Empty(){
        Customer c = new Customer();
        c.setId(1);
        c.setEmail("");
        Mockito.when(customerRepository.checkExist(c.getEmail())).thenReturn(0);
        AppException exception = Assertions.assertThrows(AppException.class, () -> {
            customerService.addCustomer(c);
        });
        Assertions.assertEquals(exception.getErrorCode(), ErrorCode.USER_EMAIL_EMPTY);
    }







}
