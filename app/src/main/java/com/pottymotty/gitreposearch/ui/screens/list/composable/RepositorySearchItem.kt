package com.pottymotty.gitreposearch.ui.screens.list.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pottymotty.gitreposearch.model.GithubRepository
import com.pottymotty.gitreposearch.ui.common.composables.Gap
import com.pottymotty.gitreposearch.ui.common.composables.Tag
import com.pottymotty.gitreposearch.ui.common.composables.TagColors
import com.pottymotty.gitreposearch.ui.common.mockData.mockRepository
import com.pottymotty.gitreposearch.ui.theme.Spacings
import com.pottymotty.gitreposearch.util.DateTimeFormatPattern
import com.pottymotty.gitreposearch.util.toFormattedString

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RepositorySearchItem(
    repository: GithubRepository,
    index: Int,
    modifier: Modifier = Modifier,
    onClick: ()->Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = Spacings.default,
                vertical = Spacings.medium
            )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = repository.name,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier =Modifier.weight(1f)
                )
                Text(text = "#${index +1}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold,color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
            }
            Text(
                text = repository.fullName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
            Gap(size = Spacings.small)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(Spacings.small)) {
                Tag(text = "Stars: ${repository.starsCount}", tagColors = TagColors.accent())
                Tag(
                    text = "Last updated at: ${
                        repository.lastUpdatedAt.toFormattedString(DateTimeFormatPattern.DATE_WITH_MONTH_TEXT)
                    }", tagColors = TagColors.accent()
                )
            }
            Gap(size = Spacings.small)
            Text(
                text = repository.description ?: "No description",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Preview
@Composable
fun RepositorySearchItemPrev() {
    RepositorySearchItem(repository = mockRepository, index = 1) {

    }
}