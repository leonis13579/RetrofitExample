package ru.realityfamily.retrofitexample.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.realityfamily.retrofitexample.Model.Employee;
import ru.realityfamily.retrofitexample.R;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    List<Employee> employeeList;
    LayoutInflater layoutInflater;

    public EmployeeAdapter(Context context, List<Employee> employeeList) {
        this.employeeList = employeeList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmployeeViewHolder(layoutInflater.inflate(R.layout.item_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.name.setText(employeeList.get(position).getName());
        holder.role.setText(employeeList.get(position).getRole());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView role;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            role = itemView.findViewById(R.id.role);
        }
    }
}
