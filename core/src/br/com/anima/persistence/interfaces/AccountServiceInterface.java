package br.com.anima.persistence.interfaces;

import java.util.Date;
import java.util.List;

import br.com.anima.utils.CharacterDescription;

public interface AccountServiceInterface {
	
	public void createCharacter(String name);
	
	public void registerLog(Date eventDate);
	
	public List<CharacterDescription> retrieveActiveCharacters(String hash);

}
