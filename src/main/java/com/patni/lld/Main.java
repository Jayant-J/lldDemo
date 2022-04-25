package com.patni.lld;

import com.patni.lld.client.ConsoleClient;
import com.patni.lld.interaction.CommandHandler;
import com.patni.lld.model.House;
import com.patni.lld.service.ExpenseManagementService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        House house = new House();
        ExpenseManagementService expenseManagementService = new ExpenseManagementService(house);
        CommandHandler commandHandler = CommandHandler.init(expenseManagementService);
        try {
            ConsoleClient consoleClient = new ConsoleClient(new BufferedReader(new InputStreamReader(System.in)), commandHandler);
            consoleClient.handleInput();
        } catch (IOException ex) {
            System.out.println("Something went wrong. Please try again!");
        }
    }
}
