package com.example.practice1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Demo: Column = sequential (non-overlapping) stacking.
 * Each child is placed one BELOW the previous one along the vertical axis.
 * Total height used = sum of all 3 children's heights.
 */
@Composable
fun ColumnStackDemo() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.size(100.dp).background(Color.Red))
        Box(modifier = Modifier.size(100.dp).background(Color.Green))
        Box(modifier = Modifier.size(100.dp).background(Color.Blue))
    }
    // Result: you see 3 separate squares, stacked top-to-bottom like a list.
    // Red on top, Green in the middle, Blue at the bottom. No overlap.
}

/**
 * Demo: Box = z-axis (overlapping) stacking.
 * Each child is placed at the SAME position (by default, top-start),
 * drawn on top of the previous one in declaration order.
 * Last child declared = drawn on top (visible above the others).
 */
@Composable
fun BoxStackDemo() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.size(100.dp).background(Color.Red))
        Box(modifier = Modifier.size(80.dp).background(Color.Green))
        Box(modifier = Modifier.size(60.dp).background(Color.Blue))
    }
    // Result: you see ONE square region where Red is the biggest (100dp) drawn first,
    // Green (80dp) drawn on top of it (covers the center of Red),
    // Blue (60dp) drawn on top of both (covers the center of Green).
    // It looks like 3 concentric squares (like a bullseye), NOT 3 separate rows.
}

@Composable
fun SideBySideDemo() {
    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(8.dp)) {
            ColumnStackDemo()
        }
        Column(modifier = Modifier.padding(8.dp)) {
            BoxStackDemo()
        }
    }
}

@Preview(showBackground = true, widthDp = 300, heightDp = 400)
@Composable
fun ColumnStackDemoPreview() {
    ColumnStackDemo()
}

@Preview(showBackground = true, widthDp = 300, heightDp = 400)
@Composable
fun BoxStackDemoPreview() {
    BoxStackDemo()
}


