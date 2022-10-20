package com.rihaviour.Bank_API_REST;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rihaviour.Bank_API_REST.entities.DTOs.AccountHolderDTO;
import com.rihaviour.Bank_API_REST.entities.accounts.Account;
import com.rihaviour.Bank_API_REST.entities.accounts.Checking;
import com.rihaviour.Bank_API_REST.entities.accounts.Savings;
import com.rihaviour.Bank_API_REST.entities.accounts.Transaction;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
import com.rihaviour.Bank_API_REST.others.Address;
import com.rihaviour.Bank_API_REST.others.Money;
import com.rihaviour.Bank_API_REST.repositories.*;
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
    SavingsRepository savingsRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    TransactionRepository transactionRepository;

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

        Savings savings = new Savings(new Money(new BigDecimal(5000)),accountHolder1);

        checkingRepository.save(checking);
        savingsRepository.save(savings);

    }

    @AfterEach
    public void tearDown(){
        accountRepository.deleteAll();
        checkingRepository.deleteAll();
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
        transactionRepository.deleteAll();
    }

    @Test
    @DisplayName("Transfer funds with given primary owner works.")
    public void transferFunds_WithGivenPrimaryOwner_Works() throws Exception {
        Transaction transaction = new Transaction(new BigDecimal(500),"rihavior",1L,2L);

        String body = objectMapper.writeValueAsString(transaction);

        MvcResult mvcResult = mockMvc.perform(patch("/transfer_funds").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        assertTrue(accountRepository.findById(transaction.getOriginAccountId()).isPresent());
        assertTrue(accountRepository.findById(transaction.getDestinyAccountId()).isPresent());
        assertEquals(new BigDecimal("500.00"), accountRepository.findById(transaction.getOriginAccountId()).get().getBalance().getAmount());
        assertEquals(new BigDecimal("5500.00"), accountRepository.findById(transaction.getDestinyAccountId()).get().getBalance().getAmount());
        assertEquals(1, transactionRepository.count());
    }

    @Test
    @DisplayName("Transfer funds with given secondary owner works.")
    public void transferFunds_WithGivenSecondaryOwner_Works() throws Exception {
        Transaction transaction = new Transaction(new BigDecimal(500),"laurita",1L,2L);

        String body = objectMapper.writeValueAsString(transaction);

        MvcResult mvcResult = mockMvc.perform(patch("/transfer_funds").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        assertTrue(accountRepository.findById(transaction.getOriginAccountId()).isPresent());
        assertTrue(accountRepository.findById(transaction.getDestinyAccountId()).isPresent());
        assertEquals(new BigDecimal("500.00"), accountRepository.findById(transaction.getOriginAccountId()).get().getBalance().getAmount());
        assertEquals(new BigDecimal("5500.00"), accountRepository.findById(transaction.getDestinyAccountId()).get().getBalance().getAmount());
        assertEquals(1, transactionRepository.count());
    }

    @Test
    @DisplayName("Transfer funds with amount greater than account balance throws exception.")
    public void transferFunds_WithAmountGreaterBalance_ThrowsException() throws Exception {
        Transaction transaction = new Transaction(new BigDecimal(1500),"rihavior",1L,2L);

        String body = objectMapper.writeValueAsString(transaction);

        MvcResult mvcResult = mockMvc.perform(patch("/transfer_funds").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();


        assertEquals("Not enough Funds", mvcResult.getResponse().getErrorMessage());
        assertTrue(accountRepository.findById(1L).isPresent());
        assertEquals(new BigDecimal("1000.00") , accountRepository.findById(1L).get().getBalance().getAmount());
        assertTrue(accountRepository.findById(2L).isPresent());
        assertEquals(new BigDecimal("5000.00") , accountRepository.findById(2L).get().getBalance().getAmount());
    }

    @Test
    @DisplayName("Transfer funds with unknown userName throws exception.")
    public void transferFunds_WithUnknownUserName_ThrowsException() throws Exception {
        Transaction transaction = new Transaction(new BigDecimal(500),"unknownUserName",1L,2L);

        String body = objectMapper.writeValueAsString(transaction);

        MvcResult mvcResult = mockMvc.perform(patch("/transfer_funds").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()).andReturn();

        assertEquals("The given userName doesn't exist.", mvcResult.getResponse().getErrorMessage());
        assertTrue(accountRepository.findById(1L).isPresent());
        assertEquals(new BigDecimal("1000.00") , accountRepository.findById(1L).get().getBalance().getAmount());
        assertTrue(accountRepository.findById(2L).isPresent());
        assertEquals(new BigDecimal("5000.00") , accountRepository.findById(2L).get().getBalance().getAmount());
    }


    @Test
    @DisplayName("Transfer funds with unknown originAccountId throws exception.")
    public void transferFunds_WithUnknownOriginAccountId_ThrowsException() throws Exception {
        Transaction transaction = new Transaction(new BigDecimal(500),"rihavior",5L,2L);

        String body = objectMapper.writeValueAsString(transaction);

        MvcResult mvcResult = mockMvc.perform(patch("/transfer_funds").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()).andReturn();

        assertEquals("The origin account doesn't exist.", mvcResult.getResponse().getErrorMessage());
        assertTrue(accountRepository.findById(1L).isPresent());
        assertEquals(new BigDecimal("1000.00") , accountRepository.findById(1L).get().getBalance().getAmount());
        assertTrue(accountRepository.findById(2L).isPresent());
        assertEquals(new BigDecimal("5000.00") , accountRepository.findById(2L).get().getBalance().getAmount());
    }

    @Test
    @DisplayName("Transfer funds with unknown finalAccountId throws exception.")
    public void transferFunds_WithUnknownFinalAccountId_ThrowsException() throws Exception {
        Transaction transaction = new Transaction(new BigDecimal(500),"rihavior",1L,5L);

        String body = objectMapper.writeValueAsString(transaction);

        MvcResult mvcResult = mockMvc.perform(patch("/transfer_funds").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()).andReturn();

        assertEquals("The final account doesn't exist.", mvcResult.getResponse().getErrorMessage());
        assertTrue(accountRepository.findById(1L).isPresent());
        assertEquals(new BigDecimal("1000.00") , accountRepository.findById(1L).get().getBalance().getAmount());
        assertTrue(accountRepository.findById(2L).isPresent());
        assertEquals(new BigDecimal("5000.00") , accountRepository.findById(2L).get().getBalance().getAmount());
    }

    @Test
    @DisplayName("Transfer funds from Checking let balance lower than minimum balance apply penalty fee.")
    public void transferFunds_CheckingBalanceLowerThanMin_ApplyPenaltyFee() throws Exception {

        Transaction transaction = new Transaction(new BigDecimal(800),"rihavior",1L,2L);

        String body = objectMapper.writeValueAsString(transaction);

        MvcResult mvcResult = mockMvc.perform(patch("/transfer_funds").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        assertTrue(accountRepository.findById(1L).isPresent());
        assertEquals(new BigDecimal("160.00") , accountRepository.findById(1L).get().getBalance().getAmount());
    }

    @Test
    @DisplayName("Transfer funds from Savings let balance lower than minimum balance apply penalty fee.")
    public void transferFunds_SavingsBalanceLowerThanMin_ApplyPenaltyFee() throws Exception {

        Transaction transaction = new Transaction(new BigDecimal(4990),"laurita",2L,1L);

        String body = objectMapper.writeValueAsString(transaction);

        MvcResult mvcResult = mockMvc.perform(patch("/transfer_funds").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        assertTrue(accountRepository.findById(2L).isPresent());
        assertEquals(new BigDecimal("-30.00") , accountRepository.findById(2L).get().getBalance().getAmount());
    }
}
