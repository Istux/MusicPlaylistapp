package vcmsa.isiyak.musicplaylistapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ViewScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_screen)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //code starts here
        //receives the data from the activity main.
        val songListText = findViewById<TextView>(R.id.TvListOfSongs)
        val averageRatingText = findViewById<TextView>(R.id.TvAverageRating)
        //retrieves the data from the intent
        val titles = intent.getStringArrayListExtra("titles") ?: arrayListOf()
        val artists = intent.getStringArrayListExtra("artists") ?: arrayListOf()
        val ratings = intent.getStringArrayListExtra("ratings") ?: arrayListOf()
        val comments = intent.getStringArrayListExtra("comments") ?: arrayListOf()

        val builder = StringBuilder()
        for (i in titles.indices) {
            builder.append("🎵 ${titles[i]} by ${artists[i]} (Rating: ${ratings[i]})\nComment: ${comments[i]}\n\n")
        }

        songListText.text = builder.toString()
        //calculates the average rating
        val average = if (ratings.isNotEmpty()) {
            ratings.mapNotNull { it.toIntOrNull() }.average()
        } else 0.0
        //calculates the average rating
        averageRatingText.text = "Average Rating: %.2f".format(average)
    }
}