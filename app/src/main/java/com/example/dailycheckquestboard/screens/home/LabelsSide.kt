package com.example.dailycheckquestboard.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabelsSide(
    spacing: Dp,
    paddingCol: Dp,
    height: Dp,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(paddingCol, vertical = 0.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(
            modifier = Modifier
                .height(height)
                .padding(0.dp)
        )

        Spacer(
            modifier = Modifier
                .height(spacing / 2)
                .padding(0.dp)
        )

        Text(
            text = "Work",
            color = Color.Blue,
            fontSize = 14.sp,
            modifier = Modifier
                .height(height)
                .padding(0.dp)
        )

        Spacer(modifier = Modifier.height(spacing))

        Text(
            text = "Physical",
            color = Color.Green,
            fontSize = 11.sp,
            modifier = Modifier
                .height(height)
                .padding(0.dp)
        )

        Spacer(
            modifier = Modifier
                .height(spacing)
                .padding(0.dp)
        )

        Text(
            text = "Social",
            color = Color.Red,
            fontSize = 13.sp,
            modifier = Modifier
                .height(height)
                .padding(0.dp)
        )
    }
}

@Preview
@Composable
fun PreviewLabelSide() {
    LabelsSide(spacing = 4.dp, paddingCol = 0.dp, height = 1.dp, Modifier)
}