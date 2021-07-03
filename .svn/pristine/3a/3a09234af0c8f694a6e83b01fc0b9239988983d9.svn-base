package org.marmots.simulator.objects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@SuppressWarnings("deprecation")
public class SpringObjectFactory extends ObjectFactory {
	private static final String TEMP_FILE = "temp.dat";
	private ObjectFactory factory;

	private String content;

	public SpringObjectFactory() throws Exception {

	}

	public SpringObjectFactory(String xml) throws Exception {
		XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource(xml));
		factory = beanFactory.getBean("factory", ObjectFactory.class);
	}

	public SpringObjectFactory(String xml, String factoryName) throws Exception {
		XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource(xml));
		factory = beanFactory.getBean(factoryName, ObjectFactory.class);
	}

	@Override
	public <O> O getInstance(Class<O> returnClass) throws Exception {
		if (factory == null) {
			factory = new GenericObjectFactory();
		}
		return factory.getInstance(returnClass);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) throws Exception {
		this.content = content;
		if (content != null && !content.equals("")) {
			String tmp_file = TEMP_FILE + System.currentTimeMillis();
			OutputStream output = new FileOutputStream(tmp_file);
			IOUtils.write(content, output);
			output.close();

			XmlBeanFactory beanFactory = new XmlBeanFactory(new FileSystemResource(tmp_file));
			factory = beanFactory.getBean("factory", ObjectFactory.class);

			new File(tmp_file).delete();
		}
	}

}
