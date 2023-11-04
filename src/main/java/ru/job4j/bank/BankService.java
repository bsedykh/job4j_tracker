package ru.job4j.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model of a banking system. Provides basic banking operations such as:
 * <ul>
 * <li>register new user</li>
 * <li>create bank account</li>
 * <li>transfer money from one bank account to another</li>
 * </ul>
 *
 * @author Boris Sedykh
 * @version 1.0
 */
public class BankService {
    /**
     * Hash map for storing users and their bank accounts.
     */
    private final Map<User, List<Account>> users = new HashMap<>();

    /**
     * Adds new user.
     * If the user was previously added, then does nothing.
     * @param user new user to add
     */
    public void addUser(User user) {
        users.putIfAbsent(user, new ArrayList<>());
    }

    /**
     * Deletes the user with the given passport.
     * If the user doesn't exist, then does nothing.
     * @param passport passport of the user
     */
    public void deleteUser(String passport) {
        users.remove(new User(passport, ""));
    }

    /**
     * Adds bank account for the user with the given passport.
     * If the user doesn't exist or already has this account, then does nothing.
     * @param passport passport of the user
     * @param account bank account
     */
    public void addAccount(String passport, Account account) {
        User user = findByPassport(passport);
        if (user != null) {
            List<Account> accounts = users.get(user);
            if (!accounts.contains(account)) {
                accounts.add(account);
            }
        }
    }

    /**
     * Finds user by passport.
     * @param passport passport of the user
     * @return user or {@code null} if the user is not found
     */
    public User findByPassport(String passport) {
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Finds user's account by user passport and account requisite.
     * @param passport passport of the user
     * @param requisite requisite of account
     * @return account or {@code null} if the account is not found
     */
    public Account findByRequisite(String passport, String requisite) {
        User user = findByPassport(passport);
        if (user != null) {
            List<Account> accounts = users.get(user);
            for (Account account : accounts) {
                if (account.getRequisite().equals(requisite)) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * Transfers money from one account to another.
     * If source / destination account is not found or source account doesn't have enough money,
     * then returns {@code false} (transfer will not be performed).
     * @param srcPassport passport of the user which source account belongs to
     * @param srcRequisite requisite of the source account
     * @param destPassport passport of the user which destination account belongs to
     * @param destRequisite requisite of the destination account
     * @param amount amount of money to transfer
     * @return {@code true} if transfer is successful, or {@code false} otherwise
     */
    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String destPassport, String destRequisite, double amount) {
        Account srcAccount = findByRequisite(srcPassport, srcRequisite);
        Account destAccount = findByRequisite(destPassport, destRequisite);
        if (srcAccount == null || srcAccount.getBalance() < amount || destAccount == null) {
            return false;
        }
        srcAccount.setBalance(srcAccount.getBalance() - amount);
        destAccount.setBalance(destAccount.getBalance() + amount);
        return true;
    }

    /**
     * Gets the list of user's accounts.
     * @param user user
     * @return {@code List} containing user's accounts
     */
    public List<Account> getAccounts(User user) {
        return users.get(user);
    }
}
