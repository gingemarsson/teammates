package teammates.test.cases.datatransfer;

import org.testng.annotations.Test;

import teammates.common.datatransfer.questions.FeedbackConstantSumQuestionDetails;
import teammates.common.datatransfer.attributes.FeedbackResponseAttributes;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class FeedbackConstantSumQuestionDetailsTest {

    @Test
    public void testValidateResponseAttributesEmptyResponses() {
        // Contract: Calling validateResponseAttributes with an empty list of FeedbackResponseAttributes
        // should return an empty list of no errors or responses.
        List<FeedbackResponseAttributes> responses = Collections.emptyList();
        int numRecipients = 0;
        FeedbackConstantSumQuestionDetails feedbackConstantSumQuestionDetails = new FeedbackConstantSumQuestionDetails();
        List<String> errors = feedbackConstantSumQuestionDetails.validateResponseAttributes(responses, numRecipients);
        assertTrue(errors.isEmpty());
    }
}
