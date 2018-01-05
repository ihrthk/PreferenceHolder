# PreferenceHolder


link:https://github.com/MarcinMoskala/PreferenceHolder

usage
```java
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
```
