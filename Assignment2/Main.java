package Assignment2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        CustomSystem systemA = new CustomSystem();
        systemA.setName("systemA");
        CustomSystem systemB = new CustomSystem();
        systemB.setName("systemB");

        while (!exit) {
            System.out.println("\n Menu ");

            if (!systemA.isConnected() && !systemB.isConnected()) {
                System.out.println("1. Connect systems");
            }

            if (systemA.isConnected() && systemB.isConnected()) {
                System.out.println("2. Access SystemA");
                System.out.println("3. Access SystemB");
                System.out.println("11. Disconnect systems");
                System.out.println("13. Exit");
                System.out.print("Enter your choice: ");
            }

            long startTime = System.nanoTime();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (!systemA.isConnected() && !systemB.isConnected()) {
                        System.out.println("Connecting systems...");
                        systemA.connect(systemB);
                        systemB.connect(systemA);
                        System.out.println("Systems connected.");
                    } else {
                        System.out.println("Both systems are already connected.");
                    }
                    break;

                case 2:
                    handleSystemMenu(systemA, systemB, scanner);
                    break;

                case 3:
                    handleSystemMenu(systemB, systemA, scanner);
                    break;

                case 11:
                    if (systemA.isConnected() && systemB.isConnected()) {
                        systemA.disconnect(systemB);
                        systemB.disconnect(systemA);
                        System.out.println("Systems are disconnected.");
                    } else {
                        System.out.println("Systems are not connected.");
                    }
                    break;

                case 13:
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number from the menu.");
                    break;
            }

            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            System.out.println("Elapsed time for choice " + choice + ": " + elapsedTime + " nanoseconds");
        }
        scanner.close();
    }

    private static void handleSystemMenu(CustomSystem sendingSystem, CustomSystem receivingSystem, Scanner scanner) {
        do {
            System.out.println("\n Menu - " + sendingSystem.getName());
            System.out.println("1. Send message");
            System.out.println("2. Receive messages");
            System.out.println("3. Read outbox messages");
            System.out.println("4. Read incoming messages");
            System.out.println("5. Process messages");
            System.out.println("0. Back to main menu");
            System.out.print("Enter your choice: ");

            int systemChoice = scanner.nextInt();
            scanner.nextLine();

            long systemStartTime = System.nanoTime();

            switch (systemChoice) {
                case 1:
                    sendMessage(sendingSystem, receivingSystem, scanner);
                    break;

                case 2:
                    receiveMessages(sendingSystem, receivingSystem);
                    break;

                case 3:
                    readOutboxMessages(sendingSystem);
                    break;

                case 4:
                    readIncomingMessages(sendingSystem);
                    break;

                case 5:
                    processMessages(sendingSystem);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice. Please enter a number from the menu.");
                    break;
            }

            long systemEndTime = System.nanoTime();
            long systemElapsedTime = systemEndTime - systemStartTime;
            System.out.println("Elapsed time for choice " + systemChoice + ": " + systemElapsedTime + " nanoseconds");

        } while (true);
    }

    private static void sendMessage(CustomSystem sendingSystem, CustomSystem receivingSystem, Scanner scanner) {
        if (sendingSystem.isConnected()) {
            System.out.println("Sending a message from " + sendingSystem.getName() + " to " + receivingSystem.getName());
            System.out.print("Enter the message: ");
            String message = scanner.nextLine();

            if (receivingSystem.isConnected()) {
                sendingSystem.sendMessage(message);
                System.out.println("Message sent.");
            } else {
                System.out.println("Systems are not connected.");
            }
        } else {
            System.out.println(sendingSystem.getName() + " is not connected.");
        }
    }

    private static void receiveMessages(CustomSystem sendingSystem, CustomSystem receivingSystem) {
        if (receivingSystem.isConnected()) {
            System.out.println("Receiving messages from " + sendingSystem.getName());
            receivingSystem.receiveMessageFromSystem(sendingSystem);
        } else {
            System.out.println(receivingSystem.getName() + " is not connected to " + sendingSystem.getName() + ".");
        }
    }

    private static void readOutboxMessages(CustomSystem system) {
        if (system.isConnected()) {
            System.out.println("Reading outbox messages from " + system.getName());
            system.readOutboxQueue();
        } else {
            System.out.println(system.getName() + " is not connected.");
        }
    }

    private static void readIncomingMessages(CustomSystem system) {
        if (system.isConnected()) {
            System.out.println("Reading incoming messages from " + system.getName());
            system.readInboxQueue();
        } else {
            System.out.println(system.getName() + " is not connected.");
        }
    }

    private static void processMessages(CustomSystem system) {
        if (system.isConnected()) {
            System.out.println("Processing messages on " + system.getName());
            system.processMessages();
        } else {
            System.out.println(system.getName() + " is not connected.");
        }
    }
}
