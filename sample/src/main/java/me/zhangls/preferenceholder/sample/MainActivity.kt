package me.zhangls.preferenceholder.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import me.zhangls.preferenceholder.PreferenceHolder

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PreferenceHolder.setContext(this)

        input.setText(Pref.text)
        input.setSelection(Pref.text.length)

        btn.setOnClickListener {
            Pref.text = input.text.toString()
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
        }

    }
}

object Pref : PreferenceHolder() {
    var text: String by bindToPreferenceField("在这里输入要保存的文本")
}