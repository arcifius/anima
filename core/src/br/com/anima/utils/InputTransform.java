package br.com.anima.utils;

public class InputTransform {
	private static int appWidth = 1366;
	private static int appHeight = 768;

	public static float getCursorToModelX(int screenX, int cursorX) {
		return (((float) cursorX) * appWidth) / ((float) screenX);
	}

	public static float getCursorToModelY(int screenY, int cursorY) {
		return ((float) (screenY - cursorY)) * appHeight / ((float) screenY);
	}
}