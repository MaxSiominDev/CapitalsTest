package dev.maxsiomin.capitals.fragments.game

import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxsiomin.capitals.R
import dev.maxsiomin.capitals.util.*
import dev.maxsiomin.capitals.util.SharedPrefsConfig.PARTS_OF_WORLD
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(uiActions: UiActions) : BaseViewModel(uiActions) {

    val actionBarTitle = MutableLiveData<String>()

    val buttonNextText = MutableLiveData(getString(R.string.next))

    val backgroundColor = MutableLiveData<Int>()

    val currentQuestion = MutableLiveData<String>()

    val answers = MutableLiveData<List<String>>()

    private val allQuestions = getQuestions()

    private val gameInfo = GameInfo()

    init {
        updateQuestion()
    }

    private lateinit var gameOverCallback: (Int) -> Unit

    fun setGameOverCallback(callback: (Int) -> Unit) {
        gameOverCallback = callback
    }

    fun updateQuestion() {
        // Out of questions
        if (gameInfo.questionsAnswered == 10) {
            gameOverCallback(gameInfo.correctAnswers)
            return
        }

        // Last question and need to change "next" to "finish"
        if (gameInfo.questionsAnswered == 9) {
            buttonNextText.value = getString(R.string.finish)
        }

        val newQuestion = allQuestions[gameInfo.questionsAnswered]

        val newAnswers = mutableListOf<String>()

        val correctAnswer = allCountriesWithCities[newQuestion]!!
        newAnswers.add(correctAnswer)

        val wrongAnswers = allCitiesOnly.toMutableList().apply {
            remove(correctAnswer)
        }.shuffled().subList(0, 2 + 1)
        newAnswers.addAll(wrongAnswers)

        newAnswers.shuffle()

        currentQuestion.value = newQuestion
        answers.value = newAnswers
        actionBarTitle.value = "${getString(R.string.new_game)} ${gameInfo.questionsAnswered + 1}/10"
    }

    fun onAnswered(answer: String) {
        gameInfo.questionsAnswered++
        if (isCorrect(answer)) {
            backgroundColor.value = ContextCompat.getColor(context, R.color.color_answer_correct)
            gameInfo.correctAnswers++
        } else {
            backgroundColor.value = ContextCompat.getColor(context, R.color.color_answer_incorrect)
        }

        Timber.i(gameInfo.toString())
    }

    private fun isCorrect(answer: String): Boolean {
        val correctAnswer = allCountriesWithCities[currentQuestion.value]
        return correctAnswer == answer
    }

    private fun getQuestions(): List<String> {
        val requestedTopics = sharedPrefs.getStringSet(PARTS_OF_WORLD, setOf())!!.toSet()
        val questions = mutableSetOf<String>()

        if ("Europe" in requestedTopics) questions += europe.keys
        if ("Africa" in requestedTopics) questions += africa.keys
        if ("Asia" in requestedTopics) questions += asia.keys
        if ("America" in requestedTopics) questions += america.keys

        return questions.shuffled().subList(0, 9 + 1)
    }

    data class GameInfo(
        var questionsAnswered: Int,
        var correctAnswers: Int,
    ) {
        constructor() : this(0, 0)
    }
}
