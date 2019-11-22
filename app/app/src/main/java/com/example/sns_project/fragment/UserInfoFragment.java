package com.example.sns_project.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sns_project.PostInfo;
import com.example.sns_project.R;
import com.example.sns_project.UserInfo;
import com.example.sns_project.activity.MainActivity;
import com.example.sns_project.activity.MemberInitActivity;
import com.example.sns_project.activity.WritePostActivity;
import com.example.sns_project.adapter.UserListAdapter;
import com.example.sns_project.listener.OnPostListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserInfoFragment extends Fragment {
    private static final String TAG = "UserInfoFragment";

    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        final View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        final ImageView profileImageView = view.findViewById(R.id.profileImageView);
        final TextView nameTextView = view.findViewById(R.id.nameTextView);
        final TextView phoneNumberTextView = view.findViewById(R.id.phoneNumberTextView);
        final TextView ageTextView = view.findViewById(R.id.ageTextView);
        final TextView addressTextView = view.findViewById(R.id.addressTextView);


        final Button updateBtn=(Button)view.findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Activity root = getActivity(); //이 클래스가 프레그먼트이기 때문에 액티비티 정보를 얻는다.
                //Toast.makeText(root, "토스트 사용!", Toast.LENGTH_SHORT).show();

                Snackbar.make(v, "수정 하시겠습니까?", 10000).setAction("YES", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //파이어 베이스 로그아웃 함수
                        //현재 프래그먼트에서 getActivity로 현재 엑티비티 가져오고 새로운 창 띄워주기
                        Intent intent = new Intent(getActivity(), MemberInitActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //엑티비티 시작
                        startActivity(intent);
                        //현재 액티비티 종료
                    }
                }).show();
            }
        });


        //로그아웃 버튼 누르면!
        final Button logoutBtn=(Button) view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Activity root = getActivity(); //이 클래스가 프레그먼트이기 때문에 액티비티 정보를 얻는다.
                //Toast.makeText(root, "토스트 사용!", Toast.LENGTH_SHORT).show();

                Snackbar.make(v, "로그아웃 하시겠습니까?", 10000).setAction("YES", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //파이어 베이스 로그아웃 함수
                        mAuth.signOut();
                        //현재 프래그먼트에서 getActivity로 현재 엑티비티 가져오고 새로운 창 띄워주기
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //엑티비티 시작
                        startActivity(intent);
                        //현재 액티비티 종료
                        getActivity().finish();
                    }
                }).show();
            }
        });

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            if(document.getData().get("photoUrl") != null){
                                Glide.with(getActivity()).load(document.getData().get("photoUrl")).centerCrop().override(500).into(profileImageView);
                            }
                            nameTextView.setText(document.getData().get("name").toString());
                            phoneNumberTextView.setText(document.getData().get("phoneNumber").toString());
                            ageTextView.setText(document.getData().get("age").toString());
                            addressTextView.setText(document.getData().get("gender").toString());
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

}
