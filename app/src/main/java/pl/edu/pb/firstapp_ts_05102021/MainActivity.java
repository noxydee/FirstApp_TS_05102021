package pl.edu.pb.firstapp_ts_05102021;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.PersistableBundle;
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
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quiz.correctAnswer";
    private static final int REQUEST_CODE_PROMPT = 0;
    private boolean answerWasShown;


    private TextView HelloBox;
    private TextView questionTextBox;

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button promptButton;


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
        promptButton = findViewById(R.id.button2);

        questions = GetQuestions().toArray(new Question[0]);
        CurrentQuestionPoint = 0;

        trueButton.setBackgroundColor(getResources().getColor(R.color.Green_correct));
        falseButton.setBackgroundColor(getResources().getColor(R.color.Red_incorrect));

        if(savedInstanceState != null)
        {
            CurrentQuestionPoint = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

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

        /*nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentQuestionPoint = (CurrentQuestionPoint +1)%questions.length;
                answerWasShown = false;
                SetNextQuestionContent();
            }

        });*/

        nextButton.setOnClickListener((v) -> {
            answerWasShown = false;
            setNextQuestion();
        });

        promptButton.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);

            boolean correctAnswer = questions[CurrentQuestionPoint].isQuestionTrue();

            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);
        });

        SetNextQuestionContent();
    }

    private void setNextQuestion() {
        CurrentQuestionPoint = (CurrentQuestionPoint +1)%questions.length;
        SetNextQuestionContent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) return;
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG, "onSaveInstanceState was invoked");
        outState.putInt(KEY_CURRENT_INDEX, CurrentQuestionPoint);
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
        if(answerWasShown)
        {
            result = R.string.answer_was_shown;
        }
        else {
            if (userAnswer == question.QuestionTrue) {
                result = R.string.correct_answer;
                LinearLayout layout = (LinearLayout) findViewById(R.id.LinearBackground);
                layout.setBackgroundColor(getResources().getColor(R.color.Green_correct));
            } else {
                result = R.string.wrong_answer;
                LinearLayout layout = (LinearLayout) findViewById(R.id.LinearBackground);
                layout.setBackgroundColor(getResources().getColor(R.color.Red_incorrect));
            }
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
