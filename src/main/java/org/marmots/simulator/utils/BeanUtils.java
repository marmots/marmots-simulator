package org.marmots.simulator.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtils {
	private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

	public static String toString(Object o) {
		return toString(o, 0);
	}

	public static ArrayList<Field> getFields(Class<?> clazz) {
		ArrayList<Field> fields = new ArrayList<Field>();
		Class<?> parent = clazz;
		while (parent != Object.class) {
			fields.addAll(Arrays.asList(parent.getDeclaredFields()));
			parent = parent.getSuperclass();
		}
		return fields;
	}

	public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException {
		try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			Class<?> parent = clazz.getSuperclass();
			if (parent != null) {
				return getField(parent, name);
			} else {
				throw new NoSuchFieldException();
			}
		}
	}

	public static HashMap<String, Class<?>> getImpliedClasses(Class<?> c) throws Exception {
		return getImpliedClasses("", c);
	}

	public static HashMap<String, Class<?>> getImpliedClasses(String path, Class<?> c) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Searching implied classes for class: {}", c.getName());
		}
		HashMap<String, Class<?>> impliedClasses = new HashMap<String, Class<?>>();
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(c);
		for (PropertyDescriptor p : propertyDescriptors) {
			if (logger.isDebugEnabled()) {
				logger.debug("Processing attribute: " + p.getName() + " type: " + p.getPropertyType().getName());
			}
			if (!isSimple(p.getPropertyType()) && !p.getPropertyType().getName().equals(Class.class.getName())) {
				String propertyPath = path + "/" + p.getName();
				if (p.getPropertyType().isArray()) {
					Class<?> arrayClass = p.getPropertyType().getComponentType();
					if (!isSimple(arrayClass)) {
						if (logger.isDebugEnabled()) {
							logger.debug("- Class added to impliedClasses list: " + p.getPropertyType().getName() + " with path: " + propertyPath);
						}
						impliedClasses.put(propertyPath + arrayClass.getName().toLowerCase(), arrayClass);
						impliedClasses.putAll(getImpliedClasses(propertyPath, arrayClass));
					}
				} else if (isCollection(p.getPropertyType()) || isList(p.getPropertyType())) {
					Class<?> listClass = getListClass(c, p.getName());
					if (!isSimple(listClass)) {
						String propertyName = p.getName().substring(0, p.getName().lastIndexOf("s"));
						if (logger.isDebugEnabled()) {
							logger.debug("- Class added to impliedClasses list: " + p.getPropertyType().getName() + " with path: " + propertyPath + "/" + propertyName);
						}
						impliedClasses.put(propertyPath, ArrayList.class);
						impliedClasses.put(propertyPath + "/" + propertyName, listClass);
						impliedClasses.putAll(getImpliedClasses(propertyPath, listClass));
					}
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("- Class added to impliedClasses list: " + p.getPropertyType().getName() + " with path: " + propertyPath);
					}
					impliedClasses.put(propertyPath, p.getPropertyType());
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Implied classes searching finished for: " + c.getName());
			logger.debug("Founded: " + impliedClasses.size() + " implied classes... ");
		}
		return impliedClasses;
	}

	public static boolean isCollection(Class<?> c) throws Exception {
		return c.isAssignableFrom(java.util.Collection.class) || c.getName().equals(Vector.class.getName());
	}

	public static boolean isList(Class<?> c) throws Exception {
		return c.isAssignableFrom(java.util.List.class) || c.getName().equals(ArrayList.class.getName());
	}

	public static Class<?> getListClass(Class<?> containerClass, String fieldName) throws Exception {
		Field field = containerClass.getDeclaredField(fieldName);
		ParameterizedType listType = (ParameterizedType) field.getGenericType();
		return (Class<?>) listType.getActualTypeArguments()[0];
	}

	public static String tabs(int n) {
		String tab = "";
		for (int i = 0; i < n; i++) {
			tab += "\t";
		}
		return tab;
	}

	public static boolean isSimple(Class<?> c) {
		return c.isPrimitive() || c.getName().equals(String.class.getName()) || c.getName().equals(java.util.Date.class.getName()) || c.getName().equals(Integer.class.getName())
				|| c.getName().equals(Long.class.getName()) || c.getName().equals(Float.class.getName()) || c.getName().equals(Short.class.getName()) || c.getName().equals(Double.class.getName())
				|| c.getName().equals(Boolean.class.getName());
	}

	private static String toString(Object o, int tabs) {
		if (o == null) {
			return tabs(tabs) + "null object\n";
		}
		if (isSimple(o.getClass())) {
			return tabs(tabs) + o.toString();
		} else {
			Class<?> c = o.getClass();
			StringBuffer s = new StringBuffer(tabs(tabs) + "+ Information for ").append(c.getName()).append("\n");
			try {
				PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(o);
				for (PropertyDescriptor p : propertyDescriptors) {
					if (p.getName().equals("class")) {
						continue;
					}
					s.append(tabs(tabs) + p.getName()).append(" = ");
					if (p.getPropertyType().getName().equals(Class.class.getName())) {
						s.append(p.getPropertyType().getName()).append("\n");
						continue;
					}
					if (p.getPropertyType().isArray()) {
						s.append("[] {\n");
						try {
							int i = 0;
							Object value;
							do {
								value = PropertyUtils.getIndexedProperty(o, p.getName(), i++);
								s.append(tabs(tabs) + value + "\n");
							} while (value != null);
						} catch (ArrayIndexOutOfBoundsException e) {
							// Array end reached...
						} catch (NullPointerException e) {
							// Null array
						}
						s.append(tabs(tabs) + "}\n");
					} else if (p.getPropertyType().isAssignableFrom(java.util.Collection.class) || p.getPropertyType().getName().equals(Vector.class.getName())) {
						s.append("Collection {\n");
						Collection<?> collection = (Collection<?>) PropertyUtils.getProperty(o, p.getName());
						for (Object lo : collection) {
							s.append(toString(lo, tabs + 1) + "\n");
						}
						s.append(tabs(tabs) + "}\n");
					} else if (p.getPropertyType().isAssignableFrom(java.util.List.class) || p.getPropertyType().getName().equals(ArrayList.class.getName())) {
						s.append("List {\n");
						List<?> list = (List<?>) PropertyUtils.getProperty(o, p.getName());
						if (list != null) {
							for (Object lo : list) {
								s.append(toString(lo, tabs + 1) + "\n");
							}
						}
						s.append(tabs(tabs) + "}\n");
					} else if (isSimple(p.getPropertyType())) {
						s.append(PropertyUtils.getProperty(o, p.getName())).append("\n");
					} else {
						s.append("\n" + tabs(tabs) + "{\n" + toString(PropertyUtils.getProperty(o, p.getName()), tabs + 1) + tabs(tabs) + "}\n");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				s.append("There was a problem getting information").append(e.getMessage()).append("\n");
			}
			return s.toString();
		}
	}
}
