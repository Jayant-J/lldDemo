package com.patni.lld.interaction.impl;

import com.patni.lld.exception.HouseFullException;
import com.patni.lld.exception.InvalidParameterException;
import com.patni.lld.exception.PersonAlreadyPresentException;
import com.patni.lld.interaction.Command;
import com.patni.lld.service.ExpenseManagementService;

import static com.patni.lld.utils.Constants.MOVE_IN_COMMAND_SYNTAX;

public class MoveInCommand implements Command {
    private ExpenseManagementService expenseManagementService;

    public MoveInCommand(ExpenseManagementService expenseManagementService) {
        this.expenseManagementService = expenseManagementService;
    }

    @Override
    public String helpText() {
        return MOVE_IN_COMMAND_SYNTAX;
    }

    @Override
    public void execute(String[] params) throws InvalidParameterException {
        if (params.length < 1) {
            throw new InvalidParameterException(MOVE_IN_COMMAND_SYNTAX);
        }
        try {
            this.expenseManagementService.personMoveIn(params[0]);
        } catch (PersonAlreadyPresentException | HouseFullException e) {
            System.out.println(e.getMessage());
        }
    }
}
