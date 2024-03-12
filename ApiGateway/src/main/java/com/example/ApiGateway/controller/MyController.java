package com.example.ApiGateway.controller;


import com.example.APIGateway.service.AuthencationService;
import com.example.ApiGateway.model.AuthencationResponse;
import com.example.ApiGateway.model.AuthenticationRequest;
import com.example.ApiGateway.model.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"api/auth"})
public class MyController {

    private final AuthencationService authencationService;

    public MyController(AuthencationService authencationService) {
        this.authencationService = authencationService;
    }

    @PostMapping({"/register"})
    public ResponseEntity<AuthencationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(this.authencationService.register(request));
    }

    @PostMapping({"/authenticate"})
    public ResponseEntity<AuthencationResponse> authenticate(@RequestBody AuthenticationRequest request) throws Exception {
        return ResponseEntity.ok(this.authencationService.authenticate(request));
    }

    @GetMapping({"hello"})
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello Duy");
    }


}