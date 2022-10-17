package com.rihaviour.Bank_API_REST.services;

import com.rihaviour.Bank_API_REST.entities.AccountDTO;
import com.rihaviour.Bank_API_REST.entities.accounts.Account;
import com.rihaviour.Bank_API_REST.entities.accounts.Checking;
import com.rihaviour.Bank_API_REST.entities.accounts.StudentChecking;
import com.rihaviour.Bank_API_REST.others.Money;
import com.rihaviour.Bank_API_REST.repositories.AccountHolderRepository;
import com.rihaviour.Bank_API_REST.repositories.StudentCheckingRepository;
import com.rihaviour.Bank_API_REST.repositories.CheckingRepository;
import com.rihaviour.Bank_API_REST.repositories.UserRepository;
import com.rihaviour.Bank_API_REST.services.interfaces.AccountServiceInterface;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService implements AccountServiceInterface {

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    UserRepository userRepository;

//    public Account createChecking(double startingBalance, AccountHolder primaryOwner) {
//
//
//        Money balance = new Money(new BigDecimal(startingBalance));
////todo -------------->
//
//        /**
//         * T.T
//         */
////        int age = Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears();
///**
// * Usando el Period.between().getYears() me da error, por eso lo pongo en el constructor en un nuevo int age.
// */
//        if (accountHolderRepository.findById(primaryOwner.getId()).isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The User introduced for Primary Owner doesn't Exists");
//        }
//
//
//        if (primaryOwner.getAge() > 24) {
//
//            Checking checking = new Checking();
//
//            if (balance.getAmount().compareTo(checking.getMinimumBalance()) < 0) {
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The balance cannot be lower than" + checking.getMinimumBalance());
//            }
//
//            checking.setBalance(balance);
//            checking.setPrimaryOwner(primaryOwner);
//
//            return checkingRepository.save(checking);
//
//            /**TODO                                 DONETE
//             * Me tiene que lanzar error si el balance es < que el balance mínimo para crear la cuenta,
//             * que no es lo mismo que minimumBalance. Se hace automático con las validaciones? Entiendo que debería, sino para q estan?
//             * No se puede hacer con las validaciones ya que el balance.getAmount() vive en la clase Money y
//             * nuestra validation es diferente según el tipo de cuenta(checking, Student, Saving, Credit).
//             *
//             *todo                               -----ES CORRECTO ???
//             */
//
//        }
//        StudentChecking studentChecking = new StudentChecking(balance, primaryOwner);
//
//        return studentCheckingRepository.save(studentChecking);
//
//        /**
//         * Se pueden guardar todas las cuentas en el account repository? Es más eficiente?
//         *
//         */
//    }


    public Account createChecking(AccountDTO accountDTO) {

        Checking checking = new Checking();
        StudentChecking studentChecking = new StudentChecking();

        //todo    ESTO HACE FALTA?
        checking.setSecondaryOwner(null);
        studentChecking.setSecondaryOwner(null);

        AccountHolder primaryOwner = accountHolderRepository.findByUserName(accountDTO.getPrimaryOwnerUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "The User introduced as Primary Owner doesn't Exists")
        );

        if (accountDTO.getSecondaryOwnerUsername() != null) {
            AccountHolder secondaryOwner = accountHolderRepository.findByUserName(accountDTO.getSecondaryOwnerUsername()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "The User introduced as Secondary Owner doesn't Exists"));
            checking.setSecondaryOwner(secondaryOwner);
            studentChecking.setSecondaryOwner(secondaryOwner);
        }

        if (primaryOwner.getAge() > 24) {

            if (accountDTO.getBalance().getAmount().compareTo(checking.getMinimumBalance()) < 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The balance cannot be lower than" + checking.getMinimumBalance());
            }

            checking.setBalance(accountDTO.getBalance());
            checking.setPrimaryOwner(primaryOwner);

            return checkingRepository.save(checking);
        }

        studentChecking.setBalance(accountDTO.getBalance());
        studentChecking.setPrimaryOwner(primaryOwner);
        return studentCheckingRepository.save(studentChecking);
    }

    public List<Account> getAllAccounts() {
        if (checkingRepository.count() == 0) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No checking accounts found");
        }
        return checkingRepository.findAll();
    }
}
