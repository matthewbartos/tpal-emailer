package com.matthewbartos.emailer.view

import com.matthewbartos.emailer.locale.Context
import com.matthewbartos.emailer.locale.ContextChangeListener
import com.matthewbartos.emailer.model.Email
import com.matthewbartos.emailer.presenter.InboxPresenter
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS
import javax.swing.ListSelectionModel.SINGLE_SELECTION


class InboxView : JPanel() {

    private val presenter = InboxPresenter()
    private val context: Context = Context.INSTANCE

    private var component = prepareUI(arrayOf(arrayOf(context.bundle!!.getString("loading"), "", "")))

    private var emailsToDisplay: List<Email> = emptyList()

    init {
        add(component)

        presenter.inboxSubject
            .subscribe {
                emailsToDisplay = it
                reloadUI()
            }

        context.addContextChangeListener(ContextChangeListener {
            reloadUI()
        })
    }

    private fun reloadUI() {
        val tableData = emailsToDisplay.map { email ->
            arrayOf(
                "${email.sent.hour}:${email.sent.minute}:${email.sent.second}",
                email.sender,
                email.subject
            )
        }.toTypedArray()

        // TODO - Find a better way to update the table
        remove(component)

        component = prepareUI(tableData)
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
                autoResizeMode = AUTO_RESIZE_ALL_COLUMNS
                setSelectionMode(SINGLE_SELECTION)

                selectionModel.addListSelectionListener {
                    if (it.valueIsAdjusting && emailsToDisplay.isNotEmpty()) {
                        JFrame().apply {
                            isVisible = true
                            contentPane = EmailView(emailsToDisplay[it.firstIndex])

                            setSize(600, 400)
                            setLocationRelativeTo(null)
                        }

                    }
                }
            }
        )
}