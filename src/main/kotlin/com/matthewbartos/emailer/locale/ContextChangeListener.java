package com.matthewbartos.emailer.locale;

public interface ContextChangeListener {
    /**
     * Signals the context has changed somehow. The listener
     * must reload all of its stateful variables and objects
     * associated with context's variables.
     *
     * <b>It is guaranteed that this method is always invoked
     * from the AWT thread.</b>
     */
    public void contextChanged();
}
