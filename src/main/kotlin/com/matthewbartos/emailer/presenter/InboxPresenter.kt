package com.matthewbartos.emailer.presenter

import com.matthewbartos.emailer.model.Email
import com.matthewbartos.emailer.model.EmailRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class InboxPresenter {

    val inboxSubject: Subject<List<Email>> = BehaviorSubject.create()

    private val emailRepository = EmailRepository()

//    val displayInboxEmails: Observable<List<Email>> = inboxSubject

    init {
        emailRepository.storedEmailsObservable
            .subscribe(inboxSubject)
    }
}
