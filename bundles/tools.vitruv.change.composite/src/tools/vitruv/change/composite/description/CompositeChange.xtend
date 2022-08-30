package tools.vitruv.change.composite.description

import java.util.List
import tools.vitruv.change.composite.description.VitruviusChange
import tools.vitruv.change.composite.MetamodelDescriptor
import java.util.Set

interface CompositeChange<C extends VitruviusChange> extends VitruviusChange {
	/**
	 * Returns the metamodel descriptors for the metamodels of the elements whose instances 
	 * have been modified in this change. It collects the metamodel descriptors for all composed 
	 * changes (according to {@link #getChanges}). 
	 * 
	 * @return the metamodel descriptors of the composed changes 
	 */
	override Set<MetamodelDescriptor> getAffectedEObjectsMetamodelDescriptors()

	/**
	 * Returns the changes this one is composed of.
	 * 
	 * @return the composed changes
	 */
	def List<C> getChanges()

	override CompositeChange<C> copy()
}
