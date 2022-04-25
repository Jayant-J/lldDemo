package com.patni.lld.interaction.impl;

import com.patni.lld.exception.InvalidParameterException;
import com.patni.lld.exception.PersonNotPresentException;
import com.patni.lld.interaction.Command;
import com.patni.lld.service.ExpenseManagementService;

import static com.patni.lld.utils.Constants.*;

public class MoveOutCommand implements Command {
    private ExpenseManagementService expenseManagementService;

    public MoveOutCommand(ExpenseManagementService expenseManagementService) {
        this.expenseManagementService = expenseManagementService;
    }

    @Override
    public String helpText() {
        return MOVE_OUT_COMMAND_SYNTAX;
    }

    @Override
    public void execute(String[] params) throws InvalidParameterException {
        if (params.length < 1) {
            throw new InvalidParameterException(MOVE_IN_COMMAND_SYNTAX);
        }
        try {
            this.expenseManagementService.personMoveOut(params[0]);
        } catch (PersonNotPresentException e) {
            System.out.println(MEMBER_NOT_FOUND);
        }
    }
}
