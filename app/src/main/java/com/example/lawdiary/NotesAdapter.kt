package com.example.lawdiary

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<Note>, context: Context): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titletv: TextView = itemView.findViewById(R.id.tvNoteTitle)
        val contenttv: TextView = itemView.findViewById(R.id.tvNoteContent)
        val updateiv: ImageView = itemView.findViewById(R.id.ivEdit)
        val deleteiv: ImageView = itemView.findViewById(R.id.ivDeleteNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titletv.text = note.title
        holder.contenttv.text = note.content

        holder.updateiv.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("idNote", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteiv.setOnClickListener {
            val context = holder.itemView.context

            // Create an AlertDialog builder
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete Note")
            builder.setMessage("Are you sure you want to delete this note?")

            // Set up the positive ("Yes") button
            builder.setPositiveButton("Yes") { dialog, which ->
                db.deleteNote(note.id)
                refreshData(db.getAllNotes())
                Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show()
            }

            // Set up the negative ("No") button
            builder.setNegativeButton("No") { dialog, which ->
                // Just dismiss the dialog
                dialog.dismiss()
            }

            // Create and show the dialog
            val dialog = builder.create()
            dialog.show()

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, android.R.color.black))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, android.R.color.black))
        }

    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun refreshData(newNotes: List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }
}