package be.christiano.demopokedex.ui.pokedexList.filter

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import be.christiano.demopokedex.R
import be.christiano.demopokedex.ui.MainNavGraph
import be.christiano.demopokedex.ui.shared.Type
import be.christiano.demopokedex.ui.shared.TypeCard
import be.christiano.demopokedex.ui.theme.DemoPokedexTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Destination(
    style = DestinationStyleBottomSheet::class
)
@MainNavGraph
@Composable
fun BottomSheetFilterScreen(
    navController: NavController,
    viewModel: BottomSheetFilterViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is BottomSheetFilterUiEvent.DummyUiEvent -> Unit
                BottomSheetFilterUiEvent.CloseBottomSheet -> navController.popBackStack()
            }
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        contentColor = contentColorFor(MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        BottomSheetFilterScreenContent(state = state, onEvent = viewModel::onEvent)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BottomSheetFilterScreenContent(
    state: BottomSheetFilterState,
    onEvent: (BottomSheetFilterEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp)
        ) {
            // Create references for the composables to constrain
            val (closeButton, content) = createRefs()

            FilledIconButton(
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(closeButton) {
                        top.linkTo(parent.top, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
                    .zIndex(10f),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                onClick = {
                    onEvent(BottomSheetFilterEvent.CloseBottomSheet)
                }
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "close bottomsheet"
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(content) {
                        top.linkTo(parent.top, margin = 16.dp)
                    }
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp, start = 16.dp, end = 42.dp),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    text = "Sort by"
                )

                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    SortOption.entries.forEachIndexed { index, sortOption ->
                        SegmentedButton(
                            selected = sortOption == state.sortOption,
                            onClick = {
                                onEvent(BottomSheetFilterEvent.UpdateOrderSelection(sortOption))
                            },
                            shape = SegmentedButtonDefaults.itemShape(index = index, count = SortOption.entries.size),
                            icon = {}
                        ) {
                            Icon(imageVector = ImageVector.vectorResource(sortOption.icon), contentDescription = sortOption.name)
                        }
                    }
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 16.dp, end = 16.dp),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    text = "Types"
                )

                FlowRow(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Type.entries.forEach {
                        TypeCard(type = it)
                    }
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 16.dp, end = 16.dp),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    text = "Height"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .defaultMinSize(100.dp, 34.dp)
                                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
                                .padding(8.dp),
                            value = state.heightRange.start.toString(),
                            onValueChange = {
                                onEvent(BottomSheetFilterEvent.UpdateHeight(it.toFloatOrNull() ?: 0f, state.heightRange.endInclusive))
                            },

                            decorationBox = { _ ->
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(
                                        text = state.heightRange.start.toInt().toString()
                                    )

                                    Text(text = "cm")
                                }
                            }
                        )

                        BasicTextField(
                            modifier = Modifier
                                .defaultMinSize(100.dp, 34.dp)
                                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
                                .padding(8.dp),
                            value = state.heightRange.endInclusive.toString(),
                            onValueChange = {
                                onEvent(BottomSheetFilterEvent.UpdateHeight(state.heightRange.start, it.toFloatOrNull() ?: 0f))
                            },

                            decorationBox = { _ ->
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(
                                        text = state.heightRange.endInclusive.toInt().toString()
                                    )

                                    Text(text = "cm")
                                }
                            }
                        )
                    }

                    RangeSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = state.heightRange,
                        valueRange = 0f..1_000f,
                        onValueChange = {
                            onEvent(BottomSheetFilterEvent.UpdateHeight(it.start, it.endInclusive))
                        }
                    )
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 16.dp, end = 16.dp),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    text = "Weight"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .defaultMinSize(100.dp, 34.dp)
                                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
                                .padding(8.dp),
                            value = state.weightRange.start.toString(),
                            onValueChange = {
                                onEvent(BottomSheetFilterEvent.UpdateWeight(it.toFloatOrNull() ?: 0f, state.weightRange.endInclusive))
                            },

                            decorationBox = { _ ->
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(
                                        text = state.weightRange.start.toInt().toString()
                                    )

                                    Text(text = "kg")
                                }
                            }
                        )

                        BasicTextField(
                            modifier = Modifier
                                .defaultMinSize(100.dp, 34.dp)
                                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
                                .padding(8.dp),
                            value = state.weightRange.endInclusive.toString(),
                            onValueChange = {
                                onEvent(BottomSheetFilterEvent.UpdateWeight(state.weightRange.start, it.toFloatOrNull() ?: 0f))
                            },

                            decorationBox = { _ ->
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(
                                        text = state.weightRange.endInclusive.toInt().toString()
                                    )

                                    Text(text = "kg")
                                }
                            }
                        )
                    }

                    RangeSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = state.weightRange,
                        valueRange = 0f..10_000f,
                        onValueChange = {
                            onEvent(BottomSheetFilterEvent.UpdateWeight(it.start, it.endInclusive))
                        }
                    )
                }
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            onClick = {
                onEvent(BottomSheetFilterEvent.DummyEvent)
            }) {
            Text(text = "Toepassen")
        }
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BottomSheetFilterScreenPreview() {
    DemoPokedexTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            contentColor = contentColorFor(MaterialTheme.colorScheme.surfaceContainerLow)
        ) {
            BottomSheetFilterScreenContent(state = BottomSheetFilterState()) {}
        }
    }
}

