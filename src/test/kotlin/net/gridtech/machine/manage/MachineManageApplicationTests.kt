package net.gridtech.machine.manage

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


fun main(args: Array<String>) {
    var a=Observable.just(1, 2, 3)
            .filter {
                println(it)
                it == 2
            }
            .firstElement().toObservable().publish()
    a.subscribe {  println("S1") }
    a.subscribe {  println("S2") }

    a.connect()

//    val p = PublishSubject.create<Int>()
//    val x=Observable.concat(
//            Observable.just(8,9,10),
//            p
//    )
//    val a = x
//            .map {
//                println("a->$it")
//                it
//            }.publish()
//    a.connect()
//    val b = a.subscribe {
//        System.err.println("b->$it")
//    }
//    val c = a.subscribe {
//        System.err.println("c->$it")
//    }

//    p.onNext(1)
//    p.onNext(2)
//    p.onNext(3)

}