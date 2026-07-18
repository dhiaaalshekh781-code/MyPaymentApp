package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etMemberName = findViewById<EditText>(R.id.etMemberName)
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val etDate = findViewById<EditText>(R.id.etDate)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val name = etMemberName.text.toString()
            val amountStr = etAmount.text.toString()
            val date = etDate.text.toString()

            if (name.isNotEmpty() && amountStr.isNotEmpty() && date.isNotEmpty()) {
                val payment = PaymentRecord(name, amountStr.toDouble(), date)
                Toast.makeText(this, "تم حفظ بيانات: ${payment.memberName}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "يرجى تعبئة جميع الحقول أولاً", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
