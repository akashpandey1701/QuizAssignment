package com.example.quizassignment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityFlowTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun skippingTheFirstQuestionAdvancesImmediately() {
        waitForQuestion(1)

        composeRule.onNodeWithText("Skip").performClick()

        waitForQuestion(2)
    }

    @Test
    fun answeringTheFirstQuestionAutoAdvancesWithoutPressingNext() {
        waitForQuestion(1)

        composeRule.onNodeWithText("Flappy Bird-style game").performClick()

        waitForQuestion(questionNumber = 2, timeoutMillis = 5_000)
    }

    @Test
    fun completingTheQuizShowsResultsAndRestartReturnsToStart() {
        waitForQuestion(1)

        answerAndAdvance("Flappy Bird-style game")
        waitForQuestion(2)

        answerAndAdvance("Detecting accidental shakes")
        waitForQuestion(3)

        answerAndAdvance("SYSTEM_ALERT_WINDOW")
        waitForQuestion(4)

        answerAndAdvance("Janky animations and slow UI")
        waitForQuestion(5)

        answerAndAdvance("Add subtle hints in UI")
        waitForQuestion(6)

        answerAndAdvance("Swipe to dismiss")
        waitForQuestion(7)

        answerAndAdvance("Respect native behaviors per platform")
        waitForQuestion(8)

        answerAndAdvance("Move logic to a background thread after splash")
        waitForQuestion(9)

        answerAndAdvance("Not all users can perform physical gestures")
        waitForQuestion(10)

        answerAndAdvance("Show GPU overdraw")

        waitForText("Perfect score")
        composeRule.onNodeWithText("10/10").assertIsDisplayed()

        composeRule.onNodeWithText("Restart quiz").performClick()

        waitForQuestion(1)
    }

    private fun answerAndAdvance(answerText: String) {
        composeRule.onNodeWithText(answerText).performClick()
    }

    private fun waitForQuestion(
        questionNumber: Int,
        timeoutMillis: Long = 20_000
    ) {
        waitForText(
            text = "Question $questionNumber",
            timeoutMillis = timeoutMillis
        )
    }

    private fun waitForText(
        text: String,
        timeoutMillis: Long = 20_000
    ) {
        composeRule.waitUntil(timeoutMillis = timeoutMillis) {
            composeRule.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText(text).assertIsDisplayed()
    }
}
