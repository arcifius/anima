package br.com.anima.persistence.implementations.list;

import java.util.Arrays;
import java.util.HashMap;

import br.com.anima.persistence.interfaces.UserServiceInterface;

public class UserService implements UserServiceInterface {

	private static HashMap<String, char[]> users = new HashMap<String, char[]>();

	static {
		users.put("augusto", "1234".toCharArray());
		users.put("tulio", "1234".toCharArray());
		users.put("fabio", "1234".toCharArray());
		users.put("gabriel", "1234".toCharArray());
	}

	@Override
	public boolean authenticate(String user, char[] password) {
			
		if (users.containsKey(user)) {

			char[] pass = users.get(user);

			if (Arrays.equals(pass, password)) {
				return true;
			}

		}

		return false;
	}

}
