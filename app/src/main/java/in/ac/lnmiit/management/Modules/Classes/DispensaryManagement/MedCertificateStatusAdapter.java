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

public class MedCertificateStatusAdapter extends RecyclerView.Adapter<MedCertificateStatusAdapter.ViewHolder> {

    Context context;
    List<MedCertificateStatusModel> list;
    static boolean isStudent;

    public MedCertificateStatusAdapter(Context context, List<MedCertificateStatusModel> list, boolean isStudent) {
        this.context = context;
        this.list = list;
        this.isStudent = isStudent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.dispensary_medcertstatus_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MedCertificateStatusModel model = list.get(position);
        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.startDate.setText(model.getStartDate());
        holder.endDate.setText(model.getEndDate());
        holder.issue.setText(model.getIssue());
        holder.days.setText(model.getNoOfDays());
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
        public TextView name,email,issue,startDate,endDate,days;
        public CheckBox checkBox;
        public ImageButton deletebtn;
        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.dispensary_medcertstatus_name);
            this.email = (TextView) itemView.findViewById(R.id.dispensary_medcertstatus_email);
            this.issue = (TextView) itemView.findViewById(R.id.dispensary_medcertstatus_issue);
            this.startDate = (TextView) itemView.findViewById(R.id.dispensary_medcertstatus_startdate);
            this.endDate = (TextView) itemView.findViewById(R.id.dispensary_medcertstatus_enddate);
            this.days = (TextView) itemView.findViewById(R.id.dispensary_medcertstatus_days);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.dispensary_medcertstatus_checkbox);
            this.deletebtn = itemView.findViewById(R.id.dispensary_medcertstatus_deletebtn);

            if (MedCertificateStatusAdapter.isStudent){
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
