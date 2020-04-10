package in.irotech.sqlitestudentdata;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextID,editTextName,editTextEmail,editTextCC;
    private Button buttonAdd,buttonDelete,buttonView,buttonShowAll,buttonUpdate;

    SqliteHelper sqliteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqliteHelper=new SqliteHelper(this);

        editTextID=findViewById(R.id.editText_id);
        editTextName=findViewById(R.id.editText_name);
        editTextEmail=findViewById(R.id.editText_email);
        editTextCC=findViewById(R.id.editText_CC);

        buttonAdd=findViewById(R.id.button_add);
        buttonDelete=findViewById(R.id.button_delete);
        buttonView=findViewById(R.id.button_view);
        buttonShowAll=findViewById(R.id.button_viewAll);
        buttonUpdate=findViewById(R.id.button_update);
        addBtn();
        deleteBtn();
        showSingle();
        viewAll();
        updateData();
    }

    public void addBtn(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean done=sqliteHelper.insert(editTextName.getText().toString(),editTextEmail.getText().toString(),editTextCC.getText().toString());
                if(done){
                    ToastyMsg(getApplicationContext(),"Inserted!");
                }else{
                    ToastyMsg(getApplicationContext(),"Failed!");
                }
                setAllBlank();
            }
        });
    }

    public void deleteBtn(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Integer done=sqliteHelper.delete(editTextID.getText().toString());
            if(done>0) {
                ToastyMsg(getApplicationContext(), "Deleted!");
            }else{
                ToastyMsg(getApplicationContext(), "Error");
            }
            setAllBlank();
            }
        });
    }

    public void showSingle(){
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=editTextID.getText().toString();
                Cursor cursor=sqliteHelper.getValue(id);
//                ToastyMsg(getApplicationContext(),"hy");
                if(id.equals(String.valueOf(""))){
                    editTextID.setError("Enter Id");
                }else {
                    String str = null;
                    if (cursor.moveToNext()) {
                        str = "Id: " + cursor.getString(0) + "\nName: " + cursor.getString(1) + "\nEmail: " + cursor.getString(2) + "\nCourse Count: " + cursor.getString(3);
                    }
                    message("Data", str);
                }
                setAllBlank();
            }
        });
    }

    public void viewAll(){
        buttonShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor=sqliteHelper.getComplete();
                StringBuffer buffer=new StringBuffer();
                while (cursor.moveToNext()){
                    buffer.append("Id: "+cursor.getString(0));
                    buffer.append("\nName: "+cursor.getString(1));
                    buffer.append("\nEmail: "+cursor.getString(2));
                    buffer.append("\nCourse Count: "+cursor.getString(3)+"\n\n");
                }
                message("All Data",new String(buffer));
            }
        });
    }

    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean done=sqliteHelper.update(editTextID.getText().toString(),editTextName.getText().toString(),editTextEmail.getText().toString(),editTextCC.getText().toString());
                if(done){
                    ToastyMsg(getApplicationContext(),"Updated!");
                }else{
                    ToastyMsg(getApplicationContext(),"Error");
                }
            }
        });
    }
    public void message(String title,String content){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.create();
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setCancelable(true);
        builder.show();
//        System.out.println("hy there:"+content);
    }
    public void ToastyMsg(Context context, String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    private void setAllBlank(){
        editTextID.setText(null);
        editTextName.setText(null);
        editTextEmail.setText(null);
        editTextCC.setText(null);
    }
}
