package br.com.anima.utils;

import com.badlogic.gdx.utils.Array;

public class RunnablePool {
	
	private Array<Runnable> runnables;
	
	public RunnablePool() {
		runnables = new Array<Runnable>();
	}
	
	public void add(Runnable r) {
		this.runnables.add(r);
	}
	
	public void run() {
		for(Runnable runnable : runnables) {
			runnable.run();
		}
		
		runnables.clear();
	}

}
