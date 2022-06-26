package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.util.Iterator;
import java.util.function.Consumer;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;
import hr.fer.zemris.java.tecaj_13.model.User;
import net.bytebuddy.implementation.bytecode.Throw;

public class Validators {
	public static final Consumer<String> EMPTY_VALIDATOR = (s) -> {
		
		if(s == null || s.isEmpty()) throw new ValidatorException("This field is required");
	};
	
	public static final Consumer<String> EMAIL_VALIDATOR = (s) -> {
		if(!s.contains("@")) throw new ValidatorException("Enter a valid email address");
	};
	
	public static final Consumer<String> PASSWORD_VALIDATOR = (s) -> {
		int minLength = 3; // radi jednostavnosti 3
		if(s.length() < minLength) throw new ValidatorException("Password must contains at least " +minLength+" characters");
	};
	public static final Consumer<String> USER_WITH_NICK_EXITS = (s) -> {
		User user = DAOProvider.getDAO().getUserByNick(s);
		if(user != null) throw new ValidatorException("User with nick " +s+ " already exits");
	};
	public static final Consumer<String> maxValidatorforLength(int length) {
		return (s) -> {
			if(s.length() > length) throw new ValidatorException("Max length is " + length);
		};
	}
	
	@SuppressWarnings("unchecked")
	public static void validateValueWithValidators(String value, Consumer<String> onError, Consumer<String>... validators) {
		for(var validator: validators) {
			try{
				validator.accept(value);
			}catch (ValidatorException e) {
				onError.accept(e.getMessage());
				return;
			}
		}
	}
	
	
}
