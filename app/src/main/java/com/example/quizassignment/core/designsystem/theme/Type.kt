package com.example.quizassignment.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val QuizScreenFontFamily = FontFamily.SansSerif

val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 30.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)

val QuizHeaderProgressTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 19.sp,
    lineHeight = 24.sp
)

val QuizHeaderBadgeTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 17.sp,
    lineHeight = 22.sp
)

val QuizSkipTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 15.sp,
    lineHeight = 20.sp
)

val QuizQuestionTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    lineHeight = 25.sp
)

val QuizAnswerTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 19.sp
)

val QuizOptionLabelTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 13.sp,
    lineHeight = 18.sp
)

val QuizSupportingTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 20.sp
)

val ResultsEyebrowTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 15.sp,
    lineHeight = 20.sp
)

val ResultsHeadlineTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 32.sp,
    lineHeight = 38.sp
)

val ResultsSupportingTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 24.sp
)

val ResultsSectionTitleTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 15.sp,
    lineHeight = 20.sp
)

val ResultsTimelineNumberTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 15.sp,
    lineHeight = 20.sp
)

val ResultsTimelineStatusTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 22.sp
)

val ResultsStatValueTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    lineHeight = 24.sp
)

val ResultsStatLabelTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    lineHeight = 16.sp
)

val ResultsButtonTextStyle = TextStyle(
    fontFamily = QuizScreenFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    lineHeight = 20.sp
)
