package com.example.healingwords

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healingwords.models.Review
import com.google.firebase.database.*
import kotlin.math.round

class UserViewDocProfile : AppCompatActivity() {

    private lateinit var btnReviews: Button
    private lateinit var tvName: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvBio: TextView
    private lateinit var tvTitle: TextView
    private lateinit var uid: String
    private lateinit var database : DatabaseReference
    private lateinit var reviewDbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_view_doc_profile)

        btnReviews = findViewById(R.id.btnViewReviewsUserView)

        tvName = findViewById(R.id.tvDocNameUserView)
        tvBio = findViewById(R.id.tvDocBioUserView)
        tvRating = findViewById(R.id.tvTotalRatingDocProfileUserView)
        tvTitle =findViewById(R.id.tvDocTitleUserView)

        uid = intent.getStringExtra("uid").toString()

        if(uid.isNotEmpty()) {
            readData(uid)
            setTotalRating(uid)
        }

        btnReviews.setOnClickListener {
            val intent = Intent(this, ShowReviews::class.java)
            intent.putExtra("docUid", uid)
            startActivity(intent)
        }




    }

    private fun readData(uid: String) {
        database = FirebaseDatabase.getInstance().getReference("Doctors")
        database.child(uid).get().addOnSuccessListener {
            if(it.exists()) {
                val name = it.child("name").value
                val title = it.child("title").value
                val bio = it.child("bio").value
                tvName.text = name.toString()
                tvBio.text = bio.toString()

                tvTitle.text = title.toString()

            }else {
                Toast.makeText(applicationContext, "User doesn't exists", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener{
            Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
        }
    }


    private fun setTotalRating(docUid: String) {
        var totStars = 0.0
        var totGivenStars = 0.0
        var noOfReviews = 0.0
        reviewDbRef =  FirebaseDatabase.getInstance().getReference("Reviews")
        reviewDbRef.addValueEventListener(object:ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot){
                if(snapshot.exists()) {
                    for(reviewSnapshot in snapshot.children) {
                        val review = reviewSnapshot.getValue(Review::class.java)
                        if(review!!.docUid == docUid) {
                            noOfReviews++
                            totStars += 5
                            totGivenStars += review.noOfStars!!.toInt()

                        }

                    }

                    val finalRating: Int = round((totGivenStars / (5*noOfReviews) )* 10).toInt()
                    tvRating.text = "$finalRating/10"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
}