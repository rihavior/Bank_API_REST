package com.rihaviour.Bank_API_REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rihaviour.Bank_API_REST.entities.DTOs.AccountDTO;
import com.rihaviour.Bank_API_REST.entities.DTOs.AccountHolderDTO;
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

import static org.junit.jupiter.api.Assertions.*;
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

    AccountHolder accountHolder_Test;

    Address testAddress;
    Address testMailingAddress;
//    AccountHolder secondaryOwner_TwoOwnersTest;
//    AccountHolder primaryOwner_LowAgeTest;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        testAddress = new Address("Diagonal", 1000, 8051,"Bcn","Esp");

        testMailingAddress = new Address("Diagonal", 1000, 8051,"Bcn","Esp");

        accountHolder_Test = new AccountHolder("rihavior", "Ricardo", LocalDate.of(1990,4,26), testAddress,testMailingAddress);
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

    @Test
    @DisplayName("Creates account holder.")
    void createAccountHolder_Works() throws Exception {

        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("testName","testUserName",1990,4,26,testAddress,testMailingAddress);

        String body = objectMapper.writeValueAsString(accountHolderDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_account_holder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(accountHolderRepository.findByUserName(accountHolderDTO.getUserName()).isPresent());
    }

    @Test
    @DisplayName("Creates account holder without name throws exception.")
    void createAccountHolder_WithOutName_ThrowsException() throws Exception {

        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("testUserName","",1990,4,26,testAddress,testMailingAddress);

        String body = objectMapper.writeValueAsString(accountHolderDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_account_holder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        assertEquals("The Name can't be null.", mvcResult.getResponse().getErrorMessage());
    }

    @Test
    @DisplayName("Creates account holder without userName throws exception.")
    void createAccountHolder_WithOutUserName_ThrowsException() throws Exception {

        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("","testName",1990,4,26,testAddress,testMailingAddress);

        String body = objectMapper.writeValueAsString(accountHolderDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_account_holder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        assertEquals("The UserName can't be null.", mvcResult.getResponse().getErrorMessage());
    }

    @Test
    @DisplayName("Creates account holder with an existing userName throws exception.")
    void createAccountHolder_WithExistingUserName_ThrowsException() throws Exception {

        AccountHolder accountHolderAlreadyExisting = new AccountHolder("testUserName","testName",LocalDate.of(1990,4,26),testAddress,testMailingAddress);

        accountHolderRepository.save(accountHolderAlreadyExisting);

        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("testUserName","testName",1990,4,26,testAddress,testMailingAddress);

        String body = objectMapper.writeValueAsString(accountHolderDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_account_holder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        assertTrue(mvcResult.getResponse().getErrorMessage().contains("The UserName: "));
    }

    @Test
    @DisplayName("Creates account holder with an invalid dayOfBirth format throws exception.")
    void createChecking_WithInvalidDayOfBirth_ThrowsException() throws Exception {

        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("testUserName","testName",1990,1,35,testAddress,testMailingAddress);

        AccountHolderDTO accountHolderDTO1 = new AccountHolderDTO("testUserName1","testName",1990,2,30,testAddress,testMailingAddress);

        AccountHolderDTO accountHolderDTO2 = new AccountHolderDTO("testUserName2","testName",1990,4,31,testAddress,testMailingAddress);

        String body = objectMapper.writeValueAsString(accountHolderDTO);

        String body1 = objectMapper.writeValueAsString(accountHolderDTO1);

        String body2 = objectMapper.writeValueAsString(accountHolderDTO2);

        MvcResult mvcResult = mockMvc.perform(post("/create_account_holder").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        MvcResult mvcResult1 = mockMvc.perform(post("/create_account_holder").content(body1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        MvcResult mvcResult2 = mockMvc.perform(post("/create_account_holder").content(body2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        assertEquals("Wrong birthDate format.", mvcResult.getResponse().getErrorMessage());
        assertEquals("Wrong birthDate format.", mvcResult1.getResponse().getErrorMessage());
        assertEquals("Wrong birthDate format.", mvcResult2.getResponse().getErrorMessage());

        /**
         * Utilizando el AccountHolderDTO porque sino el programa funcionaba
         * pero el objectMapper.writeValueAsString(accountHolder); petaba por el LocalDate
         * esta ha sido la manera mas elegante de arreglarlo junto con
         * el try catch del service para gestionar todas las excepciones que pueda lanzar LocalDate.
         * Casi escribo los if para los dias por grupos de meses (31, 30, 28/29??  T.T)...
         */

    }
}