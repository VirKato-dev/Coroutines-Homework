package otus.homework.coroutines

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CatsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        viewModel = ViewModelProvider(this)[CatsViewModel::class.java]

        viewModel.factAndPict.observe(this) { fact ->
            view.populate(fact)
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.getFactAndPict()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFactAndPict()
    }

    override fun onDestroy() {
        viewModel.stop()
        super.onDestroy()
    }
}