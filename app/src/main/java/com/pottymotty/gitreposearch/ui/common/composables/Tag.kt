package com.pottymotty.gitreposearch.ui.common.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pottymotty.gitreposearch.ui.theme.Spacings

data class TagColors private constructor(
    val background: Color,
    val foreground: Color,
    val border: Color = Color.Unspecified,
){
    companion object {

        @Composable
        fun accent() : TagColors{
            return TagColors(
                background = MaterialTheme.colorScheme.tertiaryContainer,
                foreground = MaterialTheme.colorScheme.onTertiaryContainer,
                border = MaterialTheme.colorScheme.tertiary
            )
        }

        fun custom(
            background: Color,
            foreground: Color
        ) : TagColors{
            return TagColors(background, foreground)
        }
    }
}

@Composable
fun Tag(
    text: String,
    tagColors:TagColors
){
    Surface(color = tagColors.background, shape = MaterialTheme.shapes.extraSmall, border = BorderStroke(1.dp,tagColors.border)) {
        Text(text, color = tagColors.foreground, modifier = Modifier.padding(horizontal = Spacings.small, vertical = Spacings.mini), style = MaterialTheme.typography.bodySmall)
    }
}