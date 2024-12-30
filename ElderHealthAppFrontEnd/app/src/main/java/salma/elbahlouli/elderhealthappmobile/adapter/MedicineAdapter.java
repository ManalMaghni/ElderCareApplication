package salma.elbahlouli.elderhealthappmobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import salma.elbahlouli.elderhealthappmobile.R;
import salma.elbahlouli.elderhealthappmobile.model.Medicine;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> implements Filterable {

    private List<Medicine> medicines;
    private List<Medicine> medicinesFull;
    private List<Medicine> medicinesFiltered;
    private final OnDeleteListener onDelete;
    private final OnEditListener onEdit;

    public interface OnDeleteListener {
        void onDelete(int position);
    }

    public interface OnEditListener {
        void onEdit(Medicine medicine, int position);
    }

    public MedicineAdapter(List<Medicine> medicines, OnDeleteListener onDelete, OnEditListener onEdit) {
        this.medicines = new ArrayList<>(medicines);
        this.medicinesFull = new ArrayList<>(medicines);
        this.medicinesFiltered = new ArrayList<>(medicines);
        this.onDelete = onDelete;
        this.onEdit = onEdit;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicine, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine medicine = medicinesFiltered.get(position);
        holder.textMedName.setText(medicine.getNom());
        holder.textMedTime.setText(medicine.getHeure());

        holder.itemView.setOnLongClickListener(v -> {
            onDelete.onDelete(medicinesFiltered.indexOf(medicine));
            return true;
        });

        holder.itemView.setOnClickListener(v -> {
            onEdit.onEdit(medicine, medicinesFiltered.indexOf(medicine));
        });
    }

    @Override
    public int getItemCount() {
        return (medicinesFiltered != null) ? medicinesFiltered.size() : 0;
    }

    public void updateList(List<Medicine> newList) {
        if (newList == null) {
            newList = new ArrayList<>();
        }
        this.medicines = new ArrayList<>(newList);
        this.medicinesFull = new ArrayList<>(newList);
        this.medicinesFiltered = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        Medicine medicine = medicinesFiltered.get(position);
        medicinesFiltered.remove(position);
        medicines.remove(medicine);
        medicinesFull = new ArrayList<>(medicines);
        notifyItemRemoved(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = constraint != null ? constraint.toString().toLowerCase().trim() : "";
                List<Medicine> filteredList = searchString.isEmpty() ? medicinesFull
                        : filterMedicines(searchString);
                FilterResults results = new FilterResults();
                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            private List<Medicine> filterMedicines(String searchString) {
                List<Medicine> filtered = new ArrayList<>();
                for (Medicine medicine : medicinesFull) {
                    if (medicine.getNom().toLowerCase().contains(searchString)
                            || medicine.getHeure().toLowerCase().contains(searchString)) {
                        filtered.add(medicine);
                    }
                }
                return filtered;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                medicinesFiltered = (List<Medicine>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        TextView textMedName, textMedTime;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            textMedName = itemView.findViewById(R.id.textMedName);
            textMedTime = itemView.findViewById(R.id.textMedTime);
        }
    }
}
