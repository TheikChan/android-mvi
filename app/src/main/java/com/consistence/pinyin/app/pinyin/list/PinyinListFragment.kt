package com.consistence.pinyin.app.pinyin.list

import android.os.Bundle
import com.consistence.pinyin.app.pinyin.PinyinLayout
import com.consistence.pinyin.app.pinyin.detail.PinyinDetailActivity
import com.consistence.pinyin.audio.PlayPinyAudioInPresenter
import com.consistence.pinyin.domain.pinyin.Pinyin
import com.memtrip.mxandroid.MxViewFragment
import io.reactivex.Observable
import javax.inject.Inject

abstract class PinyinListFragment
    : MxViewFragment<PinyinListIntent, PinyinListRenderAction, PinyinListViewState, PinyinListLayout>(), PinyinListLayout {

    @Inject lateinit var render: PinyinListRenderer

    private val pinyinAudio = PlayPinyAudioInPresenter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model().publish(PinyinListIntent.Search((context as PinyinLayout).currentSearchQuery))
    }

    override fun onStart() {
        super.onStart()
        context?.let { pinyinAudio.attach(it) }
    }

    override fun onStop() {
        super.onStop()
        context?.let { pinyinAudio.detach(it) }
    }

    override fun intents(): Observable<PinyinListIntent> =
            Observable.just(PinyinListIntent.Init((context as PinyinLayout).currentSearchQuery))

    override fun render(): PinyinListRenderer = render

    override fun navigateToPinyinDetails(Pinyin: Pinyin) {
        model().publish(PinyinListIntent.Idle)
        startActivity(PinyinDetailActivity.newIntent(context!!, Pinyin))
    }

    override fun playAudio(audioSrc: String) {
        context?.let { pinyinAudio.playPinyinAudio(audioSrc, it) }
    }
}