package ru.realityfamily.retrofitexample.Network;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.realityfamily.retrofitexample.Model.Employee;

public class Network {
    Retrofit retrofit;
    API api;

    public Network() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.137.1:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.api = retrofit.create(API.class);
    }

    public void getEmployees(Handler handler) {
        Call<List<Employee>> call = api.getAll();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                Message msg = new Message();
                msg.obj = response.body();
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void postEmployee(Employee employee, Handler handler) {
        api.postEmployee(employee).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                getEmployees(handler);
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void deleteEmployee(Employee employee, Handler handler) {
        api.deleteEmployee(employee.getId()).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                getEmployees(handler);
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void updateEmployee(Employee employee, Handler handler){
        api.updateEmployee(employee.getId(), employee).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                getEmployees(handler);
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
