package com.pottymotty.gitreposearch.ui.screens.list.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pottymotty.gitreposearch.ui.common.ComposableFunction
import com.pottymotty.gitreposearch.ui.common.composables.Gap
import com.pottymotty.gitreposearch.ui.theme.Spacings

@Composable
fun ErrorItem(errorText: String, modifier: Modifier =Modifier, actionItem : ComposableFunction ={}) {
    Surface(modifier=modifier,color = MaterialTheme.colorScheme.errorContainer, border = BorderStroke(1.dp,MaterialTheme.colorScheme.error), shape = MaterialTheme.shapes.medium) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(Spacings.default)) {
            Icon(imageVector = Icons.Rounded.Warning, contentDescription = null)
            Gap(size = Spacings.half)
            Text(text = errorText, color=MaterialTheme.colorScheme.onErrorContainer, modifier = Modifier.weight(1f))
            actionItem()
        }
    }
}


@Preview
@Composable
fun ErrorItemPrev() {
    ErrorItem("There is an error!")
}
@Preview
@Composable
fun ErrorItemWithActionPrev() {
    ErrorItem("There is an error!"){
        Icon(imageVector = Icons.Rounded.Refresh, contentDescription = "refresh")
    }
}