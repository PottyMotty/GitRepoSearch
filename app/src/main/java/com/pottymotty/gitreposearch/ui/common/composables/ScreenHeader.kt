package com.pottymotty.gitreposearch.ui.common.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import com.pottymotty.gitreposearch.ui.theme.Sizes
import com.pottymotty.gitreposearch.ui.theme.Spacings

@Composable
fun ScreenHeader(title: String, onBack : (()->Unit)? = null) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if(onBack!=null){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back button",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onBack()
                    }
                    .padding(Spacings.small)
                    .size(Sizes.Icons.normal)

            )
        }
        Gap(size = Spacings.medium)
        Text(text = title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium), color = MaterialTheme.colorScheme.onSurface)
    }
}