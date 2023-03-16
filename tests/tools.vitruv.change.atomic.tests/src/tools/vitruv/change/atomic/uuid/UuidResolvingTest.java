package tools.vitruv.change.atomic.uuid;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet;

import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.TestProjectManager;

@ExtendWith({ TestProjectManager.class, RegisterMetamodelsInStandalone.class })
class UuidResolvingTest {
	private ResourceSet resourceSet;
	private UuidResolver uuidResolver;
	private Path testProjectPath;

	@BeforeEach
	void setup(@TestProject Path testProjectPath) {
		this.testProjectPath = testProjectPath;
		this.resourceSet = withGlobalFactories(new ResourceSetImpl());
		this.uuidResolver = UuidResolver.create(resourceSet);
	}

	@ParameterizedTest(name = "{0} element(s)")
	@ValueSource(ints = { 1, 5, 10, 100 })
	@DisplayName("resolve element(s) without resource")
	void registerMultipleElementsWithoutResource(int count) {
		Map<String, EObject> uuidToEObjectMapping = IntStream.range(0, count).mapToObj((i) -> aet.Root())
				.collect(Collectors.toMap(root -> uuidResolver.generateUuid(root), root -> root));

		uuidToEObjectMapping.forEach((uuid, element) -> uuidResolver.registerEObject(uuid, element));
		uuidToEObjectMapping.forEach((uuid, element) -> {
			assertTrue(uuidResolver.hasUuid(element));
			assertTrue(uuidResolver.hasEObject(uuid));
			assertEquals(element, uuidResolver.getEObject(uuid));
			assertEquals(uuid, uuidResolver.getUuid(element));
		});
	}

	@ParameterizedTest(name = "{0} element(s)")
	@ValueSource(ints = { 1, 5, 10, 100 })
	@DisplayName("resolve element(s) with resource")
	void registerMultipleElementsWithResource(int count) {
		Map<String, EObject> uuidToEObjectMapping = IntStream.range(0, count).mapToObj((i) -> {
			var root = aet.Root();
			URI resourceUri = URI.createFileURI(testProjectPath.resolve("root" + i + ".aet").toString());
			Resource resource = resourceSet.createResource(resourceUri);
			resource.getContents().add(root);
			return root;
		}).collect(Collectors.toMap(root -> uuidResolver.generateUuid(root), root -> root));

		uuidToEObjectMapping.forEach((uuid, element) -> uuidResolver.registerEObject(uuid, element));
		uuidToEObjectMapping.forEach((uuid, element) -> {
			assertTrue(uuidResolver.hasUuid(element));
			assertTrue(uuidResolver.hasEObject(uuid));
			assertEquals(element, uuidResolver.getEObject(uuid));
			assertEquals(uuid, uuidResolver.getUuid(element));
		});
	}

	@Test
	@DisplayName("re-register element with same UUID")
	void reregisterElementSame() {
		var root = aet.Root();
		String uuid = uuidResolver.registerEObject(root);

		assertDoesNotThrow(() -> uuidResolver.registerEObject(uuid, root));
	}

	@Test
	@DisplayName("re-register element with changed UUID")
	void reregisterElementChanged() {
		var root = aet.Root();
		String uuid = uuidResolver.registerEObject(root);

		assertThrows(IllegalStateException.class, () -> uuidResolver.registerEObject(uuid + "modified", root));
	}

	@Test
	@DisplayName("modify element after registration")
	void modifyElementAfterRegistration() {
		var root = aet.Root();
		String uuid = uuidResolver.registerEObject(root);

		root.setId("newId");
		URI resourceUri = URI.createFileURI(testProjectPath.resolve("root.aet").toString());
		Resource rootResource = resourceSet.createResource(resourceUri);
		rootResource.getContents().add(root);

		assertEquals(root, uuidResolver.getEObject(uuid));
		assertEquals(uuid, uuidResolver.getUuid(root));

	}

	@Test
	@DisplayName("register element in wrong resource set")
	void registerElementInWrongResourceSet() {
		ResourceSet otherResourceSet = withGlobalFactories(new ResourceSetImpl());
		var root = aet.Root();
		URI resourceUri = URI.createFileURI(testProjectPath.resolve("root.aet").toString());
		Resource rootResource = otherResourceSet.createResource(resourceUri);
		rootResource.getContents().add(root);

		assertThrows(IllegalStateException.class, () -> uuidResolver.registerEObject(root));
		assertThrows(IllegalStateException.class,
				() -> uuidResolver.registerEObject(uuidResolver.generateUuid(root), root));
		assertFalse(uuidResolver.hasUuid(root));
	}

	@Test
	@DisplayName("generate UUID and resolve it after element deletion")
	void elementDeletionDoesNotRemoveUuid() {
		var root = aet.Root();
		URI resourceUri = URI.createFileURI(testProjectPath.resolve("root.aet").toString());
		Resource rootResource = resourceSet.createResource(resourceUri);
		rootResource.getContents().add(root);
		String uuid = uuidResolver.registerEObject(root);

		EcoreUtil.delete(root);
		assertEquals(root, uuidResolver.getEObject(uuid));
		assertEquals(uuid, uuidResolver.getUuid(root));
		assertTrue(uuidResolver.hasUuid(root));
		assertTrue(uuidResolver.hasEObject(uuid));
	}

	@Test
	@DisplayName("cleanup resolver when saving after element removal")
	void cleanupAfterElementRemovalRemovesUuid() {
		var root = aet.Root();
		String uuid = uuidResolver.registerEObject(root);
		uuidResolver.endTransaction();
		assertThrows(IllegalStateException.class, () -> uuidResolver.getEObject(uuid));
		assertThrows(IllegalStateException.class, () -> uuidResolver.getUuid(root));
		assertFalse(uuidResolver.hasUuid(root));
		assertFalse(uuidResolver.hasEObject(uuid));
	}

	@Test
	@DisplayName("cleanup resolver when saving resource after element removal")
	void cleanupAfterElementRemovalRemovesUuidWithResource() {
		var root = aet.Root();
		URI resourceUri = URI.createFileURI(testProjectPath.resolve("root.aet").toString());
		Resource rootResource = resourceSet.createResource(resourceUri);
		rootResource.getContents().add(root);
		String uuid = uuidResolver.registerEObject(root);

		uuidResolver.endTransaction();
		assertEquals(root, uuidResolver.getEObject(uuid));

		rootResource.getContents().clear();
		uuidResolver.endTransaction();
		assertThrows(IllegalStateException.class, () -> uuidResolver.getEObject(uuid));
		assertThrows(IllegalStateException.class, () -> uuidResolver.getUuid(root));
		assertFalse(uuidResolver.hasUuid(root));
		assertFalse(uuidResolver.hasEObject(uuid));
	}
}
