package AndroidApi.myapp;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {
    TextView tqKQ;
    FirebaseFirestore database;
    Context context = this;
    String strKQ = " ";
    ToDo toDo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tqKQ = findViewById(R.id.tqKQ);
        database = FirebaseFirestore.getInstance();
        insert();
//        update();
//        delete();
//        select();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    void insert(){
        String id = UUID.randomUUID().toString();
        toDo = new ToDo(id, "Title11", "Content11");
        database.collection("TODO").document(id).set(toDo.convertTohashMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "insert Thành Công", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "insert Thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void update(){
        String id = "35f5c3c4-0402-40b1-8a73-0243a6b328e7";
        toDo = new ToDo(id, "Title 11 update", "content11 update");
        database.collection("TODO").document(id).update(toDo.convertTohashMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "update thành công", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "update thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void delete(){
        String id = "";
        database.collection("TODO").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    ArrayList<ToDo> select(){
        ArrayList<ToDo>list = new ArrayList<>();
        database.collection("TODO").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    strKQ = "";
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        ToDo t = documentSnapshot.toObject(ToDo.class);
                        list.add(t);
                        strKQ += "id" + t.getId() + "\n";
                        strKQ += "title" + t.getTitle() + "\n";
                        strKQ += "content" + t.getContent() + "\n";
                    }
                }else {
                    Toast.makeText(context, "Select thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return list;
    }
}