package com.example.pinjamseratus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HutangFragment : Fragment() {

    private var currentDebt: Double = 0.0
    private val debtHistory = mutableListOf<Pair<String, Double>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hutang, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val debtAmountEditText: EditText = view.findViewById(R.id.debtAmountEditText)
        val processDebtButton: Button = view.findViewById(R.id.processDebtButton)
        val debtTextView: TextView = view.findViewById(R.id.debtTextView)
        val debtHistoryTextView: TextView = view.findViewById(R.id.debtHistoryTextView)

        processDebtButton.setOnClickListener {
            processDebt(debtAmountEditText.text.toString())
            updateUI(debtTextView, debtHistoryTextView)
        }
    }

    private fun processDebt(enteredAmount: String) {
        if (enteredAmount.isNotEmpty()) {
            val debt = enteredAmount.toDouble()
            currentDebt += debt
            val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            debtHistory.add(Pair(currentDate, debt))
        }
        // Handle the case where the amount is not entered if needed
    }

    private fun updateUI(debtTextView: TextView, debtHistoryTextView: TextView) {
        debtTextView.text = "Current Debt: $currentDebt"

        val historyStringBuilder = StringBuilder()
        for ((date, amount) in debtHistory) {
            historyStringBuilder.append("$date: $amount\n")
        }
        debtHistoryTextView.text = historyStringBuilder.toString()
    }
}
