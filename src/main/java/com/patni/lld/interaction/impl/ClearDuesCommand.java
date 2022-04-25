package com.patni.lld.interaction.impl;

import com.patni.lld.exception.InvalidParameterException;
import com.patni.lld.exception.PersonNotPresentException;
import com.patni.lld.interaction.Command;
import com.patni.lld.service.ExpenseManagementService;

import static com.patni.lld.utils.Constants.CLEAR_DUES_COMMAND_SYNTAX;

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
