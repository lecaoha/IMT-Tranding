package com.example.ApiGateway.controller;


import com.example.ApiGateway.model.AuthencationResponse;
import com.example.ApiGateway.model.AuthenticationRequest;
import com.example.ApiGateway.model.RegisterRequest;
import com.example.ApiGateway.model.RegisterResponse;
import com.example.ApiGateway.service.AuthencationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"api/auth"})
@Tag(name = "API Gateway", description = "Auth management APIs")
public class MyController {

    private final AuthencationService authencationService;

    public MyController(AuthencationService authencationService) {
        this.authencationService = authencationService;
    }
    @Operation(
            summary = "Đăng ký tài khoản",
            description = "Nhập đầy đủ của thông tin bên dưới để một tạo tài khoản mới"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RegisterResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = RegisterResponse.class)) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping({"/register"})
    public ResponseEntity<AuthencationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(this.authencationService.register(request));
    }
    @Operation(
            summary = "Đăng nhập bằng Email",
            description = "Nhập Email và Password. Yêu cầu phải nhập đúng thông mới có thể truy xuất đến các service bên dưới"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = AuthencationResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = AuthencationResponse.class)) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping({"/authenticate"})
    public ResponseEntity<AuthencationResponse> authenticate(@RequestBody AuthenticationRequest request) throws Exception {
        return ResponseEntity.ok(this.authencationService.authenticate(request));
    }

    @GetMapping({"hello"})
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello Duy");
    }


}