package com.example.wizard_duel_client

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.gson.Gson
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var drawingView: DrawingView
    private lateinit var spellNameInput: EditText
    private lateinit var magicCostInput: EditText
    private lateinit var damageInput: EditText
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView = findViewById(R.id.drawingView)
        spellNameInput = findViewById(R.id.spellNameInput)
        magicCostInput = findViewById(R.id.magicCostInput)
        damageInput = findViewById(R.id.damageInput)
        saveButton = findViewById(R.id.saveButton)
        clearButton = findViewById(R.id.clearButton)

        saveButton.setOnClickListener {
            saveSpell()
        }

        clearButton.setOnClickListener {
            drawingView.clear()
        }
    }

    private fun saveSpell() {
        val spellName = spellNameInput.text.toString()
        val magicCost = magicCostInput.text.toString().toIntOrNull() ?: 0
        val damage = damageInput.text.toString().toIntOrNull() ?: 0

        // Készíts egy bitmapet a DrawingView tartalmából
        val bitmap = Bitmap.createBitmap(drawingView.width, drawingView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawingView.draw(canvas)

        // Pixel adatok kinyerése
        val drawingData = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(drawingData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        // SpellData objektum létrehozása
        val spellData = SpellData(spellName, magicCost, damage, drawingData.toList())
        saveSpellData(spellData)

        // Rajz törlése mentés után
        drawingView.clear()
    }

    private fun saveSpellData(spellData: SpellData) {
        val json = Gson().toJson(spellData)
        // Írjuk hozzá vagy hozzuk létre a fájlt a belső tárolóban
        val file = File(filesDir, "spells.json")
        file.appendText("$json\n")
    }
}