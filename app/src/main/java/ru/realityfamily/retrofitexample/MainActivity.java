package ru.realityfamily.retrofitexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.realityfamily.retrofitexample.Adapters.EmployeeAdapter;
import ru.realityfamily.retrofitexample.Model.Employee;
import ru.realityfamily.retrofitexample.Network.Network;

public class MainActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText roleEditText;
    Button button;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefresh;

    Network network;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        roleEditText = findViewById(R.id.roleEditText);
        button = findViewById(R.id.addButton);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefresh = findViewById(R.id.swipeRefresh);

        Context context = this;
        network = new Network();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if (msg.obj != null && msg.obj instanceof List) {
                    ((EmployeeAdapter) recyclerView.getAdapter()).setEmployeeList((List<Employee>) msg.obj);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        };
        recyclerView.setAdapter(new EmployeeAdapter(context, new ArrayList<>()));

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    network.deleteEmployee(((EmployeeAdapter) recyclerView.getAdapter()).getEmployeeList().get(viewHolder.getAdapterPosition()), handler);

                    ((EmployeeAdapter) recyclerView.getAdapter()).getEmployeeList().remove(viewHolder.getAdapterPosition());
                    recyclerView.getAdapter().notifyDataSetChanged();
                } else if (direction == ItemTouchHelper.RIGHT) {
                    if (!nameEditText.getText().toString().isEmpty() && !roleEditText.getText().toString().isEmpty()) {
                        Employee employee = ((EmployeeAdapter) recyclerView.getAdapter()).getEmployeeList().get(viewHolder.getAdapterPosition());
                        employee.setName(nameEditText.getText().toString());
                        employee.setRole(roleEditText.getText().toString());

                        network.updateEmployee(
                                employee
                                , handler
                        );
                    }
                    cleanEditText();
                }
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameEditText.getText().toString().isEmpty() && !roleEditText.getText().toString().isEmpty()) {
                    network.postEmployee(
                            new Employee(
                                    nameEditText.getText().toString(),
                                    roleEditText.getText().toString()
                            ),
                            handler
                    );
                }
                cleanEditText();
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                network.getEmployees(handler);
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    void cleanEditText() {
        nameEditText.setText("");
        roleEditText.setText("");
    }
}