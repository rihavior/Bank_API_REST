# Bank_API_REST

## Spring Application to manage Users and Accounts in a simple Bank service.

The requests accepted are:

**GET:**

/get_accounts

    · Show a List off all account registered.

/get_account/{accountId}

    · Show an individual account

**POST:**

/create_checking

    · Creates a checking account. Requires an account as body.

/create_savings

    · Creates a savings account. Requires an account as body.

/create_credit_card

    · Creates a credit card account. Requires an account as body

/create_account_holder

    · Creates a new account holder. Requires an account holder as body.

/create_third_party

    · Creates a third party. Requires a third party as body.

**PATCH:**

/transfer_funds

    · Transfer funds from an account to another. Requires a transaction as body.

/modify_balance

    · Set a balance to a spezified quantity. Requires an account as body.

/third_transfer

    · Third party modify balance. Requires a transaction as body.


All Users start with the password "1234", at this point, there is a missing request to update it.
Swagger-UI dependency implemented. You can use a user-friendly interface to play a bit with the app throw the next link.


http://localhost:8080/swagger-ui.html

![image](https://user-images.githubusercontent.com/86107544/197408791-c3dd3501-f3b4-4db2-8a5d-33a18f271f9f.png)



This application regards the requirments from this exercise:

**_Requirements_**

_The system must have 4 types of accounts: StudentChecking, Checking, Savings, and CreditCard._

**_Checking_**


_Checking Accounts should have:_

_A balance
A secretKey
A PrimaryOwner
An optional SecondaryOwner
A minimumBalance
A penaltyFee
A monthlyMaintenanceFee
A creationDate
A status (FROZEN, ACTIVE)_

**_StudentChecking_**


_Student Checking Accounts are identical to Checking Accounts except that they do NOT have:_

_A monthlyMaintenanceFee
A minimumBalance_

**_Savings_**


_Savings are identical to Checking accounts except that they_

_Do NOT have a monthlyMaintenanceFee_
_Do have an interestRate_

**_CreditCard_**


_CreditCard Accounts have:_

_A balance_
_A PrimaryOwner_
_An optional SecondaryOwner_
_A creditLimit_
_An interestRate_
_A penaltyFee_

_The system must have 3 types of Users: Admins and AccountHolders._

**_AccountHolders_**


_The AccountHolders should be able to access their own accounts and only their accounts when passing the correct credentials using Basic Auth. AccountHolders have:_

_A name
Date of birth
A primaryAddress (which should be a separate address class)
An optional mailingAddress_

**_Admins_**


_Admins only have a name_


**_ThirdParty_**


_The ThirdParty Accounts have a hashed key and a name._


_Admins can create new accounts. When creating a new account they can create Checking, Savings, or CreditCard Accounts._

**_Savings_**


_Savings accounts have a default interest rate of 0.0025
Savings accounts may be instantiated with an interest rate other than the default, with a maximum interest rate of 0.5
Savings accounts should have a default minimumBalance of 1000
Savings accounts may be instantiated with a minimum balance of less than 1000 but no lower than 100_

**_CreditCards_**


_CreditCard accounts have a default creditLimit of 100
CreditCards may be instantiated with a creditLimit higher than 100 but not higher than 100000
CreditCards have a default interestRate of 0.2
CreditCards may be instantiated with an interestRate less than 0.2 but not lower than 0.1_

**_CheckingAccounts_**


_When creating a new Checking account, if the primaryOwner is less than 24, a StudentChecking account should be created otherwise a regular Checking Account should be created.
Checking accounts should have a minimumBalance of 250 and a monthlyMaintenanceFee of 12_

_Interest and Fees should be applied appropriately_

**_PenaltyFee_**


_The penaltyFee for all accounts should be 40.
If any account drops below the minimumBalance, the penaltyFee should be deducted from the balance automatically_

**_InterestRate_**


_Interest on savings accounts is added to the account annually at the rate of specified interestRate per year. That means that if I have 1000000 in a savings account with a 0.01 interest rate, 1% of 1 Million is added to my account after 1 year. When a savings account balance is accessed, you must determine if it has been 1 year or more since either the account was created or since interest was added to the account, and add the appropriate interest to the balance if necessary._

_Interest on credit cards is added to the balance monthly. If you have a 12% interest rate (0.12) then 1% interest will be added to the account monthly. When the balance of a credit card is accessed, check to determine if it has been 1 month or more since the account was created or since interested was added, and if so, add the appropriate interest to the balance._


**_Account Access_**

**_Admins_**


_Admins should be able to access the balance for any account and to modify it._

**_AccountHolders_**


_AccountHolders should be able to access their own account balance
Account holders should be able to transfer money from any of their accounts to any other account (regardless of owner). The transfer should only be processed if the account has sufficient funds. The user must provide the Primary or Secondary owner name and the id of the account that should receive the transfer._

**_Third-Party Users_**


_There must be a way for third-party users to receive and send money to other accounts.
Third-party users must be added to the database by an admin.
In order to receive and send money, Third-Party Users must provide their hashed key in the header of the HTTP request. They also must provide the amount, the Account id and the account secret key._
