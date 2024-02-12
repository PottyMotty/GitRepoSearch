package com.pottymotty.gitreposearch.ui.screens.detail.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
fun RepositoryDetailHeader(repositoryDetail: GithubRepository) {
    Column {
        Text(text = repositoryDetail.name, style = MaterialTheme.typography.headlineMedium)
        Text(
            text = repositoryDetail.fullName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
        Gap(size = Spacings.half)
        FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Spacings.small), verticalArrangement = Arrangement.spacedBy(Spacings.small)) {
            Tag(
                text = "Created at: ${
                    repositoryDetail.createdAt.toFormattedString(DateTimeFormatPattern.DATE_WITH_MONTH_TEXT)
                }", tagColors = TagColors.accent()
            )
            Tag(
                text = "Last updated at: ${
                    repositoryDetail.lastUpdatedAt.toFormattedString(DateTimeFormatPattern.DATE_WITH_MONTH_TEXT)
                }", tagColors = TagColors.accent()
            )
            Tag(text = "Stars: ${repositoryDetail.starsCount}", tagColors = TagColors.accent())

            Tag(text = "Forks: ${repositoryDetail.forksCount}", tagColors = TagColors.accent())
        }
        Gap(size = Spacings.small)
        
    }
}


@Preview(showSystemUi = true)
@Composable
fun RepositoryDetailPreview() {
    RepositoryDetailHeader(repositoryDetail = mockRepository)
}