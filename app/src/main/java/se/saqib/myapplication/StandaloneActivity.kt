package se.saqib.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.activity_standalone.*

class StandaloneActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standalone)

        btnPlayVideo.setOnClickListener(this)
        /*when we run the app
        * we can see it running in the landscape
        * at the moment, on it's side,
        * and that's because the Standalone
        * Player's running at full screen.
        * so the google standalone player doesn't
        * allow itself to be destroyed when the
        * orientation changes
        * but it also doesn't handle the
        * change itself.*/
        btnPlayList.setOnClickListener(this)
        /*then we wouldn't be able go to forward
        * any further, then I could go back
        * to previous videos.
        * So that functionality in the terms of playing
        * the videos in the playlist is skipping
        * back and forward,
        * that actually part and parcel of the
        * YouTube standalone player.*/
        /*
        * the advantage of the way we're using it
        * now through, is that it moves the onClick
        * code out of onCreate, and
        * this can make things more readable
        * when the onClick function's quite long.
        * */

        /*btnPlayVideo.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })*/

        /*btnPlayVideo.setOnClickListener(View.OnClickListener { v ->
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        })*/

        /*val listener = View.OnClickListener { v ->
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
        btnPlayVideo.setOnClickListener(listener)
        btnPlayList.setOnClickListener(listener)*/
        /*so here we basically created a new
        * onClickListener object, and assign it to a variable
        * and we can then pass that variable to the
        * setOnClickListener method of as many
        * buttons as we need
        *
        * now what that code does is create an
        * anonymous inner class, and assign an
        * instance of that to a variable*/

        /*third approach
        * make our activity a listener*/
    }

    override fun onClick(v: View) {
        val intent = when (v.id) {
            R.id.btnPlayVideo -> YouTubeStandalonePlayer.createVideoIntent(
                this, getString(R.string.GOOGLE_API_KEY), YOUTUBE_VIDEO_ID, 0, true, false)
            R.id.btnPlayList -> YouTubeStandalonePlayer.createPlaylistIntent(
                this, getString(R.string.GOOGLE_API_KEY), YOUTUBE_PLAYTLIST, 0, 0, true,
                true)

            else -> throw IllegalAccessException("Undefined button Clicked")
        }
        startActivity(intent)
    }
}

/*an intent
* is abstract(not abstract class means it is not tight to an particular operation)
* description of an operation to be performed*/

/*So what happening here is that google
* had prevented the activities in their
* player from being destroyed
* when the orientation changes.
* */
