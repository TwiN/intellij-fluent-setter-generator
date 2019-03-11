package org.twinnation.intellij.fluent.setter.generator.util

import org.jetbrains.annotations.NotNull


interface FormatUtils {

    companion object {
        @NotNull
        fun generateMethodName(fieldName: String, prefix: String = "with"): String {
            if (prefix.isEmpty()) {
                return fieldName
            }
            return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)
        }
    }

}
