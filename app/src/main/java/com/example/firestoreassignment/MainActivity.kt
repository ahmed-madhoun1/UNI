package com.example.firestoreassignment

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firestore.collection("Users")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                querySnapshot?.forEach {
                    textViewData.append(it.get("name").toString() + " " +it.get("number").toString() + " " + it.get("address").toString() + "\n")

                }
            }

        btnAddUser.setOnClickListener {
            addDataToFirestore(
                user = User(
                    editTextName.text.toString(),
                    editNumber.text.toString(),
                    editAddress.text.toString()
                )
            )
        }
    }

    private fun addDataToFirestore(user: User) {
        firestore.collection("Users").add(user).addOnSuccessListener {
            Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }
}