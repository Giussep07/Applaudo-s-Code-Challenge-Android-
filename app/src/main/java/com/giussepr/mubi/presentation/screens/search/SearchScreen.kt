/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.giussepr.mubi.R
import com.giussepr.mubi.presentation.theme.*
import com.giussepr.mubi.presentation.widgets.TvShowError
import com.giussepr.mubi.presentation.widgets.TvShowListItem
import com.giussepr.mubi.presentation.widgets.TvShowLoading

@Composable
@Preview
fun SearchScreenPreview() {
  SearchScreen(rememberNavController())
}

@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel = hiltViewModel()) {
  Scaffold(modifier = Modifier
    .fillMaxSize()
    .background(Background),
    topBar = {
      SearchTopAppBar(
        navController = navController,
        onSearchButtonClicked = { searchTerm ->
          if (searchTerm.isNotEmpty())
            viewModel.searchTvShowsByTerm(searchTerm)
        })
    }) { paddingValues ->
    viewModel.tvShowList.collectAsState().value?.collectAsLazyPagingItems()?.let { tvShowList ->

      if (tvShowList.itemCount == 0) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          Text(
            text = stringResource(id = R.string.no_tv_shows_results),
            style = MaterialTheme.typography.h6,
            color = ColorText
          )
        }
      } else {
        LazyVerticalGrid(
          columns = GridCells.Fixed(2),
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        ) {
          items(tvShowList.itemCount) { index ->
            tvShowList[index]?.let {
              TvShowListItem(tvShow = it, onTvShowItemClicked = { /*TODO*/ })
            }
          }

          // First pagination load
          when (val state = tvShowList.loadState.refresh) {
            is LoadState.Loading -> {
              item(span = { GridItemSpan(2) }) {
                TvShowLoading()
              }
            }
            is LoadState.Error -> {
              item(span = { GridItemSpan(2) }) {
                TvShowError(error = state.error, tvShowList)
              }
            }
            else -> {}
          }

          tvShowList.loadState.refresh

          // Pagination
          when (val state = tvShowList.loadState.append) {
            is LoadState.Loading -> {
              item(span = { GridItemSpan(2) }) {
                TvShowLoading()
              }
            }
            is LoadState.Error -> {
              item(span = { GridItemSpan(2) }) {
                TvShowError(error = state.error, tvShowList)
              }
            }
            else -> {}
          }
        }
      }
    }

  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTopAppBar(navController: NavHostController, onSearchButtonClicked: (String) -> Unit) {
  var text by remember { mutableStateOf("") }
  val keyboardController = LocalSoftwareKeyboardController.current

  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .height(56.dp),
    color = MaterialTheme.colors.primary,
    elevation = AppBarDefaults.TopAppBarElevation
  ) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
      IconButton(onClick = { navController.navigateUp() }, modifier = Modifier.size(56.dp)) {
        Icon(
          modifier = Modifier.size(24.dp),
          imageVector = Icons.Filled.ArrowBack,
          contentDescription = stringResource(R.string.navigate_back_description),
          tint = White
        )
      }
      Spacer(modifier = Modifier.width(8.dp))
      BasicTextField(
        value = text,
        onValueChange = { text = it },
        textStyle = MaterialTheme.typography.body2.copy(color = ColorText),
        cursorBrush = SolidColor(ColorText),
        singleLine = true,
        decorationBox = { innerTextField ->
          Box(
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp)
              .background(color = White, shape = MaterialTheme.shapes.small)
              .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
          ) {
            if (text.isEmpty()) {
              Text(
                text = stringResource(R.string.search),
                style = MaterialTheme.typography.body2,
                color = HintTextColor
              )
            }
            innerTextField()
          }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
          keyboardController?.hide()
          onSearchButtonClicked(text)
        })
      )
    }
  }
}
