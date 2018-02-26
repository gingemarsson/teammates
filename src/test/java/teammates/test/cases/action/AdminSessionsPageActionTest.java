package teammates.test.cases.action;

import org.testng.annotations.Test;
import teammates.common.util.Const;
import teammates.ui.controller.AdminSessionsPageAction;
/*TestClass for AdminSessionsPageAction */
public class AdminSessionsPageActionTest extends BaseActionTest{
    @Override
    protected String getActionUri() {
        return Const.ActionURIs.ADMIN_SESSIONS_PAGE;
    }

    @Override
    protected AdminSessionsPageAction getAction(String... params) {
        return (AdminSessionsPageAction) gaeSimulation.getActionObject(getActionUri(), params);
    }

    @Override
    protected void testExecuteAndPostProcess() throws Exception {
        //Not used
    }

    @Override
    protected void testAccessControl() throws Exception {
        //Not used
    };





    @Test
    public void checkAllParametersTestNull(){
        // checkAllParams("null") should return true when all values of startDate,endDate,startHour,endHour,startMin,endMin and timeZone is null.
        String[] submissionParams = new String[] {};
        AdminSessionsPageAction a = getAction(submissionParams);

       boolean b = a.checkAllParameters("null");
       assertTrue(b);
    }
}
