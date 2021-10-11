package pl.edu.pb.firstapp_ts_05102021;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {

    public UUID QuestionId;
    public int QuestionContent;
    public boolean QuestionTrue;

    public Question(UUID Id, int Content, boolean IsTrue)
    {
        QuestionId = Id;
        QuestionContent = Content;
        QuestionTrue = IsTrue;
    }

    public static Question Create(int Content, boolean IsTrue)
    {
        return new Question(UUID.randomUUID(), Content, IsTrue);
    }
}
