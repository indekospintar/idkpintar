package com.rut.indekospintarv1


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101


 //   private lateinit var m_lampu: TextView
//    private lateinit var btn_LamOn: Button
//    private lateinit var btn_LamOff: Button
    private lateinit var m_distance: TextView
//    private lateinit var m_securityApi: TextView
//    private lateinit var btn_ApiOn: Button
//    private lateinit var btn_ApiOff: Button
//    private lateinit var m_securityMaling: TextView
//    private lateinit var btn_MalingOn: Button
//    private lateinit var btn_MalingOff: Button
    private lateinit var m_humidity: TextView
    private lateinit var m_temperature: TextView
    private lateinit var m_buzzer: TextView
    private lateinit var m_pompa: TextView

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        val database = FirebaseDatabase.getInstance()
        val nApi = database.getReference("api")
        val nMaling = database.getReference("maling")
        val fLampu = database.getReference("status_lampu")
        val fDistance = database.getReference("distance")
        val fApi = database.getReference("securityApi")
        val fMaling = database.getReference("securityMaling")
        val fPompa = database.getReference("status_pompa")
        val fBuzzer = database.getReference("status_buzzer")
        val fHumidity = database.getReference("dht/humidity")
        val fTemperatur = database.getReference("dht/temperature")

        //btn_LamOff = findViewById(R.id.btn_LamOff)
       //btn_LamOn = findViewById(R.id.btn_LamOn)
        //m_lampu = findViewById(R.id.m_lampu)
        m_distance = findViewById(R.id.m_distance)

        //btn_ApiOn = findViewById(R.id.btn_ApiOn)
        //btn_ApiOff = findViewById(R.id.btn_ApiOff)
        //m_securityApi = findViewById(R.id.m_securityApi)

        //btn_MalingOn = findViewById(R.id.btn_MalingOn)
        //btn_MalingOff = findViewById(R.id.btn_MalingOff)
        //m_securityMaling = findViewById(R.id.m_securityMaling)

        m_humidity = findViewById(R.id.m_humidity)
        m_temperature = findViewById(R.id.m_temperature)
        m_buzzer = findViewById(R.id.m_buzzer)
        m_pompa = findViewById(R.id.m_pompa)



        val sw1 = findViewById<Switch>(R.id.switchLampu)
        sw1?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                Toast.makeText(this, "Lampu: ON", Toast.LENGTH_SHORT).show()
                turnLamp(fLampu, "on") }
            else {
                Toast.makeText(this, "Lampu: OFF", Toast.LENGTH_SHORT).show()
                turnLamp(fLampu, "off")
            }
        }

        val sw2 = findViewById<Switch>(R.id.switchMaling)
        sw2?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                Toast.makeText(this, "Keamanan Anti Maling: ON", Toast.LENGTH_SHORT).show()
                turnLamp(fMaling, "on") }
            else {
                Toast.makeText(this, "Keamanan Anti Maling: OFF", Toast.LENGTH_SHORT).show()
                turnLamp(fMaling, "off")
            }
        }
        val sw3 = findViewById<Switch>(R.id.switchApi)
        sw3?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                Toast.makeText(this, "Keamanan Kebakaran: ON", Toast.LENGTH_SHORT).show()
                turnApi(fApi, "on") }
            else {
                Toast.makeText(this, "Keamanan Kebakaran: OFF", Toast.LENGTH_SHORT).show()
                turnApi(fApi, "off")
            }
        }



//Lampu
//        btn_LamOn.setOnClickListener {
//            Toast.makeText(this, "Lampu: ON", Toast.LENGTH_SHORT).show()
//            turnLamp(fLampu, "on")
//        }
//
//        btn_LamOff.setOnClickListener {
//            Toast.makeText(this, "Lampu: OFF", Toast.LENGTH_SHORT).show()
//            turnLamp(fLampu, "off")
//        }

//        val sLampuListener = object : ValueEventListener {
//            @SuppressLint("SetTextI18n", "DefaultLocale")
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val sLampu = dataSnapshot.value
//                m_lampu.text = " ${sLampu.toString().uppercase()}"
//            }
//            override fun onCancelled(databaseError: DatabaseError) {}
//        }
//        fLampu.addValueEventListener(sLampuListener)

//Api
//        btn_ApiOn.setOnClickListener {
//            Toast.makeText(this, "Keamanan Kebakaran: ON", Toast.LENGTH_SHORT).show()
//            turnApi(fApi, "on")
//        }
//
//        btn_ApiOff.setOnClickListener {
//            Toast.makeText(this, "Keamanan Kebakaran: OFF", Toast.LENGTH_SHORT).show()
//            turnApi(fApi, "off")
//        }

//        val sApiListener = object : ValueEventListener {
//            @SuppressLint("SetTextI18n", "DefaultLocale")
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val sApi = dataSnapshot.value
//                m_securityApi.text = " ${sApi.toString().uppercase()}"
//            }
//            override fun onCancelled(databaseError: DatabaseError) {}
//        }
//        fApi.addValueEventListener(sApiListener)

