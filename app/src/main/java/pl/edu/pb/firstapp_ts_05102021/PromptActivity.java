package pl.edu.pb.firstapp_ts_05102021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {
    public static final String KEY_EXTRA_ANSWER_SHOWN = "answerShown";

    private boolean correctAnswer;

    private Button showCorrectAnswer;
    private TextView correctAnswerField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        showCorrectAnswer = findViewById(R.id.showButton);
        correctAnswerField = findViewById(R.id.CorrectAnswerTextBox);

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);

        showCorrectAnswer.setOnClickListener((v) -> {
            int answer = correctAnswer ? R.string.true_button : R.string.false_button;
            correctAnswerField.setText(answer);
            setAnswerShownResult(true);
        });
    }
    private void setAnswerShownResult(boolean answerWasShow)
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShow);
        setResult(RESULT_OK, resultIntent);
    }



}