package com.github.tobi812.sprykerplugin.config

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

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

    val appNames: Array<String>
        get() = Companion.appNames

    companion object {
        private var coreNames = arrayOf("Pyz", "Spryker")
        private var appNames = arrayOf("Yves", "Zed", "Client", "Glue")

        fun getInstance(project: Project): SprykerPluginConfig {
            return ServiceManager.getService(project, SprykerPluginConfig::class.java)
        }
    }
}
