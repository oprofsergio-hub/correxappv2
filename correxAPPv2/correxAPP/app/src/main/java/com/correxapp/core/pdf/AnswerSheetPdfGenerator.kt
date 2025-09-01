package com.correxapp.core.pdf

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import com.correxapp.core.utils.QrCodeGenerator
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class AnswerSheetPdfGenerator @Inject constructor(
    @ApplicationContext private val context: Context,
    private val qrCodeGenerator: QrCodeGenerator
) {

    fun generatePdf(
        examId: String,
        examName: String,
        studentNameField: Boolean,
        totalQuestions: Int,
        alternatives: Int
    ): Uri {
        val pageHeight = 1120
        val pageWidth = 792
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val titlePaint = Paint().apply {
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textSize = 20f
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
        }
        val textPaint = Paint().apply {
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            textSize = 14f
            color = Color.BLACK
        }
        val strokePaint = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.BLACK
            strokeWidth = 2f
        }

        val markerSize = 20f
        val blackPaint = Paint().apply { color = Color.BLACK }
        canvas.drawRect(0f, 0f, markerSize, markerSize, blackPaint)
        canvas.drawRect(pageWidth - markerSize, 0f, pageWidth.toFloat(), markerSize, blackPaint)
        canvas.drawRect(0f, pageHeight - markerSize, markerSize, pageHeight.toFloat(), blackPaint)
        canvas.drawRect(pageWidth - markerSize, pageHeight - markerSize, pageWidth.toFloat(), pageHeight.toFloat(), blackPaint)

        canvas.drawText(examName, (pageWidth / 2).toFloat(), 80f, titlePaint)

        if (studentNameField) {
            canvas.drawText("Nome do Aluno:", 60f, 120f, textPaint)
            canvas.drawLine(180f, 125f, 500f, 125f, strokePaint)
        }

        val qrBitmap = qrCodeGenerator.generate(examId, 120)
        qrBitmap?.let {
            canvas.drawBitmap(it, (pageWidth - 120 - 60).toFloat(), 60f, null)
        }

        val questionsPerColumn = 50
        val totalColumns = (totalQuestions + questionsPerColumn - 1) / questionsPerColumn
        val columnWidth = (pageWidth - 120) / totalColumns
        val startY = 160f
        val rowHeight = (pageHeight - startY - 60) / questionsPerColumn

        for (qNum in 1..totalQuestions) {
            val colIndex = (qNum - 1) / questionsPerColumn
            val rowIndex = (qNum - 1) % questionsPerColumn
            val currentX = (60 + colIndex * columnWidth).toFloat()
            val currentY = startY + (rowIndex * rowHeight)

            canvas.drawText("$qNum.", currentX, currentY + 20, textPaint)

            val bubbleRadius = 12f
            val bubbleStartY = currentY + 15
            val bubbleStartX = currentX + 50
            val bubbleSpacing = 50f

            for (altIndex in 0 until alternatives) {
                val bubbleCenterX = bubbleStartX + (altIndex * bubbleSpacing)
                canvas.drawCircle(bubbleCenterX, bubbleStartY, bubbleRadius, strokePaint)
                val letter = ('A' + altIndex).toString()
                canvas.drawText(letter, bubbleCenterX - 5, bubbleStartY - 18, textPaint)
            }
        }

        pdfDocument.finishPage(page)

        val file = File(context.cacheDir, "ExamSheet_$examId.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
        } catch (e: IOException) {
            throw e
        } finally {
            pdfDocument.close()
        }

        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }
}
