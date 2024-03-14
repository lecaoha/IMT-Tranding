package com.example.ApiGateway.service;


import com.example.ApiGateway.model.AuthencationResponse;
import com.example.ApiGateway.model.AuthenticationRequest;
import com.example.ApiGateway.model.RegisterRequest;
import com.example.ApiGateway.model.User;
import com.example.ApiGateway.service.JwtService;
import com.example.ApiGateway.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthencationService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;

    public AuthencationResponse register(RegisterRequest request) {
        User user = User.builder().email(request.getEmail()).password(this.passwordEncoder.encode(request.getPassword())).role(request.getRole()).build();
        this.userRepository.save(user);
//        String jwtToken = this.jwtService.generateToken(user);
        return AuthencationResponse.builder().token("Account created successfully").build();
    }

    public AuthencationResponse authenticate(AuthenticationRequest request) throws Exception {
        User user;
        try {
            user = this.userRepository.findByEmail(request.getEmail());
            if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return AuthencationResponse.builder().token("Incorrect username or password").build();
            }
        } catch (Exception var4) {
            return AuthencationResponse.builder().token("Incorrect username or password").build();
        }

        user = this.userRepository.findByEmail(request.getEmail());
        String jwtToken = this.jwtService.generateToken(user);
        return AuthencationResponse.builder().token(jwtToken).build();
    }


}
