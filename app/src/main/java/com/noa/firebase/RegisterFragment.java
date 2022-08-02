package com.noa.firebase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {
    private Button btnReg;
    private EditText etName, etEmail, etPassword, etPhone;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnReg = view.findViewById(R.id.btnReg);
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etPhone = view.findViewById(R.id.etPhone);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpWithFirebase(view);
            }
        });
    }

    private void signUpWithFirebase(View view) {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String phone = etPhone.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(getActivity(), "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // pop back to register
                    Navigation.findNavController(view).popBackStack();
                    //Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
                } else {
                    // show error toast
                    Toast.makeText(getActivity(), "Can't create account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}