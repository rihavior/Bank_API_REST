package com.rihaviour.Bank_API_REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rihaviour.Bank_API_REST.entities.AccountDTO;
import com.rihaviour.Bank_API_REST.others.Address;
import com.rihaviour.Bank_API_REST.others.Money;
import com.rihaviour.Bank_API_REST.repositories.AccountHolderRepository;
import com.rihaviour.Bank_API_REST.repositories.CheckingRepository;
import com.rihaviour.Bank_API_REST.repositories.StudentCheckingRepository;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
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
public class CheckingControllerTest {

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    AccountHolder primaryOwner_OneOwnerBigAgeTest;
    AccountHolder secondaryOwner_TwoOwnersTest;
    AccountHolder primaryOwner_LowAgeTest;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Address testAddress = new Address("Diagonal", 1000, 8051,"Bcn","Esp");

        primaryOwner_OneOwnerBigAgeTest = new AccountHolder("rihavior", "Ricardo", LocalDate.of(1990,4,26), testAddress,"ricardo@test.com");

        secondaryOwner_TwoOwnersTest = new AccountHolder("paqito", "Paco", LocalDate.of(1961,1,1), testAddress,"paqito@test.com");

        primaryOwner_LowAgeTest = new AccountHolder("laurita", "Laura", LocalDate.of(2002,1,1), testAddress,"paqito@test.com");

        accountHolderRepository.save(primaryOwner_OneOwnerBigAgeTest);

        accountHolderRepository.save(secondaryOwner_TwoOwnersTest);

        accountHolderRepository.save(primaryOwner_LowAgeTest);
    }

//    @AfterEach
//    public void tearDown(){
//        accountHolderRepository.deleteAll();
//        checkingRepository.deleteAll();
//        studentCheckingRepository.deleteAll();
//    }

    @Test
    @DisplayName("Creates checking when 2 Owners are provided.")
    void createChecking_WithTwoOwners() throws Exception {
        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_OneOwnerBigAgeTest.getUserName(), secondaryOwner_TwoOwnersTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult = mockMvc.perform(post("/create_checking").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        Assertions.assertTrue(checkingRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerBigAgeTest.getUserName()).isPresent());
        Assertions.assertTrue(checkingRepository.findBySecondaryOwnerUserName(secondaryOwner_TwoOwnersTest.getUserName()).isPresent());
    }

    @Test
    @DisplayName("Creates checking when 1 Owners is provided.")
    void createChecking_WithOneOwner() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_OneOwnerBigAgeTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult =mockMvc.perform(post("/create_checking").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        Assertions.assertTrue(checkingRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerBigAgeTest.getUserName()).isPresent());
    }

    @Test
    @DisplayName("Creates checking when primaryOwnerAge > 24 and 1 Owner.")
    void createChecking_WithUniqueOwnerOlderThan24() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_OneOwnerBigAgeTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult =mockMvc.perform(post("/create_checking").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        Assertions.assertTrue(checkingRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerBigAgeTest.getUserName()).isPresent());
    }

    @Test
    @DisplayName("Creates studentChecking when primaryOwnerAge < 24 and 1 Owner.")
    void createChecking_WithUniqueOwnerYoungerThan24() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_LowAgeTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult =mockMvc.perform(post("/create_checking").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        Assertions.assertTrue(studentCheckingRepository.findByPrimaryOwnerUserName(primaryOwner_LowAgeTest.getUserName()).isPresent());
    }

    @Test
    @DisplayName("Creates checking when primaryOwnerAge > 24 and 2 Owner.")
    void createChecking_WithTwoOwnersOlderThan24() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_OneOwnerBigAgeTest.getUserName(), secondaryOwner_TwoOwnersTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult =mockMvc.perform(post("/create_checking").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        Assertions.assertTrue(checkingRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerBigAgeTest.getUserName()).isPresent());
    }

    @Test
    @DisplayName("Creates studentChecking when primaryOwnerAge < 24 and 2 Owner.")
    void createChecking_WithTwoOwnersYoungerThan24() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_LowAgeTest.getUserName(), secondaryOwner_TwoOwnersTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        System.out.println(body);

        MvcResult mvcResult =mockMvc.perform(post("/create_checking").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        Assertions.assertTrue(studentCheckingRepository.findByPrimaryOwnerUserName(primaryOwner_LowAgeTest.getUserName()).isPresent());
    }

    @Test
    @DisplayName("Create checking with Balance lower than minimum throw exception.")
    void createChecking_WithLowBalance_ThrowsException() throws Exception {

        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(10)), primaryOwner_OneOwnerBigAgeTest.getUserName(), secondaryOwner_TwoOwnersTest.getUserName());

        String body = objectMapper.writeValueAsString(accountDTO);

        MvcResult mvcResult =  mockMvc.perform(post("/create_checking").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()).andReturn();

        assertTrue(mvcResult.getResponse().getErrorMessage().contains("The balance cannot be lower than"));
    }
}
