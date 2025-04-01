package com.exampleLoan.serviceImpl;

import com.exampleLoan.dto.request.LoanDTO;
import com.exampleLoan.entity.Loan;
import com.exampleLoan.exception.ResourceNotFoundException;
import com.exampleLoan.repo.LoanRepository;
import com.exampleLoan.service.LoanService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final ModelMapper modelMapper;

    public LoanServiceImpl(LoanRepository loanRepository, ModelMapper modelMapper) {
        this.loanRepository = loanRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LoanDTO applyForLoan(LoanDTO loanDTO) {
        Loan loan = modelMapper.map(loanDTO, Loan.class);
        Loan savedLoan = loanRepository.save(loan);
        return modelMapper.map(savedLoan, LoanDTO.class);
    }

    @Override
    public LoanDTO getLoanByEmail(String email) {
        Loan loan = loanRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found for email: " + email));
        return modelMapper.map(loan, LoanDTO.class);
    }

    @Override
    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(loan -> modelMapper.map(loan, LoanDTO.class))
                .collect(Collectors.toList());
    }
}