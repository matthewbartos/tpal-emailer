package com.matthewbartos.emailer

import com.matthewbartos.emailer.locale.Context
import com.matthewbartos.emailer.locale.ContextChangeListener
import com.matthewbartos.emailer.view.InboxView
import java.awt.EventQueue
import java.util.Locale
import javax.swing.JFrame
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.UIManager

class Main(title: String) : JFrame() {

    private val context: Context = Context.INSTANCE

    init {
        setTitle(title)

        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(500, 500)
        setLocationRelativeTo(null)

        jMenuBar = createMenu()

        context.addContextChangeListener(ContextChangeListener {
            jMenuBar = createMenu()
        })
    }

    private fun createMenu() =
        JMenuBar().apply {
            add(
                JMenu(context.bundle!!.getString("language_menu_item")).apply {
                    add(localeButton("pl"))
                    add(localeButton("en"))
                }
            )
        }

    private fun localeButton(languageCode: String): JMenuItem {
        val jMenuItem = JMenuItem(context.bundle!!.getString("language_$languageCode"))
        jMenuItem.addActionListener {
            context.locale = Locale(languageCode.toUpperCase())
        }

        return jMenuItem
    }
}

private fun createAndShowGUI() {
    val frame = Main("Mailer")
    frame.isVisible = true
    frame.contentPane.add(InboxView())
}

fun main() {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    EventQueue.invokeLater(::createAndShowGUI)
}
