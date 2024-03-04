package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.OtpDto;
import com.catering_app.Catering_app.dto.VerificationResponseDto;
import com.catering_app.Catering_app.dto.UserLoginDto;
import com.catering_app.Catering_app.dto.UserRegisterDto;
import com.catering_app.Catering_app.model.AuthenticationResponse;
import com.catering_app.Catering_app.repository.UserRepository;
import com.catering_app.Catering_app.service.authService.AuthenticationService;
import com.catering_app.Catering_app.service.authService.AuthenticationServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public AuthenticationController(AuthenticationServiceImpl authenticationService, UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto userRequest) throws MessagingException {
        if (userRepository.existsByEmail(userRequest.getEmail()) ||
                userRepository.existsByUsername(userRequest.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(" Email or Username Exist");
        }
        return ResponseEntity.ok(authenticationService.register(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserLoginDto userRequest){
        return ResponseEntity.ok(authenticationService.authenticate(userRequest));
    }

    @PostMapping("/verify-account")
    public ResponseEntity<VerificationResponseDto> verifyAccount(@RequestBody OtpDto otpDto){
        boolean verificationSuccess = authenticationService.verifyAccount(otpDto);
        VerificationResponseDto response;
        if (verificationSuccess) {
            response = new VerificationResponseDto(true, "Verification successful");
        } else {
            response = new VerificationResponseDto(false, "Invalid OTP");
        }
        return ResponseEntity.ok(response);
    }
    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestBody OtpDto otpDto) throws MessagingException {
        return ResponseEntity.ok(authenticationService.regenerateOtp(otpDto.getEmail(),otpDto.getOtp()));
    }
}
