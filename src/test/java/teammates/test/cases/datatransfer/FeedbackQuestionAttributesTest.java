package teammates.test.cases.datatransfer;

import static teammates.common.util.Const.EOL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.testng.annotations.Test;

import com.google.appengine.api.datastore.Text;

import teammates.common.datatransfer.DataBundle;
import teammates.common.datatransfer.FeedbackParticipantType;
import teammates.common.datatransfer.attributes.FeedbackQuestionAttributes;
import teammates.common.datatransfer.questions.FeedbackQuestionType;
import teammates.common.datatransfer.questions.FeedbackTextQuestionDetails;
import teammates.common.util.Const;
import teammates.common.util.FieldValidator;
import teammates.common.util.StringHelper;
import teammates.storage.entity.FeedbackQuestion;
import teammates.test.cases.BaseTestCase;

/**
 * SUT: {@link FeedbackQuestionAttributes}.
 */
public class FeedbackQuestionAttributesTest extends BaseTestCase {

    private DataBundle typicalBundle = getTypicalDataBundle();

    //First test for equals
    @Test
    public void testEqualsNull() throws Exception {
        // if a.equals(b)
        //If all parameters of a and b are null, return true.
        //If all parameters of a are null and b = null, return false.
        //If any parameter in a!= null and all parameters in b is null, return false
        FeedbackQuestionAttributes first = new FeedbackQuestionAttributes() ;
        FeedbackQuestionAttributes snd = new FeedbackQuestionAttributes() ;

            boolean  a = first.equals(null);
            boolean b = first.equals(snd);
            first.courseId = "1";
            boolean c = first.equals(snd);
            assertFalse(a);
            assertTrue(b);
            assertFalse(c);

         // attributes to be set after construction
        }


    private static class FeedbackQuestionAttributesWithModifiableTimestamp extends FeedbackQuestionAttributes {

        void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    @Test
    public void testDefaultTimestamp() {

        FeedbackQuestionAttributesWithModifiableTimestamp fq =
                new FeedbackQuestionAttributesWithModifiableTimestamp();

        fq.setCreatedAt(null);
        fq.setUpdatedAt(null);

        Date defaultTimeStamp = Const.TIME_REPRESENTS_DEFAULT_TIMESTAMP;

        ______TS("success : defaultTimeStamp for createdAt date");

        assertEquals(defaultTimeStamp, fq.getCreatedAt());

        ______TS("success : defaultTimeStamp for updatedAt date");

        assertEquals(defaultTimeStamp, fq.getUpdatedAt());
    }

