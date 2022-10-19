package com.rihaviour.Bank_API_REST;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rihaviour.Bank_API_REST.entities.DTOs.AccountHolderDTO;
import com.rihaviour.Bank_API_REST.entities.accounts.Account;
import com.rihaviour.Bank_API_REST.entities.accounts.Checking;
import com.rihaviour.Bank_API_REST.entities.accounts.Transaction;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
import com.rihaviour.Bank_API_REST.others.Address;
import com.rihaviour.Bank_API_REST.others.Money;
import com.rihaviour.Bank_API_REST.repositories.AccountHolderRepository;
import com.rihaviour.Bank_API_REST.repositories.AccountRepository;
import com.rihaviour.Bank_API_REST.repositories.CheckingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TransactionTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    AccountHolder accountHolder;
    AccountHolder accountHolder1;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Address testAddress = new Address("Diagonal", 1000, 8051,"Bcn","Esp");

        Address testMailingAddress = new Address("Diagonal", 1000, 8051,"Bcn","Esp");

        accountHolder = new AccountHolder("rihavior", "Ricardo", LocalDate.of(1990,4,26), testAddress,testMailingAddress);

        accountHolder1 = new AccountHolder("laurita", "Laura", LocalDate.of(1990,11,11), testAddress,testMailingAddress);

        accountHolderRepository.save(accountHolder);
        accountHolderRepository.save(accountHolder1);

        Checking checking = new Checking(new Money(new BigDecimal(1000)),accountHolder);

        Checking checking1 = new Checking(new Money(new BigDecimal(5000)),accountHolder1);

        checkingRepository.save(checking);
        checkingRepository.save(checking1);
    }

    @AfterEach
    public void tearDown(){
        checkingRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    @DisplayName("Transfer funds works")
    public void transferFunds_Works() throws Exception {
        Transaction transaction = new Transaction(new BigDecimal(500),"rihavior",1L,2L);

        String body = objectMapper.writeValueAsString(transaction);

        MvcResult mvcResult = mockMvc.perform(patch("/transfer_funds").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        assertTrue(accountRepository.findById(transaction.getOriginAccountId()).isPresent());
        assertTrue(accountRepository.findById(transaction.getDestinyAccountId()).isPresent());
        assertEquals(new BigDecimal("500.00"), accountRepository.findById(transaction.getOriginAccountId()).get().getBalance().getAmount());
        assertEquals(new BigDecimal("5500.00"), accountRepository.findById(transaction.getDestinyAccountId()).get().getBalance().getAmount());
    }

//    @Test
//    @DisplayName("Creates account holder.")
//    void createAccountHolder_Works() throws Exception {
//
//        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("testName","testUserName",1990,4,26,testAddress,testMailingAddress);
//
//        String body = objectMapper.writeValueAsString(accountHolderDTO);
//
//        System.out.println(body);
//
//        MvcResult mvcResult = mockMvc.perform(post("/create_account_holder").content(body).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated()).andReturn();
//
//        assertTrue(accountHolderRepository.findByUserName(accountHolderDTO.getUserName()).isPresent());
//    }
}
