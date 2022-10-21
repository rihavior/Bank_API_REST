package com.rihavior.Bank_API_REST;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.rihavior.Bank_API_REST.entities.DTOs.AccountDTO;
import com.rihavior.Bank_API_REST.entities.accounts.Savings;
import com.rihavior.Bank_API_REST.repositories.SavingsRepository;
import com.rihavior.Bank_API_REST.entities.users.AccountHolder;
import com.rihavior.Bank_API_REST.others.Address;
import com.rihavior.Bank_API_REST.others.Money;
import com.rihavior.Bank_API_REST.repositories.AccountHolderRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SavingsTest {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    AccountHolder primaryOwner_OneOwnerTest;
    AccountHolder secondaryOwner_TwoOwnersTest;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Address testAddress = new Address("Diagonal", 1000, 8051,"Bcn","Esp");

        Address testMailingAddress = new Address("Diagonal", 1000, 8051,"Bcn","Esp");

        primaryOwner_OneOwnerTest = new AccountHolder("rihavior", "Ricardo", LocalDate.of(1990,4,26), testAddress,testMailingAddress);

        secondaryOwner_TwoOwnersTest = new AccountHolder("paqito", "Paco", LocalDate.of(1961,1,1), testAddress,testMailingAddress);

        accountHolderRepository.save(primaryOwner_OneOwnerTest);

        accountHolderRepository.save(secondaryOwner_TwoOwnersTest);
    }

    @AfterEach
    public void tearDown(){
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }


    @Test
    @DisplayName("Creates savings when 2 Owners are provided.")
    void createSavings_WithTwoOwners_Works() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_OneOwnerTest.getUserName(), secondaryOwner_TwoOwnersTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(savingsRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).isPresent());
        assertTrue(savingsRepository.findBySecondaryOwnerUserName(secondaryOwner_TwoOwnersTest.getUserName()).isPresent());
    }

    @Test
    @DisplayName("Creates savings when 1 Owner is provided.")
    void createSavings_WithOneOwner_Works() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_OneOwnerTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(savingsRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).isPresent());
    }

    @Test
    @DisplayName("Creates savings without interestRate, set it to default.")
    void createSavings_WithoutInterestRate_SetToDefault() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_OneOwnerTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(savingsRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).isPresent());
        Assertions.assertEquals(new BigDecimal("0.0025"), savingsRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).get().getInterestRate());
    }

    @Test
    @DisplayName("Creates savings with given interestRate.")
    void createSavings_WithGivenInterestRate_SetIt() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_OneOwnerTest.getUserName(), new BigDecimal("0.1234"));

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(savingsRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).isPresent());
        Assertions.assertEquals(new BigDecimal("0.1234"), savingsRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).get().getInterestRate());
    }

    @Test
    @DisplayName("Creates savings with given interestRate > 0.5(MAX), throws exception.")
    void createSavings_WithGivenInterestRateGreaterThanMax_ThrowsException() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_OneOwnerTest.getUserName(), new BigDecimal("0.5678"));

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        assertEquals("The interestRate cannot be greater than max(0.5).", mvcResult.getResponse().getErrorMessage());
    }

    @Test
    @DisplayName("Creates savings without balance, set it to default.")
    void createSavings_WithoutBalance_SetToDefault() throws Exception {

        AccountDTO accountDTO = new AccountDTO(primaryOwner_OneOwnerTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(savingsRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).isPresent());
        assertEquals(new BigDecimal("1000.00"), savingsRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).get().getBalance().getAmount());
    }

    @Test
    @DisplayName("Creates savings with given balance, set it.")
    void createSavings_WithGivenBalance_SetIt() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(500)), primaryOwner_OneOwnerTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(savingsRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).isPresent());
        assertEquals(new BigDecimal("500.00"), savingsRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).get().getBalance().getAmount());
    }

    @Test
    @DisplayName("Creates savings with given balance < minimumBalance(100), throws exception.")
    void createSavings_WithGivenBalanceGreaterThanMax_ThrowsException() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(50)), primaryOwner_OneOwnerTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_savings").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        assertEquals("The Balance cannot be lower than min(100).", mvcResult.getResponse().getErrorMessage());
    }

    @Test
    @DisplayName("InterestRate is applied when it's been more than a year from lastInterestApplied.")
    void getBalance_PlusThanYearSinceLastInterestApplied_ApplyInterestRate() throws Exception {

        Savings savings = new Savings(new Money(new BigDecimal(200)),primaryOwner_OneOwnerTest);
        Savings savings1 = new Savings(new Money(new BigDecimal(200)),primaryOwner_OneOwnerTest);

        savings.setLastInterestApplied(LocalDate.of(2021,9,20));
        savings1.setLastInterestApplied(LocalDate.of(2019,7,20));

        BigDecimal balance = new BigDecimal("200.00");

        BigDecimal newBalanceOneYear = balance.add(balance.multiply(savings.getInterestRate()));
        BigDecimal newBalanceThreeYears = balance.add(balance.multiply(savings1.getInterestRate().multiply(new BigDecimal(3))));

        assertEquals(newBalanceOneYear.setScale(2, RoundingMode.HALF_EVEN), savings.getBalance().getAmount());
        assertEquals(newBalanceThreeYears.setScale(2, RoundingMode.HALF_EVEN), savings1.getBalance().getAmount());
    }
}