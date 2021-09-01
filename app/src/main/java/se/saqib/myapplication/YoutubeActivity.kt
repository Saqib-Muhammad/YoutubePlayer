package se.saqib.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.ContentView
import androidx.constraintlayout.solver.widgets.ConstraintTableLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import kotlinx.android.synthetic.main.test.*

const val YOUTUBE_VIDEO_ID = "Libvk8HUxak"
const val YOUTUBE_PLAYTLIST = "PLXtTjtWmQhg1SsviTmKkWO5n0a_-T0bnD"

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener/*interface*/ {

    private val TAG = "YoutubeActivity"
    private val DIALOG_REQUEST_CODE = 1

    val playerView by lazy { YouTubePlayerView(this) }
    /*so we have to use a lazy initialization done here
    * because we can't instantiate youtubePlayerView
    * after we add to the layout in onCreate*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_youtube)
//        val layout = findViewById<ConstraintLayout>(R.id.activity_youtube)

        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
        setContentView(layout)

       /* val button1 = Button(this)
        button1.layoutParams = ConstraintLayout.LayoutParams(600,180)
        button1.text = "Button Added"
        layout.addView(button1)*/

        //val playerView = YouTubePlayerView(this)
        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layout.addView(playerView)

        playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youtubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        /*we need to do is to initialize the player,
        * which is how to tell it what our key is
        * we do that by call the current method*/

        /*provider
        * is an object that implements the Youtube Player.Provider
        * interface, which our youTubeView Player does. So that
        * parameter will contain a reference to our Player widget.
        *
        * youtubePlayer
        * that we can use to control
        * playback such as pausing or
        * stopping the video
        *
        * restored
        * now if that's
        * true then we shouldn't start a new video,
        * and that's because the player is continue
        * with the one that it was already playing*/

        Log.d(TAG, "onInitializationSuccess: provider is ${provider?.javaClass}")
        /*whenever you get an object that's a
        * parameter, you can use this dot javaClass*/
        Log.d(TAG, "onInitializationSuccess: youTubePlayer is ${youtubePlayer?.javaClass}")
        Toast.makeText(this, "Initialized Youtube Player successfully",
            Toast.LENGTH_SHORT).show()

        youtubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
        youtubePlayer?.setPlaybackEventListener(playbackEventListener)
        /*to write call back functions that external code calls,
        * when it need to notify our apps of important events.*/

        if (!wasRestored) {
//            youtubePlayer?.cueVideo(YOUTUBE_VIDEO_ID)
            youtubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)
        } else {
            youtubePlayer?.play()
        }

    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youtubeInitializationResult: YouTubeInitializationResult?
    ) {
        // val REQUESTCODE = 0

        if (youtubeInitializationResult?.isUserRecoverableError == true) {
            youtubeInitializationResult.getErrorDialog(this, /*REQUESTCODE*/ DIALOG_REQUEST_CODE)
                .show()
        } else {
            val errorMessage = "There was an error initializing the YouTubePlayer ($youtubeInitializationResult)"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private val playbackEventListener = object: YouTubePlayer.PlaybackEventListener/*interface*/ {
        override fun onSeekTo(p0: Int) {
        }

        override fun onBuffering(p0: Boolean) {
        }

        override fun onPlaying() {
            Toast.makeText(this@YoutubeActivity, "Good, video is playing ok",
                Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@YoutubeActivity, "Video has stopped",
                Toast.LENGTH_SHORT).show()
        }

        override fun onPaused() {
            Toast.makeText(this@YoutubeActivity, "Video is paused",
                Toast.LENGTH_SHORT).show()
        }
    }

    private val playerStateChangeListener = object: YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
            Toast.makeText(this@YoutubeActivity,
                "Click Ad now, make the video creator rich!", Toast.LENGTH_SHORT).show()
        }

        override fun onLoading() {
        }

        override fun onVideoStarted() {
            Toast.makeText(this@YoutubeActivity, "Video has started",
                Toast.LENGTH_SHORT).show()
        }

        override fun onLoaded(p0: String?) {
        }

        override fun onVideoEnded() {
            Toast.makeText(this@YoutubeActivity,
                "Congratulations, You've completed another video.", Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult called with respond code $resultCode " +
                "for request $requestCode")

        if (requestCode == DIALOG_REQUEST_CODE) {
            Log.d(TAG, intent?.toString())
            Log.d(TAG, intent?.extras.toString())
            playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)

        }
    /*when we use startActivityForResult, we provide
    * a requestCode. When the started activity
    * calls your onActivityResult, it sends back
    * that requestCode.*/
    }
}

