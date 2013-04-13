package models;

import java.util.ArrayList;
import java.util.List;

import patterns.observer.Observer;
import patterns.observer.Subject;

public abstract class AbstractModel implements Model, Subject {

	private final List<Observer> observers = new ArrayList<Observer>();
	
	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for(Observer o: observers) {
			o.update();
		}
	}
	
}