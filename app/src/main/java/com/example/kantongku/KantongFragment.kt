package com.example.kantongku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_kantong.*

class KantongFragment : Fragment() {

    // Placeholder variables for savings data and transaction history
    private var savingsAmount: Double = 0.0
    private val transactionHistory = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize any variables or perform setup related to savings
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_kantong, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up onClickListener for the button (assuming you have a button with id "saveButton")
        saveButton.setOnClickListener {
            // Call the method to handle saving
            saveMoney()
        }

        // You can add more UI setup or functionality here if needed
    }

    // Method to handle saving money
    private fun saveMoney() {
        // Example: Saving a fixed amount, you can replace this with your logic
        val amountToSave = amountEditText.text.toString().toDoubleOrNull() ?: 0.0
        if (amountToSave > 0) {
            savingsAmount += amountToSave
            // Add transaction history
            transactionHistory.add("Saved $amountToSave")
            // Update the UI to reflect the new savings amount and transaction history
            updateUI()
        }
    }

    // Method to update the UI with the current savings amount and transaction history
    private fun updateUI() {
        val savingsTextView = view?.findViewById<TextView>(R.id.savingsTextView)
        val transactionHistoryTextView = view?.findViewById<TextView>(R.id.transactionHistoryTextView)

        // Update savings amount
        savingsTextView?.text = getString(R.string.current_savings, savingsAmount)

        // Update transaction history
        transactionHistoryTextView?.text = buildTransactionHistoryString()
    }

    // Method to build a string representation of the transaction history
    private fun buildTransactionHistoryString(): String {
        val builder = StringBuilder("Transaction History:\n")
        for (transaction in transactionHistory) {
            builder.append("$transaction\n")
        }
        return builder.toString()
    }
}
