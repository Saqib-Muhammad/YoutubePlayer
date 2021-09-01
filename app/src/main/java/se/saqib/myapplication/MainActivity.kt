package se.saqib.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlaySingle.setOnClickListener(this)
        btnStandalone.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val intent = when (view.id) {
            R.id.btnPlaySingle -> Intent(this, YoutubeActivity::class.java)
            R.id.btnStandalone -> Intent(this, StandaloneActivity::class.java)
            else -> throw IllegalAccessException("Undefined button clicked")
        }
        startActivity(intent)
    }
}

/*now the intent class has got several constructors,
* but when you want to use to use an intent to launch
* new activity
*
* YoutubeActivity::class.java
* that's called a class literal,
* and it's a way to pass a reference to a class as a parameter.
* now you wouldn't pass string or integer as a parameter.
* you'd use an actual string such as, you
* know, tim in double quotes, or an actual number like 100,
* and this is the same thing
* we need to an object of type class,
* whose value is the youtubeActivity class
* and that's how you do it in kotlin
*       just remember that when you want to pass a class
* to a function, you have to create a class literal
* by adding the colon class at the end
* because these are java classes we're
* also adding dot java*/
