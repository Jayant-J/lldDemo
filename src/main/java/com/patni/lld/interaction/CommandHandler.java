package com.patni.lld.interaction;

import com.patni.lld.exception.CommandNotFoundException;
import com.patni.lld.exception.InvalidParameterException;
import com.patni.lld.interaction.impl.*;
import com.patni.lld.service.ExpenseManagementService;

import java.util.HashMap;
import java.util.Map;

import static com.patni.lld.utils.Constants.*;

public class CommandHandler {

    private Map<String, Command> commands;

    public CommandHandler(Map<String, Command> commands) {
        this.commands = commands;
    }

    public static CommandHandler init(ExpenseManagementService expenseManagementService) {
        CommandHandler commandHandler = new CommandHandler(new HashMap<>());
        commandHandler.addCommand(MOVE_IN_COMMAND, new MoveInCommand(expenseManagementService));
        commandHandler.addCommand(MOVE_OUT_COMMAND, new MoveOutCommand(expenseManagementService));
        commandHandler.addCommand(SPEND_COMMAND, new SpendCommand(expenseManagementService));
        commandHandler.addCommand(DUES_COMMAND, new DuesCommand(expenseManagementService));
        commandHandler.addCommand(CLEAR_DUES_COMMAND, new ClearDuesCommand(expenseManagementService));

        return commandHandler;
    }

    public void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    public void executeCommand(String name, String[] params) throws CommandNotFoundException, InvalidParameterException {
        if (commands.containsKey(name)) {
            commands.get(name).execute(params);
        } else {
            throw new CommandNotFoundException(name);
        }
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
