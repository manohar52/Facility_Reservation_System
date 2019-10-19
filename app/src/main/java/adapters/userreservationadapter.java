package adapters;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facilityreservationsystem.R;

import java.util.List;

import model.Facility;
import model.Reservation;

public class userreservationadapter extends RecyclerView.Adapter<userreservationadapter.MyViewHolder> {

    private List<Reservation> resList;
    private static Context ct;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, type, tvdate,tvstime,tvetime;

        public MyViewHolder(View view) {
            super(view);
            ct = view.getContext();
            name = (TextView) view.findViewById(R.id.name);
            type = (TextView) view.findViewById(R.id.type);
            tvdate = (TextView) view.findViewById(R.id.tvdate);
            tvstime = (TextView) view.findViewById(R.id.tvstime);
            tvetime = (TextView) view.findViewById(R.id.tvetime);
        }
    }

    public userreservationadapter(List<Reservation> reservations){

        this.resList = reservations;
    }

    @NonNull
    @Override
    public userreservationadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userreservationitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull userreservationadapter.MyViewHolder holder, int position) {
        Reservation reservation = resList.get(position);
        holder.name.setText(reservation.getFacName());

        Facility f = Facility.getInstance(reservation.getFacName(),ct);
        holder.type.setText(f.getFtype().getFdesc());
        holder.tvdate.setText(reservation.getDate());
        holder.tvstime.setText(reservation.getStime());
        holder.tvetime.setText(reservation.getEtime());
    }

    @Override
    public int getItemCount() {
        return resList.size();
    }
}
