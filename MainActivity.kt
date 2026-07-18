package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import io.github.jan_tennert.supabase.createSupabaseClient
import io.github.jan_tennert.supabase.postgrest.Postgrest
import io.github.jan_tennert.supabase.postgrest.from

// تعريف الكلاس للاتصال بـ Supabase داخل نفس الملف
object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://bexlpwqzmwaloafnrrle.supabase.co",
        supabaseKey = "sb_publishable_GiIkF-_TW4vmTRnYbeQ49w_8_fx0PjL"
    ) {
        install(Postgrest)
    }
}

class MainActivity : AppCompatActivity() {
    
    private val paymentList = ArrayList<PaymentRecord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etMemberName = findViewById<EditText>(R.id.etMemberName)
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val tvRecordsList = findViewById<TextView>(R.id.tvRecordsList)

        btnSave.setOnClickListener {
            val name = etMemberName.text.toString()
            val amountStr = etAmount.text.toString()

            if (name.isNotEmpty() && amountStr.isNotEmpty()) {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                val currentDate = sdf.format(Date())
                
                val payment = PaymentRecord(name, amountStr.toDouble(), currentDate)
                paymentList.add(payment)

                GlobalScope.launch {
                    try {
                        SupabaseClient.client.from("societies").insert(
                            mapOf(
                                "name" to name,
                                "start_date" to currentDate,
                                "total_members" to 0
                            )
                        )
                    } catch (e: Exception) {
                        // في حال فشل الاتصال
                    }
                }

                val displayText = StringBuilder()
                for ((index, record) in paymentList.withIndex()) {
                    displayText.append("${index + 1}) الاسم: ${record.memberName} | المبلغ: ${record.amount} | التاريخ: $currentDate\n")
                    displayText.append("--------------------------------------------------\n")
                }
                tvRecordsList.text = displayText.toString()

                etMemberName.text.clear()
                etAmount.text.clear()

                Toast.makeText(this, "تم الحفظ", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "يرجى تعبئة الاسم والمبلغ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
