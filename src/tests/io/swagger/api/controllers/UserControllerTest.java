//package io.swagger.api.controllers;
//
//import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
//import io.swagger.api.repository.UserRepository;
//import io.swagger.api.service.UserService;
//import io.swagger.model.DTO.CreateUserDTO;
//import io.swagger.model.User;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatchers;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(UsersApiController.class)
//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMVC;
//
//    @MockBean
//    private UserService userService;
//
//
//    @Test
//    void usersPostTest() throws Exception {
//        // Create a CreateUserDTO object
//        CreateUserDTO createUserDTO = new CreateUserDTO();
//        createUserDTO.setFirstName("John");
//        createUserDTO.setLastName("Doe");
//        createUserDTO.setEmail("JohnDoe@Example.com");
//        createUserDTO.setPassword("password213");
//        createUserDTO.setRole(CreateUserDTO.RoleEnum.USER);
//        createUserDTO.setTransactionLimit(1000.0);
//        createUserDTO.setDailyLimit(100.0);
//
//
//        // Mock the userService.add() method to return a user
//        User user = new User();
//        when(userService.add(ArgumentMatchers.any(User.class))).thenReturn(user);
//
//        // Convert the CreateUserDTO object to a JSON string
//        ObjectMapper objectMapper = new ObjectMapper();
//        String createUserJson = objectMapper.writeValueAsString(createUserDTO);
//
//        // Perform the POST request
//        MvcResult result = mockMVC.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(createUserJson))
//                .andExpect(status().isOk())
//                .andExpect(content().json("{\"firstName\":null,\"lastName\":null,\"email\":null,\"password\":null,\"role\":null,\"transactionLimit\":null,\"dailyLimit\":null}"))
//                .andReturn();
//
//        // Verify that the userService.add() method was called with the converted User object
//        verify(userService).add(ArgumentMatchers.any(User.class));
//    }
//}
