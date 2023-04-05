package tools.vitruv.change.atomic.util

import java.util.List
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.uuid.UuidResolver

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.is
import static org.hamcrest.core.IsInstanceOf.instanceOf
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNotSame
import static org.junit.jupiter.api.Assertions.assertTrue

import static extension tools.vitruv.change.atomic.resolve.EChangeUuidResolverAndApplicator.*

/**
 * Utility class for frequently used assert methods in the tests.
 */
@FinalFieldsConstructor
class EChangeAssertHelper {
	val UuidResolver uuidResolver
	
	/**
	 * Tests whether a unresolved change and a resolved change are the same class.
	 */
	def void assertDifferentChangeSameClass(EChange unresolvedChange, EChange resolvedChange) {
		assertFalse(unresolvedChange.isResolved)
		assertTrue(resolvedChange.isResolved)
		assertNotSame(unresolvedChange, resolvedChange)
		assertEquals(unresolvedChange.getClass, resolvedChange.getClass)
	}

	/**
	 * Tests whether a unresolved changes and a resolved changes are the same classes.
	 */
	def void assertDifferentChangeSameClass(List<? extends EChange> unresolvedChange,
		List<? extends EChange> resolvedChange) {
		assertEquals(unresolvedChange.size, resolvedChange.size)
		for (var i = 0; i < unresolvedChange.size; i++) {
			assertDifferentChangeSameClass(unresolvedChange.get(i), resolvedChange.get(i))
		}
	}

	/**
	 * Tests whether a change is resolved and applies it forward.
	 */
	def void assertApplyForward(EChange change) {
		assertNotNull(change)
		assertTrue(change.isResolved)
		change.applyForward(uuidResolver)
	}

	/**
	 * Tests whether a change sequence is resolved and applies it forward.
	 */
	def void assertApplyForward(List<EChange> change) {
		change.forEach[assertApplyForward]
	}

	/**
	 * Tests whether a change is resolved and applies it backward.
	 */
	def void assertApplyBackward(EChange change) {
		assertNotNull(change)
		assertTrue(change.isResolved)
		change.applyBackward(uuidResolver)
	}

	/**
	 * Tests whether a change sequence is resolved and applies it backward.
	 */
	def void assertApplyBackward(List<EChange> change) {
		change.reverseView.forEach[assertApplyBackward]
	}
	
	static def <T> T assertType(Object original, Class<T> type) {
		assertThat(original, is(instanceOf(type)))
		return original as T
	}

}
