package com.example.miniprogram;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> students;

    public StudentAdapter(List<Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView fullNameTextView;
        private TextView emailTextView;
        private TextView mobileNumberTextView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            fullNameTextView = itemView.findViewById(R.id.textViewFullName);
            emailTextView = itemView.findViewById(R.id.textViewEmail);
            mobileNumberTextView = itemView.findViewById(R.id.textViewMobileNumber);
        }

        public void bind(Student student) {
            fullNameTextView.setText(student.getFullName());
            emailTextView.setText(student.getEmail());
            mobileNumberTextView.setText(student.getMobileNumber());
        }
    }
}
