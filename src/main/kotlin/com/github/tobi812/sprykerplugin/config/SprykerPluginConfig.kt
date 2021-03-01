package com.github.tobi812.sprykerplugin.config

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import java.util.*

@State(name = "SprykerPluginConfig", storages = [Storage("SprykerPluginConfig.xml")])
class SprykerPluginConfig: PersistentStateComponent<SprykerPluginConfig>
{
    var projectName = "Pyz"
    var basePath = "src"

    override fun getState(): SprykerPluginConfig {
        return this
    }

    override fun loadState(config: SprykerPluginConfig) {
        XmlSerializerUtil.copyBean(config, this)
    }

    val coreNames: Array<String>
        get() = Companion.coreNames

    companion object {
        private var coreNames = arrayOf("Pyz", "Spryker")

        fun getInstance(project: Project, projectName: String): SprykerPluginConfig {
            val slicedCoreNames = ArrayList<String>()
            var matched = false
            for (coreName in coreNames) {
                if (coreName == projectName) {
                    matched = true
                }
                if (matched) {
                    slicedCoreNames.add(coreName)
                }
            }
            coreNames = slicedCoreNames.toTypedArray()

            return ServiceManager.getService(project, SprykerPluginConfig::class.java)
        }
    }
}