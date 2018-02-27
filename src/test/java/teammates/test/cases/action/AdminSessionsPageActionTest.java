package teammates.test.cases.action;

import org.testng.annotations.Test;
import teammates.common.util.Const;
import teammates.ui.controller.AdminSessionsPageAction;

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

    @Test
    public void checkAllParametersTestNotNull(){
        //checkAllParmas("notNull") should return true when ALL values of
        // startDate,endDate,startHour,endHour,startMin,endMin and timeZone
        // are separated from null and the whitespace character("")

        //All values are separated from null and whitespace, should be true.
        AdminSessionsPageAction a = getAction("startdate", "notNull", "enddate", "notNull", "starthour", "notNull",
                "endhour", "notNull", "startminute", "notNull", "endminute", "notNull",
                "timezone", "notNull");

        //timezone == "" should be false
        AdminSessionsPageAction b = getAction("startdate", "notNull", "enddate", "notNull", "starthour", "notNull",
                "endhour", "notNull", "startminute", "notNull", "endminute", "notNull",
                "timezone", "");
        boolean c = a.checkAllParameters("notNull");
        boolean d = b.checkAllParameters("notNull");
        assertTrue(c);
        assertFalse(d);
    }

    public void checkAllParametersTestDefault(){
        //checkAllParams(String s) should be false when s is not equals to either
        //"null" or "notNull"
        String[] submissionParams = new String[] {};
        AdminSessionsPageAction a = getAction(submissionParams);
        boolean b = a.checkAllParameters("invalid condition");
        boolean c = a.checkAllParameters("123");

        assertFalse(b);
        assertFalse(c);
    }

}
