package com.preproduction.bobrov.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.preproduction.bobrov.bean.SecurityBean;

/**
 * Reads SecurityBean object from  resource xml file
 * @author Viacheslav_Bobrov
 *
 */
public class SecurityJaxbUnmarshaller {

	public SecurityBean unmarshall(String fileName) throws JAXBException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File configFile = new File(classLoader.getResource(fileName).getFile());
		JAXBContext jaxbContext = JAXBContext.newInstance(SecurityBean.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (SecurityBean) jaxbUnmarshaller.unmarshal(configFile);
	}

}
