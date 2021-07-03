package org.marmots.simulator.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.RandomUtils;

public class ListObjectFactory extends ObjectFactory {
	private int min = 0;
	private int max = 10;
	private ObjectFactory factory;
	private Class<?> innerClass;
	private String listClass;

	public ListObjectFactory(int min, int max, String innerClass, String listClass) throws Exception {
		this.min = min;
		this.max = max;
		this.factory = new GenericObjectFactory();
		this.innerClass = Class.forName(innerClass);
		this.listClass = "java.util.ArrayList";
	}

	public ListObjectFactory(int min, int max, ObjectFactory factory, String innerClass, String listClass) throws Exception {
		this.min = min;
		this.max = max;
		this.factory = factory;
		this.innerClass = Class.forName(innerClass);
		this.listClass = listClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <O> O getInstance(Class<O> returnClass) throws Exception {
		List<Object> v = null;
		if (listClass.equals("java.util.Vector")) {
			v = new Vector<Object>();
		} else {
			v = new ArrayList<Object>();
		}
		int size = min + (max - min != 0 ? RandomUtils.nextInt(0, max - min) : 0);
		for (int i = 0; i < size; i++) {
			v.add(factory.getInstance(innerClass));
		}
		return (O) v;
	}

}
