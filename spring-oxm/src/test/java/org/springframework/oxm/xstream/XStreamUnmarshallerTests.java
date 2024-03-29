

package org.springframework.oxm.xstream;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import org.springframework.util.xml.StaxUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Arjen Poutsma
 */
public class XStreamUnmarshallerTests {

	protected static final String INPUT_STRING = "<flight><flightNumber>42</flightNumber></flight>";

	private XStreamMarshaller unmarshaller;

	@Before
	public void createUnmarshaller() throws Exception {
		unmarshaller = new XStreamMarshaller();
		Map<String, Class<?>> aliases = new HashMap<>();
		aliases.put("flight", Flight.class);
		unmarshaller.setAliases(aliases);
	}

	private void testFlight(Object o) {
		assertTrue("Unmarshalled object is not Flights", o instanceof Flight);
		Flight flight = (Flight) o;
		assertNotNull("Flight is null", flight);
		assertEquals("Number is invalid", 42L, flight.getFlightNumber());
	}

	@Test
	public void unmarshalDomSource() throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(INPUT_STRING)));
		DOMSource source = new DOMSource(document);
		Object flight = unmarshaller.unmarshal(source);
		testFlight(flight);
	}

	@Test
	public void unmarshalStaxSourceXmlStreamReader() throws Exception {
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader = inputFactory.createXMLStreamReader(new StringReader(INPUT_STRING));
		Source source = StaxUtils.createStaxSource(streamReader);
		Object flights = unmarshaller.unmarshal(source);
		testFlight(flights);
	}

	@Test
	public void unmarshalStreamSourceInputStream() throws Exception {
		StreamSource source = new StreamSource(new ByteArrayInputStream(INPUT_STRING.getBytes("UTF-8")));
		Object flights = unmarshaller.unmarshal(source);
		testFlight(flights);
	}

	@Test
	public void unmarshalStreamSourceReader() throws Exception {
		StreamSource source = new StreamSource(new StringReader(INPUT_STRING));
		Object flights = unmarshaller.unmarshal(source);
		testFlight(flights);
	}
}

