package hr.fer.zemris.java.p12.model;

public class Polls {
	long id;
	String title;
	String message;
	
	public Polls(long id, String title, String message) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Polls other = (Polls) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
}