    @Test
    public void testValidate() throws Exception {
        FeedbackQuestionAttributes fq = new FeedbackQuestionAttributes();

        fq.feedbackSessionName = "";
        fq.courseId = "";
        fq.creatorEmail = "";
        fq.questionType = FeedbackQuestionType.TEXT;
        fq.giverType = FeedbackParticipantType.NONE;
        fq.recipientType = FeedbackParticipantType.RECEIVER;

        fq.showGiverNameTo = new ArrayList<>();
        fq.showGiverNameTo.add(FeedbackParticipantType.SELF);
        fq.showGiverNameTo.add(FeedbackParticipantType.STUDENTS);

        fq.showRecipientNameTo = new ArrayList<>();
        fq.showRecipientNameTo.add(FeedbackParticipantType.SELF);
        fq.showRecipientNameTo.add(FeedbackParticipantType.STUDENTS);

        fq.showResponsesTo = new ArrayList<>();
        fq.showResponsesTo.add(FeedbackParticipantType.NONE);
        fq.showResponsesTo.add(FeedbackParticipantType.SELF);

        assertFalse(fq.isValid());

        String errorMessage = getPopulatedEmptyStringErrorMessage(
                                  FieldValidator.SIZE_CAPPED_NON_EMPTY_STRING_ERROR_MESSAGE_EMPTY_STRING,
                                  FieldValidator.FEEDBACK_SESSION_NAME_FIELD_NAME,
                                  FieldValidator.FEEDBACK_SESSION_NAME_MAX_LENGTH) + EOL
                              + getPopulatedEmptyStringErrorMessage(
                                    FieldValidator.COURSE_ID_ERROR_MESSAGE_EMPTY_STRING,
                                    FieldValidator.COURSE_ID_FIELD_NAME, FieldValidator.COURSE_ID_MAX_LENGTH) + EOL
                              + "Invalid creator's email: "
                              + getPopulatedEmptyStringErrorMessage(
                                    FieldValidator.EMAIL_ERROR_MESSAGE_EMPTY_STRING,
                                    FieldValidator.EMAIL_FIELD_NAME, FieldValidator.EMAIL_MAX_LENGTH) + EOL
                              + String.format(FieldValidator.PARTICIPANT_TYPE_ERROR_MESSAGE, fq.giverType.toString(),
                                              FieldValidator.GIVER_TYPE_NAME) + EOL
                              + String.format(FieldValidator.PARTICIPANT_TYPE_ERROR_MESSAGE, fq.recipientType.toString(),
                                              FieldValidator.RECIPIENT_TYPE_NAME) + EOL
                              + String.format(FieldValidator.PARTICIPANT_TYPE_ERROR_MESSAGE,
                                              fq.showGiverNameTo.get(0).toString(),
                                              FieldValidator.VIEWER_TYPE_NAME) + EOL
                              + "Trying to show giver name to STUDENTS without showing response first." + EOL
                              + String.format(FieldValidator.PARTICIPANT_TYPE_ERROR_MESSAGE,
                                              fq.showRecipientNameTo.get(0).toString(),
                                              FieldValidator.VIEWER_TYPE_NAME) + EOL
                              + "Trying to show recipient name to STUDENTS without showing response first." + EOL
                              + String.format(FieldValidator.PARTICIPANT_TYPE_ERROR_MESSAGE,
                                              fq.showResponsesTo.get(0).toString(),
                                              FieldValidator.VIEWER_TYPE_NAME) + EOL
                              + String.format(FieldValidator.PARTICIPANT_TYPE_ERROR_MESSAGE,
                                              fq.showResponsesTo.get(1).toString(),
                                              FieldValidator.VIEWER_TYPE_NAME);

        assertEquals(errorMessage, StringHelper.toString(fq.getInvalidityInfo()));

        fq.feedbackSessionName = "First Feedback Session";
        fq.courseId = "CS1101";
        fq.creatorEmail = "instructor1@course1.com";
        fq.giverType = FeedbackParticipantType.TEAMS;
        fq.recipientType = FeedbackParticipantType.OWN_TEAM;

        assertFalse(fq.isValid());

        errorMessage = String.format(FieldValidator.PARTICIPANT_TYPE_TEAM_ERROR_MESSAGE,
                                     fq.recipientType.toDisplayRecipientName(),
                                     fq.giverType.toDisplayGiverName()) + EOL
                       + String.format(FieldValidator.PARTICIPANT_TYPE_ERROR_MESSAGE, fq.showGiverNameTo.get(0).toString(),
                                       FieldValidator.VIEWER_TYPE_NAME) + EOL
                       + "Trying to show giver name to STUDENTS without showing response first." + EOL
                       + String.format(FieldValidator.PARTICIPANT_TYPE_ERROR_MESSAGE,
                                       fq.showRecipientNameTo.get(0).toString(),
                                       FieldValidator.VIEWER_TYPE_NAME) + EOL
                       + "Trying to show recipient name to STUDENTS without showing response first." + EOL
                       + String.format(FieldValidator.PARTICIPANT_TYPE_ERROR_MESSAGE, fq.showResponsesTo.get(0).toString(),
                                       FieldValidator.VIEWER_TYPE_NAME) + EOL
                       + String.format(FieldValidator.PARTICIPANT_TYPE_ERROR_MESSAGE, fq.showResponsesTo.get(1).toString(),
                                       FieldValidator.VIEWER_TYPE_NAME);

        assertEquals(errorMessage, StringHelper.toString(fq.getInvalidityInfo()));

        fq.recipientType = FeedbackParticipantType.OWN_TEAM_MEMBERS;

        assertFalse(fq.isValid());

        errorMessage = String.format(FieldValidator.PARTICIPANT_TYPE_TEAM_ERROR_MESSAGE,
                                     fq.recipientType.toDisplayRecipientName(),
                                     fq.giverType.toDisplayGiverName()) + EOL
                       + String.format(FieldValidator.PARTICIPANT_TYPE_ERROR_MESSAGE, fq.showGiverNameTo.get(0).toString(),
                                       FieldValidator.VIEWER_TYPE_NAME) + EOL
                       + "Trying to show giver name to STUDENTS without showing response first." + EOL
                       + String.format(FieldValidator.PARTICIPANT_TYPE_ERROR_MESSAGE,
                                       fq.showRecipientNameTo.get(0).toString(),
                                       FieldValidator.VIEWER_TYPE_NAME) + EOL
                       + "Trying to show recipient name to STUDENTS without showing response first." + EOL
                       + String.format(FieldValidator.PARTICIPANT_TYPE_ERROR_MESSAGE, fq.showResponsesTo.get(0).toString(),
                                       FieldValidator.VIEWER_TYPE_NAME) + EOL
                       + String.format(FieldValidator.PARTICIPANT_TYPE_ERROR_MESSAGE, fq.showResponsesTo.get(1).toString(),
                                       FieldValidator.VIEWER_TYPE_NAME);

        assertEquals(errorMessage, StringHelper.toString(fq.getInvalidityInfo()));

        fq.recipientType = FeedbackParticipantType.TEAMS;

        fq.showGiverNameTo = new ArrayList<>();
        fq.showGiverNameTo.add(FeedbackParticipantType.RECEIVER);

        fq.showRecipientNameTo = new ArrayList<>();
        fq.showRecipientNameTo.add(FeedbackParticipantType.RECEIVER);

        fq.showResponsesTo = new ArrayList<>();
        fq.showResponsesTo.add(FeedbackParticipantType.RECEIVER);

        assertTrue(fq.isValid());
    }

