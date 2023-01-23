package softuni.service.account;

import softuni.entities.Account;

import java.math.BigDecimal;

public interface AccountService {

    void registerUserAccount(Account account);

    void depositAmount(long accountId, BigDecimal amount);

    void withdrawAmount(long accountId, BigDecimal amount);

    void transferAmount(long transferFrom, long transferTo, BigDecimal amount);



    /*void openAccount(Account account);

    void withdrawMoney(BigDecimal money, Long id);

    void transferMoney(BigDecimal money, Long transferFrom, Long TransferTo);
*/
}