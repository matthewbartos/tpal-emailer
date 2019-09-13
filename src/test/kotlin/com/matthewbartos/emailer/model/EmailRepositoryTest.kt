package com.matthewbartos.emailer.model

import io.reactivex.observers.TestObserver
import org.junit.Test
import java.lang.Thread.sleep

class EmailRepositoryTest {

    private val emailRepository = EmailRepository()

    @Test
    fun testing() {
        with(TestObserver<List<Email>>()) {
            emailRepository.storedEmailsObservable.subscribe(this)

            sleep(20 * 1000)

            assertValueCount(20)
        }
    }
}