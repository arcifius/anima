package br.com.anima.screens;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.components.additional.ButtonComponent;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import br.com.anima.Igniter;
import br.com.anima.utils.CharacterDescription;
import br.com.anima.utils.InputTransform;
import br.com.anima.utils.Objects;

@Deprecated
public class SelectCharacterScreen implements Screen, InputProcessor {

	private SceneLoader sl;
	private Skin skin;
	private Viewport viewport;
	private ItemWrapper rootItem;
	private Igniter game;
	private LinkedList<CharacterDescription> characters;
	private ThreadLocalRandom rand;
	private HashMap<CharacterDescription, ParticleEffect> particleSpawns;
	private BitmapFont font;

	public SelectCharacterScreen(Igniter game) {
		this.game = game;

		characters = (LinkedList<CharacterDescription>) Objects.gameController.retrieveActiveCharacters();

		rand = ThreadLocalRandom.current();

		particleSpawns = new HashMap<CharacterDescription, ParticleEffect>();
	}

	@Override
	public void show() {

		font = new BitmapFont(Gdx.files.internal("customFonts/amaticSC.fnt"));

		// Load default skin
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		viewport = new FitViewport(137, 77);
		sl = new SceneLoader();
		sl.loadScene("char_select", viewport);
		rootItem = new ItemWrapper(sl.getRoot());

		// Enable buttons
		sl.addComponentsByTagName("button", ButtonComponent.class);

		// Enabling input
		Gdx.input.setInputProcessor(this);

		// Randomly generating character position
		for (CharacterDescription character : characters) {
			int x = rand.nextInt((1200 - 100) + 1) + 100;
			int y = rand.nextInt((500 - 200) + 1) + 200;
			character.getBodySprite().setPosition(x, y);

			// Particle configuration
			ParticleEffect blueParticles = new ParticleEffect();
			blueParticles.load(Gdx.files.internal("particles/blue-particle"), Gdx.files.internal("particles"));
			blueParticles.getEmitters().first().setPosition(x + character.getBodySprite().getWidth() / 2, y);
			blueParticles.start();
			blueParticles.setPosition(x + character.getBodySprite().getWidth() / 2, y);

			particleSpawns.put(character, blueParticles);
		}
	}

	public void act() {

	}

	@Override
	public void render(float delta) {

		act();
		Gdx.gl.glClearColor(255 / 225f, 255 / 225f, 255 / 225f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sl.getEngine().update(Gdx.graphics.getDeltaTime());

		Objects.spriteBatch.begin();

		for (CharacterDescription character : characters) {

			Label nome = new Label(character.getNome(), skin);
			nome.getStyle().font = this.font;
			nome.setSize(character.getBodySprite().getWidth(), 25);
			nome.setPosition(character.getBodySprite().getX(), character.getBodySprite().getY() - 25);
			nome.setAlignment(Align.center);

			nome.draw(Objects.spriteBatch, 1);

			character.getBodySprite().draw(Objects.spriteBatch);

			ParticleEffect pe = particleSpawns.get(character);

			pe.update(Gdx.graphics.getDeltaTime());
			pe.draw(Objects.spriteBatch);
			if (pe.isComplete())
				pe.reset();

		}

		Objects.spriteBatch.end();

		// loginStage.act();
		// loginStage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		// this.selectionStage.dispose();

		// TODO: dispose particles

		this.skin.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == Buttons.LEFT) {

			float pointerX = InputTransform.getCursorToModelX(1366, screenX);
			float pointerY = InputTransform.getCursorToModelY(768, screenY);

			for (CharacterDescription character : characters) {

				Vector2 clickedPoint = new Vector2(pointerX, pointerY);

				if (character.getBodySprite().getBoundingRectangle().contains(clickedPoint)) {

					if (character.isChoosen()) {

						// TODO: use setView
						// game.setScreen(new GameScreen(this.game, character));

					} else {
						System.out.println(character.getNome() + " foi selecionado!");

						ParticleEffect yellowParticles = new ParticleEffect();
						yellowParticles.load(Gdx.files.internal("particles/yellow-particle"),
								Gdx.files.internal("particles"));
						yellowParticles.getEmitters().first().setPosition(
								character.getBodySprite().getX() + character.getBodySprite().getWidth() / 2,
								character.getBodySprite().getY());
						yellowParticles.start();
						yellowParticles.setPosition(
								character.getBodySprite().getX() + character.getBodySprite().getWidth() / 2,
								character.getBodySprite().getY());

						normalizeCharactersParticles();

						particleSpawns.put(character, yellowParticles);

						character.setChoosen(true);
					}
				}

			}

		}
		return false;
	}

	private void normalizeCharactersParticles() {
		for (CharacterDescription character : this.characters) {
			
			character.setChoosen(false);
			
			ParticleEffect blueParticles = new ParticleEffect();
			blueParticles.load(Gdx.files.internal("particles/blue-particle"), Gdx.files.internal("particles"));
			blueParticles.getEmitters().first().setPosition(
					character.getBodySprite().getX() + character.getBodySprite().getWidth() / 2,
					character.getBodySprite().getY());
			blueParticles.start();
			blueParticles.setPosition(character.getBodySprite().getX() + character.getBodySprite().getWidth() / 2,
					character.getBodySprite().getY());

			particleSpawns.put(character, blueParticles);
		}
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
