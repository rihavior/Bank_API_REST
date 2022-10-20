package com.rihaviour.Bank_API_REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rihaviour.Bank_API_REST.entities.DTOs.AccountDTO;
import com.rihaviour.Bank_API_REST.entities.accounts.Checking;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
import com.rihaviour.Bank_API_REST.others.Address;
import com.rihaviour.Bank_API_REST.others.Money;
import com.rihaviour.Bank_API_REST.repositories.AccountHolderRepository;
import com.rihaviour.Bank_API_REST.repositories.AccountRepository;
import com.rihaviour.Bank_API_REST.repositories.CheckingRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountTest {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    AccountHolder primaryOwner_Test;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Address testAddress = new Address("Diagonal", 1000, 8051,"Bcn","Esp");

        Address testMailingAddress = new Address("Diagonal", 1000, 8051,"Bcn","Esp");

        primaryOwner_Test = new AccountHolder("rihavior", "Ricardo", LocalDate.of(1990,4,26), testAddress,testMailingAddress);

        accountHolderRepository.save(primaryOwner_Test);

        Checking checking = new Checking(new Money(new BigDecimal(251)), primaryOwner_Test);

        checkingRepository.save(checking);
    }

    @Test
    @DisplayName("Get account by Id works")
    public void getAccountId_Works() throws Exception {

        Long accountId = 1L;

        String body = objectMapper.writeValueAsString(accountId);

        MvcResult mvcResult = mockMvc.perform(get("/get_account").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        assertTrue(accountRepository.findById(accountId).isPresent());
    }

    @Test
    @DisplayName("Modify Balance Works")
    public void modifyBalance_Works() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)),1L);

        String body = objectMapper.writeValueAsString(accountDTO);

        MvcResult mvcResult = mockMvc.perform(patch("/modify_balance").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        assertTrue(accountRepository.findById(accountDTO.getAccountId()).isPresent());
        assertEquals(accountDTO.getBalance().getAmount(), accountRepository.findById(accountDTO.getAccountId()).get().getBalance().getAmount());
    }
}
