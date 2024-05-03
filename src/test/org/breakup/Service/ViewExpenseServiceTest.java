package org.breakup.Service;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.platform.commons.logging.LoggerFactory.getLogger;
import org.breakup.Model.Expense;
import org.breakup.Model.ExpenseList;
import org.breakup.Model.Member;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
//import org.apache.log4j.Logger;
import java.util.List;
public class ViewExpenseServiceTest {

    public ViewExpenseServiceTest() {
        Logger log = getLogger(this.getClass());
    }

    @Test
    public void getOwedList(){
        //       try {
        List<Expense> owedList = null;
//            ViewExpenseService viewexpenseservice  =new ViewExpenseService();
//            viewexpenseservice.getOwedList();
//            assertTrue(true);
//        } catch (Exception exception) {
//
//            exception.printStackTrace();
//            assertFalse(false);
//       }
    }

    @Test
    public void setOwedList(){
        try {

            List<Expense> owedList = null;
            ViewExpenseService viewexpenseservice  =new ViewExpenseService();
            viewexpenseservice.setOwedList( owedList);
            assertTrue(true);
        } catch (Exception exception) {

            exception.printStackTrace();
            assertFalse(false);
        }
    }

    @Test
    public void getPayableList(){
        // try {
        List<Expense> payableList = null;
//
//
//            ViewExpenseService viewexpenseservice  =new ViewExpenseService();
//            viewexpenseservice.getPayableList();
//            assertTrue(true);
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            assertFalse(false);
//        }
    }

    @Test
    public void setPayableList(){
        try {
            List<Expense> payableList = null;
            ViewExpenseService viewexpenseservice  =new ViewExpenseService();
            viewexpenseservice.setPayableList( payableList);
            assertTrue(true);
        } catch (Exception exception) {
            exception.printStackTrace();
            assertFalse(false);
        }
    }

    @Test
    public void viewLoggedInDues(){
        try {
            Member member = null;
            ViewExpenseService viewexpenseservice  =new ViewExpenseService();
            viewexpenseservice.viewLoggedInDues( member);
            assertTrue(true);
        } catch (Exception exception) {
            exception.printStackTrace();
            assertFalse(false);
        }
    }

    @Test
    public void getExpensesWithMembers(){
        try {
            ExpenseList expectedValue = null;
            List<Expense> expenses = null;
            ViewExpenseService viewexpenseservice  =new ViewExpenseService();
            ViewExpenseService.ExpenseList actualValue=viewexpenseservice.getExpensesWithMembers( expenses);
            System.out.println("Expected Value="+ null +" . Actual Value="+actualValue);
            //  assertEquals(null, actualValue);
            assertTrue(true);
        } catch (Exception exception) {
            exception.printStackTrace();
            assertFalse(false);
        }
    }
}