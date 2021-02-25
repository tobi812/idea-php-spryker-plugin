package com.github.tobi812.sprykerplugin.models.definitions

import com.github.tobi812.sprykerplugin.models.definitions.spryker.*
import java.lang.Exception
import java.util.HashMap

class DefinitionProvider : DefinitionProviderInterface {

    companion object {
        var classDefinitions = HashMap<String, ClassDefinitionInterface>()
    }

    init {
       this.addClassDefinition(ClientDefinition())
       this.addClassDefinition(ClientFactoryDefinition())
       this.addClassDefinition(ClientDependencyProviderDefinition())
       this.addClassDefinition(ClientPluginDefinition())
       this.addClassDefinition(YvesControllerDefinition())
       this.addClassDefinition(YvesFactoryDefinition())
       this.addClassDefinition(YvesDependencyProviderDefinition())
       this.addClassDefinition(YvesPluginDefinition())
       this.addClassDefinition(ZedControllerDefinition())
       this.addClassDefinition(ZedQueryContainerDefinition())
       this.addClassDefinition(ZedFacadeDefinition())
       this.addClassDefinition(ZedBusinessFactoryDefinition())
       this.addClassDefinition(ZedPersistenceFactoryDefinition())
       this.addClassDefinition(ZedCommunicationFactoryDefinition())
       this.addClassDefinition(ZedConfigDefinition())
       this.addClassDefinition(ZedPluginDefinition())
       this.addClassDefinition(ZedDependencyProviderDefinition())
    }

    @Throws(Exception::class)
    override fun getDefinitionByType(classType: String): ClassDefinitionInterface {
        if (!classDefinitions.containsKey(classType)) {
            throw Exception("Class definition was not found: $classType")
        }

        return classDefinitions[classType]!!
    }

    override val allClassDefinitions: HashMap<String, ClassDefinitionInterface> =
        classDefinitions

    private fun addClassDefinition(classDefinition: ClassDefinitionInterface) {
        classDefinitions[classDefinition.classType] = classDefinition
    }
}