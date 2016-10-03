package br.com.anima.persistence.interfaces;

public interface UserServiceInterface {
	
	public boolean authenticate(String user, char[] password);

}
