package com.example.testapp.presentation.drag

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun <T>ReorderableList(
    items: List<T>,
    onMove: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    itemComposable: @Composable (T)->Unit
) {
    val scope = rememberCoroutineScope()

    var overscrollJob by remember { mutableStateOf<Job?>(null) }

    val reorderableListState = rememberReorderableListState(onMove = onMove)

    LazyColumn(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, offset ->
                        change.consume()
                        reorderableListState.onDrag(offset)

                        if (overscrollJob?.isActive == true)
                            return@detectDragGesturesAfterLongPress

                        reorderableListState.checkForOverScroll()
                            .takeIf { it != 0f }
                            ?.let { overscrollJob = scope.launch { reorderableListState.lazyListState.scrollBy(it) } }
                            ?: run { overscrollJob?.cancel() }
                    },
                    onDragStart = { offset -> reorderableListState.onDragStart(offset) },
                    onDragEnd = { reorderableListState.onDragInterrupted() },
                    onDragCancel = { reorderableListState.onDragInterrupted() }
                )
            },
        state = reorderableListState.lazyListState
    ) {
        itemsIndexed(items) { index, item ->
            Column(
                modifier = Modifier
                    .composed {
                        val offsetOrNull =
                            reorderableListState.elementDisplacement.takeIf {
                                index == reorderableListState.currentIndexOfDraggedItem
                            }

                        Modifier
                            .graphicsLayer {
                                translationY = offsetOrNull ?: 0f
                            }
                    }
                    .background(Color.White, shape = RoundedCornerShape(4.dp))
                    .fillMaxWidth()
            ) { itemComposable(item) }
        }
    }
}