package com.example.codemasterapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.codemasterapp.Quiz.QuizPage
import com.example.codemasterapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(QuizPage())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.QuizTime -> replaceFragment(QuizPage())
                R.id.Temp1 -> replaceFragment(Temp1())
                R.id.Courses -> replaceFragment(CoursesPageFragment())

                else ->{



                }

            }

            true

        }


    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()


    }
}