class BottomSheetFilterViewModel : ViewModel() {

    private val _state = MutableStateFlow(BottomSheetFilterState())
    val state = _state.asStateFlow()

    private val _eventFlow = Channel<BottomSheetFilterUiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    fun onEvent(event: BottomSheetFilterEvent) = viewModelScope.launch {
        when (event) {
            is BottomSheetFilterEvent.DummyEvent -> _eventFlow.send(BottomSheetFilterUiEvent.DummyUiEvent)
            BottomSheetFilterEvent.CloseBottomSheet -> _eventFlow.send(BottomSheetFilterUiEvent.CloseBottomSheet)
            is BottomSheetFilterEvent.UpdateOrderSelection -> _state.update { it.copy(sortOption = event.option) }
            is BottomSheetFilterEvent.UpdateHeight -> checkNewHeightAndUpdateIfValid(event.start, event.end)
            is BottomSheetFilterEvent.UpdateWeight -> checkNewWeightAndUpdateIfValid(event.start, event.end)
        }
    }

    private fun checkNewHeightAndUpdateIfValid(start: Float, end: Float) {
        if (start > end || start < 0) return
        if (end < start || end > 1_000) return

        _state.update { it.copy(heightRange = start..end) }
    }

    private fun checkNewWeightAndUpdateIfValid(start: Float, end: Float) {
        if (start > end || start < 0) return
        if (end < start || end > 10_000) return

        _state.update { it.copy(weightRange = start..end) }
    }
}

sealed class BottomSheetFilterEvent {
    data object CloseBottomSheet : BottomSheetFilterEvent()
    data class UpdateOrderSelection(val option: SortOption) : BottomSheetFilterEvent()
    data class UpdateHeight(val start: Float, val end: Float) : BottomSheetFilterEvent()
    data class UpdateWeight(val start: Float, val end: Float) : BottomSheetFilterEvent()
    data object DummyEvent : BottomSheetFilterEvent()
}

data class BottomSheetFilterState(
    val isLoading: Boolean = false,
    val sortOption: SortOption = SortOption.AlphabeticallyUp,
    val heightRange: ClosedFloatingPointRange<Float> = 0f..1_000f,
    val weightRange: ClosedFloatingPointRange<Float> = 0f..10_000f
)

enum class SortOption(@DrawableRes val icon: Int) {
    AlphabeticallyUp(R.drawable.ic_alphabetically_up),
    AlphabeticallyDown(R.drawable.ic_alphabetically_down),
    NumericalUp(R.drawable.ic_numerical_up),
    NumericalDown(R.drawable.ic_numerical_down)
}

sealed class BottomSheetFilterUiEvent {
    data object DummyUiEvent : BottomSheetFilterUiEvent()
    data object CloseBottomSheet : BottomSheetFilterUiEvent()
}
