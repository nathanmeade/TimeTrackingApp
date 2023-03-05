package com.meadetechnologies.timetrackingapp

import com.meadetechnologies.timetrackingapp.data.model.Employee
import com.meadetechnologies.timetrackingapp.data.model.Shift
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object MyApiService {

    private const val BASE_URL = "http://192.168.2.235:3000/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    interface EmployeeApi {

        @GET("employees")
        fun getEmployees(): Call<List<Employee>>

        @POST("employees")
        fun createEmployee(@Body employee: Employee): Call<Employee>

        @PUT("employees/{id}")
        fun updateEmployee(@Path("id") id: Int, @Body employee: Employee): Call<Employee>

        @DELETE("employees/{id}")
        fun deleteEmployee(@Path("id") id: Int): Call<Void>
    }

    interface ShiftApi {

        @GET("shifts")
        fun getShifts(): Call<List<Shift>>

        @POST("shifts")
        fun createShift(@Body shift: Shift): Call<Void>

        @PUT("shifts/{id}")
        fun updateShift(@Path("id") id: Int, @Body shift: Shift): Call<Void>

        @DELETE("shifts/{id}")
        fun deleteShift(@Path("id") id: Int): Call<Void>
    }

    val employeeApi: EmployeeApi by lazy {
        retrofit.create(EmployeeApi::class.java)
    }

    val shiftApi: ShiftApi by lazy {
        retrofit.create(ShiftApi::class.java)
    }
}
