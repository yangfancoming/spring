

package org.springframework.oxm.config;

import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * {@link NamespaceHandler} for the '{@code oxm}' namespace.
 *
 * @author Arjen Poutsma
 * @since 3.0
 */
public class OxmNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	@SuppressWarnings("deprecation")
	public void init() {
		registerBeanDefinitionParser("jaxb2-marshaller", new Jaxb2MarshallerBeanDefinitionParser());
		registerBeanDefinitionParser("jibx-marshaller", new JibxMarshallerBeanDefinitionParser());
	}

}
