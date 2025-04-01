package com.exampleLoan.controller;

import com.exampleLoan.dto.request.LoanDTO;
import com.exampleLoan.dto.response.LoanResponse;
import com.exampleLoan.entity.Customer;
import com.exampleLoan.service.LoanService;
import com.exampleLoan.serviceImpl.CustomerService;
import com.exampleLoan.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/add")
    public LoanResponse applyForLoan(@Valid @RequestBody LoanDTO loanDTO) {
        LoanDTO loan = loanService.applyForLoan(loanDTO);
        return new LoanResponse(true, "Loan applied successfully", loan);
    }

    @GetMapping("/{email}")
    public LoanResponse getLoanByEmail(@PathVariable String email) {
        LoanDTO loan = loanService.getLoanByEmail(email);
        return new LoanResponse(true, "Loan fetched successfully", loan);
    }

    @GetMapping("/getAll")
    public LoanResponse getAllLoans() {
        List<LoanDTO> loans = loanService.getAllLoans();
        return new LoanResponse(true, "All loans retrieved successfully", loans);
    }

    @PostMapping("/register")
    public LoanResponse registerCustomer(@RequestBody Customer customer) {
        boolean status = customerService.saveCustomer(customer);
        if (status) {
            return new LoanResponse(true, "Customer registered successfully", "Success");
        } else {
            return new LoanResponse(false, "Customer registration failed", "Failed");
        }
    }

    @PostMapping("/login")
    public LoanResponse login(@RequestBody Customer customer) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                customer.getEmail(), customer.getPwd());

        Authentication authenticate = authenticationManager.authenticate(token);

        if (authenticate.isAuthenticated()) {
            String jwt = jwtUtil.generateToken(customer.getEmail());
            return new LoanResponse(true, "Login successful", jwt);
        } else {
            return new LoanResponse(false, "Invalid credentials", null);
        }
    }

    @PostMapping("/logout")
    public LoanResponse logout(@RequestParam String email) {
        jwtUtil.invalidateToken(email);
        return new LoanResponse(true, "Logged out successfully", null);
    }


//    @PostMapping("/add")
//    public ResponseEntity<LoanDTO> applyForLoan(@Valid @RequestBody LoanDTO loanDTO) {
//        return ResponseEntity.ok(loanService.applyForLoan(loanDTO));
//    }
//
//    @GetMapping("/{email}")
//    public ResponseEntity<LoanDTO> getLoanByEmail(@PathVariable String email) {
//        return ResponseEntity.ok(loanService.getLoanByEmail(email));
//    }
//
//    @GetMapping("/getAll")
//    public ResponseEntity<List<LoanDTO>> getAllLoans() {
//        return ResponseEntity.ok(loanService.getAllLoans());
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerCustomer(@RequestBody Customer customer){
//
//        boolean status = customerService.saveCustomer(customer);
//
//        if(status){
//            return new ResponseEntity<>("Success", HttpStatus.CREATED);
//        }
//        else {
//            return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody Customer customer){
//
//        UsernamePasswordAuthenticationToken token = new
//                UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPwd());
//
//        //verify login details valid or not
//        Authentication authenticate = authenticationManager.authenticate(token);
//
//        boolean authenticatedStatus = authenticate.isAuthenticated();
//        if(authenticatedStatus){
//
//            // Generate JWT and send to the client
//            // Add here
//            String jwt = jwtUtil.generateToken(customer.getEmail());
//
//            return new ResponseEntity<>(jwt,HttpStatus.OK);
//        }
//        else{
//            return new ResponseEntity<>("Invalid Credentials", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(@RequestParam String email) {
//        jwtUtil.invalidateToken(email);
//        return ResponseEntity.ok("Logged out successfully.");
//    }
}