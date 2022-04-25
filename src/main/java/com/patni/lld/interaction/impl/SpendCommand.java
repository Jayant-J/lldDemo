package com.patni.lld.interaction.impl;

import com.patni.lld.exception.InvalidParameterException;
import com.patni.lld.exception.PersonNotPresentException;
import com.patni.lld.interaction.Command;
import com.patni.lld.service.ExpenseManagementService;

import java.util.Arrays;

import static com.patni.lld.utils.Constants.SPEND_COMMAND_SYNTAX;

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
