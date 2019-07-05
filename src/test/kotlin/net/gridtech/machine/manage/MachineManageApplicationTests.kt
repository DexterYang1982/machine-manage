package net.gridtech.machine.manage

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe


fun main(args: Array<String>) {
    val a = A()
//    val o = Observable.create(a).doFinally {
//        println("1->${a.e.isDisposed}")
//    }
//    val d = o.subscribe {  }
//    d.dispose()
//    println("2->${a.e.isDisposed}")

    Observable.just(1,2).switchMap {
        if(it==1)
            Observable.create(a).doFinally {
                println("1->${a.e.isDisposed}")
            }
        else
            Observable.create(a).doFinally {
                println("2->${a.e.isDisposed}")
            }
    }.subscribe {
        println(it)
    }

}

class A : ObservableOnSubscribe<Int> {
    lateinit var e: ObservableEmitter<Int>
    override fun subscribe(emitter: ObservableEmitter<Int>) {
        emitter.onNext(99)
        e = emitter
    }
}