package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer>{

	List<Integer> numbers = new ArrayList<>();
	List<ListDataListener> liseners = new ArrayList<>();

	public PrimListModel() {
		numbers.add(Integer.valueOf(1));	
	}

	@Override
	public int getSize() {
		return numbers.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return numbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		liseners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		liseners.remove(l);
	}

	public void next() {
		Integer next = numbers.get(numbers.size() - 1) + 1;
		while(true) {
			if(prime(next)) {
				numbers.add(next);
				notifier();
				break;
			}
			next++;
		}
	}

	private boolean prime(Integer next) {
		for (int i = 2; i < next; i++) {
			if(next % i == 0) return false;
		}
		return true;
	}

	private void notifier() {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize(), getSize());
		for(ListDataListener l : liseners) {
			l.intervalAdded(event);
		}
		
	}

}
