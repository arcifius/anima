package br.com.anima.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Box2DComponent implements Component {

	public Body body;
	public Fixture fixture;
	
	public Box2DComponent() {
		
	}

	public Box2DComponent(Body body, Fixture fixture) {		
		this.body = body;
		this.fixture = fixture;
	}

}
