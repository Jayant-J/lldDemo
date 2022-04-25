package com.patni.lld.interaction;

import com.patni.lld.model.House;
import com.patni.lld.service.ExpenseManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.patni.lld.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandHandlerTest {

    CommandHandler commandHandler;
    ExpenseManagementService expenseManagementService;
    House house;

    @BeforeEach
    void setUp() {
        house = new House();
        expenseManagementService = new ExpenseManagementService(house);
        commandHandler = CommandHandler.init(expenseManagementService);

    }

    @Test
    void init_shouldInitializeAllCommands() {
        assertTrue(commandHandler.getCommands().keySet().contains(MOVE_IN_COMMAND));
        assertTrue(commandHandler.getCommands().keySet().contains(MOVE_OUT_COMMAND));
        assertTrue(commandHandler.getCommands().keySet().contains(SPEND_COMMAND));
        assertTrue(commandHandler.getCommands().keySet().contains(DUES_COMMAND));
        assertTrue(commandHandler.getCommands().keySet().contains(CLEAR_DUES_COMMAND));
    }
}