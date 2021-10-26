package pl.edu.pb.firstapp_ts_05102021;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    private static final String QUIZ_TAG = "MainActivity";

    private TextView HelloBox;
    private TextView questionTextBox;

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;

    public Question[] questions = GetQuestions().toArray(new Question[0]);
    public int CurrentQuestionPoint = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG, "onCreate Method was invoked");
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        nextButton = findViewById(R.id.nextButton);
        questionTextBox = findViewById(R.id.questionText001);

        questions = GetQuestions().toArray(new Question[0]);
        CurrentQuestionPoint = 0;

        trueButton.setBackgroundColor(getResources().getColor(R.color.Green_correct));

        trueButton.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View view) {
                CheckAnswer(questions[CurrentQuestionPoint], true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View view) {
                CheckAnswer(questions[CurrentQuestionPoint], false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentQuestionPoint = (CurrentQuestionPoint +1)%questions.length;
                SetNextQuestionContent();
            }
        });
        SetNextQuestionContent();

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(QUIZ_TAG, "onStart Method was invoked");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(QUIZ_TAG, "onResume Method was invoked");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(QUIZ_TAG, "onPause Method was invoked");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(QUIZ_TAG, "onStop Method was invoked");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(QUIZ_TAG, "onDestroy Method was invoked");
    }

    private void SetNextQuestionContent()
    {
        questionTextBox.setText(questions[CurrentQuestionPoint].QuestionContent);
        LinearLayout layout = (LinearLayout) findViewById(R.id.LinearBackground);
        layout.setBackgroundColor(getResources().getColor(R.color.background_idle));

        trueButton.setEnabled(true);
        falseButton.setEnabled(true);
        nextButton.setEnabled(false);
    }


    private void CheckAnswer(Question question ,boolean userAnswer) throws InterruptedException {
        int result = 0;
        String colorString;
        if(userAnswer == question.QuestionTrue)
        {
            result = R.string.correct_answer;
            LinearLayout layout = (LinearLayout) findViewById(R.id.LinearBackground);
            layout.setBackgroundColor(getResources().getColor(R.color.Green_correct));
        }
        else
        {
            result = R.string.wrong_answer;
            LinearLayout layout = (LinearLayout) findViewById(R.id.LinearBackground);
            layout.setBackgroundColor(getResources().getColor(R.color.Red_incorrect));
        }
        Toast toast = Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT);
        toast.show();

        trueButton.setEnabled(false);
        falseButton.setEnabled(false);

        nextButton.setEnabled(true);
    }

    private List<Question> GetQuestions()
    {
        List<Question> questionList = new ArrayList<Question>();

        questionList.add(Question.Create(R.string.question_001, true));
        questionList.add(Question.Create(R.string.question_002, true));
        questionList.add(Question.Create(R.string.question_003, true));
        questionList.add(Question.Create(R.string.question_004, false));
        questionList.add(Question.Create(R.string.question_005, true));
        questionList.add(Question.Create(R.string.question_006, false));
        questionList.add(Question.Create(R.string.question_007, true));
        questionList.add(Question.Create(R.string.question_008, true));
        questionList.add(Question.Create(R.string.question_009, true));

        return questionList;
    }

}
