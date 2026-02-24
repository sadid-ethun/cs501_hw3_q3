package com.example.cs501_hw3_q3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.cs501_hw3_q3.ui.theme.Cs501_hw3_q3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { Cs501_hw3_q3Theme { TagBrowserApp() } }
    }
}

@Composable
fun TagBrowserApp() {
    Scaffold(modifier = Modifier.fillMaxSize()) { inner ->
        TagBrowserScreen(modifier = Modifier.padding(inner))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagBrowserScreen(modifier: Modifier = Modifier) {
    val allTags = remember {
        listOf(
            "Python", "Java", "C++", "C", "JavaScript", "HTML",
            "CSS", "Go", "C#", "SQL", "R",
            "PHP", "Rust", "Kotlin", "MATLAB"
        )
    }

    val selected = rememberSaveable { mutableStateListOf<String>() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Tag Browser", style = MaterialTheme.typography.headlineSmall)

        WhiteShadowCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Selected Tags", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.weight(1f))
                }

                FlowColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    maxItemsInEachColumn = 4
                ) {
                    if (selected.isEmpty()) {
                        AssistChip(onClick = {}, label = { Text("None yet") })
                    } else {
                        selected.forEach { tag ->
                            AssistChip(
                                onClick = { selected.remove(tag) },
                                label = { Text(tag) }
                            )
                        }
                    }
                }
            }
        }

        WhiteShadowCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Browse Tags", style = MaterialTheme.typography.titleMedium)

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    allTags.forEach { tag ->
                        val isSelected = tag in selected

                        FilterChip(
                            selected = isSelected,
                            enabled = true,
                            onClick = {
                                if (isSelected) selected.remove(tag) else selected.add(tag)
                            },
                            label = { Text(tag) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WhiteShadowCard(content: @Composable () -> Unit) {
    val shape = RoundedCornerShape(18.dp)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
            .shadow(10.dp, shape, clip = false)
            .clip(shape),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        content()
    }
}