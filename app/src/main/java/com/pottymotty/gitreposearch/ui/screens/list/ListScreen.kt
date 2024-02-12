package com.pottymotty.gitreposearch.ui.screens.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pottymotty.gitreposearch.ui.common.composables.Gap
import com.pottymotty.gitreposearch.ui.common.composables.ScreenHeader
import com.pottymotty.gitreposearch.ui.common.composables.SearchField
import com.pottymotty.gitreposearch.ui.screens.detail.DetailScreen
import com.pottymotty.gitreposearch.ui.screens.list.composable.ErrorItem
import com.pottymotty.gitreposearch.ui.screens.list.composable.RepositorySearchItem
import com.pottymotty.gitreposearch.ui.theme.Sizes
import com.pottymotty.gitreposearch.ui.theme.Spacings
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class ListScreen(val searchString: String) : Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<ListViewModel>() { parametersOf(searchString) }
        val screenState by viewModel.screenState.collectAsStateWithLifecycle()
        val items = viewModel.pagedItems.collectAsLazyPagingItems()
        val navigator = LocalNavigator.currentOrThrow
        val lazyListState = rememberLazyListState()
        val scrolledDown by remember(lazyListState) {
            derivedStateOf {
                lazyListState.firstVisibleItemIndex > 0
            }
        }
        val scope = rememberCoroutineScope()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .safeDrawingPadding()
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = Spacings.default,
                    vertical = Spacings.medium
                )
            ) {
                ScreenHeader(title = "Repository search", onBack = navigator::pop)
                Gap(size = Spacings.half)
                SearchField(
                    value = screenState.searchQuery,
                    onValueChange = viewModel::setSearchQuery,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        viewModel.fetchSearchResults(screenState.searchQuery)
                    })
                )
                Gap(size = Spacings.half)
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentAlignment = Alignment.TopCenter,
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(Spacings.half),
                    contentPadding = PaddingValues(
                        horizontal = Spacings.default,
                        vertical = Spacings.default
                    )
                ) {
                    stickyHeader {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = items.loadState.mediator?.refresh is LoadState.Error,
                            enter = expandVertically() { 0 } + fadeIn(),
                            exit = shrinkVertically() { 0 } + fadeOut()
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Gap(size = Spacings.small)
                                AnimatedContent(!scrolledDown, label = "refresh_error") {
                                    if (it) {
                                        ErrorItem(
                                            errorText = "Failed fetch from network!",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .animateItemPlacement(),
                                            actionItem = {
                                                Icon(
                                                    imageVector = Icons.Rounded.Refresh,
                                                    contentDescription = "refresh",
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .clickable {
                                                            items.retry()
                                                        }
                                                        .padding(Spacings.small))
                                            })
                                    } else {
                                        Icon(
                                            imageVector = Icons.Rounded.Warning,
                                            tint = MaterialTheme.colorScheme.onErrorContainer,
                                            contentDescription = "warning",
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .clickable {
                                                    scope.launch {
                                                        lazyListState.scrollToItem(0)
                                                    }
                                                }
                                                .background(MaterialTheme.colorScheme.errorContainer)
                                                .border(
                                                    BorderStroke(
                                                        1.dp,
                                                        MaterialTheme.colorScheme.error
                                                    ),
                                                    shape = CircleShape
                                                )
                                                .padding(Spacings.medium)

                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (items.itemCount == 0) {
                        item {
                            Text(
                                text = "No items available!",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    items(
                        items.itemCount,
                        key = items.itemKey { it.id }) { index ->
                        val item = items[index]
                        if (item != null) {
                            RepositorySearchItem(
                                repository = item,
                                index = index,
                                modifier = Modifier.fillMaxWidth()
                            ){
                                navigator.push(DetailScreen(item.id))
                            }
                        }
                    }
                    item {
                        AnimatedContent(
                            targetState = items.loadState.mediator?.append,
                            transitionSpec = {
                                expandVertically() { 0 } + fadeIn() togetherWith
                                        shrinkVertically() { 0 } + fadeOut()
                            }, label = "append_error", contentAlignment = Alignment.Center
                        ) { state ->
                            when (state) {
                                is LoadState.Error -> {
                                    ErrorItem(
                                        errorText = "Failed to fetch from network!",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .animateItemPlacement(),
                                        actionItem = {
                                            Icon(imageVector = Icons.Rounded.Refresh,
                                                contentDescription = "refresh",
                                                modifier = Modifier
                                                    .clip(CircleShape)
                                                    .clickable {
                                                        items.retry()
                                                    }
                                                    .padding(Spacings.small))
                                        })
                                }
                                LoadState.Loading -> {
                                    Box(modifier=Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                        CircularProgressIndicator(
                                            strokeCap = StrokeCap.Round,
                                            modifier = Modifier
                                                .padding(Spacings.small)
                                                .size(Sizes.Components.circularLoaderSize)
                                        )
                                    }
                                }
                                else -> {}
                            }

                        }
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = items.loadState.refresh is LoadState.Loading,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Sizes.Components.progressTrackHeight)
                    )
                }
            }
        }

    }
}
