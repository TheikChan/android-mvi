package com.consistence.pinyin.app.list.character

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxbinding2.view.RxView
import com.consistence.pinyin.legacy.AdapterClick
import com.consistence.pinyin.legacy.AdapterEvent
import com.consistence.pinyin.R
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.kit.SimpleAdapter
import com.consistence.pinyin.kit.SimpleAdapterViewHolder
import com.consistence.pinyin.kit.gone
import com.consistence.pinyin.kit.visible
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

class PinyinCharacterAdapter(
        context: Context,
        interaction: Consumer<AdapterEvent<PinyinEntity>>) : SimpleAdapter<PinyinEntity>(context, interaction) {

    override fun createViewHolder(parent: ViewGroup): SimpleAdapterViewHolder<PinyinEntity> {
        val viewHolder = PinyinCharacterViewHolder(inflater.inflate(
                R.layout.pinyin_character_list_item, parent, false))

        RxView.clicks(viewHolder.audioButton).flatMap({
            ObservableSource<AdapterEvent<PinyinEntity>> {
                val position = viewHolder.getAdapterPosition()
                it.onNext(AdapterClick(viewHolder.audioButton.getId(), data[position]))
            }
        }).subscribe(interaction)

        return viewHolder
    }
}

class PinyinCharacterViewHolder(itemView: View) : SimpleAdapterViewHolder<PinyinEntity>(itemView) {

    @BindView(R.id.pinyin_character_list_item_value)
    lateinit var characterTextView: TextView

    @BindView(R.id.pinyin_list_audio_button)
    lateinit var audioButton: ImageButton

    init {
        ButterKnife.bind(this, itemView)
    }

    override fun populate(position: Int, value: PinyinEntity) {
        characterTextView.text = value.chineseCharacters
        value.audioSrc?.let { audioButton.visible() } ?: audioButton.gone()
    }
}