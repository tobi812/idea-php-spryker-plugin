package com.github.tobi812.sprykerplugin.services.generator

import com.github.tobi812.sprykerplugin.services.renderer.dto.DocBlockItem
import java.lang.Exception

interface DocBlockGeneratorInterface {
    @Throws(Exception::class)
    fun getDocBlockItems(
        classTypes: Array<String>,
        bundleName: String
    ): List<DocBlockItem>
}
