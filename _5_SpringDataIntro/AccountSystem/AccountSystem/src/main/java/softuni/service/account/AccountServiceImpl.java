package softuni.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softuni.entities.Account;
import softuni.repositories.AccountRepository;

import java.math.BigDecimal;

import static softuni.constants.ExceptionMessages.INSUFFICIENT_FUNDS;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void registerUserAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void depositAmount(long accountId, BigDecimal amount) {

        Account account = accountRepository.findAccountById(accountId);

        account.setBalance(account.getBalance().add(amount));

        accountRepository.save(account);
    }

    @Override
    public void withdrawAmount(long accountId, BigDecimal amount) {

        Account account = accountRepository.findAccountById(accountId);

        BigDecimal currentBalance = account.getBalance();

        if (currentBalance.compareTo(amount) < 0) {
            throw new IllegalStateException(INSUFFICIENT_FUNDS);
        }

        account.setBalance(account.getBalance().subtract(amount));

        accountRepository.save(account);
    }

    @Override
    public void transferAmount(long transferFrom, long transferTo, BigDecimal amount) {
        withdrawAmount(transferFrom, amount);

        depositAmount(transferTo, amount);
    }

}
