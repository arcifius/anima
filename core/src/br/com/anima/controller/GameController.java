package br.com.anima.controller;

import br.com.anima.persistence.implementations.list.AccountService;
import br.com.anima.persistence.implementations.list.UserService;
import br.com.anima.persistence.interfaces.AccountServiceInterface;
import br.com.anima.persistence.interfaces.UserServiceInterface;
import br.com.anima.utils.CharacterDescription;
import br.com.anima.utils.Values;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

/**
 * TODO: Analyse what should be injectable the whole class or services
 *
 * Note: this class is outdated and should be revised
 */
public class GameController {

    private final UserServiceInterface userService;
    private final AccountServiceInterface accountService;
    private String userHash = "c449418429ea954722fb0fa1047ab89a9481afc1";

    public GameController() {
        switch (Values.STORAGE_TYPE) {
            case LOCAL_STORAGE:
                userService = new UserService();
                accountService = new AccountService();
                break;
            default:
                throw new RuntimeException("Invalid STORAGE_TYPE were provided!");
        }
    }

    public boolean authenticateUser(String username, char[] password) {

        if (this.userService.authenticate(username, password)) {

            userHash = DigestUtils.sha1Hex(username + new String(password));

            return true;
        }

        return false;

    }

    public List<CharacterDescription> retrieveActiveCharacters() {

        return this.accountService.retrieveActiveCharacters(userHash);

    }

}
