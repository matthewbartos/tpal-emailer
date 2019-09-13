package com.matthewbartos.emailer.view

import com.matthewbartos.emailer.presenter.InboxPresenter
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable


class InboxView : JPanel() {

    private val presenter = InboxPresenter()

    init {
        var component = prepareUI(arrayOf(arrayOf("", "", "")))

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
                remove(component)
                component = prepareUI(it.toTypedArray())
                add(component)
                updateUI()
            }
    }

    private fun prepareUI(emails: Array<Array<String>>): JScrollPane =
        JScrollPane(
            JTable(
                emails,
                arrayOf("Received", "Sender", "Subject")
            ).apply {
                fillsViewportHeight = true
            }
        )
}