package com.patni.lld.interaction.impl;

import com.patni.lld.exception.InvalidParameterException;
import com.patni.lld.exception.PersonNotPresentException;
import com.patni.lld.interaction.Command;
import com.patni.lld.service.ExpenseManagementService;

import static com.patni.lld.utils.Constants.DUES_COMMAND_SYNTAX;

public class DuesCommand implements Command {
    private ExpenseManagementService expenseManagementService;

    public DuesCommand(ExpenseManagementService expenseManagementService) {
        this.expenseManagementService = expenseManagementService;
    }

    @Override
    public String helpText() {
        return DUES_COMMAND_SYNTAX;
    }

    @Override
    public void execute(String[] params) throws InvalidParameterException {
        if (params.length != 1) {
            throw new InvalidParameterException(DUES_COMMAND_SYNTAX);
        }
        try {
            expenseManagementService.getDues(params[0]);
        } catch (PersonNotPresentException e) {
            System.out.println(e.getMessage());
        }
    }
}

