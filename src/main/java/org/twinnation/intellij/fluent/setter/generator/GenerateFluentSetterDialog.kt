package org.twinnation.intellij.fluent.setter.generator

import com.intellij.ide.util.DefaultPsiElementCellRenderer
import com.intellij.lang.jvm.JvmModifier
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.LabeledComponent
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiField
import com.intellij.ui.CollectionListModel
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBTextField
import org.twinnation.intellij.fluent.setter.generator.util.FormatUtils
import java.awt.Component
import java.awt.Dimension
import javax.swing.BorderFactory
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingConstants


class GenerateFluentSetterDialog(private val psiClass: PsiClass) : DialogWrapper(psiClass.project) {

    private var fields: CollectionListModel<PsiField>? = null
    private var panel: JPanel? = null
    private val prefixField: JTextField = JBTextField("with")
    private val useSettersField: JCheckBox = JBCheckBox("Use setters?", false)

    init {
        this.init()
    }


    override fun init() {
        title = "Generate fluent setters"
        buildPanel()
        super.init()
    }


    private fun buildPanel() {
        val container = JPanel()
        container.layout = BoxLayout(container, BoxLayout.Y_AXIS)
        container.add(Box.createRigidArea(Dimension(0, 5)))
        container.add(ToolbarDecorator.createDecorator(createFieldSelectionComponent()).createPanel())
        container.add(Box.createRigidArea(Dimension(0, 5)))
        container.add(labelAndComponent("Prefix: ", prefixField))
        container.add(Box.createRigidArea(Dimension(0, 5)))
        container.add(useSettersField)
        this.panel = LabeledComponent.create(container, "Fields for which you wish to create fluent setters")
    }


    private fun createFieldSelectionComponent(): JBList<PsiField> {
        val classMethods = psiClass.methods.asSequence()
                .map { it.name }
                .toSet()
        fields = CollectionListModel(
                psiClass.fields
                        .filter { field -> !field.hasModifier(JvmModifier.STATIC) }
                        .filter { field -> !field.hasModifier(JvmModifier.FINAL) }
                        .filter { field -> !classMethods.contains(FormatUtils.generateMethodName(field.name)) }
        )
        val listComponent = JBList(fields!!)
        listComponent.cellRenderer = DefaultPsiElementCellRenderer()
        return listComponent
    }


    private fun labelAndComponent(text: String, component: Component): Component {
        val container = JPanel()
        container.layout = BoxLayout(container, BoxLayout.X_AXIS)
        container.add(createLabel(text))
        container.add(Box.createRigidArea(Dimension(5, 0)))
        container.add(component)
        return container
    }


    private fun createLabel(text: String): JLabel {
        val label = JLabel(text)
        label.horizontalTextPosition = SwingConstants.RIGHT
        label.horizontalAlignment = SwingConstants.RIGHT
        label.border = BorderFactory.createEmptyBorder(2, 2, 2, 2)
        return label
    }


    override fun createCenterPanel(): JComponent? {
        return panel
    }


    fun getFields(): List<PsiField> {
        return fields!!.items
    }


    fun getPrefix(): String {
        return prefixField.text
    }


    fun isUsingSetters(): Boolean {
        return useSettersField.isSelected
    }

}
