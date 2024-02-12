package com.pottymotty.gitreposearch.ui.screens.detail.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pottymotty.gitreposearch.model.RepositoryOwner
import com.pottymotty.gitreposearch.ui.common.composables.Gap
import com.pottymotty.gitreposearch.ui.common.mockData.mockOwner
import com.pottymotty.gitreposearch.ui.theme.Spacings

@Composable
fun OwnerDetail(
    ownerDetail: RepositoryOwner,
    modifier: Modifier = Modifier,
    openProfile: (String) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(Spacings.medium)
        ) {
            AsyncImage(
                ownerDetail.avatarImageUrl,
                contentDescription = "profile picture",
                modifier = Modifier
                    .aspectRatio(1f, matchHeightConstraintsFirst = true)
                    .clip(
                        CircleShape
                    ),
                error = rememberVectorPainter(image = Icons.Default.AccountCircle)
            )
            Gap(size = Spacings.medium)
            Column {
                Text(ownerDetail.name, style = MaterialTheme.typography.titleLarge)
                Gap(size = Spacings.small)
                Button(onClick = { openProfile(ownerDetail.linkToProfile) }) {
                    Text(text = "Open profile")
                }
            }
        }
    }
}


@Preview
@Composable
fun OwnerDetailPreview() {
    OwnerDetail(ownerDetail = mockOwner) {}
}