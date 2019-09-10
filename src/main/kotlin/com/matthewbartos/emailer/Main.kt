package com.matthewbartos.emailer

import java.awt.EventQueue
import javax.swing.JFrame
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

class Main(title: String) : JFrame() {

    init {
        setTitle(title)

        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(300, 200)
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
}

fun main() = EventQueue.invokeLater(::createAndShowGUI)
