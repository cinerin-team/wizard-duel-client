package com.example.wizard_duel_client

data class SpellData(
    val spellName: String,
    val magicCost: Int,
    val damage: Int,
    val drawing: List<Int>
)