package org.breakup.Service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteExpenseServiceTest {

        @Test
        void updateValues() {
            Double Balance = 40d;
            Double ExpenseAmount = 10d;
            Double output = DeleteExpenseService.updateValues(Balance,ExpenseAmount);
            assertTrue(output==30d);
        }

        @Test
        void balance() {
            Double owed = 50d;
            Double payable= 10d;
            Double output = DeleteExpenseService.balance(owed,payable);
            assertTrue(output==40d);
        }
    }

