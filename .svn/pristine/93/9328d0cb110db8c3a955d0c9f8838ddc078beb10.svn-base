package org.marmots.simulator.objects;

import org.apache.commons.lang3.RandomUtils;


public class AbstractClassObjectFactory extends ObjectFactory {
	private Class<?>[] implementingClasses;
	private ObjectFactory factory;

	public AbstractClassObjectFactory(String[] classes) throws Exception {
		implementingClasses = new Class<?>[classes.length];
		int i = 0;
		for (String c : classes) {
			implementingClasses[i++] = Class.forName(c);
		}
		factory = new GenericObjectFactory();
	}
	
	public AbstractClassObjectFactory(String[] classes, ObjectFactory factory) throws Exception {
		implementingClasses = new Class<?>[classes.length];
		int i = 0;
		for (String c : classes) {
			implementingClasses[i++] = Class.forName(c);
		}
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <O> O getInstance(Class<O> returnClass) throws Exception {
		int type = RandomUtils.nextInt(0, implementingClasses.length);
		return (O) factory.getInstance(implementingClasses[type]);
	}

}
