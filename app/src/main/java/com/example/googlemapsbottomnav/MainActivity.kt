package com.example.googlemapsbottomnav

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import com.example.googlemapsbottomnav.ui.theme.GoogleMapsBottomNavTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoogleMapsBottomNavTheme {
                GoogleMapsOnList()
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GoogleMapsOnList() {
    // code for scrolling
    val scrollState = rememberScrollState()
    var enableScroll by remember { mutableStateOf(true) }

    // code for map state
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    // enable list scroll when map camera has stopped moving
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            enableScroll = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = scrollState,
                enabled = enableScroll
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .background(Color.Red)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = singapore),
                    title = "Singapore",
                    snippet = "Marker in Singapore"
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInteropFilter(
                        onTouchEvent = {
                            when (it.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    enableScroll = false
                                    false
                                }
                                MotionEvent.ACTION_UP -> {
                                    enableScroll = true
                                    true
                                }
                                MotionEvent.ACTION_MOVE -> {
                                    enableScroll = false
                                    false
                                }
                                else -> {
                                    enableScroll = true
                                    true
                                }
                            }
                        }
                    )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.Green)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.Blue)
        )
    }
}
