package com.pottymotty.gitreposearch.ui.main

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.pottymotty.gitreposearch.ui.screens.search.SearchScreen
import com.pottymotty.gitreposearch.ui.theme.GitRepoSearchTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitRepoSearchTheme {
               Navigator(SearchScreen()){
                   SlideTransition(it)
               }
            }
        }
    }
}