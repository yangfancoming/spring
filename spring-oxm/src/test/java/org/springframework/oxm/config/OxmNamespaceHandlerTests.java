

package org.springframework.oxm.config;

import org.junit.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the {@link OxmNamespaceHandler} class.
 *
 * @author Arjen Poustma
 * @author Jakub Narloch
 * @author Sam Brannen
 */
public class OxmNamespaceHandlerTests {

	private final ApplicationContext applicationContext =
			new ClassPathXmlApplicationContext("oxmNamespaceHandlerTest.xml", getClass());


	@Test
	public void jaxb2ContextPathMarshaller() {
		Jaxb2Marshaller jaxb2Marshaller = applicationContext.getBean("jaxb2ContextPathMarshaller", Jaxb2Marshaller.class);
		assertNotNull(jaxb2Marshaller);
	}

	@Test
	public void jaxb2ClassesToBeBoundMarshaller() {
		Jaxb2Marshaller jaxb2Marshaller = applicationContext.getBean("jaxb2ClassesMarshaller", Jaxb2Marshaller.class);
		assertNotNull(jaxb2Marshaller);
	}

}
