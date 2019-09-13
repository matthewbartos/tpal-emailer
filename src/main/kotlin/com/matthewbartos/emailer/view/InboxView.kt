package com.matthewbartos.emailer.view

import com.matthewbartos.emailer.locale.Context
import com.matthewbartos.emailer.locale.ContextChangeListener
import com.matthewbartos.emailer.presenter.InboxPresenter
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable


class InboxView : JPanel() {

    private val presenter = InboxPresenter()
    private val context: Context = Context.INSTANCE

    private var component = prepareUI(arrayOf(arrayOf("", "", "")))

    private var loadedEmails: List<Array<String>> = emptyList()


    init {
        add(component)

        presenter.inboxSubject
            .map {
                it.map { email ->
                    arrayOf(
                        email.sent.toLocalTime().toString(),
                        email.sender,
                        email.subject
                    )
                }
            }
            .subscribe {
                loadedEmails = it
                reloadUI()
            }

        context.addContextChangeListener(ContextChangeListener {
            reloadUI()
        })
    }

    private fun reloadUI() {
        // TODO - Find a better way to update the table
        remove(component)

        component = prepareUI(loadedEmails.toTypedArray())
        add(component)

        updateUI()
    }

    private fun prepareUI(emails: Array<Array<String>>): JScrollPane =
        JScrollPane(
            JTable(
                emails,
                arrayOf(
                    context.bundle!!.getString("inbox_table_header_received"),
                    context.bundle!!.getString("inbox_table_header_sender"),
                    context.bundle!!.getString("inbox_table_header_subject")
                )
            ).apply {
                fillsViewportHeight = true
            }
        )
}