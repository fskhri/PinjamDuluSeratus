package com.fakhri.pinjamduluseratus

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var friendNameEditText: EditText
    private lateinit var borrowButton: Button
    private lateinit var resetButton: Button
    private lateinit var displayMessageTextView: TextView
    private lateinit var saldoTextView: TextView
    private lateinit var totalPeminjamanTextView: TextView
    private var saldo = 500
    private var totalPinjaman = 0

    private lateinit var buttonAnimation: Animation // Animasi tombol "Pinjam 100"

    companion object {
        private const val DEFAULT_FRIEND_NAME = "Teman"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        friendNameEditText = findViewById(R.id.friendNameEditText)
        borrowButton = findViewById(R.id.borrowButton)
        resetButton = findViewById(R.id.resetButton)
        displayMessageTextView = findViewById(R.id.displayMessageTextView)
        saldoTextView = findViewById(R.id.saldoTextView)
        totalPeminjamanTextView = findViewById(R.id.totalPeminjamanTextView)

        friendNameEditText.setText(DEFAULT_FRIEND_NAME)

        saldoTextView.text = "Saldo: $saldo rupiah"
        totalPeminjamanTextView.text = "Total Peminjaman: $totalPinjaman rupiah"

        // Mengambil animasi dari file XML
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation)

        borrowButton.setOnClickListener {
            borrowMoney()
            // Menjalankan animasi saat tombol ditekan
            borrowButton.startAnimation(buttonAnimation)
        }

        resetButton.setOnClickListener {
            showResetConfirmationDialog()
        }
    }

    private fun borrowMoney() {
        val friendName = friendNameEditText.text.toString()
        if (friendName.isNotBlank() && saldo >= 100) {
            saldo -= 100
            totalPinjaman += 100
            val currentTime = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date())
            val message = "Anda meminjam 100 rupiah dari $friendName pada $currentTime. Saldo sekarang: $saldo rupiah."
            displayMessageTextView.text = message
            saldoTextView.text = "Saldo: $saldo rupiah"
            totalPeminjamanTextView.text = "Total Peminjaman: $totalPinjaman rupiah"
        } else if (friendName.isNotBlank() && saldo < 100) {
            displayMessageTextView.text = "Saldo tidak mencukupi untuk melakukan peminjaman."
        } else {
            displayMessageTextView.text = "Masukkan nama teman Anda."
        }
    }

    private fun showResetConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Reset Saldo")
        builder.setMessage("Apakah Anda yakin ingin mereset saldo dan total peminjaman?")
        builder.setPositiveButton("Ya") { dialog: DialogInterface, which: Int ->
            resetBalanceAndTotalLoan()
        }
        builder.setNegativeButton("Tidak") { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun resetBalanceAndTotalLoan() {
        saldo = 500
        totalPinjaman = 0
        saldoTextView.text = "Saldo: $saldo rupiah"
        totalPeminjamanTextView.text = "Total Peminjaman: $totalPinjaman rupiah"
        displayMessageTextView.text = "Saldo telah direset."
    }
}