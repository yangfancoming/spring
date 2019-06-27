

package org.springframework.context.index.sample.cdi;

import javax.transaction.Transactional;

/**
 * Test candidate for {@link Transactional}. This verifies that the annotation processor
 * can process an annotation that declares itself with an annotation that is not on the
 * classpath.
 *
 * @author Vedran Pavic
 * @author Stephane Nicoll
 */
@Transactional
public class SampleTransactional {
}
