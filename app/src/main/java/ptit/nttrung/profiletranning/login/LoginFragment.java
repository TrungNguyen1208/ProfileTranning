package ptit.nttrung.profiletranning.login;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ptit.nttrung.profiletranning.R;
import ptit.nttrung.profiletranning.createaccount.CreateAccountActivity;
import ptit.nttrung.profiletranning.data.auth.AuthInjection;
import ptit.nttrung.profiletranning.data.scheduler.SchedulerInjection;
import ptit.nttrung.profiletranning.profilepage.ProfilePageActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginContract.View {

    @BindView(R.id.tv_login_email_sub)
    TextView tvEmail;
    @BindView(R.id.edt_login_email)
    EditText edtEmail;
    @BindView(R.id.tv_login_pass_sub)
    TextView tvPass;
    @BindView(R.id.edt_login_password_sub)
    EditText edtPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_create_acc)
    Button btnCreateAcc;
    @BindView(R.id.pro_login_loading)
    ProgressBar proLoginLoading;
    @BindView(R.id.cont_login_content)
    View contentContainer;

    private LoginContract.Presenter presenter;

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,view);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLogInClick();
            }
        });

        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCreateClick();
            }
        });

        setUpListeners();
        edtPassword.requestFocus();

        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if (presenter == null) {
            presenter = new LoginPresenter(
                    AuthInjection.provideAuthSource(),
                    this,
                    SchedulerInjection.provideSchedulerProvider());

            presenter.subscribe();
        }
    }

    @Override
    public void makeToast(@StringRes int stringId) {
        Toast.makeText(getActivity().getApplicationContext(), getString(stringId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getEmail() {
        return edtEmail.getText().toString();
    }

    @Override
    public String getPassword() {
        return edtPassword.getText().toString();
    }

    @Override
    public void startProfileActivity() {
        presenter.unsubscribe();
        Intent i = new Intent(getActivity(), ProfilePageActivity.class);
        startActivity(i);
    }

    @Override
    public void startCreateAccountActivity() {
        presenter.unsubscribe();
        Intent i = new Intent(getActivity(), CreateAccountActivity.class);
        startActivity(i);
    }

    @Override
    public void showProgressIndicator(boolean show) {
        if (show){
            proLoginLoading.setVisibility(View.VISIBLE);
            proLoginLoading.setVisibility(View.INVISIBLE);
        } else {
            proLoginLoading.setVisibility(View.INVISIBLE);
            contentContainer.setVisibility(View.VISIBLE);
        }
    }
    /**
     * Sets Listeners to manage highlight colors for Inputs/Labels, based on hasFocus
     */
    public void setUpListeners() {
        edtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View label, boolean hasFocus) {
                if (hasFocus) {
                    tvEmail.setTextColor(
                            ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorAccent)
                    );
                } else {
                    tvEmail.setTextColor(
                            ContextCompat.getColor(getActivity().getApplicationContext(), android.R.color.white)
                    );
                }
            }
        });

        edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View label, boolean hasFocus) {
                if (hasFocus) {
                    tvPass.setTextColor(
                            ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorAccent)
                    );
                } else {
                    tvPass.setTextColor(
                            ContextCompat.getColor(getActivity().getApplicationContext(), android.R.color.white)
                    );
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }
}
