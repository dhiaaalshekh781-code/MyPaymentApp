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
import io.github.jan_tennert.supabase.postgrest.from

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
                
                // جلب التاريخ والوقت الحالي تلقائياً من نظام الهاتف
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                val currentDate = sdf.format(Date())
                
                // إنشاء السجل وحفظه
                val payment = PaymentRecord(name, amountStr.toDouble(), currentDate)
                paymentList.add(payment)
                        // إضافة للربط مع Supabase
        GlobalScope.launch {
            try {
                supabase.from("societies").insert(
                    mapOf(
                        "name" to name, // هنا ضع المتغير الذي يحمل اسم العضو
                        "start_date" to currentDate, // استخدم المتغير الموجود عندك
                        "total_members" to 0 // أو القيمة التي تريدها
                    )
                )
            } catch (e: Exception) {
                // خطأ في الإرسال
            }
        }

                // تحديث شكل الجدول في الأسفل ليعرض كل البيانات المرتبة
                val displayText = StringBuilder()
                for ((index, record) in paymentList.withIndex()) {
                    displayText.append("${index + 1}) الاسم: ${record.memberName} | المبلغ: ${record.amount} | التاريخ: $currentDate\n")
                    displayText.append("--------------------------------------------------\n")
                }
                tvRecordsList.text = displayText.toString()

                // تنظيف الخانات للكتابة من جديد
                etMemberName.text.clear()
                etAmount.text.clear()

                Toast.makeText(this, "تم الحفظ بتاريخ $currentDate", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "يرجى تعبئة الاسم والمبلغ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
