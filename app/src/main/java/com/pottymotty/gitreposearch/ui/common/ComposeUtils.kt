package com.pottymotty.gitreposearch.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

typealias ComposableFunction  = @Composable ()->Unit


@Composable
fun Modifier.clickableWithoutIndication(enabled : Boolean =true,action: ()->Unit) = this.clickable(
    interactionSource = remember {
        MutableInteractionSource()
    },
    indication = null,
    enabled = enabled,
    onClick = action
)
