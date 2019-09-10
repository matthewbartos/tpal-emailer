package com.matthewbartos.emailer

import java.awt.EventQueue
import javax.swing.JFrame

class Main(title: String) : JFrame() {

    init {
        createUI(title)
    }

    private fun createUI(title: String) {

        setTitle(title)

        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(300, 200)
        setLocationRelativeTo(null)
    }
}

private fun createAndShowGUI() {

    val frame = Main("Simple")
    frame.isVisible = true
}

fun main(args: Array<String>) {
    EventQueue.invokeLater(::createAndShowGUI)
}