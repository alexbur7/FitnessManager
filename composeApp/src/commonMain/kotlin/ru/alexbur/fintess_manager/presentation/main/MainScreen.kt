package ru.alexbur.fintess_manager.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.zIndex
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.alexbur.fintess_manager.feature.calendar.presentation.CalendarRoute
import ru.alexbur.fintess_manager.feature.clients.presentation.ClientsRoute
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.presentation.factory.CalendarComposeScreenFactory
import ru.alexbur.fintess_manager.presentation.factory.ClientsComposeScreenFactory
import ru.alexbur.fintess_manager.presentation.rememberNavigator
import ru.alexbur.fintess_manager.uikit.AppColors
import kotlin.reflect.KClass

@Composable
internal fun MainScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    var currentTab by remember { mutableStateOf(BottomNavTab.Schedule) }

    Scaffold(
        modifier = modifier,
        backgroundColor = AppColors.BgScreen,
        bottomBar = {
            MainBottomNavBar(
                currentTab = currentTab,
                onTabSelected = { currentTab = it },
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            TabContent(
                isVisible = currentTab == BottomNavTab.Schedule,
                startDestination = CalendarRoute::class
            ) {
                CalendarComposeScreenFactory(this).create(it)
            }
            TabContent(
                isVisible = currentTab == BottomNavTab.Clients,
                startDestination = ClientsRoute::class,
            ) {
                ClientsComposeScreenFactory(this).create(it)
            }
            /*TabContent(isVisible = currentTab == BottomNavTab.Workouts) {

            }
            TabContent(isVisible = currentTab == BottomNavTab.Profile) {

            }*/
        }
    }
}

@Composable
private fun TabContent(
    isVisible: Boolean,
    startDestination: KClass<*>,
    modifier: Modifier = Modifier,
    builder: NavGraphBuilder.(Navigator) -> Unit
) {
    val navController = rememberNavController()
    Box(
        modifier = modifier
            .fillMaxSize()
            .zIndex(if (isVisible) 1f else 0f)
            .alpha(if (isVisible) 1f else 0f)
            .then(
                if (!isVisible) {
                    Modifier.pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitPointerEvent(PointerEventPass.Initial)
                                    .changes
                                    .forEach { it.consume() }
                            }
                        }
                    }
                } else {
                    Modifier
                }
            ),
    ) {
        val navigator = rememberNavigator(navController)
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            builder(navigator)
        }
    }
}

@Composable
private fun MainBottomNavBar(
    currentTab: BottomNavTab,
    onTabSelected: (BottomNavTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(AppColors.BgNavBar)
            .drawBehind {
                drawLine(
                    color = AppColors.BgCardBorder,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx(),
                )
            },
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            BottomNavTab.entries.forEach { tab ->
                TabItem(
                    tab = tab,
                    isSelected = tab == currentTab,
                    onClick = { onTabSelected(tab) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun TabItem(
    tab: BottomNavTab,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable(onClick = onClick)
            .semantics(mergeDescendants = true) {
                role = Role.Tab
                selected = isSelected
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) AppColors.PurpleTabActive else AppColors.BgDayCell)
                .semantics { contentDescription = tab.label },
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = tab.label,
            color = if (isSelected) AppColors.Purple else AppColors.TextMuted,
            fontSize = 9.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        )
    }
}