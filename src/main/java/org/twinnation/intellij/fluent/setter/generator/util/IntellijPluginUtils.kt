package org.twinnation.intellij.fluent.setter.generator.util

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.PsiClass
import com.intellij.psi.util.PsiTreeUtil


interface IntellijPluginUtils {

    companion object {
        fun getPsiClassFromAnActionEvent(event: AnActionEvent): PsiClass? {
            val psiFile = event.getData(CommonDataKeys.PSI_FILE)
            val editor = event.getData(CommonDataKeys.EDITOR)
            if (psiFile == null || editor == null) {
                return null
            }
            val psiElement = psiFile.findElementAt(editor.caretModel.offset)
            return PsiTreeUtil.getParentOfType(psiElement, PsiClass::class.java)
        }
    }

}
