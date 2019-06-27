

package org.springframework.oxm.config;

import org.w3c.dom.Element;

import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;

/**
 * Parser for the {@code <oxm:jibx-marshaller/>} element.
 *
 * @author Arjen Poutsma
 * @since 3.0
 * @deprecated as of Spring Framework 5.1.5, due to the lack of activity on the JiBX project
 */
@Deprecated
class JibxMarshallerBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {

	@Override
	protected String getBeanClassName(Element element) {
		return "org.springframework.oxm.jibx.JibxMarshaller";
	}

}