/*"interface"
* as a sort of contract
* so if a class implements an interface
* then what it's doing is it's guaranteeing that it will provide
* certain functions: the function defined in the interface.
* so by doing this, anything that expects
* an object of the interface type knows
* that it can call these functions
*
* YouTubePlayer.OnInitializedListener interface
* so by implementing that interface we're telling that youtube API
* that it will be able to call certain
* that our class will definitely provide
* Now because this contract is totally binding, we actually get an error
* until we implement those methods. And AndroidStudio here is insisting that we do implementing
* these methods
* */

/*for now though i just want to clear the error,
* so that we can look at adding the youtubePlayerView in our code
* so we have seen how to add widgets in our layout, so the layout designer,
* and this youtube library come with a youtubePlayerView widget, that we can add to a
* layout to play videos
* now we're not going to add it the layout designer though
* we're going to do it all in code
* few reason to why to do that
* it easier for one thing, but it also a good chance to show you how to add widget to a
* layout code
* "i'm just going to use the layout designer to show you
* a couple of problems if we are trying to add to the youtube player in the designer"
* 1. now the first problem is that that the youtube player doesn't appear anywhere here in
* the list of the widgets even though we've added the api library to our project
*
* so basically it's complaining that it can't instantiate the youtube player class
* and further on down if we
* have a bit of read
* */

/*
* So that's actually doing exactly the same thing
* as the previous two lines of code.
* when setContentView is called with the resources ID of an XML
* layout file, it inflate the xml and sets the activities view to be the view
* it creates from the xml
* Now this the second way inflates the xml*/

/*Now at the moment we've got a warning
* here on line 15, 'Avoid passing null as the view route'.
* Now I do definitely recommend paying attention to
* the warning when ever you see it
* and that's because it's a common cause
* of displaying problems
* there also a lot of code on line that passes null there
* when it should not and we'll be seeing examples of what you
* should be passing as the view's route in the later apps
*
* Now there are always exceptions to any rule though,
* and here we're inflating the route layout for the activity,
* so this is route view. So this is one of only couple
* of times when you pass null and we can safely ignore this warning*/

/*But fortunately our YoutubePlayerView is just going to fill
* the entire space available in the layout
* So the code going to be most identical to this
* The only difference here is that.
* we're going to specify MATCH_PARENT
* for the width and height*/

/*enum
* is basically a way to group
* constants together into a type*/

/*so to create one, we just call a static
* make function. Now we have to give
* it a context, which is something
* we've been doing a lot*/

/*DIFFERENT TYPES OF EVENT THE YOUTUBEPLAYER RESPOND TO:
* now the google have separate into two different types of events:
* events that are associated with the playing of a video,
* and those that represent a change in the state of the video.
*
* create a new playbackEventListener, to handle the playback events.*/

/*but if you play too quickly, the video hasn't finished loading.
* so there actually two things i suggest you do if you hit problems like this.
* First add a lot of logging to all the functions,
* and that includes the callback functions
* that we haven't used.
*       now what you will find is that we'll be trying to call play,
* before onLoaded callback's been received,
* the one in the playerStateChangeListener.
* So obviously the video can't play if it hasn't finished loading
*
* secondly, importantly, make sure you read all the documentation.
* now if you scroll down all of these methods*/
