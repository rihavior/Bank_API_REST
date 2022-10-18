package com.rihaviour.Bank_API_REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rihaviour.Bank_API_REST.entities.AccountDTO;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
import com.rihaviour.Bank_API_REST.others.Address;
import com.rihaviour.Bank_API_REST.others.Money;
import org.junit.jupiter.api.Assertions;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SavingsTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    AccountHolder primaryOwner_OneOwnerBigAgeTest;
    AccountHolder secondaryOwner_TwoOwnersTest;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Address testAddress = new Address("Diagonal", 1000, 8051,"Bcn","Esp");

        primaryOwner_OneOwnerBigAgeTest = new AccountHolder("rihavior", "Ricardo", LocalDate.of(1990,4,26), testAddress,"ricardo@test.com");

        secondaryOwner_TwoOwnersTest = new AccountHolder("paqito", "Paco", LocalDate.of(1961,1,1), testAddress,"paqito@test.com");
    }

//    @Test
//    @DisplayName("Create savings works")
//    void createSavings_Works() {
//
//        AccountDTO accountDTO = new AccountDTO(new Money(new BigDecimal(1000)), primaryOwner_OneOwnerTest.getUserName(), secondaryOwner_TwoOwnersTest.getUserName());
//
//        String body = objectMapper.writeValueAsString(accountDTO);
//
//        System.out.println(body);
//
//        MvcResult mvcResult = mockMvc.perform(post("/create_checking").content(body).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated()).andReturn();
//
//        Assertions.assertTrue(checkingRepository.findByPrimaryOwnerUserName(primaryOwner_OneOwnerBigAgeTest.getUserName()).isPresent());
//        Assertions.assertTrue(checkingRepository.findBySecondaryOwnerUserName(secondaryOwner_TwoOwnersTest.getUserName()).isPresent());
//    }
}
