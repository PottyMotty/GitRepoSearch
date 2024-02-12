package com.pottymotty.gitreposearch.ui.common.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.pottymotty.gitreposearch.ui.common.clickableWithoutIndication
import com.pottymotty.gitreposearch.ui.theme.Spacings

@Composable
fun SearchField(
    value: String,
    modifier: Modifier =Modifier,
    colors : TextFieldColors? =null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String)->Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        maxLines = 5,
        colors = colors ?: OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "search")
        },
        placeholder = {
            Text(text = "Search query")
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = value.isNotEmpty(),
                enter = slideInHorizontally { fullWidth -> fullWidth / 5 } + fadeIn(),
                exit = slideOutHorizontally { fullWidth -> fullWidth / 5 } + fadeOut()) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(start = Spacings.small)
                        .size(22.dp)
                        .clickableWithoutIndication { onValueChange("") },
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear"
                )
            }
        }
    )
}