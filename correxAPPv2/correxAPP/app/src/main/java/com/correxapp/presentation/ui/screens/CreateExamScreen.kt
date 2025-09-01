package com.correxapp.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.correxapp.R
import com.correxapp.presentation.ui.viewmodel.CreateExamViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExamScreen(
    navController: NavController,
    viewModel: CreateExamViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiState.collectLatest { state ->
            if (state.examCreatedSuccessfully) {
                navController.popBackStack()
            }
            state.error?.let {
                snackbarHostState.showSnackbar(it)
                viewModel.dismissError()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.create_exam)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = { viewModel.saveExam() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled =!uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text(stringResource(R.string.save_exam))
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Usa o padding do Scaffold
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            // Adiciona padding extra no topo e no fundo para o conteúdo não ser coberto
            contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
        ) {
            item {
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = viewModel::onNameChange,
                    label = { Text(stringResource(R.string.exam_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            item {
                OutlinedTextField(
                    value = uiState.subject,
                    onValueChange = viewModel::onSubjectChange,
                    label = { Text(stringResource(R.string.subject)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.totalQuestions,
                        onValueChange = viewModel::onTotalQuestionsChange,
                        label = { Text(stringResource(R.string.number_of_questions)) },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = uiState.alternativesPerQuestion,
                        onValueChange = viewModel::onAlternativesChange,
                        label = { Text(stringResource(R.string.alternatives)) },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
            item {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = stringResource(R.string.answer_key),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            item {
                AnswerKeyEditor(
                    answerKey = uiState.answerKey,
                    alternatives = uiState.alternativesPerQuestion.toIntOrNull()?: 0,
                    onAnswerChange = viewModel::onAnswerChange
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerKeyEditor(
    answerKey: Map<Int, Int>,
    alternatives: Int,
    onAnswerChange: (Int, Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val sortedQuestions = answerKey.keys.sorted()
        for (questionNumber in sortedQuestions) {
            val selectedAlternative = answerKey[questionNumber]?: -1
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$questionNumber.",
                    modifier = Modifier.width(40.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (altIndex in 0 until alternatives) {
                        val isSelected = selectedAlternative == altIndex
                        FilterChip(
                            selected = isSelected,
                            onClick = { onAnswerChange(questionNumber, altIndex) },
                            label = { Text(text = ('A' + altIndex).toString()) }
                        )
                    }
                }
            }
        }
    }
}