package otus.homework.coroutines

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import otus.homework.coroutines.model.FactAndPict

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(fact: FactAndPict) {
        findViewById<TextView>(R.id.fact_textView).text = fact.fact.text
        Picasso.get().load(fact.pict).resize(100, 100).centerCrop()
            .into(findViewById<ImageView>(R.id.fact_imageView))
    }
}

interface ICatsView {

    fun populate(fact: FactAndPict)
}