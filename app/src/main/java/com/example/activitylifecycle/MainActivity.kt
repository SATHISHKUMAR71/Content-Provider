package com.example.activitylifecycle

import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val contactList = mutableListOf<com.example.activitylifecycle.Contacts>()
    override fun onStart() {

        super.onStart()
        println("Application Started")
//        no long running operations
//        Register broadcast receivers, bind to services, and prepare data that will be needed soon.
//        REGISTER THE LISTENERS AND RECEIVERS NOT START ACTUAL UPDATES
//        prepares the activity to the foreground
//        load data from database
//        start the service when the app is started
//        starting the broadcast receiver
//        start services for background operations that should be done when the activity is visible to user
    }

    private fun fetchContacts(){
        val sortOrder = "${Contacts.DISPLAY_NAME} DESC"
        val projections = arrayOf(
            Contacts._ID,
            Contacts.DISPLAY_NAME
        )
        contentResolver.query(
            Contacts.CONTENT_URI,
            projections,
            null,
            null,
            sortOrder
        ). use { cursor ->
            val contactID = cursor?.getColumnIndex(Contacts._ID)
            val contactName = cursor?.getColumnIndex(Contacts.DISPLAY_NAME)


            if (cursor != null) {
                while (cursor.moveToNext()){
                    val id = cursor.getLong(contactID!!)
                    val name = cursor.getString(contactName!!)
                    val uri = ContentUris.withAppendedId(
                        Contacts.CONTENT_URI,id
                    )
                    println("Id: $id, Name: $name")
                    contactList.add(com.example.activitylifecycle.Contacts(id,name,uri))
                }
            }
        }
    }

    
    override fun onCreate(savedInstanceState: Bundle?) {
        println("Application Created")
//        PERFORM ONLY INITIALIZATION TASKS
//        restoring the previous state if it exists
//        called only once during the lifecycle
//        used to initialize ui components
//        initialize variables that required for activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED) {
            fetchContacts()
        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS) ,0)
            setContentView(R.layout.activity_main)
        }

        val adapter = ContactAdapter(contactList)
        val rv = findViewById<RecyclerView>(R.id.recyclerViewContact)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)

    }


    override fun onResume() {

//   User Interaction: Resume tasks that are needed for immediate user interaction.
//    Avoid: Initializing heavy operations that might delay UI rendering.
//    Do: Start animations, refresh UI with latest data,
//     and begin tasks that should run only while the activity is in the foreground.
        super.onResume()
//        fetchContacts()
        println("Application Resumed")
//        run some animations
//        acquiring resources for user interactions (camera,mic)
//        for dynamic ui updates
//        get the updated data from database

    }

    override fun onPause() {
        super.onPause()
        println("Application Paused")
//        do operations before the app goes to foreground
//        saving changes
//        pause onGoing operations that are not necessary
    }

    override fun onStop() {
        super.onStop()
        println("Application Stopped")
//        invisible to user
//        releasing resources like location updates and so on
//        commit pending data changes
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Application Destroyed")
//        perform clean up tasks like database connection close
    }

    override fun onRestart() {
        super.onRestart()
        println("Application Restarted")
//        reconnect to the activity services and other components need to restart
    }
}