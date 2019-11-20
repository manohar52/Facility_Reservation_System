package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facilityreservationsystem.R;

import java.util.List;

import model.Facility;

public class fmfaclistadapter extends RecyclerView.Adapter<fmfaclistadapter.MyViewHolder> {

    private List<Facility> facilityList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, type, status;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tvname);
            type = (TextView) view.findViewById(R.id.tvtype);
            status = (TextView) view.findViewById(R.id.tvstatus);
        }
    }

    public fmfaclistadapter(List<Facility> facilities){
        this.facilityList = facilities;
    }

    @NonNull
    @Override
    public fmfaclistadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fmfacilityitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull fmfaclistadapter.MyViewHolder holder, int position) {
        Facility facility = facilityList.get(position);
        holder.name.setText(facility.getName());
        holder.type.setText(facility.getFtype().getFdesc());
        holder.status.setText(facility.getAvailabilityString());
    }

    @Override
    public int getItemCount() {
        return facilityList.size();
    }
}
