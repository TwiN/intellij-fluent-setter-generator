package org.twinnation.intellij.fluent.setter.generator

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiField
import org.twinnation.intellij.fluent.setter.generator.util.FormatUtils
import org.twinnation.intellij.fluent.setter.generator.util.IntellijPluginUtils


class GenerateFluentSetterAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        IntellijPluginUtils.getPsiClassFromAnActionEvent(event)?.let {
            val dialog = GenerateFluentSetterDialog(it)
            dialog.show()
            if (dialog.isOK) {
                WriteCommandAction.writeCommandAction(it.project, it.containingFile).run<RuntimeException> {
                    dialog.getFields().forEach { psiField ->
                        val elementFactory = JavaPsiFacade.getElementFactory(it.project)
                        val methodAsText = generateFluentMethod(it, psiField, dialog.getPrefix(), dialog.isUsingSetters())
                        it.add(elementFactory.createMethodFromText(methodAsText, it))
                    }
                }
            }
        }
    }


    private fun generateFluentMethod(c: PsiClass, f: PsiField, prefix: String, useSetters: Boolean): String {
        val methodName = FormatUtils.generateMethodName(f.name, prefix)
        val parameter = "${f.type.presentableText} ${f.name}"
        val setParameter = if (useSetters) FormatUtils.generateMethodName(f.name, "set") + "(${f.name})" else "this.${f.name} = ${f.name}"
        return "public ${c.name} $methodName($parameter) {\n" +
                "	$setParameter;\n" +
                "	return this;\n" +
                "}\n"
    }


    override fun update(e: AnActionEvent) {
        val psiClass = IntellijPluginUtils.getPsiClassFromAnActionEvent(e)
        e.presentation.isEnabled = psiClass != null
    }

}