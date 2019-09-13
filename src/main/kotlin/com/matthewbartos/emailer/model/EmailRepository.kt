package com.matthewbartos.emailer.model

import io.reactivex.Observable
import io.reactivex.Observable.interval
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.time.LocalDateTime.now
import java.util.concurrent.TimeUnit.SECONDS

class EmailRepository {

    var storedEmailsObservable: Observable<List<Email>>

    private val storedEmailsSubject: Subject<List<Email>> = BehaviorSubject.create()

    private val emailGeneratorSubject: Subject<Email> = PublishSubject.create()

    private val storedEmails: MutableList<Email> = mutableListOf()

    init {
        interval(EMAIL_GENERATION_INTERVAL, SECONDS)
            .map {
                Email(
                    sender = SENDERS.random(),
                    subject = SUBJECTS.random(),
                    content = CONTENTS.random(),
                    sent = now(),
                    isRead = false,
                    isDeleted = false
                )
            }
            .subscribe(emailGeneratorSubject)

        emailGeneratorSubject
            .map { storedEmails.apply { add(it) } }
            .subscribe(storedEmailsSubject)

        storedEmailsObservable = storedEmailsSubject
    }

    companion object {

        const val EMAIL_GENERATION_INTERVAL = 4L

        val SUBJECTS = listOf(
            "Action required: Account suspended",
            "FW: Invoice F/1543/45",
            "Issue with the latest deployment",
            "Question about new privacy policy",
            "Invitation: Annual stakeholders meeting",
            "IMPORTANT: TP AL - Miniproject reminder"
        )

        val CONTENTS = listOf(
            "Hello! \nPlease check your calendar and pick a proper date to meet.",
            "Hi there, \nwe need to discuss this topic offline.\n Please respond ASAP!",
            "Good morning!\n We're informing you that your latest inquiry couldn't be completed.\n Please contact our technical department.",
            "Hello!\n Due to a recent database incident we are not able to complete your order."
        )

        val SENDERS = listOf(
            "jweglarz@cs.put.poznan.pl",
            "jjozefowska@cs.put.poznan.pl",
            "tmorzy@cs.put.poznan.pl",
            "it@cs.put.poznan.pl"
        )
    }
}
