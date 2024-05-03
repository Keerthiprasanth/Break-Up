package org.breakup.Service;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class SettleExpenseServiceTest {

    @org.junit.jupiter.api.Test
    void settleup() {
        Double owed = 20.0;
        Double pay = 10.0;
        Double output = SettleExpenseService.settleup(owed, pay);
        Assertions.assertTrue(output == 10.0);
    }

    @org.junit.jupiter.api.Test
    void balance() {
        Double owed = 30.0;
        Double payable = 10.0;
        Double output = SettleExpenseService.balance(owed, payable);
        Assertions.assertTrue(output == 20.0);
    }
}