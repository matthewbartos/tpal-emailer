package com.matthewbartos.emailer.locale

import java.util.ArrayList
import java.util.Locale
import java.util.ResourceBundle
import javax.swing.SwingUtilities


/**
 * A GUI context object.
 *
 * @author Dawid Weiss
 */
class Context(private val baseName: String) {

    /**
     * Listeners waiting for events happening
     * on this context.
     *
     * @see ContextChangeListener
     */
    private val listeners = ArrayList<ContextChangeListener>()

    /**
     * @return Returns currently active locale.
     */
    /**
     * Sets new locale to be used by the GUI. This
     * method triggers an event propagated to all listeners.
     */
    var locale: Locale? = null
        set(locale) {
            if (locale == this.locale) {
                return
            }

            val bundle = ResourceBundle.getBundle(baseName, locale!!, this.javaClass.classLoader)
                ?: throw IllegalArgumentException("No resource bundle for: " + locale.language)

            field = locale
            this.bundle = bundle

            fireContextChangedEvent()
        }
    /**
     * @return Returns currently active resource bundle.
     */
    var bundle: ResourceBundle? = null
        private set

    /**
     * Adds a new [ContextChangeListener] to the list of
     * objects listening on the changes of this context.
     */
    @Synchronized
    fun addContextChangeListener(listener: ContextChangeListener) {
        listeners.add(listener)
    }

    /**
     * Fires an event to all listeners. The event may be delayed
     * but will be delivered eventually. The order of calls to this
     * method is always preserved.
     */
    private fun fireContextChangedEvent() {
        // Event dispatcher code wrapped in a runnable.
        val dispatcher = Runnable {
            synchronized(this@Context) {
                val i = listeners.iterator()
                while (i.hasNext()) {
                    i.next().contextChanged()
                }
            }
        }

        // The contract in ContextChangeListener states that
        // <code>contextChanged</code> method must be invoked
        // from the AWT thread. If we're the AWT thread, execute
        // immediately. Otherwise just enqueue the event dispatcher.
        if (SwingUtilities.isEventDispatchThread()) {
            dispatcher.run()
        } else {
            SwingUtilities.invokeLater(dispatcher)
        }
    }
}
