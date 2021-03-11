package ru.realityfamily.retrofitexample.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.realityfamily.retrofitexample.Model.Employee;

public interface API {
    @GET("employees")
    Call<List<Employee>> getAll();

    @POST("employees")
    Call<Employee> postEmployee(@Body Employee employee);

    @DELETE("/employees/{id}")
    Call<Employee> deleteEmployee(@Path("id") Long id);

    @PUT("/employees/{id}")
    Call<Employee> updateEmployee(@Path("id") Long id, @Body Employee employee);
}
