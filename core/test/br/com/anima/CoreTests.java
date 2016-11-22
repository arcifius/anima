package br.com.anima.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.anima.controller.GameController;

public class CoreTests {
	@Test
	public void loginTest() {
		
		loggedIn = false;		
		assertEquals(true, loggedIn);			
		
	}
}