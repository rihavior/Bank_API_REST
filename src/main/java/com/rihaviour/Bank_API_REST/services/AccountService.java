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

        AccountHolder primaryOwner = accountHolderRepository.findByUserName(accountDTO.getPrimaryOwnerUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The User introduced as Primary Owner doesn't Exists")
        );

        if (accountDTO.getSecondaryOwnerUsername() == null) {

            if (primaryOwner.getAge() > 24) {
//
//            if (accountDTO.getBalance().getAmount().compareTo(checking.getMinimumBalance()) < 0) {
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The balance cannot be lower than" + checking.getMinimumBalance());
//            }
//
            Checking checking = new Checking(new Money(new BigDecimal(accountDTO.getBalance())), primaryOwner);

            return checkingRepository.save(checking);
            }
            StudentChecking studentChecking = new StudentChecking(new Money(new BigDecimal(accountDTO.getBalance())), primaryOwner);
            return studentCheckingRepository.save(studentChecking);
        }

        AccountHolder secondaryOwner = accountHolderRepository.findByUserName(accountDTO.getSecondaryOwnerUsername()).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The User introduced as Secondary Owner doesn't Exists"));

        if (primaryOwner.getAge() > 24) {

            Checking checking = new Checking();

            if (new BigDecimal(accountDTO.getBalance()).compareTo(checking.getMinimumBalance()) < 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The balance cannot be lower than" + checking.getMinimumBalance());
            }

            checking.setBalance(new Money(new BigDecimal(accountDTO.getBalance())));
            checking.setPrimaryOwner(primaryOwner);
            checking.setSecondaryOwner(secondaryOwner);

            return checkingRepository.save(checking);
        }
        StudentChecking studentChecking = new StudentChecking(new Money(new BigDecimal(accountDTO.getBalance())), primaryOwner, secondaryOwner);
        return studentCheckingRepository.save(studentChecking);



//        AccountHolder secondaryOwner = accountHolderRepository.findByUserName(accountDTO.getSecondaryOwnerUsername()).get(); //todo  PUEDE dar NULL??
        /**
         * Si no tiene secondary owner petara?
         */

//        if (primaryOwner.getAge() > 24) {
//
//            Checking checking = new Checking();
//
//            if (accountDTO.getBalance().getAmount().compareTo(checking.getMinimumBalance()) < 0) {
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The balance cannot be lower than" + checking.getMinimumBalance());
//            }
//
//            checking.setBalance(accountDTO.getBalance());
//            checking.setPrimaryOwner(primaryOwner);
//
//
//            if (!accountDTO.getSecondaryOwnerUsername().isEmpty()) {
//
//                AccountHolder secondaryOwner = accountHolderRepository.findByUserName(accountDTO.getSecondaryOwnerUsername()).orElseThrow(
//                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The User introduced as Secondary Owner doesn't Exists")
//                );
//                checking.setSecondaryOwner(accountHolderRepository.findByUserName(accountDTO.getSecondaryOwnerUsername()).get());//todo  PUEDE DAR NULL???
//            }
//            return checkingRepository.save(checking);
//        }

//        if (!accountDTO.getSecondaryOwnerUsername().isEmpty()) {
//
//            AccountHolder secondaryOwner = accountHolderRepository.findByUserName(accountDTO.getSecondaryOwnerUsername()).orElseThrow(
//                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The User introduced as Secondary Owner doesn't Exists")
//            );
//            StudentChecking studentChecking = new StudentChecking(accountDTO.getBalance(), primaryOwner, secondaryOwner);
//            return studentCheckingRepository.save(studentChecking);
//        }
//        Checking checking = new Checking(accountDTO.getBalance(),accountHolderRepository.findByUserName(accountDTO.getPrimaryOwnerUsername()).get());
//
//        return checkingRepository.save(checking);
    }


        public List<Account> getAllAccounts () {
            //todo -----> Añadir if (no existe ninguna account) lanzar excepcion
            return checkingRepository.findAll();
        }
    }
