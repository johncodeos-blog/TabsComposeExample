package com.example.tabscomposeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    @ExperimentalPagerApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    val tabs = listOf(TabItem.Music, TabItem.Movies, TabItem.Books)
    val pagerState = rememberPagerState(pageCount = tabs.size)
    Scaffold(
        topBar = { TopBar() },
    ) {
        Column {
            Tabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
        backgroundColor = colorResource(id = R.color.colorPrimary),
        contentColor = Color.White
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    // OR ScrollableTabRow()
    TabRow(
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        // Override the indicator, using the provided pagerTabIndicatorOffset modifier
        backgroundColor = colorResource(id = R.color.colorPrimaryDark),
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }) {
        // Add tabs for all of our pages
        tabs.forEachIndexed { index, tab ->
            // OR Tab()
            LeadingIconTab(
                icon = { Icon(painter = painterResource(id = tab.icon), contentDescription = "") },
                text = { Text(tab.title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun TabsPreview() {
    val tabs = listOf(
        TabItem.Music,
        TabItem.Movies,
        TabItem.Books
    )
    val pagerState = rememberPagerState(pageCount = tabs.size)
    Tabs(tabs = tabs, pagerState = pagerState)
}

@ExperimentalPagerApi
@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState) { page ->
        tabs[page].screen()
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun TabsContentPreview() {
    val tabs = listOf(
        TabItem.Music,
        TabItem.Movies,
        TabItem.Books
    )
    val pagerState = rememberPagerState(pageCount = tabs.size)
    TabsContent(tabs = tabs, pagerState = pagerState)
}