    @Test
    public void testGetQuestionDetails() {

        ______TS("Text question: new Json format");

        FeedbackQuestionAttributes fq = typicalBundle.feedbackQuestions.get("qn5InSession1InCourse1");
        FeedbackTextQuestionDetails questionDetails = new FeedbackTextQuestionDetails("New format text question");
        fq.setQuestionDetails(questionDetails);

        assertTrue(fq.isValid());
        assertEquals(fq.getQuestionDetails().getQuestionText(), "New format text question");

        ______TS("Text question: old string format");

        fq = typicalBundle.feedbackQuestions.get("qn2InSession1InCourse1");
        assertEquals(fq.getQuestionDetails().getQuestionText(), "Rate 1 other student's product");
    }

    @Test
    public void testRemoveIrrelevantVisibilityOptions() {

        ______TS("test teams->none");

        FeedbackQuestionAttributes question = new FeedbackQuestionAttributes();
        List<FeedbackParticipantType> participants = new ArrayList<>();

        question.feedbackSessionName = "test session";
        question.courseId = "some course";
        question.creatorEmail = "test@case.com";
        question.questionMetaData = new Text("test qn from teams->none.");
        question.questionNumber = 1;
        question.questionType = FeedbackQuestionType.TEXT;
        question.giverType = FeedbackParticipantType.TEAMS;
        question.recipientType = FeedbackParticipantType.NONE;
        question.numberOfEntitiesToGiveFeedbackTo = Const.MAX_POSSIBLE_RECIPIENTS;
        participants.add(FeedbackParticipantType.OWN_TEAM_MEMBERS);
        participants.add(FeedbackParticipantType.RECEIVER);
        participants.add(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS);
        question.showGiverNameTo = new ArrayList<>(participants);
        question.showRecipientNameTo = new ArrayList<>(participants);
        participants.add(FeedbackParticipantType.STUDENTS);
        question.showResponsesTo = new ArrayList<>(participants);

        question.removeIrrelevantVisibilityOptions();

        assertTrue(question.showGiverNameTo.isEmpty());
        assertTrue(question.showRecipientNameTo.isEmpty());
        // check that other types are not removed
        assertTrue(question.showResponsesTo.contains(FeedbackParticipantType.STUDENTS));
        assertEquals(question.showResponsesTo.size(), 1);

        ______TS("test students->teams");

        question.giverType = FeedbackParticipantType.STUDENTS;
        question.recipientType = FeedbackParticipantType.TEAMS;

        participants.clear();
        participants.add(FeedbackParticipantType.INSTRUCTORS);
        participants.add(FeedbackParticipantType.OWN_TEAM_MEMBERS);
        participants.add(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS);
        question.showGiverNameTo = new ArrayList<>(participants);
        participants.add(FeedbackParticipantType.STUDENTS);
        question.showRecipientNameTo = new ArrayList<>(participants);
        question.showResponsesTo = new ArrayList<>(participants);

        question.removeIrrelevantVisibilityOptions();

        assertEquals(question.showGiverNameTo.size(), 2);
        assertEquals(question.showRecipientNameTo.size(), 3);
        assertEquals(question.showResponsesTo.size(), 3);
        assertFalse(question.showGiverNameTo.contains(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS));
        assertFalse(question.showRecipientNameTo.contains(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS));
        assertFalse(question.showResponsesTo.contains(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS));

        ______TS("test students->team members including giver");

        question.giverType = FeedbackParticipantType.STUDENTS;
        question.recipientType = FeedbackParticipantType.OWN_TEAM_MEMBERS_INCLUDING_SELF;

        participants.clear();
        participants.add(FeedbackParticipantType.INSTRUCTORS);
        participants.add(FeedbackParticipantType.OWN_TEAM_MEMBERS);
        participants.add(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS);
        question.showGiverNameTo = new ArrayList<>(participants);
        participants.add(FeedbackParticipantType.STUDENTS);
        question.showRecipientNameTo = new ArrayList<>(participants);
        question.showResponsesTo = new ArrayList<>(participants);

        question.removeIrrelevantVisibilityOptions();

        assertEquals(question.showGiverNameTo.size(), 3);
        assertEquals(question.showRecipientNameTo.size(), 4);
        assertEquals(question.showResponsesTo.size(), 4);
        assertFalse(question.showGiverNameTo.contains(FeedbackParticipantType.OWN_TEAM_MEMBERS_INCLUDING_SELF));
        assertFalse(question.showRecipientNameTo.contains(FeedbackParticipantType.OWN_TEAM_MEMBERS_INCLUDING_SELF));
        assertFalse(question.showResponsesTo.contains(FeedbackParticipantType.OWN_TEAM_MEMBERS_INCLUDING_SELF));

        ______TS("test students->instructors");

        question.giverType = FeedbackParticipantType.STUDENTS;
        question.recipientType = FeedbackParticipantType.INSTRUCTORS;

        participants.clear();
        participants.add(FeedbackParticipantType.RECEIVER);
        participants.add(FeedbackParticipantType.INSTRUCTORS);
        participants.add(FeedbackParticipantType.OWN_TEAM_MEMBERS);
        participants.add(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS);
        participants.add(FeedbackParticipantType.STUDENTS);
        question.showGiverNameTo = new ArrayList<>(participants);
        question.showRecipientNameTo = new ArrayList<>(participants);
        question.showResponsesTo = new ArrayList<>(participants);

        question.removeIrrelevantVisibilityOptions();

        assertEquals(question.showGiverNameTo.size(), 4);
        assertEquals(question.showRecipientNameTo.size(), 4);
        assertEquals(question.showResponsesTo.size(), 4);
        assertFalse(question.showGiverNameTo.contains(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS));
        assertFalse(question.showRecipientNameTo.contains(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS));
        assertFalse(question.showResponsesTo.contains(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS));

        ______TS("test students->own team");

        question.giverType = FeedbackParticipantType.STUDENTS;
        question.recipientType = FeedbackParticipantType.OWN_TEAM;

        participants.clear();
        participants.add(FeedbackParticipantType.RECEIVER);
        participants.add(FeedbackParticipantType.INSTRUCTORS);
        participants.add(FeedbackParticipantType.OWN_TEAM_MEMBERS);
        participants.add(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS);
        participants.add(FeedbackParticipantType.STUDENTS);
        question.showGiverNameTo = new ArrayList<>(participants);
        question.showRecipientNameTo = new ArrayList<>(participants);
        question.showResponsesTo = new ArrayList<>(participants);

        question.removeIrrelevantVisibilityOptions();

        assertEquals(question.showGiverNameTo.size(), 4);
        assertEquals(question.showRecipientNameTo.size(), 4);
        assertEquals(question.showResponsesTo.size(), 4);
        assertFalse(question.showGiverNameTo.contains(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS));
        assertFalse(question.showRecipientNameTo.contains(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS));
        assertFalse(question.showResponsesTo.contains(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS));

        ______TS("test students->own team members");

        question.giverType = FeedbackParticipantType.STUDENTS;
        question.recipientType = FeedbackParticipantType.OWN_TEAM_MEMBERS;

        participants.clear();
        participants.add(FeedbackParticipantType.RECEIVER);
        participants.add(FeedbackParticipantType.INSTRUCTORS);
        participants.add(FeedbackParticipantType.OWN_TEAM_MEMBERS);
        participants.add(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS);
        participants.add(FeedbackParticipantType.STUDENTS);
        question.showGiverNameTo = new ArrayList<>(participants);
        question.showRecipientNameTo = new ArrayList<>(participants);
        question.showResponsesTo = new ArrayList<>(participants);

        question.removeIrrelevantVisibilityOptions();

        assertEquals(question.showGiverNameTo.size(), 4);
        assertEquals(question.showRecipientNameTo.size(), 4);
        assertEquals(question.showResponsesTo.size(), 4);
        assertFalse(question.showGiverNameTo.contains(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS));
        assertFalse(question.showRecipientNameTo.contains(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS));
        assertFalse(question.showResponsesTo.contains(FeedbackParticipantType.RECEIVER_TEAM_MEMBERS));
    }

