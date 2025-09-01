package com.correxapp.presentation.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object CreateExam : Screen("create_exam")
    object ExamDetails : Screen("exam_details/{examId}") {
        fun createRoute(examId: Long) = "exam_details/$examId"
    }
    object ScanSheet : Screen("scan_sheet/{examId}") {
        fun createRoute(examId: Long) = "scan_sheet/$examId"
    }
    // Novas rotas
    object Classes : Screen("classes")
    object ClassDetails : Screen("class_details/{classId}") {
        fun createRoute(classId: Long) = "class_details/$classId"
    }
}
