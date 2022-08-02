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


public class LoginFragment extends Fragment {
    private Button btnLogin, btnReg;
    private EditText etEmail, etPassword;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnLogin = view.findViewById(R.id.btnLogin);
        btnReg = view.findViewById(R.id.btnReg);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithFirebase(view);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
    }

    private void signInWithFirebase(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill all fields!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getActivity(), "Password length should be at least 6 chars", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // nav to home
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                } else {
                    // show error toast
                    Toast.makeText(getActivity(), "Wrong email or password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}