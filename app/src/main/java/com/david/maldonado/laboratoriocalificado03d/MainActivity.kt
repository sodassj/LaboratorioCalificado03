package com.david.maldonado.laboratoriocalificado03d

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.david.maldonado.laboratoriocalificado03d.adapter.TeacherAdapter
import com.david.maldonado.laboratoriocalificado03d.databinding.ActivityMainBinding
import com.david.maldonado.laboratoriocalificado03d.model.Teacher

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val teacherList = mutableListOf<Teacher>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        fetchTeachers()
    }

    private fun fetchTeachers() {
        val url = "https://private-effe28-tecsup1.apiary-mock.com/list/teacher-b"
        val requestQueue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            val teachersArray = response.getJSONArray("teachers")
            for (i in 0 until teachersArray.length()) {
                val teacherJson = teachersArray.getJSONObject(i)
                teacherList.add(
                    Teacher(
                        name = teacherJson.getString("name"),
                        lastName = teacherJson.getString("last_name"),
                        phoneNumber = teacherJson.getString("phone_number"),
                        email = teacherJson.getString("email"),
                        imageUrl = teacherJson.getString("image_url")
                    )
                )
            }
            binding.recyclerView.adapter = TeacherAdapter(this, teacherList)
        }, {
            it.printStackTrace()
        })

        requestQueue.add(request)
    }
}
