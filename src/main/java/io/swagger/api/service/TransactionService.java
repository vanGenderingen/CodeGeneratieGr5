package io.swagger.api.service;

import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.TransactionRepository;
import io.swagger.api.repository.UserRepository;
import io.swagger.api.specification.SearchCriteria;
import io.swagger.api.specification.TransactionSpecification;
import io.swagger.model.Account;
import io.swagger.model.Role;
import io.swagger.model.User;
import io.swagger.model.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountsRepository;

    @Autowired
    private UserRepository userRepository;


    public Transaction add(Transaction transaction) {
        //Verify if the IBANs are from real accounts
        Account fromAccount = verificateIBAN(transaction.getFromIBAN());
        Account toAccount = verificateIBAN(transaction.getToIBAN());

        User userPerforming = userRepository.getUserByUserID(transaction.getUserPerforming());

        if (userPerforming == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user performing the transaction is not valid");
        }

        User fromUser = userRepository.getUserByUserID(fromAccount.getUserID());
        User toUser = userRepository.getUserByUserID(toAccount.getUserID());

        if(!userPerforming.getRoles().contains(Role.ROLE_EMPLOYEE)){
            if(userPerforming.getUserID() != fromUser.getUserID()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't perform a transaction from an account that isn't yours");
            }
            if (toAccount.getType() == Account.TypeEnum.SAVINGS && fromUser.getUserID() != toUser.getUserID()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't transfer money to a savings account that isn't yours");
            }
            if (fromAccount.getType() == Account.TypeEnum.SAVINGS && fromUser.getUserID() != toUser.getUserID()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't transfer money from a savings account that isn't yours");
            }
        }

        if (fromAccount.getBalance() < fromAccount.getMinBal()+transaction.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You don't have enough money to transfer this amount");
        }
        if (fromUser.getTransactionLimit() < transaction.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't withdraw more than your transaction limit");
        }

        fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());
        toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());
        accountsRepository.save(fromAccount);
        accountsRepository.save(toAccount);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Integer offset, Integer limit, SearchCriteria filters, Principal principal) {

        //Add user verification

        return getAllTransactions(PageRequest.of(offset, limit), new TransactionSpecification(filters));
    }

    public List<Transaction> getAllTransactions(PageRequest pageRequest, TransactionSpecification specification) {
        return transactionRepository.findAll(specification, pageRequest).getContent();
    }

    public Account verificateIBAN(String IBAN) {
        if (accountsRepository.getAccountByIBAN(IBAN) != null) {
            return accountsRepository.getAccountByIBAN(IBAN);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The IBAN is not valid");
    }
}
