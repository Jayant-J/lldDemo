package com.patni.lld.client;

import com.patni.lld.exception.CommandNotFoundException;
import com.patni.lld.exception.InvalidParameterException;
import com.patni.lld.interaction.CommandHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class ConsoleClient {

    private BufferedReader reader;
    private CommandHandler commandHandler;

    public ConsoleClient(BufferedReader reader, CommandHandler commandHandler) {
        this.reader = reader;
        this.commandHandler = commandHandler;
    }

    public void handleInput() throws IOException {
        try {
            while (true) {
                String inputLine = this.reader.readLine();
                if (inputLine == null) {
                    break;
                }

                inputLine = inputLine.trim();
                if (inputLine.isEmpty()) {
                    continue;
                }

                if (inputLine.equalsIgnoreCase("exit")) {
                    break;
                }

                processInputLine(inputLine);
            }
        } finally {
            reader.close();
        }
    }

    private void processInputLine(String inputLine) {
        String[] inputChunks = inputLine.split(" ");

        String command = inputChunks[0];
        String[] params = Arrays.copyOfRange(inputChunks, 1, inputChunks.length);

        try {
            commandHandler.executeCommand(command, params);
        } catch (CommandNotFoundException | InvalidParameterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

}
