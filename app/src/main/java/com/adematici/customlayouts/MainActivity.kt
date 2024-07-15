package com.adematici.customlayouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.unit.dp
import com.adematici.customlayouts.ui.theme.CustomLayoutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomLayoutsTheme {
                MyScreen()
            }
        }
    }
}

@Composable
fun MyScreen() {
    CustomGridLayout(modifier = Modifier.statusBarsPadding(), columns = 2) {
        Text("Item 1", Modifier.padding(8.dp))
        Text("Item 2", Modifier.padding(8.dp))
        Text("Item 3", Modifier.padding(8.dp))
        Text("Item 4", Modifier.padding(8.dp))
        Text("Item 5", Modifier.padding(8.dp))
    }
}

@Composable
private fun CustomGridLayout(
    modifier: Modifier = Modifier,
    columns: Int,
    content: @Composable () -> Unit,
) {
    val measurePolicy = MeasurePolicy { measurables, constraints ->
        val columnWidth = constraints.maxWidth / columns
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints.copy(maxWidth = columnWidth))
        }
        val height = placeables.maxOfOrNull { it.height } ?: 0

        layout(width = constraints.maxWidth, height = height * (measurables.size / columns + 1)) {
            var xPosition = 0
            var yPosition = 0
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(x = xPosition, y = yPosition)
                xPosition += columnWidth
                if ((index + 1) % columns == 0) {
                    xPosition = 0
                    yPosition += height
                }
            }
        }
    }

    Layout(
        content = content,
        modifier = modifier,
        measurePolicy = measurePolicy,
    )
}