    @Test
    public void testEqualsShowResponsesTo() {
        // If showResponsesTo is the same for both objects, equals should return true (when all other attributes are
        // null). If showResponsesTo is different for the two objects, equals should return false (when all other
        // attributes are null).
        FeedbackQuestionAttributes first = new FeedbackQuestionAttributes();
        FeedbackQuestionAttributes snd = new FeedbackQuestionAttributes();


        List<FeedbackParticipantType> instructors = Arrays.asList(FeedbackParticipantType.INSTRUCTORS);
        List<FeedbackParticipantType> students = Arrays.asList(FeedbackParticipantType.STUDENTS);

        first.showResponsesTo = instructors;
        snd.showResponsesTo = students;

        boolean a = first.equals(snd);
        // first.showResponsesTo is not equal to snd.showResponsesTo
        assertFalse(a);

        snd.showResponsesTo = instructors;
        boolean b = first.equals(snd);
        // first.showResponsesTo is equal to snd.showResponsesTo
        assertTrue(b);
    }

    @Test
    public void testEqualsShowRecipientNameTo() {
        // If showRecipientNameTo is the same for both objects, equals should return true (when all other attributes are
        // null). If showRecipientNameTo is different for the two objects, equals should return false (when all other
        // attributes are null).
        FeedbackQuestionAttributes first = new FeedbackQuestionAttributes();
        FeedbackQuestionAttributes snd = new FeedbackQuestionAttributes();

        List<FeedbackParticipantType> instructors = Arrays.asList(FeedbackParticipantType.INSTRUCTORS);
        List<FeedbackParticipantType> students = Arrays.asList(FeedbackParticipantType.STUDENTS);

        first.showRecipientNameTo = instructors;
        snd.showRecipientNameTo = students;

        boolean a = first.equals(snd);
        // first.showRecipientNameTo is not equal to snd.showRecipientNameTo
        assertFalse(a);

        snd.showRecipientNameTo = instructors;
        boolean b = first.equals(snd);
        // first.showRecipientNameTo is equal to snd.showRecipientNameTo
        assertTrue(b);
    }

