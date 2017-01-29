package com.example.alexandrup.ps_sharedprefferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;

public class GSON_Activ extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView txvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson_);

        txvDisplay = (TextView) findViewById(R.id.txvDisplay);
    }

    public void saveObjectType(View view) {


        Employee employee = getEmployeeObject();


        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //searialisation
        Gson gson = new Gson();
        String jsonString = gson.toJson(employee, Employee.class);
        Log.i(TAG, "save: " + jsonString);

        editor.putString("employee_key", jsonString);
        editor.apply();

    }

    public void loadObjectType(View view) {

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String jsonString = sharedPreferences.getString("employee_key", "n/a");

        Log.i(TAG, "load: " + jsonString);

        //deserialization
        Gson gson = new Gson();
        Employee employee = gson.fromJson(jsonString, Employee.class);

        displayText(employee);

    }



    public void saveGenericType(View view) {

        Employee employee = getEmployeeObject();
        Foo<Employee> foo = new Foo<>();
        foo.setObject(employee);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //searialisation
        Gson gson = new Gson();

        Type type = new TypeToken<Foo<Employee>>(){}.getType();

        String jsonString = gson.toJson(foo, type); //for generic type you cant just use Foo.class
        Log.i(TAG, "save: " + jsonString);

        editor.putString("foo_key", jsonString);
        editor.apply();

    }

    public void loadGenericType(View view) {

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String jsonString = sharedPreferences.getString("foo_key", "n/a");

        Log.i(TAG, "load: " + jsonString);

        //deserialization
        Gson gson = new Gson();

        Type type = new TypeToken<Foo<Employee>>(){}.getType();
        Foo<Employee> employeeFoo = gson.fromJson(jsonString, type);

        Employee employee = employeeFoo.getObject();

        displayText(employee);

    }

    private Employee getEmployeeObject() {
        Employee employee = new Employee();
        employee.setName("Alex P");
        employee.setProfession("Dev");
        employee.setProfId(287);
        employee.setRoles(Arrays.asList("Developer", "Admin"));

        return employee;
    }

    private void displayText(Employee employee) {

        if(employee == null){
            return;
        }

        String displayTxt = employee.getName()
                +'\n' + employee.getProfession()
                +'\n' + employee.getProfId()
                +'\n' + employee.getRoles().toString();

        txvDisplay.setText(displayTxt);
    }
}
