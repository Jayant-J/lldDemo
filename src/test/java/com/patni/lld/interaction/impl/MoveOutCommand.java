package com.example.geektrust.interaction.impl;

import com.example.geektrust.exception.InvalidParameterException;
import com.example.geektrust.exception.PersonNotPresentException;
import com.example.geektrust.interaction.Command;
import com.example.geektrust.service.ExpenseManagementService;

import static com.example.geektrust.utils.Constants.*;

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
