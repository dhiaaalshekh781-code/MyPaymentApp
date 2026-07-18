package com.example.myapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.jan_tennert.supabase.createSupabaseClient
import io.github.jan_tennert.supabase.postgrest.Postgrest
import io.github.jan_tennert.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // إعداد عميل Supabase
    private val client = createSupabaseClient(
        supabaseUrl = "https://bexlpwqzmwaloafnrrle.supabase.co",
        supabaseKey = "sb_publishable_GiIkF-_TW4vmTRnYbeQ49w_8_fx0PjL"
    ) {
        install(Postgrest)
    }

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
                val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                // استخدام CoroutineScope بدلاً من GlobalScope لتجنب مشاكل الذاكرة
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        client.from("societies").insert(
                            mapOf(
                                "name" to name,
                                "start_date" to currentDate,
                                "total_members" to 0
                            )
                        )
                        runOnUiThread {
                            tvRecordsList.append("تم حفظ: $name\n")
                            Toast.makeText(this@MainActivity, "تم الحفظ بنجاح", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "خطأ: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}
