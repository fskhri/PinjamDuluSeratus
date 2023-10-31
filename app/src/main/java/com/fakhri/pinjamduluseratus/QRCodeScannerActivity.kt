package com.fakhri.pinjamduluseratus

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class QRCodeScannerActivity : AppCompatActivity() {

    private lateinit var barcodeScannerView: DecoratedBarcodeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qrscannerbutton)

        barcodeScannerView = findViewById(R.id.barcodeScannerView)
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, start the camera
            initializeBarcodeScanner()
        } else {
            // Request the permission
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
        }
    }

    private fun initializeBarcodeScanner() {
        barcodeScannerView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    handleBarcodeResult(it)
                }
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
                // Do something with possible result points
            }
        })
    }

    private fun handleBarcodeResult(barcodeResult: BarcodeResult) {
        val scannedData = barcodeResult.text
        // Handle the scanned data here (e.g., display it, send it to server, etc.)
        Toast.makeText(this, "Scanned Data: $scannedData", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start the camera
                initializeBarcodeScanner()
            } else {
                // Permission denied, handle the denial
                Toast.makeText(this, "Camera permission denied. Cannot scan QR codes.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

}
