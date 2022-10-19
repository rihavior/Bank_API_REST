package com.rihaviour.Bank_API_REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rihaviour.Bank_API_REST.entities.DTOs.AccountDTO;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
import com.rihaviour.Bank_API_REST.others.Address;
import com.rihaviour.Bank_API_REST.others.Money;
import com.rihaviour.Bank_API_REST.repositories.AccountHolderRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountHolderTest {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

//    AccountHolder primaryOwner_OneOwnerBigAgeTest;
//    AccountHolder secondaryOwner_TwoOwnersTest;
//    AccountHolder primaryOwner_LowAgeTest;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

//        Address testAddress = new Address("Diagonal", 1000, 8051,"Bcn","Esp");
//
//        Address testMailingAddress = new Address("Diagonal", 1000, 8051,"Bcn","Esp");
//
//        primaryOwner_OneOwnerBigAgeTest = new AccountHolder("rihavior", "Ricardo", LocalDate.of(1990,4,26), testAddress,testMailingAddress);
//
//        secondaryOwner_TwoOwnersTest = new AccountHolder("paqito", "Paco", LocalDate.of(1961,1,1), testAddress,testMailingAddress);
//
//        primaryOwner_LowAgeTest = new AccountHolder("laurita", "Laura", LocalDate.of(2002,1,1), testAddress,testMailingAddress);
//
//        accountHolderRepository.save(primaryOwner_OneOwnerBigAgeTest);
//
//        accountHolderRepository.save(secondaryOwner_TwoOwnersTest);
//
//        accountHolderRepository.save(primaryOwner_LowAgeTest);
    }

    @AfterEach
    public void tearDown(){
//        checkingRepository.deleteAll();
//        studentCheckingRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

//    @Test
//    @DisplayName("Creates account holder.")
//    void createChecking_WithTwoOwners() throws Exception {
//        AccountHolder accountHolder = new AccountHolder()
//
//        String body = objectMapper.writeValueAsString(accountHolder);
//
//        System.out.println(body);
//
//        MvcResult mvcResult = mockMvc.perform(post("/create_account_holder").content(body).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated()).andReturn();
//
//        Assertions.assertTrue(accountHolderRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerBigAgeTest.getUserName()).isPresent());
//        Assertions.assertTrue(checkingRepository.findBySecondaryOwnerUserName(secondaryOwner_TwoOwnersTest.getUserName()).isPresent());
//    }
}
