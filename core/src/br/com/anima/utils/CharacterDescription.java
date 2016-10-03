package br.com.anima.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CharacterDescription {

	private String nome;
	private Sprite bodySprite;
	private Sprite hairSprite;
	private Sprite torsoSprite;
	private Sprite legsSprite;
	private Sprite shoesSprite;
	private boolean choosen;

	public boolean isChoosen() {
		return choosen;
	}

	public void setChoosen(boolean choosen) {
		this.choosen = choosen;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Sprite getBodySprite() {
		return bodySprite;
	}

	public void setBodyTexture(TextureRegion bodyTexture) {		
		Sprite sprite = new Sprite(bodyTexture);
		sprite.setPosition(0, 0);
		//sprite.setSize(bodyTexture.getWidth(), bodyTexture.getHeight());
		sprite.setOrigin(0, 0);
		
		this.bodySprite = sprite;
	}

	public Sprite getHairSprite() {
		return hairSprite;
	}

	public void setHairTexture(TextureRegion hairTexture) {
		Sprite sprite = new Sprite(hairTexture);
		sprite.setPosition(0, 0);
		sprite.setOrigin(0, 0);
		//sprite.setSize(hairTexture.getWidth(), hairTexture.getHeight());
		this.hairSprite = sprite;
	}

	public Sprite getTorsoSprite() {		
		return torsoSprite;
	}

	public void setTorsoTexture(TextureRegion torsoTexture) {
		Sprite sprite = new Sprite(torsoTexture);
		sprite.setPosition(0, 0);
		sprite.setOrigin(0, 0);
		//sprite.setSize(torsoTexture.getWidth(), torsoTexture.getHeight());

		this.torsoSprite = sprite;
	}

	public Sprite getLegsSprite() {
		return legsSprite;
	}

	public void setLegsTexture(TextureRegion legsTexture) {
		Sprite sprite = new Sprite(legsTexture);
		sprite.setPosition(0, 0);
		sprite.setOrigin(0, 0);
		//sprite.setSize(legsTexture.getWidth(), legsTexture.getHeight());
		
		this.legsSprite = sprite;
	}

	public Sprite getShoesSprite() {
		return shoesSprite;
	}

	public void setShoesTexture(TextureRegion shoesTexture) {
		Sprite sprite = new Sprite(shoesTexture);
		sprite.setPosition(0, 0);
		sprite.setOrigin(0, 0);
		//sprite.setSize(shoesTexture.getWidth(), shoesTexture.getHeight());
		
		this.shoesSprite = sprite;
	}

}
