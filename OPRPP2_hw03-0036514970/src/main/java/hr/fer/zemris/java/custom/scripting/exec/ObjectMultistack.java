package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

public class ObjectMultistack  {

	static class MultistackEntry{
		ValueWrapper value;
		MultistackEntry next;

		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			super();
			this.value = value;
			this.next = next;
		}


	}
	

	public ObjectMultistack() {
		super();
	}
	Map<String,MultistackEntry> data = new HashMap<>();

	public void push(String keyName, ValueWrapper valueWrapper) {
		MultistackEntry entry = data.get(keyName);
		data.put(keyName, new MultistackEntry(valueWrapper,entry));

	}
	public ValueWrapper pop(String keyName) {
		ValueWrapper value= data.get(keyName).value;
		data.put(keyName, data.get(keyName).next);
		return value;
	}
	public ValueWrapper peek(String keyName) {
		return  data.get(keyName).value;
	}
	public boolean isEmpty(String keyName) {
		return data.get(keyName) == null;
	}
}
