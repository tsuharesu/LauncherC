package com.tsuharesu.launcher

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.drawablepainter.rememberDrawablePainter

data class AppInfo(
    var label: CharSequence,
    var packageName: CharSequence,
    var icon: Drawable
)

@Composable
fun AppInfoRow(appInfo: AppInfo, onClick: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
        onClick(appInfo.packageName.toString())
    }) {
        Image(
            rememberDrawablePainter(drawable = appInfo.icon),
            contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .size(48.dp)
        )
        Text(
            text = appInfo.label.toString(),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Clip,
            style = MaterialTheme.typography.titleSmall.copy(shadow = Shadow(blurRadius = 2f))
        )
    }
}

@Preview
@Composable
fun AppInfoRow() {
    ContextCompat.getDrawable(LocalContext.current, R.drawable.ic_launcher_background)?.let {
        AppInfoRow(appInfo = AppInfo("Chrome", "", it), onClick = {})
    }
}

