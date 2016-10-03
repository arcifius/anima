package br.com.anima.controller;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.anima.persistence.interfaces.AccountServiceInterface;
import br.com.anima.persistence.interfaces.UserServiceInterface;
import br.com.anima.utils.CharacterDescription;
import br.com.anima.utils.Values;

public class GameController {

	private UserServiceInterface userService;
	private AccountServiceInterface accountService;
	private String userHash = "c449418429ea954722fb0fa1047ab89a9481afc1";

	public GameController() {

		switch (Values.STORAGE_TYPE) {

		case DATABASE_MYSQL:
			// Not implemented yet
			// userService = new
			// br.com.anima.persistence.implementations.mysql.UserService();
			break;
		case LOCAL_STORAGE:
			userService = new br.com.anima.persistence.implementations.list.UserService();
			accountService = new br.com.anima.persistence.implementations.list.AccountService();
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
