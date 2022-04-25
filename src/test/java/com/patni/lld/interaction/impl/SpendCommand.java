package com.example.geektrust.interaction.impl;

import com.example.geektrust.exception.InvalidParameterException;
import com.example.geektrust.exception.PersonNotPresentException;
import com.example.geektrust.interaction.Command;
import com.example.geektrust.service.ExpenseManagementService;

import java.util.Arrays;

import static com.example.geektrust.utils.Constants.SPEND_COMMAND_SYNTAX;

public class SpendCommand implements Command {
    private ExpenseManagementService expenseManagementService;

    public SpendCommand(ExpenseManagementService expenseManagementService) {
        this.expenseManagementService = expenseManagementService;
    }

    @Override
    public String helpText() {
        return SPEND_COMMAND_SYNTAX;
    }

    @Override
    public void execute(String[] params) throws InvalidParameterException {
        if (params.length < 3) {
            throw new InvalidParameterException(SPEND_COMMAND_SYNTAX);
        }
        try {
            int amount = Integer.parseInt(params[0]);
            String byPerson = params[1];
            String[] forPersons = Arrays.copyOfRange(params, 2, params.length);
            expenseManagementService.spentLogic(amount, byPerson, forPersons);
        } catch (NumberFormatException | PersonNotPresentException e) {
            System.out.println(e.getMessage());
        }
    }
}
