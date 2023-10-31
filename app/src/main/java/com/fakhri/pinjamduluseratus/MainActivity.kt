package com.fakhri.pinjamduluseratus

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.animation.Animation
import android.content.Intent
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var friendNameEditText: EditText
    private lateinit var borrowButton: Button
    private lateinit var resetButton: Button
    private lateinit var qrScannerButton: Button
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
        qrScannerButton = findViewById(R.id.qrScannerButton)
        saldoTextView = findViewById(R.id.saldoTextView)
        totalPeminjamanTextView = findViewById(R.id.totalPeminjamanTextView)

        friendNameEditText.setText(DEFAULT_FRIEND_NAME)

        saldoTextView.text = "Saldo: $saldo rupiah"
        totalPeminjamanTextView.text = "Total Uang Yang Di Pinjam: $totalPinjaman rupiah"

        // Mengambil animasi dari file XML
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation)

        borrowButton.setOnClickListener {
            borrowMoney()
            // Menjalankan animasi saat tombol ditekan
            borrowButton.startAnimation(buttonAnimation)
        }

        qrScannerButton = findViewById(R.id.qrScannerButton)

        qrScannerButton.setOnClickListener {
            // Memulai pemindaian kode QR saat tombol ditekan
            startQRCodeScanner()
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
            val message =
                "Anda meminjamkan 100 rupiah untuk $friendName pada $currentTime. Saldo sekarang: $saldo rupiah."
            displayMessageTextView.text = message
            saldoTextView.text = "Saldo: $saldo rupiah"
            totalPeminjamanTextView.text = "Total Uang Yang Di Pinjam: $totalPinjaman rupiah"
        } else if (friendName.isNotBlank() && saldo < 100) {
            displayMessageTextView.text = "Saldo tidak mencukupi untuk melakukan transaksi."
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

    private fun startQRCodeScanner() {
        // Memulai QR code
        val integrator = IntentIntegrator(this)
        val dataWithTotal = "$totalPinjaman,informasi_lain_yang_ingin_disematkan"
        integrator.setOrientationLocked(false) // Membuka orientasi pemindaian
        integrator.setBeepEnabled(true)
        integrator.setPrompt("Pemindaian Kode QR, Arahkan ke Kode QR, Create By Fakhri") // Pesan prompt pemindaian
        integrator.initiateScan() // Memulai pemindaian
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                // Pemindaian dibatalkan atau tidak ada data
                // Handle sesuai kebutuhan
            } else {
                // Hasil pemindaian kode QR
                val scannedData = result.contents
                // Handle data hasil pemindaian di sini (misalnya, tampilkan, kirim ke server, dll.)

                // Otomatis meminjam 100 rupiah setelah pemindaian berhasil
                borrowMoney()
            }
        }
    }

    private fun resetBalanceAndTotalLoan() {
        saldo = 500
        totalPinjaman = 0
        saldoTextView.text = "Saldo: $saldo rupiah"
        totalPeminjamanTextView.text = "Total Uang Yang Di Pinjam: $totalPinjaman rupiah"
        displayMessageTextView.text = "Saldo telah direset."
    }
}
