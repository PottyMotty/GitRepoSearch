package com.pottymotty.gitreposearch.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pottymotty.gitreposearch.ui.common.composables.Gap
import com.pottymotty.gitreposearch.ui.common.composables.ScreenHeader
import com.pottymotty.gitreposearch.ui.screens.detail.composable.OwnerDetail
import com.pottymotty.gitreposearch.ui.screens.detail.composable.RepositoryDetailHeader
import com.pottymotty.gitreposearch.ui.theme.Spacings
import com.pottymotty.gitreposearch.util.openBrowser
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class DetailScreen(val repositoryId: Long) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<DetailViewModel>() { parametersOf(repositoryId) }
        val detail = viewModel.detail.collectAsStateWithLifecycle().value
        val context = LocalContext.current
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (detail != null) {
                Column {
                    Column(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(
                                horizontal = Spacings.default,
                                vertical = Spacings.medium
                            )
                    ) {
                        ScreenHeader(title = "Repository", onBack = navigator::pop)
                        Gap(size = Spacings.huge)
                        RepositoryDetailHeader(repositoryDetail = detail.repository)
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.padding(
                                horizontal = Spacings.default,
                                vertical = Spacings.small
                            )
                        ) {
                            detail.repository.description?.let { description ->
                                Gap(size = Spacings.double)
                                Text(
                                    description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    lineHeight = 1.15.em
                                )
                            }
                            Gap(size = Spacings.default)
                            Text(
                                text = "Repository owner:",
                                style = MaterialTheme.typography.labelLarge
                            )
                            Gap(size = Spacings.mini)
                            OwnerDetail(
                                ownerDetail = detail.owner,
                                modifier = Modifier.fillMaxWidth()
                            ) { urlToProfile ->
                                context.openBrowser(urlToProfile)
                            }
                            Gap(size = Spacings.half)
                            Button(
                                onClick = { context.openBrowser(detail.repository.repositoryUrl) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Open repository on Github")
                            }
                        }
                    }
                }

            }
        }
    }
}

