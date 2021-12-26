package com.vb.breastfeedingnotes

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.vb.breastfeedingnotes.navigation.Navigation
import com.vb.breastfeedingnotes.ui.theme.BreastFeedingNotesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.time.ExperimentalTime


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BreastFeedingNotesTheme {
                Navigation()
            }
        }
    }
}