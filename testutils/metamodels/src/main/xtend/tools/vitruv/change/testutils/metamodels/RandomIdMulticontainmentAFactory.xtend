package tools.vitruv.change.testutils.metamodels

import multicontainment_a.Identified
import multicontainment_a.impl.Multicontainment_aFactoryImpl
import tools.vitruv.change.utils.activeannotations.WithGeneratedRandomIds

@WithGeneratedRandomIds(identifierMetaclass=Identified)
class RandomIdMulticontainmentAFactory extends Multicontainment_aFactoryImpl {
}