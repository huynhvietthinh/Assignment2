package Assignment2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class CustomSystem {

    protected Queue<String> inBoxQueue;
    protected Queue<String> outBoxQueue;
    protected Stack<String> processingStack;
    protected CustomSystem connectedSystem;

    public Queue<String> getOutBoxQueue() {
        return outBoxQueue;
    }

    public Queue<String> getInBoxQueue() {
        return inBoxQueue;
    }

    public Stack<String> getProcessingStack() {
        return processingStack;
    }

    private boolean connected = false;

    public CustomSystem() {
        inBoxQueue = new LinkedList<>();
        outBoxQueue = new LinkedList<>();
        processingStack = new Stack<>();
        connectedSystem= null;
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void connect(CustomSystem system) {
        String thisName = this.getName();
        String systemName = system.getName();

        if ((thisName.equalsIgnoreCase("systemA") && systemName.equalsIgnoreCase("systemB")) ||
                (thisName.equalsIgnoreCase("systemB") && systemName.equalsIgnoreCase("systemA"))) {

            this.connectedSystem = system;
            system.connectedSystem = this;

            System.out.println(this.getClass().getSimpleName() + " '" + thisName + "' is connecting to " +
                    system.getClass().getSimpleName() + " '" + systemName + "'");
            connected = true;
        }
    }

    public void sendMessage(String message) {
        try {
            if (connectedSystem == null) {
                throw new IllegalStateException("Error: Not connected to any system.");
            }

            if (connected) {
                if (message.isEmpty()) {
                    throw new IllegalArgumentException("Error: Message is empty.");
                }

                if (message.length() > 250) {
                    System.out.println("Error: Message is too long. This message will be split into 2 messages .");
                    int chunkSize = 250;
                    for (int i = 0; i < message.length(); i += chunkSize) {
                        int end = Math.min(message.length(), i + chunkSize);
                        String chunk = message.substring(i, end);
                        outBoxQueue.offer(chunk);
                    }
                } else {
                    outBoxQueue.offer(message);
                }
            } else {
                System.out.println("Error: Not connected to the any system.");
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public void readOutboxQueue() {
        System.out.println(" Messages sending from " + this.getName() + "':");
        if (getOutBoxQueue().isEmpty()) {
            System.out.println  ("No messages are waiting to be sent.");
        } else {
            for (String message : getOutBoxQueue()) {
                System.out.println(message);
            }
        }
    }

    public void readInboxQueue() {
        System.out.println(" Messages sent to " + this.getClass().getSimpleName() + " '" + this.getName() + "':");
        if (getInBoxQueue().isEmpty()) {
            System.out.println("No messages were sent to.");
        } else {
            for (String message : getInBoxQueue()) {
                System.out.println(message);
            }
        }
    }


    public void sendRequestToReceive() {
        if (!isConnected()) {
            System.out.println("Error: Connection not established.");
            return;
        }
        Queue<String> connectedOutBoxQueue = connectedSystem.getOutBoxQueue();

        if (connectedOutBoxQueue.isEmpty()) {
            System.out.println("System's outboxQueue is empty.");
        } else {
            while (!connectedOutBoxQueue.isEmpty()) {
                String message = connectedOutBoxQueue.poll();
                getInBoxQueue().offer(message);
            }
            System.out.println("Received messages from connected system.");
        }
    }

    public void receiveMessageFromSystem(CustomSystem connectedSystem) {
        if (connectedSystem == null) {
            System.out.println("Error: No connected system provided.");
            return;
        }

        if (connectedSystem.isConnected()) {
            connectedSystem.sendRequestToReceive();

            //System.out.println("Received messages from " + connectedSystem.getClass().getSimpleName());
        } else {
            System.out.println("Error: No Connection .");
        }
    }




    public void processMessages() {
        try {
            if (connectedSystem == null) {
                throw new IllegalStateException("Error: Connection not established.");
            }
            while (!getInBoxQueue().isEmpty()) {
                String message = getInBoxQueue().poll();
                getProcessingStack().push(message);
            }

            while (!getProcessingStack().isEmpty()) {
                String poppedMessage = getProcessingStack().pop();
                System.out.println("Message popped from processingStack: " + poppedMessage);
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }


    public void disconnect(CustomSystem systemToDisconnect) {
        if (this.connectedSystem != null && systemToDisconnect.connectedSystem != null) {
            if (this.connectedSystem == systemToDisconnect && systemToDisconnect.connectedSystem == this) {
                this.connectedSystem = null;
                systemToDisconnect.connectedSystem = null;

                System.out.println("Disconnected " + systemToDisconnect.getClass().getSimpleName() + " '" + systemToDisconnect.getName() +
                        "' from " + this.getClass().getSimpleName() + " '" + this.getName() + "'");
            } else {
                System.out.println("Invalid disconnection. Please choose the correct systems to disconnect.");
            }
        }
    }


    public boolean isConnected() {
        return connected;
    }
}