package net.gridtech.machine.manage

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


abstract class A(){
    val D="".apply { println("D") }
    init {
        println("A")
    }
}
class B:A(){
    val C="".apply { println("C") }
    init {
        println("B")
    }
}

fun main(args: Array<String>) {
    B()


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