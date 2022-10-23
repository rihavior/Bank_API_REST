package com.rihavior.Bank_API_REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rihavior.Bank_API_REST.entities.DTOs.AccountHolderDTO;
import com.rihavior.Bank_API_REST.entities.DTOs.TransactionDTO;
import com.rihavior.Bank_API_REST.entities.users.AccountHolder;
import com.rihavior.Bank_API_REST.entities.users.ThirdParty;
import com.rihavior.Bank_API_REST.others.Address;
import com.rihavior.Bank_API_REST.repositories.AccountHolderRepository;
import com.rihavior.Bank_API_REST.repositories.AccountRepository;
import com.rihavior.Bank_API_REST.repositories.UserRepository;
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

import javax.validation.constraints.Digits;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ThirdPartyTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    AccountHolder accountHolder_Test;
    Address testAddress;
    Address testMailingAddress;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void tearDown() {
//        accountHolderRepository.deleteAll();
//        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Create third party works")
    public void creteThirdParty_Works() throws Exception {

        ThirdParty thirdParty = new ThirdParty("thirdPartyTest1","thirdPartyTest1");

        String body = objectMapper.writeValueAsString(thirdParty);

        MvcResult mvcResult = mockMvc.perform(post("/create_third_party").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(userRepository.findByUsername(thirdParty.getUserName()).isPresent());
    }

    @Test
    @DisplayName("Third transfer")
    public void thirdTransfer_Works() throws Exception {

        ThirdParty thirdParty = new ThirdParty("thirdPartyTest","thirdPartyTest");

        userRepository.save(thirdParty);

        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal(50),"thirdPartyTest",1L);

        String body = objectMapper.writeValueAsString(transactionDTO);

        MvcResult mvcResult = mockMvc.perform(patch("/third_transfer").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertEquals(new BigDecimal("1050.00"), accountRepository.findByPrimaryOwnerUsername("rihavior").get().getBalance().getAmount());
    }
}
