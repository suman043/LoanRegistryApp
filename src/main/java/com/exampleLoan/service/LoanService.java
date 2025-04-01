package com.exampleLoan.service;

import com.exampleLoan.dto.request.LoanDTO;

import java.util.List;

public interface LoanService {

    LoanDTO applyForLoan(LoanDTO loanDTO);
    LoanDTO getLoanByEmail(String email);
    List<LoanDTO> getAllLoans();

}