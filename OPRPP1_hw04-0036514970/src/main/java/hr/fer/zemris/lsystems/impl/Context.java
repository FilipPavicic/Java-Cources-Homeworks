package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Razred omogućuje spremanje TurtleState na stog
 * tepruža osnovne metode za rad s tim stogom. 
 * 
 * @author Filip Pavicic
 *
 */
public class Context {
	private ObjectStack<TurtleState> stack;
	
	public Context() {
		stack = new ObjectStack<TurtleState>();
	}

	/**
	 * vraća stanje s vrha stoga bez uklanjanja
	 * 
	 * @return
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
		
	}
	
	/**
	 * na vrh gura predano stanje
	 * 
	 * @param state
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * briše jedno stanje s vrha
	 * 
	 */
	public void popState() {
		stack.pop();
	}
	
}