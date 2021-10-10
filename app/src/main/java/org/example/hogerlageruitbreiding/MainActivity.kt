package org.example.hogerlageruitbreiding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import org.example.hogerlageruitbreiding.Game.*
import org.example.hogerlageruitbreiding.Game.PlayType.*
import org.example.hogerlageruitbreiding.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var game: Game

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val myRoot = binding.root
        setContentView(myRoot)

        // get game class from ViewModel. Using a ViewModel to store the game allows the game
        // state to be persisted throughout the lifecycle of the Activity
        // (e.g.: the score will not reset if the activity is rotated)
        val gameViewModel: GameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // if you add the following line to your module's gradle file:
        //     implementation 'androidx.activity:activity-ktx:1.3.1'
        // you can use the abbreviated syntax to retrieve the ViewModel:
        //     val model: GameViewModel by viewModels()

        game = gameViewModel.game

        binding.playButton.setOnClickListener { btnClicked() }

        updateScore()
    }

    /**
     * Callback for Play button
     */
    private fun btnClicked() {
        var userGuess = 0

        // get the number the user has input
        val edtNumberText: String = binding.numberEditText.text.toString()
        if (edtNumberText.isNotEmpty()) {
            userGuess = edtNumberText.toInt()
        }
        if (userGuess < 1 || userGuess > 12) {
            //Toast.makeText(this, R.string.input_out_of_bounds, Toast.LENGTH_LONG).show()
            binding.numberEditText.error = getString(R.string.input_out_of_bounds)
        } else {
            val toastMessage: String = when (game.play(userGuess)) {
                LOWER -> getString(R.string.lower)
                EQUAL -> getString(R.string.correct)
                HIGHER -> getString(R.string.higher)
                else -> getString(R.string.something_went_horribly_wrong)
            }
            // display the message to the user using a Toast
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
            updateScore()
        }
    }

    /**
     * Update the score TextView with the current score
     */
    private fun updateScore() {
        binding.scoreTextView.text = getString(R.string.score, game.score)
    }

}