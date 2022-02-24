package in.ac.lnmiit.management.Modules.Classes.DispensaryManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.ac.lnmiit.management.R;

public class AppointmentTimingAdapter extends RecyclerView.Adapter<AppointmentTimingAdapter.ViewHolder> {

    Context context;
    List<AppointmentTimingModel> list;

    public AppointmentTimingAdapter(Context context, List<AppointmentTimingModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_appointment_timing_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppointmentTimingModel model = list.get(position);
        holder.doctorType.setText(model.getDoctorName());
        holder.doctorTiming.setText(model.getDoctorTiming());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView doctorType;
        public TextView doctorTiming;
        public ViewHolder(View itemView) {
            super(itemView);
            this.doctorType = (TextView) itemView.findViewById(R.id.doctor_name);
            this.doctorTiming = (TextView) itemView.findViewById(R.id.doctor_timing);
        }
    }
}