//Maling
//        btn_MalingOn.setOnClickListener {
//            Toast.makeText(this, "Keamanan Anti Maling: ON", Toast.LENGTH_SHORT).show()
//            turnMaling(fMaling, "on")
//        }
//
//        btn_MalingOff.setOnClickListener {
//            Toast.makeText(this, "Keamanan Anti Maling: OFF", Toast.LENGTH_SHORT).show()
//            turnMaling(fMaling, "off")
//        }

//        val sMalingListener = object : ValueEventListener {
//            @SuppressLint("SetTextI18n", "DefaultLocale")
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val sM = dataSnapshot.value
//                m_securityMaling.text = " ${sM.toString().uppercase()}"
//            }
//            override fun onCancelled(databaseError: DatabaseError) {}
//        }
//        fMaling.addValueEventListener(sMalingListener)


//Distance
        val sDistanceListener = object : ValueEventListener {
            @SuppressLint("SetTextI18n", "DefaultLocale")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val sD = dataSnapshot.value
                m_distance.text = "${sD.toString().uppercase()} CM"
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        fDistance.addValueEventListener(sDistanceListener)

        //Temperatur
        val sTemperaturListener = object : ValueEventListener {
            @SuppressLint("SetTextI18n", "DefaultLocale")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val sT = dataSnapshot.value
                m_temperature.text = "${sT.toString()} C"
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        fTemperatur.addValueEventListener(sTemperaturListener)

        //Humidity
        val sHumidityListener = object : ValueEventListener {
            @SuppressLint("SetTextI18n", "DefaultLocale")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val sD = dataSnapshot.value
                m_humidity.text = "${sD.toString()}"
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        fHumidity.addValueEventListener(sHumidityListener)

//Buzzer
        val sBuzzerListener = object : ValueEventListener {
            @SuppressLint("SetTextI18n", "DefaultLocale")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val status = dataSnapshot.value
                m_buzzer.text = "${status.toString().uppercase()}"
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        fBuzzer.addValueEventListener(sBuzzerListener)

        //Pompa
        val sPompaListener = object : ValueEventListener {
            @SuppressLint("SetTextI18n", "DefaultLocale")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val status = dataSnapshot.value
                m_pompa.text = "${status.toString().uppercase()}"
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        fPompa.addValueEventListener(sPompaListener)
//notif
        //notif
        val notMalingListener = object : ValueEventListener {
            @SuppressLint("SetTextI18n", "DefaultLocale")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val status = dataSnapshot.value
                if(status == "on")
                    sendNotificationMaling()

            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        nMaling.addValueEventListener(notMalingListener)

        //notif
        val notApiListener = object : ValueEventListener {
            @SuppressLint("SetTextI18n", "DefaultLocale")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val status = dataSnapshot.value
                if(status == "on")
                    sendNotificationApi()

            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        nApi.addValueEventListener(notApiListener)
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            val name = "Notification Tittle"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotificationApi(){
        val intent = Intent(this, MainActivity::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this,0,intent,0)

        //val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.war1)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.war1)



        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.war1)
            .setContentTitle("Kebakaran!")
            .setContentText("Terdeteksi Suhu Panas")
            .setLargeIcon(bitmapLargeIcon)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Segera periksa kamar kos Anda, dikarenakan suhu melebihi 30'C. BERPOTENSI KEBAKARAN"))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
    }

    private fun sendNotificationMaling(){
        val intent = Intent(this, MainActivity::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this,0,intent,0)

        //val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.war1)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.war1)

        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.war1)
            .setContentTitle("Maling")
            .setContentText("Terdeteksi Perubahan Jarak")
            .setLargeIcon(bitmapLargeIcon)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Segera periksa kamar kos Anda, dikarenakan jarak melebihi 10CM. BERPOTENSI KEMALINGAN"))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
    }

    private fun turnLamp(fLampu: DatabaseReference, status: String) {
        fLampu.setValue(status)
    }
    private fun turnApi(fApi: DatabaseReference, status: String) {
        fApi.setValue(status)
    }
    private fun turnMaling(fMaling: DatabaseReference, status: String) {
        fMaling.setValue(status)
    }
//    private fun turnPompa(fPompa: DatabaseReference, status: String) {
//        fPompa.setValue(status)
//    }
//    private fun turnBuzzer(fBuzzer: DatabaseReference, status: String) {
//        fBuzzer.setValue(status)
//    }
//    private fun turnHumidity(fHumidity: DatabaseReference, status: String) {
//        fHumidity.setValue(status)
//    }
//    private fun turnTemperatur(fTemperatur: DatabaseReference, status: String) {
//        fTemperatur.setValue(status)
//    }
//    private fun notifApi(nApi: DatabaseReference, status: String) {
//        nApi.setValue(status)
//    }
//    private fun notifMaling(nMaling: DatabaseReference, status: String) {
//        nMaling.setValue(status)
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.info -> {
            msgShow("Info")
            val intent = Intent(this, Info::class.java)
            this.startActivity(intent)
            true
        }
        R.id.home -> {
            msgShow("Home")
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    fun msgShow(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}