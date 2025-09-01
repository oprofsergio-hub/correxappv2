package com.correxapp.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.correxapp.domain.model.Exam
import com.correxapp.presentation.navigation.Screen
import com.correxapp.presentation.ui.viewmodel.DashboardViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val stats by viewModel.dashboardStats.collectAsState()
    val exams by viewModel.allExams.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Correx - OMR Scanner") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Settings, contentDescription = "Configurações")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.CreateExam.route) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Criar Prova")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
        ) {
            item {
                StatsGrid(stats.totalExams, stats.totalCorrectedSheets, stats.averageScore)
            }

            item {
                Text(
                    text = "Minhas Provas",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            if (exams.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Nenhuma prova criada ainda")
                    }
                }
            } else {
                items(exams) { exam ->
                    ExamCard(
                        exam = exam,
                        onClick = { navController.navigate(Screen.ExamDetails.createRoute(exam.id)) }
                    )
                }
            }
        }
    }
}

@Composable
fun ExamCard(exam: Exam, onClick: () -> Unit) {
    val dateString = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(exam.creationDate))

    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = exam.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = exam.subject, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${exam.totalQuestions} questões",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(text = dateString, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun StatsGrid(totalExams: Int, totalSheets: Int, averageScore: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatCard(
            label = "Total de Provas",
            value = totalExams.toString(),
            modifier = Modifier.weight(1f)
        )
        StatCard(
            label = "Folhas Corrigidas",
            value = totalSheets.toString(),
            modifier = Modifier.weight(1f)
        )
        StatCard(
            label = "Nota Média",
            value = "%.1f".format(averageScore),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = label, style = MaterialTheme.typography.bodySmall)
        }
    }
}
