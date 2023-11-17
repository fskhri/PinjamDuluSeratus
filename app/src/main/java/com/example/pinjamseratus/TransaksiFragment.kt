package com.example.pinjamseratus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class TransaksiFragment : Fragment() {

    // Placeholder variables for transaction data
    private var transactionAmount: Double = 0.0
    private var currentBalance: Double = 1000.0  // Initial balance for example

    // List to store transaction history
    private val transactionHistory: MutableList<Transaction> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaksi, container, false)

        // Assuming you have a button with ID "processTransactionButton" in your layout
        val processTransactionButton: Button = view.findViewById(R.id.processTransactionButton)
        processTransactionButton.setOnClickListener {
            // Call the method to process the transaction
            processTransaction()
        }

        return view
    }

    // Method to process a transaction
    private fun processTransaction() {
        val transactionAmountEditText: EditText? = view?.findViewById(R.id.transactionAmountEditText)
        val amountString = transactionAmountEditText?.text.toString()

        if (amountString.isNotEmpty()) {
            val enteredAmount = amountString.toDouble()

            if (currentBalance >= enteredAmount) {
                // Sufficient funds, process the transaction
                currentBalance -= enteredAmount
                // Add the transaction to the history
                addTransactionToHistory(enteredAmount)
                // Update the UI to reflect the new balance and transaction history
                updateUI()
            } else {
                // Insufficient funds, show a message or take appropriate action
                // For example, display a Toast message
                Toast.makeText(requireContext(), "Duit Kamu Kurang, Silahkan TopUp!", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Handle the case where the amount is not entered
            // For example, display a Toast message
            Toast.makeText(requireContext(), "Masukan Nominal Yang Akan Di Kirim!", Toast.LENGTH_SHORT).show()
        }
    }

    // Method to add a transaction to the history
    private fun addTransactionToHistory(amount: Double) {
        val timestamp = System.currentTimeMillis()
        val formattedDate = formatDate(timestamp)

        val transaction = Transaction(timestamp, amount, formattedDate)
        transactionHistory.add(transaction)
    }

    // Method to update the UI with the current balance and transaction history
    private fun updateUI() {
        val balanceTextView: TextView = view?.findViewById(R.id.balanceTextView) ?: return
        balanceTextView.text = getString(R.string.current_balance, currentBalance)

        // Display transaction history
        val transactionHistoryTextView: TextView = view?.findViewById(R.id.transactionHistoryTextView) ?: return
        transactionHistoryTextView.text = buildTransactionHistoryText()
    }

    // Method to build a formatted string for the transaction history
    private fun buildTransactionHistoryText(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("Transaction History:\n")

        for (transaction in transactionHistory) {
            val transactionString =
                "Amount: ${transaction.amount}, Date: ${transaction.formattedDate}\n"
            stringBuilder.append(transactionString)
        }

        return stringBuilder.toString()
    }

    // Method to format the timestamp to a readable date
    private fun formatDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val date = Date(timestamp)
        return dateFormat.format(date)
    }

    // Data class to represent a transaction
    data class Transaction(val timestamp: Long, val amount: Double, val formattedDate: String)
}