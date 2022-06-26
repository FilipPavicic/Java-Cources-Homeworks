package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
	
	public static void main(String[] args) {
		String exp = args[0];
		ObjectStack stack = new ObjectStack();
		
		String[] devided_exp=exp.split(" ");
		
		for (String part : devided_exp) {
			if(part.length() == 0) continue;
			
			try {
				stack.push(Integer.parseInt(part));
				
			}catch (NumberFormatException e) {
				if(stack.size() < 2) throw new IllegalArgumentException("Pokrešan izraz! "
						+ "- prije operatora moraju biti navedena barem 2 operanda.");
					
				int number2 = (Integer) stack.pop();
				int number1 = (Integer) stack.pop();
				
				if(part.equals("+")) stack.push(number1 + number2);
				else if(part.equals("-")) stack.push(number1 - number2);
				else if(part.equals("*")) stack.push(number1 * number2);
				else if(part.equals("/")) {
					if(number2 == 0) throw new IllegalArgumentException("Argument dijelitelj je 0");
					
					stack.push(number1 / number2);
				}
				else if(part.equals("%")) {
					if(number2 == 0) throw new IllegalArgumentException("Argument dijelitelj je 0");
					
					stack.push(number1 % number2);
				}
				else throw new IllegalArgumentException("Pokrešan izraz! Operator "+part+" nije prepoznat.");
			}
		}
		if(stack.size() != 1) throw new IllegalArgumentException("Pokrešan izraz! Na stogu je osalo "+stack.size()+" elemenata,"
				+ " oèekivano je da ostane 1 element.");
		
		System.out.println("Expression evaluates to "+stack.pop()+".");
	}
	

}
  