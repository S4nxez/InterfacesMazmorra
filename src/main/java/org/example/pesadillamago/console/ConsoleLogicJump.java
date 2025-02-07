package org.example.pesadillamago.console;

import org.example.pesadillamago.game.character.Wizard;
import org.example.pesadillamago.game.dungeon.Door;
import org.example.pesadillamago.game.dungeon.Dungeon;
import org.example.pesadillamago.game.dungeon.Room;
import org.example.pesadillamago.game.dungeon.Site;
import org.example.pesadillamago.game.spell.Jump;

import java.util.List;
import java.util.Scanner;

public class ConsoleLogicJump {
    private final Wizard wizard;
    private final Dungeon dungeon;
    private final Scanner scanner;

    public ConsoleLogicJump(Wizard wizard, Dungeon dungeon) {
        this.wizard = wizard;
        this.dungeon = dungeon;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the Dungeon Game!");
        wizard.setCurrentLocation(dungeon.getRoom(0));// Start at the first room

        while (true) {
            displayCurrentState();
            String command = getUserInput();

            if (command.equalsIgnoreCase("quit")) {
                break;
            }

            processCommand(command);
        }

        System.out.println("Thanks for playing!");
    }

    private void displayCurrentState() {
        System.out.println("\nCurrent Location: " + wizard.getCurrentLocation().getDescription());
        System.out.println("Available doors:");
        List<Door> availableDoors = dungeon.getAvailableDoorsForLocation(wizard.getCurrentLocation());
        for (int i = 0; i < availableDoors.size(); i++) {
            System.out.println(i + 1 + ". " + availableDoors.get(i));
        }
    }

    private String getUserInput() {
        System.out.print("Enter your command (move <door number> / jump <room id> / quit): ");
        return scanner.nextLine().trim();
    }

    private void processCommand(String command) {
        String[] parts = command.split("\\s+");
        if (parts.length != 2) {
            System.out.println("Invalid command. Please try again.");
            return;
        }

        String action = parts[0].toLowerCase();
        String target = parts[1];

        switch (action) {
            case "move":
                moveWizard(target);
                break;
            case "jump":
                jumpWizard(target);
                break;
            default:
                System.out.println("Unknown command. Please try again.");
        }
    }

    private void moveWizard(String doorNumber) {
        try {
            int index = Integer.parseInt(doorNumber) - 1;
            List<Door> availableDoors = dungeon.getAvailableDoorsForLocation(wizard.getCurrentLocation());
            if (index >= 0 && index < availableDoors.size()) {
                Door selectedDoor = availableDoors.get(index);
                Site newLocation = selectedDoor.openFrom(wizard.getCurrentLocation());
                wizard.setCurrentLocation(newLocation);
                System.out.println("You moved to a new location.");
            } else {
                System.out.println("Invalid door number. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid door number. Please try again.");
        }
    }

    private void jumpWizard(String roomId) {
        Jump jumpSpell = new Jump(dungeon);
        int result = jumpSpell.cast(roomId, 0);
        if (result != -1) {
            Room targetRoom = dungeon.getRoom(result);
            wizard.setCurrentLocation(targetRoom);
            System.out.println("You jumped to room " + roomId);
        } else {
            System.out.println("Jump failed. Invalid room ID.");
        }
    }
}



