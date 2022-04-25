package com.example.geektrust.interaction.impl;

import com.example.geektrust.exception.InvalidParameterException;
import com.example.geektrust.exception.PersonNotPresentException;
import com.example.geektrust.interaction.Command;
import com.example.geektrust.service.ExpenseManagementService;

import static com.example.geektrust.utils.Constants.CLEAR_DUES_COMMAND_SYNTAX;

public class ClearDuesCommand implements Command {
    private ExpenseManagementService expenseManagementService;

    public ClearDuesCommand(ExpenseManagementService expenseManagementService) {
        this.expenseManagementService = expenseManagementService;
    }

    @Override
    public String helpText() {
        return CLEAR_DUES_COMMAND_SYNTAX;
    }

    @Override
    public void execute(String[] params) throws InvalidParameterException {
        if (params.length != 3) {
            throw new InvalidParameterException(CLEAR_DUES_COMMAND_SYNTAX);
        }
        try {
            Integer amount = Integer.valueOf(params[2]);
            expenseManagementService.clearDues(params[0], params[1], amount);
        } catch (NumberFormatException | PersonNotPresentException e) {
            System.out.println(e.getMessage());
        }
    }
}
