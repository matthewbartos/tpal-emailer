package com.matthewbartos.emailer.view

import com.matthewbartos.emailer.locale.Context
import com.matthewbartos.emailer.locale.ContextChangeListener
import com.matthewbartos.emailer.model.Email
import java.awt.Dimension
import javax.swing.JEditorPane
import javax.swing.JPanel


class EmailView(email: Email) : JPanel() {

    private val context: Context = Context.INSTANCE

    init {
        var component = displayEmail(email)
        add(component)

        context.addContextChangeListener(ContextChangeListener {
            remove(component)
            component = displayEmail(email)
            add(component)
            updateUI()
        })
    }

    private fun displayEmail(email: Email) = with(email) {
        JEditorPane(
            "text/html",
            """
            <b>${context.bundle!!.getString("email_view_from")}:</b> $sender <br/>
            <b>${context.bundle!!.getString("email_view_received")}:</b> $sent <br/>
            <b>${context.bundle!!.getString("email_view_subject")}: $subject </b><br/>
            <hr/>
            
            ${content.replace("\n", "<br/>")}
        """.trimIndent()
        ).apply {
            isEditable = false
            size = Dimension(500, 400)
        }
    }
}