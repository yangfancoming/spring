

package org.springframework.context.index.processor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.context.index.processor.Metadata.hasComponent;

/**
 * Tests for {@link PropertiesMarshaller}.
 *
 * @author Stephane Nicoll
 */
public class PropertiesMarshallerTests {

	@Test
	public void readWrite() throws IOException {
		CandidateComponentsMetadata metadata = new CandidateComponentsMetadata();
		metadata.add(createItem("com.foo", "first", "second"));
		metadata.add(createItem("com.bar", "first"));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PropertiesMarshaller.write(metadata, outputStream);
		CandidateComponentsMetadata readMetadata = PropertiesMarshaller.read(
				new ByteArrayInputStream(outputStream.toByteArray()));
		assertThat(readMetadata, hasComponent("com.foo", "first", "second"));
		assertThat(readMetadata, hasComponent("com.bar", "first"));
		assertThat(readMetadata.getItems(), hasSize(2));
	}

	private static ItemMetadata createItem(String type, String... stereotypes) {
		return new ItemMetadata(type, new HashSet<>(Arrays.asList(stereotypes)));
	}

}
