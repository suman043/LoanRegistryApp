package com.exampleLoan.controller;

import com.exampleLoan.dto.LoanDTO;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<LoanDTO> applyForLoan(@Valid @RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loanService.applyForLoan(loanDTO));
    }

    @GetMapping("/{email}")
    public ResponseEntity<LoanDTO> getLoanByEmail(@PathVariable String email) {
        return ResponseEntity.ok(loanService.getLoanByEmail(email));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody Customer customer){

        boolean status = customerService.saveCustomer(customer);

        if(status){
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Customer customer){

        UsernamePasswordAuthenticationToken token = new
                UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPwd());

        //verify login details valid or not
        Authentication authenticate = authenticationManager.authenticate(token);

        boolean authenticatedStatus = authenticate.isAuthenticated();
        if(authenticatedStatus){

            // Generate JWT and send to the client
            // Add here
            String jwt = jwtUtil.generateToken(customer.getEmail());

            return new ResponseEntity<>(jwt,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Invalid Credentials", HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/test")
//    public ResponseEntity<String> testAccess() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName(); // Extract authenticated user's email
//
//        return new ResponseEntity<>("Access granted! Authenticated as: " + userEmail, HttpStatus.OK);
//    }
}