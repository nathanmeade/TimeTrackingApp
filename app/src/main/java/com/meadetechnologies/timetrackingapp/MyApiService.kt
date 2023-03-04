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
        fun getEmployees(): Call<List<EmployeeDTO>>

        @POST("employees")
        fun createEmployee(@Body employee: EmployeeDTO): Call<EmployeeDTO>

        @PUT("employees/{id}")
        fun updateEmployee(@Path("id") id: Long, @Body employee: EmployeeDTO): Call<EmployeeDTO>

        @DELETE("employees/{id}")
        fun deleteEmployee(@Path("id") id: Long): Call<Void>
    }

    interface ShiftApi {

        @GET("shifts")
        fun getShifts(): Call<List<Shift>>

        @POST("shifts")
        fun createShift(@Body shift: Shift): Call<Shift>

        @PUT("shifts/{id}")
        fun updateShift(@Path("id") id: Long, @Body shift: Shift): Call<Shift>

        @DELETE("shifts/{id}")
        fun deleteShift(@Path("id") id: Long): Call<Void>
    }

    val employeeApi: EmployeeApi by lazy {
        retrofit.create(EmployeeApi::class.java)
    }

    val shiftApi: ShiftApi by lazy {
        retrofit.create(ShiftApi::class.java)
    }
}
