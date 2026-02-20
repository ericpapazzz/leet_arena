package com.example.leetarena.services;

import com.example.leetarena.dtos.LeetcodeProfileDTO;
import com.example.leetarena.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnLeetcodeProfileCreated() throws Exception {

        String username = "emiliano_0X1";

        String fake_json = """
        {
          "username" : "emiliano_0X1",
          "profile": {
            "userAvatar": "https://avatar.com/test.png",
            "ranking" : 10000,
          }
        }
        """;

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Mockito.when(restTemplate.getForObject(
                "https://leetcode-api-pied.vercel.app/user/" + username,
                String.class
        )).thenReturn(fake_json);

        LeetcodeProfileDTO res = userService.validateLeetcodeAccount(username);
        assertNotNull(res);

    }
    
}
