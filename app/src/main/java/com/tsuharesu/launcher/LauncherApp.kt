package com.tsuharesu.launcher

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.tsuharesu.launcher.ui.theme.LauncherCTheme

@Composable
fun LauncherApp() {
    val context = LocalContext.current
    val packageManager = context.packageManager
    val allApps = getAllActivities(packageManager)

    LauncherCTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Top
                    )
                ),
            color = Color.Transparent
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(allApps) { app ->
                    AppInfoRow(app) { pkg ->
                        packageManager.getLaunchIntentForPackage(pkg)
                            ?.let { startActivity(context, it, null) }
                    }
                }
            }
        }
    }
}

//TODO: Use LauncherApps to get all apps + work profile
// https://developer.android.com/reference/kotlin/android/content/pm/LauncherApps
private fun getAllActivities(packageManager: PackageManager): List<AppInfo> {
    val i = Intent(Intent.ACTION_MAIN, null).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
    }
    val allApps: List<ResolveInfo> = packageManager.queryIntentActivities(i, 0)


    return allApps.map { ri ->
        AppInfo(
            ri.loadLabel(packageManager),
            ri.activityInfo.packageName,
            ri.activityInfo.loadIcon(packageManager)
        )
    }.sortedBy { it.label.toString() }
}