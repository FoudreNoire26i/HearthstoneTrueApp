package com.example.hearthstonetrueapp.ui.Card

data class FilterItemForSpinner(
    val filterName : String,
    val filterValueList: Int, // la référence sur les tableaux
    var isChecked : Boolean,
    var actualValue : Int // position dans la liste de la valeur
)