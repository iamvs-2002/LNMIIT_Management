package in.ac.lnmiit.management.Modules.Classes.DispensaryManagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.ac.lnmiit.management.R;

public class AppointmentStatusAdapter extends RecyclerView.Adapter<AppointmentStatusAdapter.ViewHolder> {

    Context context;
    List<AppointmentStatusModel> list;
    static boolean isStudent;

    public AppointmentStatusAdapter(Context context, List<AppointmentStatusModel> list, boolean isStudent) {
        this.context = context;
        this.list = list;
        this.isStudent = isStudent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.dispensary_appointmentstatus_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AppointmentStatusModel model = list.get(position);
        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
        holder.issue.setText(model.getIssue());
        holder.checkBox.setChecked(model.isApproved());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                model.setApproved(b);
            }
        });
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Item deleted at position: "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,email,issue,date,time;
        public CheckBox checkBox;
        public ImageButton deletebtn;
        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.dispensary_appointmentstatus_name);
            this.email = (TextView) itemView.findViewById(R.id.dispensary_appointmentstatus_email);
            this.issue = (TextView) itemView.findViewById(R.id.dispensary_appointmentstatus_issue);
            this.date = (TextView) itemView.findViewById(R.id.dispensary_appointmentstatus_date);
            this.time = (TextView) itemView.findViewById(R.id.dispensary_appointmentstatus_time);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.dispensary_appointmentstatus_checkbox);
            this.deletebtn = itemView.findViewById(R.id.dispensary_appointmentstatus_deletebtn);

            if (AppointmentStatusAdapter.isStudent){
                checkBox.setEnabled(false);
                checkBox.setClickable(false);
                checkBox.setFocusable(false);
                deletebtn.setVisibility(View.VISIBLE);
            }
            else{
                checkBox.setEnabled(true);
                checkBox.setClickable(true);
                checkBox.setFocusable(true);
                deletebtn.setVisibility(View.GONE);
            }
        }
    }
}
