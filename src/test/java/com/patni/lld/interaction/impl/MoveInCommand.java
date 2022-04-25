package com.example.geektrust.interaction.impl;

import com.example.geektrust.exception.HouseFullException;
import com.example.geektrust.exception.InvalidParameterException;
import com.example.geektrust.exception.PersonAlreadyPresentException;
import com.example.geektrust.interaction.Command;
import com.example.geektrust.service.ExpenseManagementService;

import static com.example.geektrust.utils.Constants.MOVE_IN_COMMAND_SYNTAX;

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
