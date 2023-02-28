package com.example.firebasesample

import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.firebasesample.databinding.ActivityMainBinding
import com.example.firebasesample.databinding.LayoutDialogAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            btnStartService.setOnClickListener {
                startService(Intent(this@MainActivity, MyService::class.java))
                startActivity(Intent(Intent.ACTION_MAIN).apply {
                    addCategory(Intent.CATEGORY_HOME)
                    setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
                (it as Button).run {
                    isEnabled = !isEnabled
                    btnStopService.isEnabled = !isEnabled
                }
                NotifyHelper.notify(this@MainActivity, getString(R.string.app_name), "start service..")
            }
            btnStopService.setOnClickListener {
                stopService(Intent(this@MainActivity, MyService::class.java))

                btnStartService.run { isEnabled = !isEnabled }
            }
            btnCreate.setOnClickListener {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this@MainActivity, MyService::class.java))
        Toast.makeText(this, "App is destroyed", Toast.LENGTH_SHORT).show()
    }

    private fun showDialog(type: String) {
        val dlgBinding = LayoutDialogAuthBinding.inflate(layoutInflater)
        val dlg = AlertDialog.Builder(this@MainActivity).create().apply {
            setCancelable(true)
            setView(dlgBinding.root)
        }

        dlgBinding.apply {
            btnAuth.apply {
                text = type
                setOnClickListener {
                    if(edtId.text.isEmpty() || edtPw.text.isEmpty()) {
                        Toast.makeText(this@MainActivity, "빈 칸은 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    when(type) {
                        "login" -> {

                        }
                        "create" -> {
                            createAccount(edtId.text.toString(), edtPw.text.toString()) {
                                dlg.dismiss()
                            }
                        }
                    }
                }

                setOnLongClickListener {
                    dlg.dismiss()
                    false
                }
            }
        }
    }

    private fun createAccount(id: String, pw: String, dismiss: () -> Unit) {
        auth.createUserWithEmailAndPassword(id, pw).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                Toast.makeText(this@MainActivity, "계정 생성 성공", Toast.LENGTH_SHORT).show()
                dismiss()
            }
            else {
                Toast.makeText(this@MainActivity, "계정 생성 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginAccount(id: String, pw: String, dismiss: () -> Unit) {
        auth.signInWithEmailAndPassword(id, pw).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                Toast.makeText(this@MainActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                dismiss()
            }
            else {
                Toast.makeText(this@MainActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
}