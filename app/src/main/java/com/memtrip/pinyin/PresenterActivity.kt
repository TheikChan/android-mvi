package com.memtrip.pinyin

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class PresenterActivity<V : PresenterView>(interact: Interact = RxInteract())
    : AppCompatActivity(), PresenterView, Interact by interact {

    var init = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inject()

        presenter().attach(view())
    }

    override fun onStart() {
        super.onStart()

        if (!init) {
            init = true
            presenter().first()
        } else {
            presenter().start()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter().resume()
    }

    override fun onPause() {
        super.onPause()
        presenter().pause()
    }

    override fun onStop() {
        super.onStop()
        presenter().stop()
    }

    override fun sendEvent(event: Event) {
        presenter().event().accept(event)
    }

    override fun showProgress() {
        TODO("not implemented")
    }

    override fun hideProgress() {
        TODO("not implemented")
    }

    override fun close() {
        finish()
    }

    override fun context(): Context = this

    abstract fun inject()

    abstract fun presenter(): Presenter<V>

    abstract fun view(): V
}