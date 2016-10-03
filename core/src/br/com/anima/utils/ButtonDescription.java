package br.com.anima.utils;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ButtonDescription {

	private String text;
	private ClickListener listener;
	private ButtonColor color;

	public ButtonDescription(String text, ButtonColor color, ClickListener listener) {
		this.text = text;
		this.listener = listener;
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ClickListener getListener() {
		return listener;
	}

	public void setListener(ClickListener listener) {
		this.listener = listener;
	}

	public ButtonColor getColor() {
		return color;
	}

	public void setColor(ButtonColor color) {
		this.color = color;
	}

}
