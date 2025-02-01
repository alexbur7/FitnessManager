package ru.alexbur.fintess_manager.feature.login.presentation.phone

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

@Composable
internal fun PhoneNumber(numberEntered: (String) -> Unit) {
    var phoneNumber = rememberSaveable { mutableStateOf("") }
    val numericRegex = Regex("[^0-9]")
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = phoneNumber.value,
        onValueChange = { data: String ->
            val stripped = numericRegex.replace(data, "")
            phoneNumber.value = if (stripped.length >= 11) {
                stripped.substring(0..10)
            } else if (stripped.length == 1) {
                "7"
            } else {
                stripped
            }
            numberEntered(phoneNumber.value)
        },
        visualTransformation = PhoneVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

private class PhoneVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 11) text.text.substring(0..10) else text.text
        var out = if (trimmed.isNotEmpty()) "+" else ""
        for (i in trimmed.indices) {
            if (i == 1) out += " ("
            if (i == 4) out += ") "
            if (i == 7) out += "-"
            if (i == 9) out += "-"
            out += trimmed[i]
        }
        return TransformedText(AnnotatedString(out), phoneNumberOffsetTranslator)
    }

    private val phoneNumberOffsetTranslator = object : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int = when (offset) {
            0 -> offset
            1 -> offset + 1
            in 2..4 -> offset + 3
            in 5..7 -> offset + 5
            in 8..9 -> offset + 6
            else -> offset + 7
        }

        override fun transformedToOriginal(offset: Int): Int = when (offset) {
            0 -> offset
            in 1..2 -> offset - 1
            in 3..5 -> offset - 3
            in 6..8 -> offset - 5
            in 9..10 -> offset - 6
            else -> offset - 7
        }
    }
}