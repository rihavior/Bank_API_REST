package com.rihavior.Bank_API_REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rihavior.Bank_API_REST.entities.DTOs.AccountDTO;
import com.rihavior.Bank_API_REST.entities.accounts.CreditCard;
import com.rihavior.Bank_API_REST.repositories.AccountHolderRepository;
import com.rihavior.Bank_API_REST.entities.users.AccountHolder;
import com.rihavior.Bank_API_REST.others.Address;
import com.rihavior.Bank_API_REST.others.Money;
import com.rihavior.Bank_API_REST.repositories.CreditCardRepository;
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
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CreditCardTest {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

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
        creditCardRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }


    @Test
    @DisplayName("Creates creditCard when 1 Owner is provided.")
    void createCreditCard_WithOneOwner_Works() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_OneOwnerTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_credit_card").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(creditCardRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).isPresent());
    }

    @Test
    @DisplayName("Creates creditCard when 2 Owner are provided.")
    void createCreditCard_WithTwoOwners_Works() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_OneOwnerTest.getUserName(), secondaryOwner_TwoOwnersTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_credit_card").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(creditCardRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).isPresent());
    }

    @Test
    @DisplayName("Creates creditCard without creditLimit, set it to default.")
    void createCreditCard_WithoutCreditLimit_SetToDefault() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_OneOwnerTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_credit_card").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(creditCardRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).isPresent());
        assertEquals(new BigDecimal("100.00"), creditCardRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).get().getCreditLimit());
    }

    @Test
    @DisplayName("Creates creditCard with given creditLimit.")
    void createCreditCard_WithGivenCreditLimit_SetIt() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), new BigDecimal("1000"), primaryOwner_OneOwnerTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_credit_card").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(creditCardRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).isPresent());
        assertEquals(new BigDecimal("1000.00"), creditCardRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).get().getCreditLimit());
    }

    @Test
    @DisplayName("Creates creditCard with given creditLimit > 100 000, throws exception.")
    void createCreditCard_WithGivenCreditLimitGreaterThanMax_ThrowsException() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), new BigDecimal("1000000"), primaryOwner_OneOwnerTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_credit_card").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        assertEquals("Forbidden Credit Limit should be between 100 and 100000.", mvcResult.getResponse().getErrorMessage());
    }

    @Test
    @DisplayName("Creates creditCard with given creditLimit < 100, throws exception.")
    void createCreditCard_WithGivenCreditLimitLowerThanMin_ThrowsException() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), new BigDecimal("10"), primaryOwner_OneOwnerTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_credit_card").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        assertEquals("Forbidden Credit Limit should be between 100 and 100000.", mvcResult.getResponse().getErrorMessage());
    }

    @Test
    @DisplayName("Creates creditCard without interestRate.")
    void createCreditCard_WithInterestRate_SetIt() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_OneOwnerTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_credit_card").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(creditCardRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).isPresent());
        assertEquals(new BigDecimal("0.2000"), creditCardRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).get().getInterestRate());
    }

    @Test
    @DisplayName("Creates creditCard with a given interestRate.")
    void createCreditCard_WithGivenInterestRate_SetIt() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new BigDecimal("0.1234"),new Money(new BigDecimal(1000)), primaryOwner_OneOwnerTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_credit_card").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(creditCardRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).isPresent());
        assertEquals(new BigDecimal("0.1234"), creditCardRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerTest.getUserName()).get().getInterestRate());
    }

    @Test
    @DisplayName("Creates creditCard with a given interestRate > 0.2, throws exception")
    void createCreditCard_WithGivenInterestRateGreaterThanMax_ThrowsException() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new BigDecimal("0.2345"),new Money(new BigDecimal(1000)), primaryOwner_OneOwnerTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_credit_card").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        assertEquals("Forbidden Interest Rate should be between 0.1000 and 0.2000.", mvcResult.getResponse().getErrorMessage());
    }

    @Test
    @DisplayName("Creates creditCard with a given interestRate < 0.1, throws exception")
    void createCreditCard_WithGivenInterestRateLowerThanMin_ThrowsException() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new BigDecimal("0.0987"),new Money(new BigDecimal(1000)), primaryOwner_OneOwnerTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_credit_card").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        assertEquals("Forbidden Interest Rate should be between 0.1000 and 0.2000.", mvcResult.getResponse().getErrorMessage());
    }

    @Test
    @DisplayName("InterestRate is applied when it's been more than a month from lastInterestApplied.")
    void getBalance_PlusThanMonthSinceLastInterestApplied_ApplyInterestRate() throws Exception {

        CreditCard creditCard = new CreditCard(new Money(new BigDecimal(200)),primaryOwner_OneOwnerTest);
        CreditCard creditCard1 = new CreditCard(new Money(new BigDecimal(200)),primaryOwner_OneOwnerTest);

        creditCard.setLastInterestApplied(LocalDate.of(2022,9,20));
        creditCard1.setLastInterestApplied(LocalDate.of(2022,7,20));

        BigDecimal balance = new BigDecimal("200.00");

        BigDecimal newBalanceOneMonth = balance.add(balance.multiply(creditCard.getInterestRate()));

        BigDecimal newBalanceThreeMonths = balance.add(balance.multiply(creditCard1.getInterestRate().multiply(new BigDecimal(3))));

        assertEquals(newBalanceOneMonth.setScale(2, RoundingMode.HALF_EVEN), creditCard.getBalance().getAmount());
        assertEquals(newBalanceThreeMonths.setScale(2, RoundingMode.HALF_EVEN), creditCard1.getBalance().getAmount());
    }
}
