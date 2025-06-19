package vcmsa.isiyak.musicplaylistapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private val songTitles = ArrayList<String>()
    private val artistNames = ArrayList<String>()
    private val songRatings = ArrayList<String>()
    private val comments = ArrayList<String>()

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //code starts here
        //declarations
        val TvSongTitle = findViewById<EditText>(R.id.TvSongTitle)
        val TvArtistName = findViewById<EditText>(R.id.TvArtistName)
        val TvSongRating = findViewById<EditText>(R.id.TvSongRating)
        val TvComment = findViewById<EditText>(R.id.TvComment)

        val btnAddToThePlaylist = findViewById<Button>(R.id.btnAddToThePlaylist)
        val btnExit = findViewById<Button>(R.id.btnExit)
        val btnInformation = findViewById<Button>(R.id.btnInformation)
        //adding listeners
        btnAddToThePlaylist.setOnClickListener {
            val title = TvSongTitle.text.toString().trim()
            val artist = TvArtistName.text.toString().trim()
            val ratingStr = TvSongRating.text.toString().trim() // <-- fixed: use correct variable
            val comment = TvComment.text.toString().trim()

            val rating = ratingStr.toIntOrNull()
            // <-- fixed: check if rating is null or out of range
            if (title.isEmpty() || artist.isEmpty() || rating == null || rating !in 1..5) {
                Toast.makeText(this, "Please enter valid data (rating must be 1–5)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        // <-- fixed: check if playlist is full
            if (songTitles.size >= 4) {
                Toast.makeText(this, "Playlist is full", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            songTitles.add(title)
            artistNames.add(artist)
            songRatings.add(rating.toString())
            comments.add(comment)

            Toast.makeText(this, "Song added!", Toast.LENGTH_SHORT).show()

            // Clear inputs
            TvSongTitle.text.clear()
            TvArtistName.text.clear()
            TvSongRating.text.clear()
            TvComment.text.clear()
        }

        btnExit.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
        // <-- fixed: use correct variable name
        btnInformation.setOnClickListener {
            val intent = Intent(this, ViewScreen::class.java)
            intent.putStringArrayListExtra("titles", songTitles)
            intent.putStringArrayListExtra("artists", artistNames)
            intent.putStringArrayListExtra("ratings", songRatings)
            intent.putStringArrayListExtra("comments", comments)
            startActivity(intent)
        }
    }
}