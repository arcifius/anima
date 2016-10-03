package br.com.anima.persistence.implementations.list;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import br.com.anima.persistence.interfaces.AccountServiceInterface;
import br.com.anima.utils.CharacterDescription;

public class AccountService implements AccountServiceInterface {

	private static HashMap<String, List<CharacterDescription>> user_characters = new HashMap<String, List<CharacterDescription>>();

	static {
		CharacterDescription c1, c2;
		
		TextureAtlas textureAtlas_persona1 = new TextureAtlas(Gdx.files.internal("gameSprites/persona1.atlas"));

		// augusto
		LinkedList<CharacterDescription> characters_augusto = new LinkedList<CharacterDescription>();
		// Char Arcifius
		c1 = new CharacterDescription();
		c1.setNome("Arcifius");
		c1.setBodyTexture((TextureRegion)(textureAtlas_persona1.findRegion("walk-down-1")));
		characters_augusto.add(c1);

		// Char Terbirous
		c2 = new CharacterDescription();
		c2.setNome("Terbirous");
		c2.setBodyTexture((TextureRegion)(textureAtlas_persona1.findRegion("walk-down-1")));
		characters_augusto.add(c2);
		
		// Adicionando
		user_characters.put("c449418429ea954722fb0fa1047ab89a9481afc1", characters_augusto);

		// tulio
		LinkedList<CharacterDescription> characters_tulio = new LinkedList<CharacterDescription>();
		// Char Tulimshar
		c1 = new CharacterDescription();
		c1.setNome("Tulimshar");
		c1.setBodyTexture((TextureRegion)(textureAtlas_persona1.findRegion("walk-down-1")));
		characters_tulio.add(c1);
		// Adicionando
		user_characters.put("?", characters_tulio);
	}

	@Override
	public void createCharacter(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerLog(Date eventDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CharacterDescription> retrieveActiveCharacters(String hash) {
		return AccountService.user_characters.get(hash);
	}

}
