package org.marmots.simulator.objects;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.marmots.exceptions.NotInitializedException;
import org.marmots.simulator.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericObjectFactory extends ObjectFactory {
	public static final int MAX_SIZE_FOR_NESTED_LIST = 5;

	private static final Logger logger = LoggerFactory.getLogger(GenericObjectFactory.class);

	private Hashtable<String, ObjectFactory> registeredClassFactories = new Hashtable<String, ObjectFactory>();

	public void addRegisteredClassFactory(String returnClass, ObjectFactory factory) {
		registeredClassFactories.put(returnClass, factory);
	}

	private Hashtable<String, ObjectFactory> registeredFieldFactories = new Hashtable<String, ObjectFactory>();

	public void addRegisteredFieldFactory(String field, ObjectFactory factory) {
		registeredFieldFactories.put(field, factory);
	}

	@Override
	public <O> O getInstance(Class<O> returnClass) throws Exception {
		if (registeredClassFactories.containsKey(returnClass.getName())) {
			ObjectFactory factory = registeredClassFactories.get(returnClass.getName());
			if (logger.isDebugEnabled()) {
				logger.debug("-> Registered class factory: " + factory.getClass().getName());
				logger.debug("for: " + returnClass.getName());
			}
			return factory.getInstance(returnClass);

		} else if (BeanUtils.isSimple(returnClass)) {
			return getSimpleInstance(returnClass);

		} else if (Modifier.isAbstract(returnClass.getModifiers())) {

			throw new NotInitializedException("Abstract class " + returnClass.getName() + " must be handled by some registered factory");

		} else {
			return getComplexInstance(returnClass);

		}
	}

	@SuppressWarnings("unchecked")
	private <O> O getSimpleInstance(Class<O> returnClass) throws Exception {
		Object bean = null;
		if (returnClass.isAssignableFrom(String.class)) {
			bean = SimpleObjectFactory.getRandomDescription();
		} else if (returnClass.isAssignableFrom(Date.class)) {
			bean = SimpleObjectFactory.getRandomBirthDate();
		} else if (returnClass.isAssignableFrom(Boolean.class)) {
			bean = RandomUtils.nextInt(0, 1) > 0;
		} else if (returnClass.isAssignableFrom(Integer.class)) {
			bean = RandomUtils.nextInt(0, Integer.MAX_VALUE);
		} else if (returnClass.isAssignableFrom(Short.class)) {
			bean = (short) RandomUtils.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
		} else if (returnClass.isAssignableFrom(Long.class)) {
			bean = RandomUtils.nextLong(0, Long.MAX_VALUE);
		} else if (returnClass.isAssignableFrom(Double.class)) {
			bean = RandomUtils.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
		} else if (returnClass.isAssignableFrom(Float.class)) {
			bean = RandomUtils.nextFloat(Float.MIN_VALUE, Float.MAX_VALUE);
		} else if (returnClass.isPrimitive()) {
			if (returnClass.getName().equals("int")) {
				bean = RandomUtils.nextInt(0, Integer.MAX_VALUE);
			} else if (returnClass.getName().equals("short")) {
				bean = (short) RandomUtils.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
			} else if (returnClass.getName().equals("long")) {
				bean = RandomUtils.nextLong(0, Long.MAX_VALUE);
			} else if (returnClass.getName().equals("double")) {
				bean = RandomUtils.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
			} else if (returnClass.getName().equals("float")) {
				bean = RandomUtils.nextFloat(Float.MIN_VALUE, Float.MAX_VALUE);
			}
		}
		return (O) bean;
	}

	private <O> O getComplexInstance(Class<O> returnClass) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Getting instance using class: " + returnClass.getName());
		}
		O bean = returnClass.newInstance();
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(returnClass);
		for (PropertyDescriptor descriptor : descriptors) {
			String name = descriptor.getName();
			String fieldName = returnClass.getName() + "." + name;
			Class<?> type = descriptor.getPropertyType();
			boolean writeMethod = PropertyUtils.getWriteMethod(descriptor) != null;
			if (!writeMethod) {
				logger.debug("Class don't has write method, skiped.");
				continue;
			}
			String extendingFieldName = PropertyUtils.getWriteMethod(descriptor).getDeclaringClass().getName() + "." + name;
			if (logger.isDebugEnabled()) {
				logger.debug("--------------------- ");
				logger.debug("Processing attribute: " + name);
				logger.debug("Fully qualified name: " + fieldName);
				logger.debug("Extending fully qualified name: " + extendingFieldName);
				logger.debug("Of type: " + type.getName() + " - " + type.getCanonicalName());
				logger.debug("Inside class: " + returnClass.getName());
				logger.debug("Write method: " + PropertyUtils.getWriteMethod(descriptor));
			}

			if (registeredFieldFactories.containsKey(fieldName)) {
				ObjectFactory factory = registeredFieldFactories.get(fieldName);
				if (logger.isDebugEnabled()) {
					logger.debug("-> Registered field factory: " + factory.getClass().getName());
					logger.debug("for: " + fieldName);
				}
				Object value = factory.getInstance(type);
				PropertyUtils.setSimpleProperty(bean, name, value);

			} else if (registeredFieldFactories.containsKey(extendingFieldName)) {
				ObjectFactory factory = registeredFieldFactories.get(extendingFieldName);
				if (logger.isDebugEnabled()) {
					logger.debug("-> Registered parent field factory: " + factory.getClass().getName());
					logger.debug("for: " + fieldName + " (" + extendingFieldName + ")");
				}
				Object value = factory.getInstance(type);
				PropertyUtils.setSimpleProperty(bean, name, value);

			} else if (BeanUtils.isSimple(type)) {
				PropertyUtils.setSimpleProperty(bean, name, getInstance(type));

			} else if (ArrayUtils.contains(type.getInterfaces(), List.class) || ArrayUtils.contains(type.getInterfaces(), Collection.class)) {
				// Only work for parametrized lists
				Class<?> listClass = BeanUtils.getListClass(returnClass, name);
				List<Object> v = new ArrayList<Object>();
				int size = RandomUtils.nextInt(0, MAX_SIZE_FOR_NESTED_LIST);
				for (int i = 0; i < size; i++) {
					v.add(getInstance(listClass));
				}
				PropertyUtils.setSimpleProperty(bean, name, v);

			} else if (type.isArray()) {
				Class<?> arrayClass = type.getComponentType();
				int size = RandomUtils.nextInt(0, MAX_SIZE_FOR_NESTED_LIST);
				Object array = Array.newInstance(arrayClass, size);
				for (int i = 0; i < size; i++) {
					Array.set(array, i, getInstance(arrayClass));
				}
				PropertyUtils.setSimpleProperty(bean, name, array);

			} else {
				PropertyUtils.setSimpleProperty(bean, name, getInstance(type));
			}
		}
		return bean;
	}

	public Hashtable<String, ObjectFactory> getRegisteredClassFactories() {
		return registeredClassFactories;
	}

	public void setRegisteredClassFactories(Hashtable<String, ObjectFactory> registeredClassFactories) {
		this.registeredClassFactories = registeredClassFactories;
	}

	public Hashtable<String, ObjectFactory> getRegisteredFieldFactories() {
		return registeredFieldFactories;
	}

	public void setRegisteredFieldFactories(Hashtable<String, ObjectFactory> registeredFieldFactories) {
		this.registeredFieldFactories = registeredFieldFactories;
	}

}
