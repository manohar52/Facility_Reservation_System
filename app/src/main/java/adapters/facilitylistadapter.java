package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facilityreservationsystem.R;

import org.w3c.dom.Text;

import java.util.List;

import model.Facility;

public class facilitylistadapter extends RecyclerView.Adapter<facilitylistadapter.MyViewHolder> {

    private List<Facility> facilityList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, type, deposit;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            type = (TextView) view.findViewById(R.id.type);
            deposit = (TextView) view.findViewById(R.id.deposit);
        }
    }

    public facilitylistadapter(List<Facility> facilities){
        this.facilityList = facilities;
    }

    @NonNull
    @Override
    public facilitylistadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.availablefacilityitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull facilitylistadapter.MyViewHolder holder, int position) {
        Facility facility = facilityList.get(position);
        holder.name.setText(facility.getName());
        holder.type.setText(facility.getFdesc());
        holder.deposit.setText(Integer.toString(facility.getDeposit()));
    }

    @Override
    public int getItemCount() {
        return facilityList.size();
    }
}
