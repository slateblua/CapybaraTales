package com.bluesourceplus.capybaratales

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.bluesourceplus.capybaratales.feature.preferences.PreferencesScreenRoute
import com.bluesourceplus.capybaratales.feature.aboutmoodentry.AboutMoodRoute
import com.bluesourceplus.capybaratales.feature.create.CreateGoalMode
import com.bluesourceplus.capybaratales.feature.create.CreateScreenRoute
import com.bluesourceplus.capybaratales.feature.home.HomeScreenRoute

@Composable
fun BlueDaysScreensHost(
    navController: NavHostController,
    padding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Home.route,
        modifier =
        Modifier
            .padding(padding)
            .fillMaxSize(),
    ) {
        appScreen(Destination.Home) {
            HomeScreenRoute(onAddButton = {
                navController.navigate(CREATE_SCREEN_ROUTE)
            }, onMoodCardPressed = {
                navController.navigate("$ABOUT_GOAL_SCREEN_ROUTE/$it") { launchSingleTop = true }
            })
        }

        appScreen(Destination.AboutGoal) { backStackEntry ->
            backStackEntry.arguments?.getInt(GOAL_ID_ARG)?.let { goalId ->
                AboutMoodRoute(
                    goalId = goalId,
                    back = navController::popBackStack,
                    onEditPressed = {
                        navController.navigate("$CREATE_SCREEN_ROUTE?$GOAL_ID_ARG=$goalId")
                    }
                )
            }
        }

        appScreen(Destination.Preferences) {
            PreferencesScreenRoute(back = navController::popBackStack)
        }

        appScreen(Destination.Create) { backStackEntry ->
            val goalId = backStackEntry.arguments?.getString(GOAL_ID_ARG)
            val mode = if (goalId != null) {
                CreateGoalMode.Edit(Integer.parseInt(goalId))
            } else {
                CreateGoalMode.Create
            }
            CreateScreenRoute(mode = mode, back = navController::popBackStack)
        }
    }
}

@Composable
internal fun BottomBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        AppNavigationItem(
            rootScreen = Destination.Home,
            currentDestination = currentDestination,
            navController = navController,
            usualIcon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = "Home") },
            selectedIcon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home Selected") },
            title = "Home",
        )
        AppNavigationItem(
            rootScreen = Destination.Preferences,
            currentDestination = currentDestination,
            navController = navController,
            usualIcon = { Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Preferences") },
            selectedIcon = { Icon(imageVector = Icons.Filled.Settings, contentDescription = "Preferences Selected") },
            title = "Preferences",
        )
    }
}

@Composable
private fun RowScope.AppNavigationItem(
    rootScreen: Screen,
    currentDestination: NavDestination?,
    navController: NavController,
    usualIcon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = usualIcon,
    title: String,
) {
    val selected = currentDestination?.hierarchy?.any { it.route == rootScreen.route } == true
    NavigationBarItem(
        selected = selected,
        onClick = {
            navController.navigate(rootScreen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = if (selected) selectedIcon else usualIcon,
        alwaysShowLabel = false,
        label = {
            AnimatedVisibility(
                visible = selected,
                enter = expandVertically(
                    expandFrom = Alignment.Bottom,
                    animationSpec = spring()
                ),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top,
                    animationSpec = spring()
                )
            ) {
                Text(text = title, fontSize = 12.sp)
            }
        },
    )
}

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val hasBottomBar: Boolean = false,
)

fun NavGraphBuilder.appScreen(
    screen: Screen,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = screen.route,
        arguments = screen.arguments,
        content = content,
    )
}

const val CREATE_SCREEN_ROUTE = "create"
const val HOME_SCREEN_ROUTE = "home"
const val PREFERENCES_SCREEN_ROUTE = "preferences"

const val ABOUT_GOAL_SCREEN_ROUTE = "about_goal"
const val GOAL_ID_ARG = "goal_id"

object ShowNavBarScreens {
    val screens = listOf(
        Destination.Home,
        Destination.Preferences,
    )
}

object Destination {

    data object Create : Screen(
        route = "$CREATE_SCREEN_ROUTE?$GOAL_ID_ARG={$GOAL_ID_ARG}",
        arguments =
        listOf(
            navArgument(GOAL_ID_ARG) {
                type = NavType.StringType
                nullable = true
            },
        ),
    )

    data object Home : Screen(
        route = HOME_SCREEN_ROUTE,
        hasBottomBar = true,
    )

    data object Preferences : Screen(
        route = PREFERENCES_SCREEN_ROUTE,
        hasBottomBar = true,
    )

    data object AboutGoal : Screen(
        route = "$ABOUT_GOAL_SCREEN_ROUTE/{$GOAL_ID_ARG}",
        arguments =
        listOf(
            navArgument(GOAL_ID_ARG) {
                type = NavType.IntType
                nullable = false
            },
        ),
    )
}