package com.correxapp.presentation.ui.screens

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.correxapp.presentation.ui.components.CameraPreview
import com.correxapp.presentation.ui.viewmodel.ScanViewModel
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    navController: NavController,
    viewModel: ScanViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Escanear Cartão-Resposta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when {
                cameraPermissionState.status.isGranted -> {
                    // Mostrar resultado se houver
                    uiState.result?.let { result ->
                        ResultDisplay(
                            result = result,
                            onScanAgain = { viewModel.resetScan() },
                            onDismiss = { navController.popBackStack() }
                        )
                    } ?: run {
                        // Mostrar câmera
                        CameraPreview(
                            modifier = Modifier.fillMaxSize(),
                            onImageAnalyzed = { bitmap ->
                                if (uiState.result == null) {
                                    viewModel.onImageCaptured(bitmap)
                                }
                            }
                        )

                        // Overlay com instruções
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.3f))
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = uiState.instruction,
                                color = Color.White,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )

                            if (uiState.isProcessing) {
                                CircularProgressIndicator(color = Color.White)
                            }

                            uiState.error?.let { error ->
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer
                                    )
                                ) {
                                    Text(
                                        text = error,
                                        modifier = Modifier.padding(16.dp),
                                        color = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                }
                            }
                        }
                    }
                }
                cameraPermissionState.status.shouldShowRationale -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Permissão de câmera necessária para escanear")
                        Button(
                            onClick = { cameraPermissionState.launchPermissionRequest() }
                        ) {
                            Text("Conceder Permissão")
                        }
                    }
                }
                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Permissão de câmera negada")
                    }
                }
            }
        }
    }
}

@Composable
fun ResultDisplay(
    result: com.correxapp.domain.model.StudentResponse,
    onScanAgain: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Correção Concluída!",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Aluno: ${result.studentName}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Nota: ${"%.1f".format(result.score)}%",
                    style = MaterialTheme.typography.titleLarge,
                    color = if (result.score >= 60) MaterialTheme.colorScheme.primary 
                           else MaterialTheme.colorScheme.error
                )

                Text(
                    text = "Acertos: ${result.hits}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedButton(onClick = onScanAgain) {
                        Text("Escanear Novamente")
                    }
                    Button(onClick = onDismiss) {
                        Text("Concluir")
                    }
                }
            }
        }
    }
}
