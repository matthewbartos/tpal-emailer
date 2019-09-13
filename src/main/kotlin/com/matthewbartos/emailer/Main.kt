package com.matthewbartos.emailer

import com.matthewbartos.emailer.view.InboxView
import java.awt.BorderLayout
import java.awt.Color.BLUE
import java.awt.EventQueue
import javax.swing.JFrame
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.UIManager

class Main(title: String) : JFrame() {

    init {
        setTitle(title)

        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(500, 500)
        setLocationRelativeTo(null)

        jMenuBar = JMenuBar().apply {
            add(
                JMenu("Language").apply {
                    add(JMenuItem("English"))
                    add(JMenuItem("Polish"))
                }
            )
        }
    }
}

private fun createAndShowGUI() {
    val frame = Main("Mailer")
    frame.isVisible = true
    frame.contentPane.add(InboxView())
}

fun main() {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    com.sun.javafx.application.PlatformImpl.startup { }
    EventQueue.invokeLater(::createAndShowGUI)
}