    @Test
    public void testEqualsShowGiverNameTo() {
        // If showGiverNameTo is the same for both objects, equals should return true (when all other attributes are
        // null). If showGiverNameTo is different for the two objects, equals should return false (when all other
        // attributes are null).

        FeedbackQuestionAttributes first = new FeedbackQuestionAttributes();
        FeedbackQuestionAttributes snd = new FeedbackQuestionAttributes();

        List<FeedbackParticipantType> instructors = Arrays.asList(FeedbackParticipantType.INSTRUCTORS);
        List<FeedbackParticipantType> students = Arrays.asList(FeedbackParticipantType.STUDENTS);

        first.showGiverNameTo = instructors;
        snd.showGiverNameTo = students;

        boolean a = first.equals(snd);
        // first.showGiverNameTo is not equal to snd.showGiverNameTo
        assertFalse(a);

        snd.showGiverNameTo = instructors;
        boolean b = first.equals(snd);
        // first.showGiverNameTo is equal to snd.showGiverNameTo
        assertTrue(b);
    }

    @Test
    public void testEqualsCourseId() {
        // If courseId is the same for both objects, equals should return true (when all other attributes are
        // null). If courseId is different for the two objects, equals should return false (when all other
        // attributes are null).

        FeedbackQuestionAttributes first = new FeedbackQuestionAttributes();
        FeedbackQuestionAttributes snd = new FeedbackQuestionAttributes();

        first.courseId = "DemoCourseID1";
        snd.courseId = "DemoCourseID2";

        // first.courseId is not equal to snd.courseId
        boolean a = first.equals(snd);

        snd.courseId = "DemoCourseID1";
        // first.courseId is equal to snd.courseId

        boolean b = first.equals(snd);
        assertFalse(a);
        assertTrue(b);
    }

    @Test
    public void testEqualsCreatorEmail() {
        // If creatorEmail is the same for both objects, equals should return true (when all other attributes are
        // null). If creatorEmail is different for the two objects, equals should return false (when all other
        // attributes are null).
        FeedbackQuestionAttributes first = new FeedbackQuestionAttributes();
        FeedbackQuestionAttributes snd = new FeedbackQuestionAttributes();

        first.creatorEmail = "DemoCreatorEmail1";
        snd.creatorEmail = "DemoCreatorEmail2";

        // first.creatorEmail is not equal to snd.creatorEmail
        boolean a = first.equals(snd);

        snd.creatorEmail = "DemoCreatorEmail1";
        // first.creatorEmail is equal to snd.creatorEmail

        boolean b = first.equals(snd);
        assertFalse(a);
        assertTrue(b);
    }
}
