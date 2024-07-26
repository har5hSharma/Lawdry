package com.example.lawdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lawdiary.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)
        noteID = intent.getIntExtra("idNote", -1)
        if(noteID == -1){
            finish()
            return
        }

        val note = db.getNoteByID(noteID)

        binding.apply {
            etUpdateTittle.setText(note.title)
            etUpdateNote.setText(note.content)
        }
        binding.ivsaveData.setOnClickListener {
            val title = binding.etUpdateTittle.text.toString()
            val content = binding.etUpdateNote.text.toString()
            val note = Note(noteID, title, content)
            db.updateNote(note)
            finish()
            Toast.makeText(this, "Data updated", Toast.LENGTH_SHORT).show()
        }

    }
}